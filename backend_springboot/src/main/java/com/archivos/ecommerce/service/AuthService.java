package com.archivos.ecommerce.service;

import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.entity.Rol;
import com.archivos.ecommerce.repository.UsuarioRepository;
import com.archivos.ecommerce.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getPasswordHash())) {
            return usuario.get();
        }
        return null;
    }

    public Usuario register(String nombreCompleto, String email, String password, String telefono, String direccion) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(password));
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        usuario.setSuspendido(false);
        
        Rol rolComun = rolRepository.findByNombreRol(Rol.NombreRol.COMUN)
            .orElseThrow(() -> new RuntimeException("Rol COMUN no encontrado"));
        usuario.setRol(rolComun);

        return usuarioRepository.save(usuario);
    }
}