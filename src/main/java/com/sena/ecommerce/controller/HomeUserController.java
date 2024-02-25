package com.sena.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.sena.ecommerce.service.ProductoService;

import ch.qos.logback.classic.Logger;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/") // mapeamos a la raiz del proyecto
public class HomeUserController {

	// instancia del LLOGER para ver datos por consola
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(HomeUserController.class);

	// creamos un objeto privado con anotación autowired
	@Autowired
	private ProductoService productoService;

	// crar dos variables
	// lista de detalles de la orden para almacenarlos
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// objeto que almacena los datos de la orden
	Orden orden = new Orden();

	// metodo que mapea la vista de usuario en la raiz dle proyecto
	@GetMapping("")
	public String home(Model model) {
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
		
		// detalles		
		detalles.add(detalleOrden);

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

}
