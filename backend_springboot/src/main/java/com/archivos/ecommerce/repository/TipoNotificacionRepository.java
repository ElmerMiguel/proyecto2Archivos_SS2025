package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Integer> {
    Optional<TipoNotificacion> findByNombreTipo(TipoNotificacion.NombreTipo nombreTipo);
}