package com.sena.ecommerce.controller;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IUsuarioService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/registro")
	public String createUser() {
		return "usuario/registro";
	}

	@PostMapping("/save")
	public String save(Usuario usuario, Model model) {
		LOGGER.info("Usuario a registrar: {}", usuario);
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	// metodo de autenticacion 1 con postmapping sin spring security
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		LOGGER.info("Accesos: {}", usuario);
		// acceder a la db para validar los datos
		Optional<Usuario> userEmail = usuarioService.findByEmail(usuario.getEmail());
		// logger para validar solo los usuarios que existen en la db si no existe habra
		// error con este logger
		// LOGGER.info("usuario db obtenido: {}", userEmail.get());
		// momentaneo sin spring security
		if (userEmail.isPresent()) {
			// id de el usuario con la sesion logeada
			session.setAttribute("idUsuario", userEmail.get().getId());
			// validacion tipo usuario
			if (userEmail.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			} else {
				return "redirect:/";
			}
		} else {
			LOGGER.info("Usuario no existe en DB");
		}
		return "redirect:/";
	}

}
