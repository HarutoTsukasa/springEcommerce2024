package com.sena.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.ecommerce.model.Producto;

//DAO de productos

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
