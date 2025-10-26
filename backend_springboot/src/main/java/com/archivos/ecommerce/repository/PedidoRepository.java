package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByComprador_IdUsuario(Integer idComprador);    
}