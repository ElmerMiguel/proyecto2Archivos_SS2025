package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.UsuarioDTO;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarTodosLosUsuarios() {
        return usuarioRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
            .map(this::convertirADTO);
    }

    public Optional<UsuarioDTO> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .map(this::convertirADTO);
    }

    // Método para convertir Entity a DTO
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getIdUsuario(),
            usuario.getNombreCompleto(),
            usuario.getEmail(),
            usuario.getTelefono(),
            usuario.getDireccion(),
            usuario.getFechaRegistro(),
            usuario.getActivo(),
            usuario.getSuspendido(),
            usuario.getFechaSuspension(),
            usuario.getRol().getIdRol(),
            usuario.getRol().getNombreRol().toString()
        );
    }
}