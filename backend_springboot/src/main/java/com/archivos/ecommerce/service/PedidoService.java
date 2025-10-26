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

    // Listar todos los pedidos
    public List<PedidoDTO> listarTodosLosPedidos() {
        return pedidoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Obtener pedido por ID
    public Optional<PedidoDTO> obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
            .map(this::convertirADTO);
    }

    // Obtener pedidos por usuario
    public List<PedidoDTO> obtenerPedidosPorUsuario(Integer idUsuario) {
        return pedidoRepository.findByComprador_IdUsuario(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Crear pedido desde el carrito con tarjeta
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
    
    // Calcular comisión (5% del subtotal)
    BigDecimal comision = item.getSubtotal().multiply(new BigDecimal("0.05"));
    detalle.setComisionPlataforma(comision);
    
    // Calcular ganancia vendedor (subtotal - comisión)
    BigDecimal ganancia = item.getSubtotal().subtract(comision);
    detalle.setGananciaVendedor(ganancia);

    detallePedidoRepository.save(detalle);
}

        carritoService.limpiarCarrito(idUsuario);

        return convertirADTO(pedidoGuardado);
    }

    // Calcular total del carrito
    private BigDecimal calcularTotalCarrito(List<ItemCarritoDTO> items) {
        return items.stream()
            .map(ItemCarritoDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Convertir Pedido a DTO
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

    // Convertir DetallePedido a DTO
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
}
