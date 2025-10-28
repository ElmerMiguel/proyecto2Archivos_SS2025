package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.ProductoDTO;
import com.archivos.ecommerce.entity.Producto;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.entity.Categoria;
import com.archivos.ecommerce.entity.EstadoProducto;
import com.archivos.ecommerce.repository.ProductoRepository;
import com.archivos.ecommerce.repository.UsuarioRepository;
import com.archivos.ecommerce.repository.CategoriaRepository;
import com.archivos.ecommerce.repository.EstadoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// import java.util.stream.Collectors;
import java.util.Map;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstadoProductoRepository estadoProductoRepository;

    // Listar productos activos
    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener producto por ID
    public Optional<ProductoDTO> obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Obtener productos por vendedor
    public List<ProductoDTO> obtenerProductosPorVendedor(Integer idVendedor) {
        return productoRepository.findByVendedor_IdUsuario(idVendedor)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar todos los productos
    public List<ProductoDTO> listarTodosLosProductos() {
        return productoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Crear nuevo producto
    public ProductoDTO crearProducto(ProductoDTO productoDTO, Integer idVendedor) {
        Usuario vendedor = usuarioRepository.findById(idVendedor)
            .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        EstadoProducto estado = estadoProductoRepository.findById(1) // Estado PENDIENTE por defecto
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Producto producto = new Producto();
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setUrlImagen(productoDTO.getUrlImagen());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setActivo(true);
        producto.setFechaPublicacion(LocalDateTime.now());
        producto.setVendedor(vendedor);
        producto.setCategoria(categoria);
        producto.setEstadoProducto(estado);

        Producto productoGuardado = productoRepository.save(producto);
        return convertirADTO(productoGuardado);
    }

    // Actualizar producto existente
    public ProductoDTO actualizarProducto(Integer id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (productoDTO.getNombreProducto() != null) {
            producto.setNombreProducto(productoDTO.getNombreProducto());
        }
        if (productoDTO.getDescripcion() != null) {
            producto.setDescripcion(productoDTO.getDescripcion());
        }
        if (productoDTO.getUrlImagen() != null) {
            producto.setUrlImagen(productoDTO.getUrlImagen());
        }
        if (productoDTO.getPrecio() != null) {
            producto.setPrecio(productoDTO.getPrecio());
        }
        if (productoDTO.getStock() != null) {
            producto.setStock(productoDTO.getStock());
        }
        if (productoDTO.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        producto.setFechaUltimaActualizacion(LocalDateTime.now());

        Producto productoActualizado = productoRepository.save(producto);
        return convertirADTO(productoActualizado);
    }

    // Desactivar producto
    public void desactivarProducto(Integer id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    // Convertir entidad a DTO
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
            producto.getCategoria().getNombreCategoria().toString(),
            producto.getEstadoProducto().getIdEstadoProducto(),
            producto.getEstadoProducto().getNombreEstado().toString()
        );
    }

    // Métodos adicionales
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public boolean existeProducto(Integer id) {
        return productoRepository.existsById(id);
    }


    // Top 10 clientes más productos en venta
    public List<Map<String, Object>> getTop10ClientesMasProductosEnVenta() {
        return productoRepository.findTop10ClientesMasProductosEnVenta();
    }

    // Listar productos pendientes
    public List<ProductoDTO> listarProductosPendientes() {
        return productoRepository.findByEstadoProducto_IdEstadoProducto(1)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar productos rechazados
    public List<ProductoDTO> listarProductosRechazados() {
        return productoRepository.findByEstadoProducto_IdEstadoProducto(3)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Productos por categoría
    public List<Map<String, Object>> getProductosPorCategoria() {
        return productoRepository.getProductosPorCategoria();
    }

    
}
