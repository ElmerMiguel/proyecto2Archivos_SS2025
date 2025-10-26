package com.archivos.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Integer idPedido;
    private Integer idUsuario;
    private String nombreUsuario;
    private BigDecimal total;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEntrega;
    private String direccionEntrega;
    private Integer idEstadoPedido;
    private String nombreEstado;
    private List<DetallePedidoDTO> detalles;

    public PedidoDTO() {}

    public PedidoDTO(Integer idPedido, Integer idUsuario, String nombreUsuario, BigDecimal total,
                    LocalDateTime fechaPedido, LocalDateTime fechaEntrega, String direccionEntrega,
                    Integer idEstadoPedido, String nombreEstado, List<DetallePedidoDTO> detalles) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.total = total;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.direccionEntrega = direccionEntrega;
        this.idEstadoPedido = idEstadoPedido;
        this.nombreEstado = nombreEstado;
        this.detalles = detalles;
    }

    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }
    
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    
    public Integer getIdEstadoPedido() { return idEstadoPedido; }
    public void setIdEstadoPedido(Integer idEstadoPedido) { this.idEstadoPedido = idEstadoPedido; }
    
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoDTO> detalles) { this.detalles = detalles; }
}