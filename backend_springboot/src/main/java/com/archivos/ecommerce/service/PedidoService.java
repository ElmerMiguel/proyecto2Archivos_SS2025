package com.archivos.ecommerce.service;

import com.archivos.ecommerce.dto.CarritoDTO;
import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.dto.PedidoDTO;
import com.archivos.ecommerce.dto.DetallePedidoDTO;
import com.archivos.ecommerce.entity.Pedido;
import com.archivos.ecommerce.entity.DetallePedido;
import com.archivos.ecommerce.entity.EstadoPedido;
import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.entity.TarjetaCredito;
import com.archivos.ecommerce.repository.PedidoRepository;
import com.archivos.ecommerce.repository.DetallePedidoRepository;
import com.archivos.ecommerce.repository.EstadoPedidoRepository;
import com.archivos.ecommerce.repository.ProductoRepository;
import com.archivos.ecommerce.repository.UsuarioRepository;
import com.archivos.ecommerce.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

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

    @Transactional
    public PedidoDTO crearPedidoDesdeCarrito(Integer idUsuario, Integer idTarjeta, String direccionEntrega) {
        CarritoDTO carritoDTO = carritoService.obtenerCarritoPorUsuario(idUsuario)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (carritoDTO.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Usuario comprador = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TarjetaCredito tarjeta = tarjetaRepository.findById(idTarjeta)
            .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        EstadoPedido estadoPendiente = estadoPedidoRepository.findById(1)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setComprador(comprador);
        pedido.setTarjeta(tarjeta);
        pedido.setTotal(calcularTotalCarrito(carritoDTO.getItems()));
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaEntregaEstimada(LocalDate.now().plusDays(5));
        pedido.setDireccionEntrega(direccionEntrega);
        pedido.setEstadoPedido(estadoPendiente);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        for (ItemCarritoDTO item : carritoDTO.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedidoGuardado);
            detalle.setProducto(productoRepository.findById(item.getIdProducto()).get());
            detalle.setVendedor(detalle.getProducto().getVendedor());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubtotal(item.getSubtotal());

            BigDecimal comision = item.getSubtotal().multiply(new BigDecimal("0.05"));
            detalle.setComisionPlataforma(comision);

            BigDecimal ganancia = item.getSubtotal().subtract(comision);
            detalle.setGananciaVendedor(ganancia);

            detallePedidoRepository.save(detalle);
        }

        carritoService.limpiarCarrito(idUsuario);

        return convertirADTO(pedidoGuardado);
    }

    private BigDecimal calcularTotalCarrito(List<ItemCarritoDTO> items) {
        return items.stream()
            .map(ItemCarritoDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
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
            pedido.getFechaEntregaEstimada().atStartOfDay(),
            pedido.getDireccionEntrega(),
            pedido.getEstadoPedido().getIdEstadoPedido(),
            pedido.getEstadoPedido().getNombreEstado().toString(),
            detalles
        );
    }

    private DetallePedidoDTO convertirDetalleADTO(DetallePedido detalle) {
        return new DetallePedidoDTO(
            detalle.getIdDetallePedido(),
            detalle.getProducto().getIdProducto(),
            detalle.getProducto().getNombreProducto(),
            detalle.getCantidad(),
            detalle.getPrecioUnitario(),
            detalle.getSubtotal()
        );
    }

    public List<Map<String, Object>> getTop10ProductosMasVendidos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findTop10ProductosMasVendidos(fechaInicio, fechaFin);
    }

    public List<Map<String, Object>> getTop5ClientesMasGanancias(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findTop5ClientesMasGanancias(fechaInicio, fechaFin);
    }

    public List<Map<String, Object>> getTop5ClientesMasProductosVendidos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findTop5ClientesMasProductosVendidos(fechaInicio, fechaFin);
    }

    public List<Map<String, Object>> getTop10ClientesMasPedidos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findTop10ClientesMasPedidos(fechaInicio, fechaFin);
    }

    public Map<String, Object> getDashboardAdmin() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalPedidos", pedidoRepository.count());
        dashboard.put("totalVentasHoy", pedidoRepository.getTotalVentasHoy());
        dashboard.put("pedidosPendientes", pedidoRepository.countByEstadoPedido_IdEstadoPedido(1));
        dashboard.put("pedidosEnCurso", pedidoRepository.countByEstadoPedido_IdEstadoPedido(2));
        dashboard.put("pedidosEntregados", pedidoRepository.countByEstadoPedido_IdEstadoPedido(3));
        return dashboard;
    }

    public List<Map<String, Object>> getVentasMensualesPorAno(int year) {
        return pedidoRepository.getVentasMensualesPorAno(year);
    }
}