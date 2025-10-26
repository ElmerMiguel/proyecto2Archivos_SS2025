package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.PedidoDTO;
import com.archivos.ecommerce.dto.DetallePedidoDTO;
import com.archivos.ecommerce.entity.Pedido;
import com.archivos.ecommerce.entity.DetallePedido;
import com.archivos.ecommerce.repository.PedidoRepository;
import com.archivos.ecommerce.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<PedidoDTO> listarTodosLosPedidos() {
        return pedidoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Optional<PedidoDTO> obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
            .map(this::convertirADTO);
    }

    public List<PedidoDTO> obtenerPedidosPorUsuario(Integer idUsuario) {
        return pedidoRepository.findByComprador_IdUsuario(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private PedidoDTO convertirADTO(Pedido pedido) {
        List<DetallePedidoDTO> detalles = detallePedidoRepository.findByPedido_IdPedido(pedido.getIdPedido())
            .stream()
            .map(this::convertirDetalleADTO)
            .collect(Collectors.toList());

        return new PedidoDTO(
            pedido.getIdPedido(),
            pedido.getComprador().getIdUsuario(),
            pedido.getComprador().getNombreCompleto(),
            pedido.getTotal(),
            pedido.getFechaPedido(),
            pedido.getFechaEntrega(),
            pedido.getDireccionEntrega(),
            pedido.getEstadoPedido().getIdEstadoPedido(),
            pedido.getEstadoPedido().getNombreEstado().toString(),
            detalles
        );
    }

    private DetallePedidoDTO convertirDetalleADTO(DetallePedido detalle) {
        return new DetallePedidoDTO(
            detalle.getIdDetalle(),
            detalle.getProducto().getIdProducto(),
            detalle.getProducto().getNombreProducto(),
            detalle.getCantidad(),
            detalle.getPrecioUnitario(),
            detalle.getSubtotal()
        );
    }
}