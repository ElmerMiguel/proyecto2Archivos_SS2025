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

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductosActivos() {
        List<ProductoDTO> productos = productoService.listarProductosActivos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.obtenerProductoPorId(id)
            .map(producto -> ResponseEntity.ok(producto))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorVendedor(@PathVariable Integer idVendedor) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorVendedor(idVendedor);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/admin/todos")
    public ResponseEntity<List<ProductoDTO>> listarTodosLosProductos() {
        List<ProductoDTO> productos = productoService.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Producto controller funcionando"));
    }
}