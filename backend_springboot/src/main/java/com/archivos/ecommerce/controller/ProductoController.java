package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.ProductoDTO;
import com.archivos.ecommerce.service.ProductoService;
import com.archivos.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.archivos.ecommerce.entity.Usuario;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    // Obtener productos activos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductosActivos() {
        List<ProductoDTO> productos = productoService.listarProductosActivos();
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.obtenerProductoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Obtener productos por vendedor
    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorVendedor(@PathVariable Integer idVendedor) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorVendedor(idVendedor);
        return ResponseEntity.ok(productos);
    }

    // Obtener todos los productos (admin)
    @GetMapping("/admin/todos")
    public ResponseEntity<List<ProductoDTO>> listarTodosLosProductos() {
        List<ProductoDTO> productos = productoService.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    // Endpoint de prueba
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Producto controller funcionando"));
    }

    // Crear nuevo producto (sin autenticación)
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO productoDTO,
                                           @RequestParam Integer idVendedor) {
        try {
            ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO, idVendedor);
            return ResponseEntity.ok(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear nuevo producto (autenticado)
    @PostMapping("/nuevo")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ProductoDTO> crearProductoAutenticado(@RequestBody ProductoDTO productoDTO, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerEntidadPorEmail(email)
    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO, usuario.getIdUsuario());
        return ResponseEntity.ok(nuevoProducto);
    }

    // Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id,
                                                @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Desactivar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarProducto(@PathVariable Integer id) {
        try {
            productoService.desactivarProducto(id);
            return ResponseEntity.ok(Map.of("message", "Producto desactivado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener productos por categoría
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Integer idCategoria) {
        List<ProductoDTO> productos = productoService.listarProductosActivos()
            .stream()
            .filter(p -> p.getIdCategoria().equals(idCategoria))
            .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    // Obtener productos del usuario autenticado
    @GetMapping("/mis-productos")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<ProductoDTO>> obtenerMisProductos(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerEntidadPorEmail(email)
    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<ProductoDTO> productos = productoService.obtenerProductosPorVendedor(usuario.getIdUsuario());
        return ResponseEntity.ok(productos);
    }
}
