package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre_rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreRol nombreRol;

    public enum NombreRol {
        COMUN, MODERADOR, LOGISTICA, ADMINISTRADOR
    }
}