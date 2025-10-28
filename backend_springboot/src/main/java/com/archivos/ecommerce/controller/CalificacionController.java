package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.CalificacionDTO;
import com.archivos.ecommerce.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "http://localhost:8081")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CalificacionController funcionando correctamente");
    }

    // Listar todas las calificaciones
    @GetMapping
    public ResponseEntity<List<CalificacionDTO>> listarTodasLasCalificaciones() {
        List<CalificacionDTO> calificaciones = calificacionService.listarTodasLasCalificaciones();
        return ResponseEntity.ok(calificaciones);
    }

    // Obtener calificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCalificacionPorId(@PathVariable Integer id) {
        return calificacionService.obtenerCalificacionPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Obtener calificaciones por producto
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorProducto(@PathVariable Integer idProducto) {
        List<CalificacionDTO> calificaciones = calificacionService.obtenerCalificacionesPorProducto(idProducto);
        return ResponseEntity.ok(calificaciones);
    }

    // Obtener calificaciones por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CalificacionDTO>> obtenerCalificacionesPorUsuario(@PathVariable Integer idUsuario) {
        List<CalificacionDTO> calificaciones = calificacionService.obtenerCalificacionesPorUsuario(idUsuario);
        return ResponseEntity.ok(calificaciones);
    }

    // Crear nueva calificación
    @PostMapping
    public ResponseEntity<?> crearCalificacion(@RequestParam Integer idUsuario,
                                             @RequestParam Integer idProducto,
                                             @RequestParam Integer idPedido,
                                             @RequestParam Integer puntuacion,
                                             @RequestParam(required = false) String comentario) {
        try {
            CalificacionDTO nuevaCalificacion = calificacionService.crearCalificacion(
                idUsuario, idProducto, idPedido, puntuacion, comentario);
            return ResponseEntity.ok(nuevaCalificacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar calificación
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCalificacion(@PathVariable Integer id,
                                                   @RequestParam Integer puntuacion,
                                                   @RequestParam(required = false) String comentario) {
        try {
            CalificacionDTO calificacionActualizada = calificacionService.actualizarCalificacion(
                id, puntuacion, comentario);
            return ResponseEntity.ok(calificacionActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar calificación
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCalificacion(@PathVariable Integer id) {
        try {
            calificacionService.eliminarCalificacion(id);
            return ResponseEntity.ok(Map.of("message", "Calificación eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}