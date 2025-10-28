package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByComprador_IdUsuario(Integer idComprador);

    @Query(value = """
        SELECT p.nombre_producto, SUM(dp.cantidad) as total_vendido
        FROM detalle_pedido dp
        JOIN productos p ON dp.id_producto = p.id_producto
        JOIN pedidos pe ON dp.id_pedido = pe.id_pedido
        WHERE pe.fecha_pedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY p.id_producto, p.nombre_producto
        ORDER BY total_vendido DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Map<String, Object>> findTop10ProductosMasVendidos(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                            @Param("fechaFin") LocalDateTime fechaFin);

    @Query(value = """
        SELECT u.nombre_completo, SUM(dp.ganancia_vendedor) as total_ganancias
        FROM detalle_pedido dp
        JOIN usuarios u ON dp.id_vendedor = u.id_usuario
        JOIN pedidos pe ON dp.id_pedido = pe.id_pedido
        WHERE pe.fecha_pedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY u.id_usuario, u.nombre_completo
        ORDER BY total_ganancias DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Map<String, Object>> findTop5ClientesMasGanancias(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                           @Param("fechaFin") LocalDateTime fechaFin);

    @Query(value = """
        SELECT u.nombre_completo, SUM(dp.cantidad) as total_productos_vendidos
        FROM detalle_pedido dp
        JOIN usuarios u ON dp.id_vendedor = u.id_usuario
        JOIN pedidos pe ON dp.id_pedido = pe.id_pedido
        WHERE pe.fecha_pedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY u.id_usuario, u.nombre_completo
        ORDER BY total_productos_vendidos DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Map<String, Object>> findTop5ClientesMasProductosVendidos(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                                   @Param("fechaFin") LocalDateTime fechaFin);

    @Query(value = """
        SELECT u.nombre_completo, COUNT(p.id_pedido) as total_pedidos
        FROM pedidos p
        JOIN usuarios u ON p.id_comprador = u.id_usuario
        WHERE p.fecha_pedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY u.id_usuario, u.nombre_completo
        ORDER BY total_pedidos DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Map<String, Object>> findTop10ClientesMasPedidos(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                          @Param("fechaFin") LocalDateTime fechaFin);

    @Query(value = """
        SELECT COALESCE(SUM(total), 0) as total_ventas
        FROM pedidos
        WHERE DATE(fecha_pedido) = CURRENT_DATE
        """, nativeQuery = true)
    BigDecimal getTotalVentasHoy();

    Long countByEstadoPedido_IdEstadoPedido(Integer idEstado);

    @Query(value = """
        SELECT EXTRACT(MONTH FROM fecha_pedido) as mes, SUM(total) as total_ventas
        FROM pedidos
        WHERE EXTRACT(YEAR FROM fecha_pedido) = :year
        GROUP BY EXTRACT(MONTH FROM fecha_pedido)
        ORDER BY mes
        """, nativeQuery = true)
    List<Map<String, Object>> getVentasMensualesPorAno(@Param("year") int year);

     List<Pedido> findByEstadoPedido_IdEstadoPedido(Integer idEstado);
}