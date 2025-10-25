package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByProducto_IdProducto(Integer idProducto);
}