package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    List<ItemCarrito> findByCarrito_IdCarrito(Integer idCarrito);
}