package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.SolicitudProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudProductoRepository extends JpaRepository<SolicitudProducto, Integer> {
    List<SolicitudProducto> findByVendedor_IdUsuario(Integer idVendedor);
    List<SolicitudProducto> findByEstadoSolicitud_IdEstadoSolicitud(Integer idEstado);
}