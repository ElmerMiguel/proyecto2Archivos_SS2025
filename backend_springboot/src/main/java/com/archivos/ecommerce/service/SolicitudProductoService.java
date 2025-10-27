package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.SolicitudProductoDTO;
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
public class SolicitudProductoService {

    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;

    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    private EstadoProductoRepository estadoProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar todas las solicitudes
    public List<SolicitudProductoDTO> listarTodasLasSolicitudes() {
        return solicitudProductoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar solicitudes pendientes
    public List<SolicitudProductoDTO> listarSolicitudesPendientes() {
        return solicitudProductoRepository.findByEstadoSolicitud_IdEstadoSolicitud(1) // PENDIENTE
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener solicitud por ID
    public Optional<SolicitudProductoDTO> obtenerSolicitudPorId(Integer id) {
        return solicitudProductoRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Listar solicitudes por vendedor
    public List<SolicitudProductoDTO> listarSolicitudesPorVendedor(Integer idVendedor) {
        return solicitudProductoRepository.findByVendedor_IdUsuario(idVendedor)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Crear nueva solicitud
@Transactional
public SolicitudProductoDTO crearSolicitud(String nombreProducto, String descripcion, 
                                          Double precio, Integer stock, Integer idVendedor, 
                                          Integer idCategoria) {
    Usuario vendedor = usuarioRepository.findById(idVendedor)
        .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

    Categoria categoria = categoriaRepository.findById(idCategoria)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    EstadoSolicitud estadoPendiente = estadoSolicitudRepository.findById(1) // PENDIENTE
        .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no encontrado"));

    EstadoProducto estadoProducto = estadoProductoRepository.findById(1) // NUEVO
        .orElseThrow(() -> new RuntimeException("Estado de producto NUEVO no encontrado"));

    SolicitudProducto solicitud = new SolicitudProducto();
    solicitud.setNombreProducto(nombreProducto);
    solicitud.setDescripcion(descripcion);
    solicitud.setPrecio(java.math.BigDecimal.valueOf(precio));
    solicitud.setStock(stock);
    solicitud.setVendedor(vendedor);
    solicitud.setCategoria(categoria);
    solicitud.setEstadoSolicitud(estadoPendiente);
    solicitud.setEstadoProducto(estadoProducto); 
    solicitud.setFechaSolicitud(LocalDateTime.now());

    SolicitudProducto solicitudGuardada = solicitudProductoRepository.save(solicitud);
    return convertirADTO(solicitudGuardada);
}


    // Aprobar solicitud
    @Transactional
    public SolicitudProductoDTO aprobarSolicitud(Integer idSolicitud, String comentarioModerador) {
        SolicitudProducto solicitud = solicitudProductoRepository.findById(idSolicitud)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud() != 1) {
            throw new RuntimeException("Solo se pueden aprobar solicitudes pendientes");
        }

        // Cambiar estado a APROBADO
        EstadoSolicitud estadoAprobado = estadoSolicitudRepository.findById(2) // APROBADO
            .orElseThrow(() -> new RuntimeException("Estado APROBADO no encontrado"));

        solicitud.setEstadoSolicitud(estadoAprobado);
        solicitud.setFechaRespuesta(LocalDateTime.now());
        solicitud.setComentarioModerador(comentarioModerador);

        // Crear producto en la tabla productos
        EstadoProducto estadoDisponible = estadoProductoRepository.findById(1) // DISPONIBLE
            .orElseThrow(() -> new RuntimeException("Estado DISPONIBLE no encontrado"));

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombreProducto(solicitud.getNombreProducto());
        nuevoProducto.setDescripcion(solicitud.getDescripcion());
        nuevoProducto.setPrecio(solicitud.getPrecio());
        nuevoProducto.setStock(solicitud.getStock());
        nuevoProducto.setVendedor(solicitud.getVendedor());
        nuevoProducto.setCategoria(solicitud.getCategoria());
        nuevoProducto.setEstadoProducto(estadoDisponible);
        nuevoProducto.setFechaPublicacion(LocalDateTime.now());

        productoRepository.save(nuevoProducto);

        SolicitudProducto solicitudActualizada = solicitudProductoRepository.save(solicitud);
        return convertirADTO(solicitudActualizada);
    }

    // Rechazar solicitud
    @Transactional
    public SolicitudProductoDTO rechazarSolicitud(Integer idSolicitud, String comentarioModerador) {
        SolicitudProducto solicitud = solicitudProductoRepository.findById(idSolicitud)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud() != 1) {
            throw new RuntimeException("Solo se pueden rechazar solicitudes pendientes");
        }

        // Cambiar estado a RECHAZADO
        EstadoSolicitud estadoRechazado = estadoSolicitudRepository.findById(3) // RECHAZADO
            .orElseThrow(() -> new RuntimeException("Estado RECHAZADO no encontrado"));

        solicitud.setEstadoSolicitud(estadoRechazado);
        solicitud.setFechaRespuesta(LocalDateTime.now());
        solicitud.setComentarioModerador(comentarioModerador);

        SolicitudProducto solicitudActualizada = solicitudProductoRepository.save(solicitud);
        return convertirADTO(solicitudActualizada);
    }

    // Convertir Entity a DTO
    private SolicitudProductoDTO convertirADTO(SolicitudProducto solicitud) {
        return new SolicitudProductoDTO(
            solicitud.getIdSolicitud(),
            solicitud.getNombreProducto(),
            solicitud.getDescripcion(),
            solicitud.getPrecio(),
            solicitud.getStock(),
            solicitud.getVendedor().getIdUsuario(),
            solicitud.getVendedor().getNombreCompleto(),
            solicitud.getCategoria().getIdCategoria(),
            solicitud.getCategoria().getNombreCategoria().toString(),
            solicitud.getEstadoSolicitud().getIdEstadoSolicitud(),
            solicitud.getEstadoSolicitud().getNombreEstado().toString(),
            solicitud.getFechaSolicitud(),
            solicitud.getFechaRespuesta(),
            solicitud.getComentarioModerador()
        );
    }
}