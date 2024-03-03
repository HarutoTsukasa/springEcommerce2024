package com.sena.ecommerce.service;

import java.util.List;

import com.sena.ecommerce.model.Orden;

public interface IOrdenService {
	// un metodo de momento
	public Orden save(Orden orden);

	// lista de ordenes
	public List<Orden> findAll();

	// metedo para los numeros de ordenes incrmentales
	public String generarNumeroOrden();
}
