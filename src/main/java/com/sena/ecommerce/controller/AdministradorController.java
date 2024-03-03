package com.sena.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.service.IProductoService;

// decirle que es un controller
@Controller
// decirle que mapee a admon
@RequestMapping("/administrador")
public class AdministradorController {

	// variable de productoService
	@Autowired
	private IProductoService productoService;

	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> produtos = productoService.findAll();
		model.addAttribute("productos",produtos);
		
		return "administrador/home";
	}

}
