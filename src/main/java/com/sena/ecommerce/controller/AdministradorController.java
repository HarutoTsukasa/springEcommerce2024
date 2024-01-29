package com.sena.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //decirle que es un controller
@RequestMapping("/administrador") //decirle que mapee a admon
public class AdministradorController {
	
	
	@GetMapping("")
	public String home() {
		return "administrador/home";
	}

}
