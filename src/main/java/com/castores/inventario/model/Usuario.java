package com.castores.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String password;

    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}