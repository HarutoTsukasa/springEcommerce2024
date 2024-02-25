package com.sena.ecommerce.controller;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.service.ProductoService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/") // mapeamos a la raiz del proyecto
public class HomeUserController {

	// instancia del LLOGER para ver datos por consola
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(HomeUserController.class);

	// creamos un objeto privado con anotación autowired
	@Autowired
	private ProductoService productoService;

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

}
