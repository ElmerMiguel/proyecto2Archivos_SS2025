package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.CalificacionDTO;
import com.archivos.ecommerce.entity.Calificacion;
import com.archivos.ecommerce.entity.Producto;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.entity.Pedido;
import com.archivos.ecommerce.repository.CalificacionRepository;
import com.archivos.ecommerce.repository.ProductoRepository;
import com.archivos.ecommerce.repository.UsuarioRepository;
import com.archivos.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<CalificacionDTO> listarTodasLasCalificaciones() {
        return calificacionRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Optional<CalificacionDTO> obtenerCalificacionPorId(Integer id) {
        return calificacionRepository.findById(id)
            .map(this::convertirADTO);
    }

    public List<CalificacionDTO> obtenerCalificacionesPorProducto(Integer idProducto) {
        return calificacionRepository.findByProducto_IdProducto(idProducto)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<CalificacionDTO> obtenerCalificacionesPorUsuario(Integer idUsuario) {
        return calificacionRepository.findByUsuario_IdUsuario(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CalificacionDTO crearCalificacion(Integer idUsuario, Integer idProducto, 
                                           Integer idPedido, Integer puntuacion, String comentario) {
        // Verificar si ya existe calificación
        if (calificacionRepository.existsByUsuario_IdUsuarioAndPedido_IdPedidoAndProducto_IdProducto(
                idUsuario, idPedido, idProducto)) {
            throw new RuntimeException("Ya existe una calificación para este producto en este pedido");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Calificacion calificacion = new Calificacion();
        calificacion.setUsuario(usuario);
        calificacion.setProducto(producto);
        calificacion.setPedido(pedido);
        calificacion.setPuntuacion(puntuacion);
        calificacion.setComentario(comentario);
        calificacion.setFechaCalificacion(LocalDateTime.now());

        Calificacion calificacionGuardada = calificacionRepository.save(calificacion);
        return convertirADTO(calificacionGuardada);
    }

    @Transactional
    public CalificacionDTO actualizarCalificacion(Integer id, Integer puntuacion, String comentario) {
        Calificacion calificacion = calificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));

        calificacion.setPuntuacion(puntuacion);
        calificacion.setComentario(comentario);

        Calificacion calificacionActualizada = calificacionRepository.save(calificacion);
        return convertirADTO(calificacionActualizada);
    }

    @Transactional
    public void eliminarCalificacion(Integer id) {
        if (!calificacionRepository.existsById(id)) {
            throw new RuntimeException("Calificación no encontrada");
        }
        calificacionRepository.deleteById(id);
    }

    // Método para convertir Entity a DTO
    private CalificacionDTO convertirADTO(Calificacion calificacion) {
        return new CalificacionDTO(
            calificacion.getIdCalificacion(),
            calificacion.getProducto().getIdProducto(),
            calificacion.getProducto().getNombreProducto(),
            calificacion.getUsuario().getIdUsuario(),
            calificacion.getUsuario().getNombreCompleto(),
            calificacion.getPedido().getIdPedido(),
            calificacion.getPuntuacion(),
            calificacion.getComentario(),
            calificacion.getFechaCalificacion()
        );
    }
}