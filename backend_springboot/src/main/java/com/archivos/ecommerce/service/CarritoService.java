package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.CarritoDTO;
import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.entity.Carrito;
import com.archivos.ecommerce.entity.ItemCarrito;
import com.archivos.ecommerce.entity.Producto;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.repository.CarritoRepository;
import com.archivos.ecommerce.repository.ItemCarritoRepository;
import com.archivos.ecommerce.repository.ProductoRepository;
import com.archivos.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener carrito por usuario
    public Optional<CarritoDTO> obtenerCarritoPorUsuario(Integer idUsuario) {
        return carritoRepository.findByUsuario_IdUsuario(idUsuario)
                .map(this::convertirADTO);
    }

    // Obtener ítems del carrito
    public List<ItemCarritoDTO> obtenerItemsDelCarrito(Integer idCarrito) {
        return itemCarritoRepository.findByCarrito_IdCarrito(idCarrito)
                .stream()
                .map(this::convertirItemADTO)
                .collect(Collectors.toList());
    }

    // Agregar producto al carrito
    public ItemCarritoDTO agregarProductoAlCarrito(Integer idUsuario, Integer idProducto, Integer cantidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    nuevoCarrito.setFechaCreacion(LocalDateTime.now());
                    nuevoCarrito.setFechaUltimaActualizacion(LocalDateTime.now());
                    return carritoRepository.save(nuevoCarrito);
                });

        Optional<ItemCarrito> itemExistente = itemCarritoRepository
                .findByCarrito_IdCarritoAndProducto_IdProducto(carrito.getIdCarrito(), idProducto);

        ItemCarrito item;
        if (itemExistente.isPresent()) {
            // Actualizar cantidad
            item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            // Crear nuevo item
            item = new ItemCarrito();
            item.setCarrito(carrito);
            item.setProducto(producto);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.getPrecio()); 
            item.setFechaAgregado(LocalDateTime.now());
        }

        ItemCarrito itemGuardado = itemCarritoRepository.save(item);

        carrito.setFechaUltimaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);

        return convertirItemADTO(itemGuardado);
    }

    // Actualizar cantidad de un item
    public ItemCarritoDTO actualizarCantidadItem(Integer idItem, Integer nuevaCantidad) {
        ItemCarrito item = itemCarritoRepository.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setCantidad(nuevaCantidad);
        ItemCarrito itemActualizado = itemCarritoRepository.save(item);

        Carrito carrito = item.getCarrito();
        carrito.setFechaUltimaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);

        return convertirItemADTO(itemActualizado);
    }

    // Eliminar ítem del carrito
    public void eliminarItemDelCarrito(Integer idItem) {
        ItemCarrito item = itemCarritoRepository.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Carrito carrito = item.getCarrito();
        itemCarritoRepository.delete(item);

        carrito.setFechaUltimaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    // Convertir Carrito a DTO
    private CarritoDTO convertirADTO(Carrito carrito) {
        List<ItemCarritoDTO> items = obtenerItemsDelCarrito(carrito.getIdCarrito());

        return new CarritoDTO(
                carrito.getIdCarrito(),
                carrito.getUsuario().getIdUsuario(),
                carrito.getUsuario().getNombreCompleto(),
                carrito.getFechaCreacion(),
                carrito.getFechaUltimaActualizacion(),
                items);
    }

    // Convertir ItemCarrito a DTO
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
                subtotal);
    }
}
