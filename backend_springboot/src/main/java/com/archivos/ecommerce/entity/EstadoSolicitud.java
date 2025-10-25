package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_solicitud")
@Data
public class EstadoSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_solicitud")
    private Integer idEstadoSolicitud;

    @Column(name = "nombre_estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreEstado nombreEstado;

    public enum NombreEstado {
        PENDIENTE, APROBADO, RECHAZADO
    }
}