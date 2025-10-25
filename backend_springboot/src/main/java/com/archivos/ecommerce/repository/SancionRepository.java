package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Integer> {
    List<Sancion> findByUsuarioSancionado_IdUsuario(Integer idUsuario);
}