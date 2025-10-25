package com.archivos.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "nombre_categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreCategoria nombreCategoria;

    public enum NombreCategoria {
        TECNOLOGIA, HOGAR, ACADEMICO, PERSONAL, DECORACION, OTRO
    }
}