package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.NotificacionDTO;
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
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private TipoNotificacionRepository tipoNotificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    // Listar todas las notificaciones
    public List<NotificacionDTO> listarTodasLasNotificaciones() {
        return notificacionRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener notificación por ID
    public Optional<NotificacionDTO> obtenerNotificacionPorId(Integer id) {
        return notificacionRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Listar notificaciones por usuario
    public List<NotificacionDTO> listarNotificacionesPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuario_IdUsuario(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar notificaciones por estado de envío exitoso
    public List<NotificacionDTO> listarNotificacionesEnviadasExitosamente(Integer idUsuario) {
        return notificacionRepository.findByUsuario_IdUsuarioAndEnviadoExitosamenteTrue(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Crear notificación
    @Transactional
    public NotificacionDTO crearNotificacion(String asunto, String mensaje, Integer idUsuario,
                                           TipoNotificacion.NombreTipo tipoNotificacion, Integer idReferencia) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TipoNotificacion tipo = tipoNotificacionRepository.findByNombreTipo(tipoNotificacion)
            .orElseThrow(() -> new RuntimeException("Tipo de notificación no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setAsunto(asunto);
        notificacion.setMensaje(mensaje);
        notificacion.setUsuario(usuario);
        notificacion.setTipoNotificacion(tipo);
        notificacion.setIdReferencia(idReferencia);
        notificacion.setEnviadoExitosamente(true);
        notificacion.setFechaEnvio(LocalDateTime.now());

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionGuardada);
    }

    // Crear notificación de cambio de estado de pedido y enviar email
    public void crearNotificacionCambioEstadoPedido(Integer idUsuario, Integer idPedido, String mensaje) {
        TipoNotificacion tipo = tipoNotificacionRepository.findByNombreTipo(TipoNotificacion.NombreTipo.CAMBIO_ESTADO_PEDIDO)
            .orElseThrow(() -> new RuntimeException("Tipo de notificación no encontrado"));

        // Crear notificación sin asunto explícito
        Notificacion notificacion = new Notificacion();
        notificacion.setAsunto("Actualización de Pedido");
        notificacion.setMensaje(mensaje);
        notificacion.setUsuario(usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        notificacion.setTipoNotificacion(tipo);
        notificacion.setIdReferencia(idPedido);
        notificacion.setEnviadoExitosamente(true);
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacionRepository.save(notificacion);

        // Enviar email
        usuarioService.obtenerUsuarioPorId(idUsuario).ifPresent(usuario -> {
            emailService.enviarNotificacionCambioEstado(usuario.getEmail(), usuario.getNombreCompleto(), idPedido, "ENTREGADO");
        });
    }

    // Eliminar notificación
    @Transactional
    public void eliminarNotificacion(Integer idNotificacion) {
        if (!notificacionRepository.existsById(idNotificacion)) {
            throw new RuntimeException("Notificación no encontrada");
        }
        notificacionRepository.deleteById(idNotificacion);
    }

    // Notificación de pedido creado
    public void notificarPedidoCreado(Integer idUsuario, Integer idPedido, String total) {
        String asunto = "Pedido Confirmado";
        String mensaje = "Tu pedido #" + idPedido + " por Q" + total + " ha sido confirmado. Te mantendremos informado sobre el estado de tu entrega.";
        crearNotificacion(asunto, mensaje, idUsuario, TipoNotificacion.NombreTipo.CAMBIO_ESTADO_PEDIDO, idPedido);
    }

    // Notificación de producto aprobado
    public void notificarProductoAprobado(Integer idVendedor, String nombreProducto, Integer idProducto) {
        String asunto = "Producto Aprobado";
        String mensaje = "Tu producto '" + nombreProducto + "' ha sido aprobado y ya está disponible para la venta.";
        crearNotificacion(asunto, mensaje, idVendedor, TipoNotificacion.NombreTipo.PRODUCTO_APROBADO, idProducto);
    }

    // Notificación de producto rechazado
    public void notificarProductoRechazado(Integer idVendedor, String nombreProducto, String razon, Integer idProducto) {
        String asunto = "Producto Rechazado";
        String mensaje = "Tu producto '" + nombreProducto + "' ha sido rechazado. Razón: " + razon;
        crearNotificacion(asunto, mensaje, idVendedor, TipoNotificacion.NombreTipo.PRODUCTO_RECHAZADO, idProducto);
    }

    // Convertir Entity a DTO
    private NotificacionDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionDTO(
            notificacion.getIdNotificacion(),
            notificacion.getAsunto(),
            notificacion.getMensaje(),
            notificacion.getFechaEnvio(),
            notificacion.getEnviadoExitosamente(),
            notificacion.getIdReferencia(),
            notificacion.getUsuario().getIdUsuario(),
            notificacion.getUsuario().getNombreCompleto(),
            notificacion.getTipoNotificacion().getIdTipoNotificacion(),
            notificacion.getTipoNotificacion().getNombreTipo().toString()
        );
    }
}
