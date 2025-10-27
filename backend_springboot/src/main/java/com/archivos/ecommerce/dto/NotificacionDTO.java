package com.archivos.ecommerce.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {
    private Integer idNotificacion;
    private String asunto;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private Boolean enviadoExitosamente;
    private Integer idReferencia;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idTipoNotificacion;
    private String nombreTipo;

    // Constructor vacío
    public NotificacionDTO() {}

    // Constructor completo
    public NotificacionDTO(Integer idNotificacion, String asunto, String mensaje,
                          LocalDateTime fechaEnvio, Boolean enviadoExitosamente, Integer idReferencia,
                          Integer idUsuario, String nombreUsuario, Integer idTipoNotificacion, String nombreTipo) {
        this.idNotificacion = idNotificacion;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.enviadoExitosamente = enviadoExitosamente;
        this.idReferencia = idReferencia;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idTipoNotificacion = idTipoNotificacion;
        this.nombreTipo = nombreTipo;
    }

    // Getters y Setters
    public Integer getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(Integer idNotificacion) { this.idNotificacion = idNotificacion; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getEnviadoExitosamente() { return enviadoExitosamente; }
    public void setEnviadoExitosamente(Boolean enviadoExitosamente) { this.enviadoExitosamente = enviadoExitosamente; }

    public Integer getIdReferencia() { return idReferencia; }
    public void setIdReferencia(Integer idReferencia) { this.idReferencia = idReferencia; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Integer getIdTipoNotificacion() { return idTipoNotificacion; }
    public void setIdTipoNotificacion(Integer idTipoNotificacion) { this.idTipoNotificacion = idTipoNotificacion; }

    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
}