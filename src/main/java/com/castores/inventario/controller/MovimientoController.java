package com.castores.inventario.controller;

import com.castores.inventario.repository.MovimientoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovimientoController {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @GetMapping("/movimientos")
    public String listarMovimientos(
            @RequestParam(required = false)
            String tipo,
            Model model
    ){

        if(tipo != null && !tipo.isEmpty()){

            model.addAttribute(
                    "movimientos",
                    movimientoRepository
                            .findByTipoMovimiento(tipo)
            );

        }else{

            model.addAttribute(
                    "movimientos",
                    movimientoRepository.findAll()
            );
        }

        return "movimientos";
    }
}