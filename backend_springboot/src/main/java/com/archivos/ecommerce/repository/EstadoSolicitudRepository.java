package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Integer> {
}