package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipos_notificacion")
@Data
public class TipoNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_notificacion")
    private Integer idTipoNotificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_tipo", nullable = false)
    private NombreTipo nombreTipo;

    public enum NombreTipo {
        CAMBIO_ESTADO_PEDIDO,     // ← AGREGADO según DDL.sql
        PRODUCTO_APROBADO,        // ← YA EXISTÍA
        PRODUCTO_RECHAZADO        // ← YA EXISTÍA
    }
}