package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.CarritoDTO;
import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.entity.Carrito;
import com.archivos.ecommerce.entity.ItemCarrito;
import com.archivos.ecommerce.repository.CarritoRepository;
import com.archivos.ecommerce.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    public Optional<CarritoDTO> obtenerCarritoPorUsuario(Integer idUsuario) {
        return carritoRepository.findByUsuario_IdUsuario(idUsuario)
            .map(this::convertirADTO);
    }

    public List<ItemCarritoDTO> obtenerItemsDelCarrito(Integer idCarrito) {
        return itemCarritoRepository.findByCarrito_IdCarrito(idCarrito)
            .stream()
            .map(this::convertirItemADTO)
            .collect(Collectors.toList());
    }

    // Método para convertir Carrito Entity a DTO
    private CarritoDTO convertirADTO(Carrito carrito) {
        List<ItemCarritoDTO> items = obtenerItemsDelCarrito(carrito.getIdCarrito());
        
        return new CarritoDTO(
            carrito.getIdCarrito(),
            carrito.getUsuario().getIdUsuario(),
            carrito.getUsuario().getNombreCompleto(),
            carrito.getFechaCreacion(),
            carrito.getFechaUltimaActualizacion(),
            items
        );
    }

    // Método para convertir ItemCarrito Entity a DTO
    private ItemCarritoDTO convertirItemADTO(ItemCarrito item) {
        BigDecimal subtotal = item.getProducto().getPrecio()
            .multiply(BigDecimal.valueOf(item.getCantidad()));

        return new ItemCarritoDTO(
            item.getIdItemCarrito(),
            item.getCantidad(),
            item.getFechaAgregado(),
            item.getProducto().getIdProducto(),
            item.getProducto().getNombreProducto(),
            item.getProducto().getPrecio(),
            subtotal
        );
    }
}