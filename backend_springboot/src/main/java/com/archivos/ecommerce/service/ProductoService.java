package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.ProductoDTO;
import com.archivos.ecommerce.entity.Producto;
import com.archivos.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
            .map(this::convertirADTO);
    }

    public List<ProductoDTO> obtenerProductosPorVendedor(Integer idVendedor) {
        return productoRepository.findByVendedor_IdUsuario(idVendedor)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarTodosLosProductos() {
        return productoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Método para convertir Entity a DTO
    private ProductoDTO convertirADTO(Producto producto) {
    return new ProductoDTO(
        producto.getIdProducto(),
        producto.getNombreProducto(),
        producto.getDescripcion(),
        producto.getUrlImagen(),
        producto.getPrecio(),
        producto.getStock(),
        producto.getActivo(),
        producto.getFechaPublicacion(),
        producto.getVendedor().getIdUsuario(),
        producto.getVendedor().getNombreCompleto(),
        producto.getCategoria().getIdCategoria(),
        producto.getCategoria().getNombreCategoria().toString(), // ← enum a String
        producto.getEstadoProducto().getIdEstadoProducto(),
        producto.getEstadoProducto().getNombreEstado().toString() // ← enum a String
    );
}


    // Métodos adicionales para operaciones CRUD
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public boolean existeProducto(Integer id) {
        return productoRepository.existsById(id);
    }
}