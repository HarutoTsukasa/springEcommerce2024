package com.sena.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.model.Usuario;

public interface IOrdenService {
	// un metodo de momento
	public Orden save(Orden orden);

	// lista de ordenes
	public List<Orden> findAll();

	// metedo para los numeros de ordenes incrmentales
	public String generarNumeroOrden();
	
	// metodo findbyuserid
	public List<Orden> findByUsuario(Usuario usuario);
	
	//metodo busqueda por id
	public Optional<Orden> findById(Integer id);
}
