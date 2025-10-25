package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoProductoRepository extends JpaRepository<EstadoProducto, Integer> {
}