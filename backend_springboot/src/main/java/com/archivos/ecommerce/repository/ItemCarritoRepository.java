package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {

    // Obtener todos los items de un carrito
    List<ItemCarrito> findByCarrito_IdCarrito(Integer idCarrito);

    // Buscar un item específico por carrito y producto
    Optional<ItemCarrito> findByCarrito_IdCarritoAndProducto_IdProducto(Integer idCarrito, Integer idProducto);

    void deleteByCarrito_IdCarrito(Integer idCarrito);

}
