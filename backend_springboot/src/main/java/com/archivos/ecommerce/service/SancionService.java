package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.SancionDTO;
import com.archivos.ecommerce.entity.*;
import com.archivos.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SancionService {

    @Autowired
    private SancionRepository sancionRepository;

    @Autowired
    private EstadoSancionRepository estadoSancionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todas las sanciones
    public List<SancionDTO> listarTodasLasSanciones() {
        return sancionRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener sanción por ID
    public Optional<SancionDTO> obtenerSancionPorId(Integer id) {
        return sancionRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Listar sanciones activas
    public List<SancionDTO> listarSancionesActivas() {
        return sancionRepository.findByEstadoSancion_IdEstadoSancion(1) // ACTIVA
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar sanciones por usuario
    public List<SancionDTO> listarSancionesPorUsuario(Integer idUsuario) {
        return sancionRepository.findByUsuarioSancionado_IdUsuario(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Crear nueva sanción
    @Transactional
    public SancionDTO crearSancion(Integer idUsuarioSancionado, Integer idModerador,
                                  String motivo, String descripcion, Integer diasSuspension) {
        Usuario usuarioSancionado = usuarioRepository.findById(idUsuarioSancionado)
            .orElseThrow(() -> new RuntimeException("Usuario a sancionar no encontrado"));

        Usuario moderador = usuarioRepository.findById(idModerador)
            .orElseThrow(() -> new RuntimeException("Moderador no encontrado"));

        // Verificar que el moderador tenga rol MODERADOR o ADMIN
        if (!moderador.getRol().getNombreRol().toString().equals("MODERADOR") && 
            !moderador.getRol().getNombreRol().toString().equals("ADMIN")) {
            throw new RuntimeException("Solo moderadores y administradores pueden crear sanciones");
        }

        EstadoSancion estadoActiva = estadoSancionRepository.findById(1) // ACTIVA
            .orElseThrow(() -> new RuntimeException("Estado ACTIVA no encontrado"));

        LocalDateTime fechaInicio = LocalDateTime.now();
        LocalDateTime fechaFin = fechaInicio.plusDays(diasSuspension);

        Sancion sancion = new Sancion();
        sancion.setUsuarioSancionado(usuarioSancionado);
        sancion.setModerador(moderador);
        sancion.setMotivo(motivo);
        sancion.setDescripcion(descripcion);
        sancion.setDiasSuspension(diasSuspension);
        sancion.setFechaInicio(fechaInicio);
        sancion.setFechaFin(fechaFin);
        sancion.setEstadoSancion(estadoActiva);

        // Suspender al usuario
        usuarioSancionado.setSuspendido(true);
        usuarioRepository.save(usuarioSancionado);

        Sancion sancionGuardada = sancionRepository.save(sancion);
        return convertirADTO(sancionGuardada);
    }

    // Finalizar sanción (levantar suspensión)
    @Transactional
    public SancionDTO finalizarSancion(Integer idSancion) {
        Sancion sancion = sancionRepository.findById(idSancion)
            .orElseThrow(() -> new RuntimeException("Sanción no encontrada"));

        if (sancion.getEstadoSancion().getIdEstadoSancion() != 1) {
            throw new RuntimeException("Solo se pueden finalizar sanciones activas");
        }

        EstadoSancion estadoFinalizada = estadoSancionRepository.findById(2) // FINALIZADA
            .orElseThrow(() -> new RuntimeException("Estado FINALIZADA no encontrado"));

        sancion.setEstadoSancion(estadoFinalizada);

        // Levantar suspensión del usuario
        Usuario usuario = sancion.getUsuarioSancionado();
        usuario.setSuspendido(false);
        usuarioRepository.save(usuario);

        Sancion sancionActualizada = sancionRepository.save(sancion);
        return convertirADTO(sancionActualizada);
    }

    // Verificar si un usuario está sancionado actualmente
    public boolean usuarioEstaSancionado(Integer idUsuario) {
        List<Sancion> sancionesActivas = sancionRepository.findByUsuarioSancionado_IdUsuario(idUsuario)
            .stream()
            .filter(s -> s.getEstadoSancion().getIdEstadoSancion() == 1 && // ACTIVA
                        s.getFechaFin().isAfter(LocalDateTime.now()))
            .collect(Collectors.toList());
        
        return !sancionesActivas.isEmpty();
    }

    // Proceso automático para finalizar sanciones vencidas
    @Transactional
    public void finalizarSancionesVencidas() {
        List<Sancion> sancionesVencidas = sancionRepository.findByEstadoSancion_IdEstadoSancion(1)
            .stream()
            .filter(s -> s.getFechaFin().isBefore(LocalDateTime.now()))
            .collect(Collectors.toList());

        EstadoSancion estadoFinalizada = estadoSancionRepository.findById(2)
            .orElseThrow(() -> new RuntimeException("Estado FINALIZADA no encontrado"));

        for (Sancion sancion : sancionesVencidas) {
            sancion.setEstadoSancion(estadoFinalizada);
            
            // Levantar suspensión del usuario
            Usuario usuario = sancion.getUsuarioSancionado();
            usuario.setSuspendido(false);
            usuarioRepository.save(usuario);
            
            sancionRepository.save(sancion);
        }
    }

    // Convertir Entity a DTO
    private SancionDTO convertirADTO(Sancion sancion) {
        return new SancionDTO(
            sancion.getIdSancion(),
            sancion.getUsuarioSancionado().getIdUsuario(),
            sancion.getUsuarioSancionado().getNombreCompleto(),
            sancion.getUsuarioSancionado().getEmail(),
            sancion.getModerador().getIdUsuario(),
            sancion.getModerador().getNombreCompleto(),
            sancion.getMotivo(),
            sancion.getDescripcion(),
            sancion.getDiasSuspension(),
            sancion.getFechaInicio(),
            sancion.getFechaFin(),
            sancion.getEstadoSancion().getIdEstadoSancion(),
            sancion.getEstadoSancion().getNombreEstado().toString()
        );
    }
}