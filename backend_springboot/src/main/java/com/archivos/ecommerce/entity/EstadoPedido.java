package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_pedido")
@Data
public class EstadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pedido")
    private Integer idEstadoPedido;

    @Column(name = "nombre_estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreEstado nombreEstado;

    public enum NombreEstado {
        EN_CURSO, ENTREGADO
    }
}