package com.archivos.ecommerce.dto;

import java.math.BigDecimal;

public class DetallePedidoDTO {
    private Integer idDetallePedido;
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetallePedidoDTO() {}

    public DetallePedidoDTO(Integer idDetallePedido, Integer idProducto, String nombreProducto,
                           Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idDetallePedido = idDetallePedido;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Integer getIdDetallePedido() { return idDetallePedido; }
    public void setIdDetallePedido(Integer idDetallePedido) { this.idDetallePedido = idDetallePedido; }
    
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}