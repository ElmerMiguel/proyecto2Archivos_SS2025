package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:3000")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener carrito por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCarritoPorUsuario(@PathVariable Integer idUsuario) {
        return carritoService.obtenerCarritoPorUsuario(idUsuario)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Obtener ítems del carrito
    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<ItemCarritoDTO>> obtenerItemsDelCarrito(@PathVariable Integer idCarrito) {
        List<ItemCarritoDTO> items = carritoService.obtenerItemsDelCarrito(idCarrito);
        return ResponseEntity.ok(items);
    }

    // Agregar producto al carrito
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProductoAlCarrito(@RequestParam Integer idUsuario,
                                                      @RequestParam Integer idProducto,
                                                      @RequestParam Integer cantidad) {
        try {
            ItemCarritoDTO item = carritoService.agregarProductoAlCarrito(idUsuario, idProducto, cantidad);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar cantidad de un ítem
    @PutMapping("/item/{idItem}")
    public ResponseEntity<?> actualizarCantidadItem(@PathVariable Integer idItem,
                                                    @RequestParam Integer cantidad) {
        try {
            ItemCarritoDTO item = carritoService.actualizarCantidadItem(idItem, cantidad);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar ítem del carrito
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<?> eliminarItemDelCarrito(@PathVariable Integer idItem) {
        try {
            carritoService.eliminarItemDelCarrito(idItem);
            return ResponseEntity.ok(Map.of("message", "Item eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint de prueba
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Carrito controller funcionando"));
    }
}
