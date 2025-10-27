package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.SolicitudProductoDTO;
import com.archivos.ecommerce.service.SolicitudProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/moderador")
@CrossOrigin(origins = "http://localhost:3000")
public class ModeradorController {

    @Autowired
    private SolicitudProductoService solicitudProductoService;

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ModeradorController funcionando correctamente");
    }

    // Listar todas las solicitudes
    @GetMapping("/solicitudes")
    public ResponseEntity<List<SolicitudProductoDTO>> listarTodasLasSolicitudes() {
        List<SolicitudProductoDTO> solicitudes = solicitudProductoService.listarTodasLasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    // Listar solo solicitudes pendientes
    @GetMapping("/solicitudes/pendientes")
    public ResponseEntity<List<SolicitudProductoDTO>> listarSolicitudesPendientes() {
        List<SolicitudProductoDTO> solicitudes = solicitudProductoService.listarSolicitudesPendientes();
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener solicitud específica por ID
    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<?> obtenerSolicitudPorId(@PathVariable Integer id) {
        return solicitudProductoService.obtenerSolicitudPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Listar solicitudes por vendedor
    @GetMapping("/solicitudes/vendedor/{idVendedor}")
    public ResponseEntity<List<SolicitudProductoDTO>> listarSolicitudesPorVendedor(@PathVariable Integer idVendedor) {
        List<SolicitudProductoDTO> solicitudes = solicitudProductoService.listarSolicitudesPorVendedor(idVendedor);
        return ResponseEntity.ok(solicitudes);
    }

    // Aprobar solicitud
    @PutMapping("/solicitudes/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Integer id,
                                            @RequestParam(required = false) String comentario) {
        try {
            String comentarioFinal = comentario != null ? comentario : "Solicitud aprobada por el moderador";
            SolicitudProductoDTO solicitudAprobada = solicitudProductoService.aprobarSolicitud(id, comentarioFinal);
            return ResponseEntity.ok(solicitudAprobada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Rechazar solicitud
    @PutMapping("/solicitudes/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable Integer id,
                                             @RequestParam String comentario) {
        try {
            if (comentario == null || comentario.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El comentario es obligatorio para rechazar una solicitud"));
            }
            SolicitudProductoDTO solicitudRechazada = solicitudProductoService.rechazarSolicitud(id, comentario);
            return ResponseEntity.ok(solicitudRechazada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear nueva solicitud (endpoint para vendedores)
    @PostMapping("/solicitudes")
    public ResponseEntity<?> crearSolicitud(@RequestParam String nombreProducto,
                                          @RequestParam String descripcion,
                                          @RequestParam Double precio,
                                          @RequestParam Integer stock,
                                          @RequestParam Integer idVendedor,
                                          @RequestParam Integer idCategoria) {
        try {
            SolicitudProductoDTO nuevaSolicitud = solicitudProductoService.crearSolicitud(
                nombreProducto, descripcion, precio, stock, idVendedor, idCategoria);
            return ResponseEntity.ok(nuevaSolicitud);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}