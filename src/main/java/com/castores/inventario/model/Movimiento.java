package com.castores.inventario.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoMovimiento;

    private Integer cantidad;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}