package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.PedidoDTO;
import com.archivos.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Integer id) {
        return pedidoService.obtenerPedidoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Obtener pedidos por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorUsuario(@PathVariable Integer idUsuario) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
        return ResponseEntity.ok(pedidos);
    }

    // Crear pedido desde el carrito con tarjeta
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestParam Integer idUsuario,
                                         @RequestParam Integer idTarjeta,
                                         @RequestParam String direccionEntrega) {
        try {
            PedidoDTO nuevoPedido = pedidoService.crearPedidoDesdeCarrito(idUsuario, idTarjeta, direccionEntrega);
            return ResponseEntity.ok(nuevoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint de prueba
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Pedido controller funcionando"));
    }
}
