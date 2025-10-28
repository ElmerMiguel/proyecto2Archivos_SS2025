package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.service.AuthService;
import com.archivos.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint de login
     * Genera un token JWT con el email y rol del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Usuario usuario = authService.login(email, password);
        
        if (usuario != null) {
            // Generar token con el rol del usuario
            String token = jwtService.generateToken(
                usuario.getEmail(), 
                usuario.getRol().getNombreRol().name() // Asegúrate que devuelva "COMUN", "MODERADOR", etc.
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", usuario.getEmail());
            response.put("nombreCompleto", usuario.getNombreCompleto());
            response.put("rol", usuario.getRol().getNombreRol().name());
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
    }

    /**
     * Endpoint de registro
     * Crea un usuario COMUN y genera su token
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            Usuario usuario = authService.register(
                userData.get("nombreCompleto"),
                userData.get("email"),
                userData.get("password"),
                userData.get("telefono"),
                userData.get("direccion")
            );

            // Generar token para el nuevo usuario
            String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().getNombreRol().name() // Será "COMUN"
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", usuario.getEmail());
            response.put("nombreCompleto", usuario.getNombreCompleto());
            response.put("rol", usuario.getRol().getNombreRol().name());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint para verificar el token actual
     * Útil para debugging y verificar que el rol esté correcto
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtService.validateToken(token)) {
                String email = jwtService.getEmailFromToken(token);
                String rol = jwtService.getRolFromToken(token);
                
                Map<String, String> response = new HashMap<>();
                response.put("email", email);
                response.put("rol", rol);
                response.put("status", "Token válido");
                
                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
    }
}