package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.CarritoDTO;
import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.service.CarritoService;
import com.archivos.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    // NUEVO: Obtener carrito del usuario autenticado
    @GetMapping("/mis-items")
    @PreAuthorize("hasRole('COMUN')")
    public ResponseEntity<?> obtenerMiCarrito(Authentication authentication) {
        try {
            String email = authentication.getName();
            Usuario usuario = usuarioService.obtenerEntidadPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Optional<CarritoDTO> carrito = carritoService.obtenerCarritoPorUsuario(usuario.getIdUsuario());

            if (carrito.isPresent()) {
                return ResponseEntity.ok(carrito.get());
            } else {
                CarritoDTO carritoVacio = new CarritoDTO();
                carritoVacio.setIdUsuario(usuario.getIdUsuario());
                carritoVacio.setNombreUsuario(usuario.getNombreCompleto());
                carritoVacio.setItems(List.of());
                return ResponseEntity.ok(carritoVacio);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener carrito por usuario (uso interno o administrativo)
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

    // MODIFICADO: Agregar producto al carrito del usuario autenticado
    @PostMapping("/agregar")
    @PreAuthorize("hasRole('COMUN')")
    public ResponseEntity<?> agregarProductoAlCarrito(@RequestParam Integer idProducto,
                                                      @RequestParam Integer cantidad,
                                                      Authentication authentication) {
        try {
            String email = authentication.getName();
            Usuario usuario = usuarioService.obtenerEntidadPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            ItemCarritoDTO item = carritoService.agregarProductoAlCarrito(usuario.getIdUsuario(), idProducto, cantidad);
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
