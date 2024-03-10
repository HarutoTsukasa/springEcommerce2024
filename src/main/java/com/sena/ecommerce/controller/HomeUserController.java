package com.sena.ecommerce.controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.ecommerce.model.DetalleOrden;
import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IDetalleOrdenService;
import com.sena.ecommerce.service.IOrdenService;
import com.sena.ecommerce.service.IProductoService;
import com.sena.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/") // mapeamos a la raiz del proyecto
public class HomeUserController {

	// instancia del LLOGER para ver datos por consola
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(HomeUserController.class);

	// creamos un objeto privado con anotación autowired
	@Autowired
	private IProductoService productoService;

	// creamos un objeto privado de usuario service con anotacion autowired
	@Autowired
	private IUsuarioService usuarioservice;

	// creamos un objeto privado de orden service con su anotacion autowired
	@Autowired
	private IOrdenService ordenService;

	// creamos un objeto privado de detalleOrden service con su anotacion autowired
	@Autowired
	private IDetalleOrdenService detalleOrdenService;

	// crear dos variables
	// lista de detalles de la orden para almacenarlos
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// objeto que almacena los datos de la orden
	Orden orden = new Orden();

	// metodo que mapea la vista de usuario en la raiz del proyecto
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		LOGGER.info("sesion usuario: {}", session.getAttribute("idUsuario"));
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}

	// metodo que carga el producto de usuario con el id
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		LOGGER.info("Id producto enviado como parametro {}", id);
		// variable de la clase prodcuto
		Producto producto = new Producto();
		// objeto de tipo optional
		Optional<Producto> productoOptional = productoService.get(id);
		// pasar el producto
		producto = productoOptional.get();
		// enviamos con model a la vista los detalles del producto con el id
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}

	// metodo para enviar del boton de productohome a carrito
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		// variable de tipo double que siempre que entre en el metodo se inicializa en
		// cero despues de cada compra
		double sumaTotal = 0;
		// variable de tipo optional para buscar el porducto
		Optional<Producto> productoOptional = productoService.get(id);
		LOGGER.info("Producto añadido: {}", productoOptional.get());
		LOGGER.info("Cantidad añadida: {}", cantidad);
		// poner lo que esta en el optional
		producto = productoOptional.get();
		// poner en detalle orden en cada campo
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto);

		// validacion para que un producto no se duplique o añada dos veces
		Integer idProducto = producto.getId();
		// funcion lamda stream y una funcion anonima con predicado de anyMatch
		// retorna un true o false
		boolean insertado = detalles.stream().anyMatch(prod -> prod.getProducto().getId() == idProducto);
		// si no es true añade el producto
		if (!insertado) {
			// detalles
			detalles.add(detalleOrden);
		}

		// suma de los totales de la lista que el usuario añada al carrito
		// funcion lamda stream
		// funcion anonima dt
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		// pasar variables a la vista
		orden.setTotal(sumaTotal);

		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}

	// metodo para quitar productos del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		// lista nueva de productos
		List<DetalleOrden> ordenesNuevas = new ArrayList<>();
		// quitar un objeto de la lista de detalleOrden
		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != id) {
				ordenesNuevas.add(detalleOrden);
			}
		}
		// poner la nueva lista con los productos restantes del carrito
		detalles = ordenesNuevas;
		// recalcular los productos
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		// pasar variables a la vista
		orden.setTotal(sumaTotal);

		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);

		return "usuario/carrito";
	}

	// metodo para redirigir al carrito sin productos
	@GetMapping("/getCart")
	public String getCart(Model model) {
		// detalles y ordenes son variables globales
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "/usuario/carrito";
	}

	// metodo para pasar a la vista de resumen orden
	@GetMapping("/order")
	public String order(Model model, HttpSession session) {

		// se crea un objeto de la clase usuario donde de momento
		// se envia el id de usuario quemado
		Usuario usuario = usuarioservice.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();

		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);

		return "usuario/resumenorden";
	}

	// metodo getmapping para el boton de generar orden en la vista
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		// usuario que se referencia en esa compra previamente logeado
		Usuario usuario = usuarioservice.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		orden.setUsuario(usuario);
		ordenService.save(orden);
		// guardar detalles de la orden
		for (DetalleOrden dt : detalles) {
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		// limpiar valores que no se añadan a lo orden recien guardad
		orden = new Orden();
		detalles.clear();
		return "redirect:/";
	}

	// metodo post para buscar productos en la vista del home de usuario
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		LOGGER.info("Nombre del producto: {}", nombre);
		List<Producto> productos = productoService.findAll().stream()
				.filter(p -> p.getNombre().toUpperCase().contains(nombre.toUpperCase())).collect(Collectors.toList());
		model.addAttribute("productos", productos);
		return "usuario/home";
	}

}
