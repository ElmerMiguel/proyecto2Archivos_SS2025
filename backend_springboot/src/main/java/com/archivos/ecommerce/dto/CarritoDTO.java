package com.archivos.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CarritoDTO {
    private Integer idCarrito;
    private Integer idUsuario;
    private String nombreUsuario;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaActualizacion;
    private List<ItemCarritoDTO> items;

    // Constructor vacío
    public CarritoDTO() {}

    // Constructor completo
    public CarritoDTO(Integer idCarrito, Integer idUsuario, String nombreUsuario,
                      LocalDateTime fechaCreacion, LocalDateTime fechaUltimaActualizacion,
                      List<ItemCarritoDTO> items) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.items = items;
    }

    // Getters y Setters
    public Integer getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Integer idCarrito) { this.idCarrito = idCarrito; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaUltimaActualizacion() { return fechaUltimaActualizacion; }
    public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) { this.fechaUltimaActualizacion = fechaUltimaActualizacion; }

    public List<ItemCarritoDTO> getItems() { return items; }
    public void setItems(List<ItemCarritoDTO> items) { this.items = items; }

    // AGREGAR: Método para calcular total
    public double getTotal() {
        if (items == null) return 0.0;
        return items.stream()
            .mapToDouble(item -> item.getSubtotal().doubleValue())
            .sum();
    }
}
