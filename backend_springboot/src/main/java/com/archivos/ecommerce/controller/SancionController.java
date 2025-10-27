package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.SancionDTO;
import com.archivos.ecommerce.service.SancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sanciones")
@CrossOrigin(origins = "http://localhost:3000")
public class SancionController {

    @Autowired
    private SancionService sancionService;

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("SancionController funcionando correctamente");
    }

    // Listar todas las sanciones - ADMIN/MODERADOR
    @GetMapping
    public ResponseEntity<List<SancionDTO>> listarTodasLasSanciones() {
        List<SancionDTO> sanciones = sancionService.listarTodasLasSanciones();
        return ResponseEntity.ok(sanciones);
    }

    // Obtener sanción por ID - ADMIN/MODERADOR
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerSancionPorId(@PathVariable Integer id) {
        return sancionService.obtenerSancionPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Listar sanciones activas - ADMIN/MODERADOR
    @GetMapping("/activas")
    public ResponseEntity<List<SancionDTO>> listarSancionesActivas() {
        List<SancionDTO> sanciones = sancionService.listarSancionesActivas();
        return ResponseEntity.ok(sanciones);
    }

    // Listar sanciones por usuario - ADMIN/MODERADOR
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<SancionDTO>> listarSancionesPorUsuario(@PathVariable Integer idUsuario) {
        List<SancionDTO> sanciones = sancionService.listarSancionesPorUsuario(idUsuario);
        return ResponseEntity.ok(sanciones);
    }

    // Verificar si usuario está sancionado - CUALQUIER USUARIO
    @GetMapping("/verificar/{idUsuario}")
    public ResponseEntity<Map<String, Boolean>> verificarSancion(@PathVariable Integer idUsuario) {
        boolean estaSancionado = sancionService.usuarioEstaSancionado(idUsuario);
        return ResponseEntity.ok(Map.of("sancionado", estaSancionado));
    }

    // Crear nueva sanción - MODERADOR/ADMIN
    @PostMapping
    public ResponseEntity<?> crearSancion(@RequestParam Integer idUsuarioSancionado,
                                        @RequestParam Integer idModerador,
                                        @RequestParam String motivo,
                                        @RequestParam String descripcion,
                                        @RequestParam Integer diasSuspension) {
        try {
            if (diasSuspension <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Los días de suspensión deben ser mayor a 0"));
            }
            
            SancionDTO nuevaSancion = sancionService.crearSancion(
                idUsuarioSancionado, idModerador, motivo, descripcion, diasSuspension);
            return ResponseEntity.ok(nuevaSancion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Finalizar sanción (levantar suspensión) - MODERADOR/ADMIN
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarSancion(@PathVariable Integer id) {
        try {
            SancionDTO sancionFinalizada = sancionService.finalizarSancion(id);
            return ResponseEntity.ok(sancionFinalizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Finalizar sanciones vencidas automáticamente - ADMIN
    @PostMapping("/finalizar-vencidas")
    public ResponseEntity<?> finalizarSancionesVencidas() {
        try {
            sancionService.finalizarSancionesVencidas();
            return ResponseEntity.ok(Map.of("message", "Sanciones vencidas finalizadas correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}