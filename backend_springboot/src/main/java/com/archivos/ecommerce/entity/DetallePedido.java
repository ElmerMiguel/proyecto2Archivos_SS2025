package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido") // ← CAMBIAR DE "id_detalle"
    private Integer idDetallePedido; // ← CAMBIAR NOMBRE

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "comision_plataforma", nullable = false, precision = 10, scale = 2)
    private BigDecimal comisionPlataforma; // ← AGREGAR CAMPO

    @Column(name = "ganancia_vendedor", nullable = false, precision = 12, scale = 2)
    private BigDecimal gananciaVendedor; // ← AGREGAR CAMPO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Usuario vendedor;
}