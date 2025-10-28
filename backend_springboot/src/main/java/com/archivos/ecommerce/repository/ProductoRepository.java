package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();

    List<Producto> findByVendedor_IdUsuario(Integer idVendedor);

    List<Producto> findByEstadoProducto_IdEstadoProducto(Integer idEstado);

    @Query(value = """
        SELECT u.nombre_completo, COUNT(p.id_producto) as total_productos
        FROM productos p
        JOIN usuario u ON p.id_vendedor = u.id_usuario
        WHERE p.activo = true
        GROUP BY u.id_usuario, u.nombre_completo
        ORDER BY total_productos DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Map<String, Object>> findTop10ClientesMasProductosEnVenta();

    @Query(value = """
        SELECT c.nombre_categoria, COUNT(p.id_producto) as total_productos
        FROM productos p
        JOIN categoria c ON p.id_categoria = c.id_categoria
        WHERE p.activo = true
        GROUP BY c.id_categoria, c.nombre_categoria
        ORDER BY total_productos DESC
        """, nativeQuery = true)
    List<Map<String, Object>> getProductosPorCategoria();
}
