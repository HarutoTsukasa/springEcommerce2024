package com.sena.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.ecommerce.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	// metodo para validar email
	Optional<Usuario> findByEmail(String email);
	// metodo para validar username
	Optional<Usuario> findByUsername(String username);

}
