package com.sena.ecommerce.service;

import java.util.Optional;

import com.sena.ecommerce.model.Usuario;

public interface IUsuarioService {
	// definir metodos
	public Usuario save(Usuario usuario);

	public Optional<Usuario> get(Integer id);

	public void update(Usuario usuario);

	public void delete(Integer id);

	Optional<Usuario> findById(Integer id);

}
