package com.archivos.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemCarritoDTO {
    private Integer idItemCarrito;
    private Integer cantidad;
    private LocalDateTime fechaAgregado;
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructor vacío
    public ItemCarritoDTO() {}

    // Constructor completo
    public ItemCarritoDTO(Integer idItemCarrito, Integer cantidad, LocalDateTime fechaAgregado,
                         Integer idProducto, String nombreProducto, BigDecimal precioUnitario,
                         BigDecimal subtotal) {
        this.idItemCarrito = idItemCarrito;
        this.cantidad = cantidad;
        this.fechaAgregado = fechaAgregado;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public Integer getIdItemCarrito() { return idItemCarrito; }
    public void setIdItemCarrito(Integer idItemCarrito) { this.idItemCarrito = idItemCarrito; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public LocalDateTime getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(LocalDateTime fechaAgregado) { this.fechaAgregado = fechaAgregado; }
    
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}