package com.archivos.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcion;
    private String urlImagen;
    private BigDecimal precio;
    private Integer stock;
    private Boolean activo;
    private LocalDateTime fechaPublicacion;
    
    // Datos del vendedor
    private Integer idVendedor;
    private String nombreVendedor;
    
    // Datos de categoría
    private Integer idCategoria;
    private String nombreCategoria;
    
    // Datos de estado
    private Integer idEstadoProducto;
    private String nombreEstado;

    // Constructor vacío
    public ProductoDTO() {}

    // Constructor completo
    public ProductoDTO(Integer idProducto, String nombreProducto, String descripcion, 
                      String urlImagen, BigDecimal precio, Integer stock, Boolean activo,
                      LocalDateTime fechaPublicacion, Integer idVendedor, String nombreVendedor,
                      Integer idCategoria, String nombreCategoria, Integer idEstadoProducto, String nombreEstado) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
        this.fechaPublicacion = fechaPublicacion;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.idEstadoProducto = idEstadoProducto;
        this.nombreEstado = nombreEstado;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    
    public Integer getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Integer idVendedor) { this.idVendedor = idVendedor; }
    
    public String getNombreVendedor() { return nombreVendedor; }
    public void setNombreVendedor(String nombreVendedor) { this.nombreVendedor = nombreVendedor; }
    
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
    
    public Integer getIdEstadoProducto() { return idEstadoProducto; }
    public void setIdEstadoProducto(Integer idEstadoProducto) { this.idEstadoProducto = idEstadoProducto; }
    
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
}