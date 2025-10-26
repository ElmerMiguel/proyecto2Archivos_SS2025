package com.archivos.ecommerce.dto;

import java.time.LocalDateTime;

public class UsuarioDTO {
    private Integer idUsuario;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
    private Boolean suspendido;
    private LocalDateTime fechaSuspension;
    private Integer idRol;
    private String nombreRol;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer idUsuario, String nombreCompleto, String email, String telefono,
                     String direccion, LocalDateTime fechaRegistro, Boolean activo, Boolean suspendido,
                     LocalDateTime fechaSuspension, Integer idRol, String nombreRol) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
        this.suspendido = suspendido;
        this.fechaSuspension = fechaSuspension;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public Boolean getSuspendido() { return suspendido; }
    public void setSuspendido(Boolean suspendido) { this.suspendido = suspendido; }
    
    public LocalDateTime getFechaSuspension() { return fechaSuspension; }
    public void setFechaSuspension(LocalDateTime fechaSuspension) { this.fechaSuspension = fechaSuspension; }
    
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}