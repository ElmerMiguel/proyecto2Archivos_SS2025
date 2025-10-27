package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.NotificacionDTO;
import com.archivos.ecommerce.entity.TipoNotificacion;
import com.archivos.ecommerce.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("NotificacionController funcionando correctamente");
    }

    // Listar todas las notificaciones - ADMIN
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarTodasLasNotificaciones() {
        List<NotificacionDTO> notificaciones = notificacionService.listarTodasLasNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    // Obtener notificación por ID - ADMIN/USUARIO DUEÑO
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerNotificacionPorId(@PathVariable Integer id) {
        return notificacionService.obtenerNotificacionPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Listar notificaciones por usuario - USUARIO COMÚN
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionDTO>> listarNotificacionesPorUsuario(@PathVariable Integer idUsuario) {
        List<NotificacionDTO> notificaciones = notificacionService.listarNotificacionesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    // Listar notificaciones enviadas exitosamente por usuario - USUARIO COMÚN
    @GetMapping("/usuario/{idUsuario}/exitosas")
    public ResponseEntity<List<NotificacionDTO>> listarNotificacionesExitosas(@PathVariable Integer idUsuario) {
        List<NotificacionDTO> notificaciones = notificacionService.listarNotificacionesEnviadasExitosamente(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    // Crear notificación manual - ADMIN/MODERADOR
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@RequestParam String asunto,
                                             @RequestParam String mensaje,
                                             @RequestParam Integer idUsuario,
                                             @RequestParam String tipoNotificacion,
                                             @RequestParam(required = false) Integer idReferencia) {
        try {
            TipoNotificacion.NombreTipo tipo = TipoNotificacion.NombreTipo.valueOf(tipoNotificacion);
            NotificacionDTO nuevaNotificacion = notificacionService.crearNotificacion(
                asunto, mensaje, idUsuario, tipo, idReferencia);
            return ResponseEntity.ok(nuevaNotificacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar notificación - ADMIN/USUARIO DUEÑO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Integer id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return ResponseEntity.ok(Map.of("message", "Notificación eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}