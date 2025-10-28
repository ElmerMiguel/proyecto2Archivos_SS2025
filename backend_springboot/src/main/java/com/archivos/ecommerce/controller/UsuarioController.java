package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.UsuarioDTO;
import com.archivos.ecommerce.service.UsuarioService;
import com.archivos.ecommerce.service.SolicitudProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8081")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private SolicitudProductoService solicitudProductoService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id)
            .map(usuario -> ResponseEntity.ok(usuario))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> obtenerUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.obtenerUsuarioPorEmail(email)
            .map(usuario -> ResponseEntity.ok(usuario))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Usuario controller funcionando"));
    }

    @PostMapping("/solicitar-producto")
    @PreAuthorize("hasRole('COMUN')")
    public ResponseEntity<?> solicitarProducto(
            @RequestParam String nombreProducto,
            @RequestParam String descripcion,
            @RequestParam Double precio,
            @RequestParam Integer stock,
            @RequestParam Integer idCategoria,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            
            UsuarioDTO usuario = usuarioService.obtenerUsuarioPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Integer idVendedor = usuario.getIdUsuario();
            solicitudProductoService.crearSolicitud(nombreProducto, descripcion, precio, stock, idVendedor, idCategoria);
            
            return ResponseEntity.ok(Map.of("message", "Solicitud enviada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}