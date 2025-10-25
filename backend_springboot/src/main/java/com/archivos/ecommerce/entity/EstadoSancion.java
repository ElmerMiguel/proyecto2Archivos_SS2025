package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_sancion")
@Data
public class EstadoSancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_sancion")
    private Integer idEstadoSancion;

    @Column(name = "nombre_estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreEstado nombreEstado;

    public enum NombreEstado {
        ACTIVA, FINALIZADA
    }
}