package com.sena.ecommerce.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.ProductoService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	// instancia del LLOGER para ver datos por consola
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService productoService;

	// metodo pra llevar a la vista show de productos
	@GetMapping("")
	public String show(Model model) {
		// listar productos
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}

	// metodo que redirige a la vista de creacion de productos
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}

	// metodo para crear nuevos procutos
	@PostMapping("/save")
	public String save(Producto producto) {
		// Logger para saber lo que se va a guardar en la DB
		LOGGER.info("Este es el objeto del producto a guardar en la DB {}", producto);
		// usuario que registra desde la vista
		Usuario u = new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		productoService.save(producto);
		return "redirect:/productos";
	}

	// metodo para llenar la vista edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		// nos devuelve la busqueda de un objeto de tipo producto
		Optional<Producto> optionalproducto = productoService.get(id);
		// trae el producto que hemos mandado a buscar
		producto = optionalproducto.get();
		// logger para saber si es el producto a buscar
		LOGGER.info("Busqueda de producto por id: {}", producto);
		model.addAttribute("producto", producto);
		return "productos/edit";
	}

	// metodo para actualizar productos
	@PostMapping("/update")
	public String update(Producto producto) {
		// Logger para saber lo que se va a actualizar en la DB
		LOGGER.info("Este es el objeto del producto a actualizar en la DB {}", producto);
		// usuario que registra desde la vista
		Usuario u = new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		productoService.update(producto);
		return "redirect:/productos";
	}

}
