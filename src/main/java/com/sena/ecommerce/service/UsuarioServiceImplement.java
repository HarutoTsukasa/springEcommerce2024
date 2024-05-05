package com.sena.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	// objeto de tipo private que va a ser del tipo repository
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> get(Integer id) {
		return Optional.empty();
	}

	@Override
	public void update(Usuario usuario) {
		//
	}

	@Override
	public void delete(Integer id) {
		//
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

}
