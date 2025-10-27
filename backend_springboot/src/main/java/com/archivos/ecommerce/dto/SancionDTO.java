package com.archivos.ecommerce.dto;

import java.time.LocalDateTime;

public class SancionDTO {
    private Integer idSancion;
    private Integer idUsuarioSancionado;
    private String nombreUsuarioSancionado;
    private String emailUsuarioSancionado;
    private Integer idModerador;
    private String nombreModerador;
    private String motivo;
    private String descripcion;
    private Integer diasSuspension;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer idEstadoSancion;
    private String nombreEstado;

    // Constructor vacío
    public SancionDTO() {}

    // Constructor completo
    public SancionDTO(Integer idSancion, Integer idUsuarioSancionado, String nombreUsuarioSancionado,
                     String emailUsuarioSancionado, Integer idModerador, String nombreModerador,
                     String motivo, String descripcion, Integer diasSuspension, LocalDateTime fechaInicio,
                     LocalDateTime fechaFin, Integer idEstadoSancion, String nombreEstado) {
        this.idSancion = idSancion;
        this.idUsuarioSancionado = idUsuarioSancionado;
        this.nombreUsuarioSancionado = nombreUsuarioSancionado;
        this.emailUsuarioSancionado = emailUsuarioSancionado;
        this.idModerador = idModerador;
        this.nombreModerador = nombreModerador;
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.diasSuspension = diasSuspension;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idEstadoSancion = idEstadoSancion;
        this.nombreEstado = nombreEstado;
    }

    // Getters y Setters
    public Integer getIdSancion() { return idSancion; }
    public void setIdSancion(Integer idSancion) { this.idSancion = idSancion; }

    public Integer getIdUsuarioSancionado() { return idUsuarioSancionado; }
    public void setIdUsuarioSancionado(Integer idUsuarioSancionado) { this.idUsuarioSancionado = idUsuarioSancionado; }

    public String getNombreUsuarioSancionado() { return nombreUsuarioSancionado; }
    public void setNombreUsuarioSancionado(String nombreUsuarioSancionado) { this.nombreUsuarioSancionado = nombreUsuarioSancionado; }

    public String getEmailUsuarioSancionado() { return emailUsuarioSancionado; }
    public void setEmailUsuarioSancionado(String emailUsuarioSancionado) { this.emailUsuarioSancionado = emailUsuarioSancionado; }

    public Integer getIdModerador() { return idModerador; }
    public void setIdModerador(Integer idModerador) { this.idModerador = idModerador; }

    public String getNombreModerador() { return nombreModerador; }
    public void setNombreModerador(String nombreModerador) { this.nombreModerador = nombreModerador; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getDiasSuspension() { return diasSuspension; }
    public void setDiasSuspension(Integer diasSuspension) { this.diasSuspension = diasSuspension; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Integer getIdEstadoSancion() { return idEstadoSancion; }
    public void setIdEstadoSancion(Integer idEstadoSancion) { this.idEstadoSancion = idEstadoSancion; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
}