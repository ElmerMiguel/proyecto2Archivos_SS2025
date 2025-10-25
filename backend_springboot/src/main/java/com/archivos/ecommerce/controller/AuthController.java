package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.service.AuthService;
import com.archivos.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Auth controller funcionando"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            Usuario usuario = authService.login(email, password);
            if (usuario != null) {
                String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol().getNombreRol().toString());
                
                return ResponseEntity.ok(Map.of(
                    "token", token,
                    "usuario", Map.of(
                        "id", usuario.getIdUsuario(),
                        "nombre", usuario.getNombreCompleto(),
                        "email", usuario.getEmail(),
                        "rol", usuario.getRol().getNombreRol().toString()
                    )
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Credenciales incorrectas"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String nombreCompleto = request.get("nombreCompleto");
            String email = request.get("email");
            String password = request.get("password");
            String telefono = request.get("telefono");
            String direccion = request.get("direccion");

            Usuario usuario = authService.register(nombreCompleto, email, password, telefono, direccion);
            String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol().getNombreRol().toString());

            return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", Map.of(
                    "id", usuario.getIdUsuario(),
                    "nombre", usuario.getNombreCompleto(),
                    "email", usuario.getEmail(),
                    "rol", usuario.getRol().getNombreRol().toString()
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}