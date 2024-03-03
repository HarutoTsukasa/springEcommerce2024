package com.sena.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.ecommerce.model.Orden;
import com.sena.ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImplement implements IOrdenService {

	// declarar objeto de tipo private que se va a usar para los metodos crud
	@Autowired
	private IOrdenRepository ordenRepository;

	@Override
	public Orden save(Orden orden) {
		return ordenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		return ordenRepository.findAll();
	}

	// crear un metodo para generar el no. secuencial de la orden
	@Override
	public String generarNumeroOrden() {
		// en el se incrementa el numero de la orden para luego pasarlo a string
		int numero = 0;
		// nos devuelve el string con el numero secuencial de la orden
		String numeroConcatenado = "";
		// lista de ordenes
		List<Orden> ordenes = findAll();
		// lista de enteror para el incrmento
		List<Integer> numeros = new ArrayList<Integer>();
		// usamos funciones de java 8
		// usamos variable anonima o
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		// validacion
		if (ordenes.isEmpty()) {
			numero = 1;
		} else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}

		if (numero < 10) {
			numeroConcatenado = "000000000000" + String.valueOf(numero);
		} else if (numero < 100) {
			numeroConcatenado = "00000000000" + String.valueOf(numero);
		} else if (numero < 1000) {
			numeroConcatenado = "0000000000" + String.valueOf(numero);
		} else if (numero < 10000) {
			numeroConcatenado = "000000000" + String.valueOf(numero);
		} else if (numero < 100000) {
			numeroConcatenado = "00000000" + String.valueOf(numero);
		}

		return numeroConcatenado;
	}
}
