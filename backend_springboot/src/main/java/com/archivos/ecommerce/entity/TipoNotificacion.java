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

    @Column(name = "nombre_tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreTipo nombreTipo;

    public enum NombreTipo {
        PRODUCTO_APROBADO, PRODUCTO_RECHAZADO, PEDIDO_REALIZADO, 
        PEDIDO_ENTREGADO, SANCION_APLICADA, SANCION_FINALIZADA
    }
}