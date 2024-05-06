package com.sena.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.service.IOrdenService;
//import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IProductoService;
import com.sena.ecommerce.service.IUsuarioService;

// decirle que es un controller
@Controller
// decirle que mapee a admon
@RequestMapping("/administrador")
public class AdministradorController {

	// variable de productoService
	@Autowired
	private IProductoService productoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IOrdenService ordenservice;

	@GetMapping("")
	public String home(Model model) {

		List<Producto> produtos = productoService.findAll();
		model.addAttribute("productos", produtos);

		return "administrador/home";
	}

	// metodo usuarios
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
//		List<Usuario> usuarios = usuarioService.findAll();
//		model.addAttribute("usuarios", usuarios);
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}

	// metodo ordenes
	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes", ordenservice.findAll());
		return "administrador/ordenes";
	}

}
