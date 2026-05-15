package com.castores.inventario.repository;

import com.castores.inventario.model.Movimiento;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository
        extends JpaRepository<Movimiento, Long> {
    List<Movimiento>
    findByTipoMovimiento(String tipoMovimiento);

}

