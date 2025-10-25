package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuario_IdUsuario(Integer idUsuario);
}