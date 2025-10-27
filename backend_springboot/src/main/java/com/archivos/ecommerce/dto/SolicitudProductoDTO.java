package com.archivos.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitudProductoDTO {
    private Integer idSolicitud;
    private String nombreProducto;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer idVendedor;
    private String nombreVendedor;
    private Integer idCategoria;
    private String nombreCategoria;
    private Integer idEstadoSolicitud;
    private String nombreEstado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
    private String comentarioModerador;

    // Constructor vacío
    public SolicitudProductoDTO() {}

    // Constructor completo
    public SolicitudProductoDTO(Integer idSolicitud, String nombreProducto, String descripcion,
                               BigDecimal precio, Integer stock, Integer idVendedor, String nombreVendedor,
                               Integer idCategoria, String nombreCategoria, Integer idEstadoSolicitud,
                               String nombreEstado, LocalDateTime fechaSolicitud, LocalDateTime fechaRespuesta,
                               String comentarioModerador) {
        this.idSolicitud = idSolicitud;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.nombreEstado = nombreEstado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRespuesta = fechaRespuesta;
        this.comentarioModerador = comentarioModerador;
    }

    // Getters y Setters
    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Integer idVendedor) { this.idVendedor = idVendedor; }

    public String getNombreVendedor() { return nombreVendedor; }
    public void setNombreVendedor(String nombreVendedor) { this.nombreVendedor = nombreVendedor; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public Integer getIdEstadoSolicitud() { return idEstadoSolicitud; }
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) { this.idEstadoSolicitud = idEstadoSolicitud; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(LocalDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }

    public String getComentarioModerador() { return comentarioModerador; }
    public void setComentarioModerador(String comentarioModerador) { this.comentarioModerador = comentarioModerador; }
}