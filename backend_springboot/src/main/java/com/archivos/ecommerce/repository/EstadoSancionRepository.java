package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.EstadoSancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoSancionRepository extends JpaRepository<EstadoSancion, Integer> {
    Optional<EstadoSancion> findByNombreEstado(String nombreEstado);
}