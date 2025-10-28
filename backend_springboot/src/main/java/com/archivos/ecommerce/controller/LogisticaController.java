package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.PedidoDTO;
import com.archivos.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/logistica")
@PreAuthorize("hasRole('EMPLEADO')")
public class LogisticaController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos-en-curso")
    public ResponseEntity<List<PedidoDTO>> getPedidosEnCurso() {
        List<PedidoDTO> pedidos = pedidoService.listarPedidosEnCurso();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/entregar-pedido/{idPedido}")
    public ResponseEntity<PedidoDTO> entregarPedido(@PathVariable Integer idPedido) {
        PedidoDTO pedidoEntregado = pedidoService.marcarComoEntregado(idPedido);
        return ResponseEntity.ok(pedidoEntregado);
    }

    @PutMapping("/modificar-fecha-entrega/{idPedido}")
    public ResponseEntity<PedidoDTO> modificarFechaEntrega(
            @PathVariable Integer idPedido,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFecha) {
        PedidoDTO pedidoModificado = pedidoService.modificarFechaEntrega(idPedido, nuevaFecha);
        return ResponseEntity.ok(pedidoModificado);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Integer idPedido) {
        return pedidoService.obtenerPedidoPorId(idPedido)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}