package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_producto")
@Data
public class EstadoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_producto")
    private Integer idEstadoProducto;

    @Column(name = "nombre_estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreEstado nombreEstado;

    public enum NombreEstado {
        NUEVO, USADO
    }
}