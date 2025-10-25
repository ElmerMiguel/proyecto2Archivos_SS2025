package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
}