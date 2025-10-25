package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarjetas_credito")
@Data
public class TarjetaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "numero_tarjeta_encriptado", nullable = false)
    private String numeroTarjetaEncriptado;

    @Column(name = "nombre_titular", nullable = false, length = 200)
    private String nombreTitular;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(name = "cvv_encriptado", nullable = false)
    private String cvvEncriptado;

    @Column(name = "tipo_tarjeta", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;

    @Column(name = "es_principal", nullable = false)
    private Boolean esPrincipal = false;

    @Column(name = "fecha_agregada", nullable = false)
    private LocalDateTime fechaAgregada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public enum TipoTarjeta {
        VISA, MASTERCARD, AMEX
    }
}