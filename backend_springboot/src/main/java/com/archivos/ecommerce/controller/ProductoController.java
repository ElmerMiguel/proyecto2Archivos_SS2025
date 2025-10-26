package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.ProductoDTO;
import com.archivos.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

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

    // Crear nuevo producto
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
}
