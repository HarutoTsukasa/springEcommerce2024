package com.sena.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.sena.ecommerce.model.Producto;

//aqui definimos todos los metodos CRUD para la tabla producto

public interface ProductoService {
	
	public Producto save(Producto producto);//metodo de tipo publico que retorna un producto recibe un objeto de tipo producto
	public Optional<Producto> get(Integer id);//valida si el objeto que llamamos de la DB existe o no
	public void update(Producto producto);// actualiza el producto
	public void delete(Integer id); //elimina el producto con el id
	public List<Producto> findAll();//metodo para listar productos
}
