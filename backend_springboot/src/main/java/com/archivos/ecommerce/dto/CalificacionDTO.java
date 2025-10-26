package com.archivos.ecommerce.dto;

import java.time.LocalDateTime;

public class CalificacionDTO {
    private Integer idCalificacion;
    private Integer idProducto;
    private String nombreProducto;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idPedido;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaCalificacion;

    // Constructor vacío
    public CalificacionDTO() {}

    // Constructor completo
    public CalificacionDTO(Integer idCalificacion, Integer idProducto, String nombreProducto,
                          Integer idUsuario, String nombreUsuario, Integer idPedido,
                          Integer puntuacion, String comentario, LocalDateTime fechaCalificacion) {
        this.idCalificacion = idCalificacion;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idPedido = idPedido;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaCalificacion = fechaCalificacion;
    }

    // Getters y Setters
    public Integer getIdCalificacion() { return idCalificacion; }
    public void setIdCalificacion(Integer idCalificacion) { this.idCalificacion = idCalificacion; }
    
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }
    
    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }
    
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    
    public LocalDateTime getFechaCalificacion() { return fechaCalificacion; }
    public void setFechaCalificacion(LocalDateTime fechaCalificacion) { this.fechaCalificacion = fechaCalificacion; }
}