package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @Column(name = "asunto", nullable = false, length = 200)
    private String asunto;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "enviado_exitosamente", nullable = false)
    private Boolean enviadoExitosamente = true;

    @Column(name = "id_referencia")
    private Integer idReferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_notificacion", nullable = false)
    private TipoNotificacion tipoNotificacion;
}