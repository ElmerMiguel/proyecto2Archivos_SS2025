package com.archivos.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String asunto, String mensaje) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinatario);
            email.setSubject(asunto);
            email.setText(mensaje);
            email.setFrom("noreply@ecommercegt.com");
            
            mailSender.send(email);
        } catch (Exception e) {
            System.out.println("Error enviando email: " + e.getMessage());
        }
    }

    public void enviarNotificacionCambioEstado(String email, String nombreUsuario, Integer idPedido, String nuevoEstado) {
        String asunto = "Cambio de estado en tu pedido #" + idPedido;
        String mensaje = String.format(
            "Hola %s,\n\n" +
            "Tu pedido #%d ha cambiado de estado a: %s\n\n" +
            "Gracias por comprar en E-Commerce GT.",
            nombreUsuario, idPedido, nuevoEstado
        );
        enviarEmail(email, asunto, mensaje);
    }

    public void enviarNotificacionProductoAprobado(String email, String nombreUsuario, String nombreProducto) {
        String asunto = "Producto aprobado - " + nombreProducto;
        String mensaje = String.format(
            "Hola %s,\n\n" +
            "Tu producto '%s' ha sido aprobado y ya está disponible para la venta.\n\n" +
            "Gracias por vender en E-Commerce GT.",
            nombreUsuario, nombreProducto
        );
        enviarEmail(email, asunto, mensaje);
    }

    public void enviarNotificacionProductoRechazado(String email, String nombreUsuario, String nombreProducto, String razon) {
        String asunto = "Producto rechazado - " + nombreProducto;
        String mensaje = String.format(
            "Hola %s,\n\n" +
            "Tu producto '%s' ha sido rechazado.\n" +
            "Razón: %s\n\n" +
            "Puedes enviar una nueva solicitud corrigiendo los aspectos mencionados.",
            nombreUsuario, nombreProducto, razon
        );
        enviarEmail(email, asunto, mensaje);
    }
}