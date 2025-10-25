package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Integer> {
    List<TarjetaCredito> findByUsuario_IdUsuario(Integer idUsuario);
}