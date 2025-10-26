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

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Integer id) {
        return pedidoService.obtenerPedidoPorId(id)
            .map(pedido -> ResponseEntity.ok(pedido))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorUsuario(@PathVariable Integer idUsuario) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Pedido controller funcionando"));
    }
}