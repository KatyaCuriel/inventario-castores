package com.castores.inventario.controller;

import com.castores.inventario.model.Producto;

import com.castores.inventario.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import com.castores.inventario.model.Movimiento;
import com.castores.inventario.repository.MovimientoRepository;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;

    // LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model){

        model.addAttribute(
                "productos",
                productoRepository.findAll()
        );

        return "productos";
    }

    // FORMULARIO NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){

        model.addAttribute(
                "producto",
                new Producto()
        );

        return "nuevo-producto";
    }

    // GUARDAR PRODUCTO
    @PostMapping("/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto
    ){

        producto.setStock(0);

        producto.setActivo(true);

        productoRepository.save(producto);

        return "redirect:/productos";


    }

    // FORMULARIO ENTRADA
    @GetMapping("/entrada/{id}")
    public String mostrarEntrada(
            @PathVariable Long id,
            Model model
    ){

        Producto producto =
                productoRepository.findById(id).orElse(null);

        model.addAttribute("producto", producto);

        return "entrada-producto";
    }

    // GUARDAR ENTRADA
    @PostMapping("/entrada")
    public String guardarEntrada(
            @RequestParam Long idProducto,
            @RequestParam Integer cantidad
    ){

        Producto producto =
                productoRepository.findById(idProducto)
                        .orElse(null);

        if(producto != null){

            producto.setStock(
                    producto.getStock() + cantidad
            );

            productoRepository.save(producto);

            Movimiento movimiento = new Movimiento();

            movimiento.setTipoMovimiento("ENTRADA");

            movimiento.setCantidad(cantidad);

            movimiento.setFecha(
                    java.time.LocalDateTime.now()
            );

            movimiento.setProducto(producto);

            movimientoRepository.save(movimiento);
        }

        return "redirect:/productos";
    }

    // FORMULARIO SALIDA
    @GetMapping("/salida/{id}")
    public String mostrarSalida(
            @PathVariable Long id,
            Model model
    ){

        Producto producto =
                productoRepository.findById(id)
                        .orElse(null);

        model.addAttribute("producto", producto);

        return "salida-producto";
    }

    // GUARDAR SALIDA
    @PostMapping("/salida")
    public String guardarSalida(
            @RequestParam Long idProducto,
            @RequestParam Integer cantidad
    ){

        Producto producto =
                productoRepository.findById(idProducto)
                        .orElse(null);

        if(producto != null){

            // VALIDAR STOCK
            if(producto.getStock() >= cantidad){

                producto.setStock(
                        producto.getStock() - cantidad
                );

                productoRepository.save(producto);

                Movimiento movimiento =
                        new Movimiento();

                movimiento.setTipoMovimiento("SALIDA");

                movimiento.setCantidad(cantidad);

                movimiento.setFecha(
                        java.time.LocalDateTime.now()
                );

                movimiento.setProducto(producto);

                movimientoRepository.save(movimiento);
            }
        }

        return "redirect:/productos?error=stock";
    }
    @GetMapping("/toggle/{id}")
    public String cambiarEstatus(
            @PathVariable Long id
    ){

        Producto producto =
                productoRepository.findById(id)
                        .orElse(null);

        if(producto != null){

            producto.setActivo(
                    !producto.isActivo()
            );

            productoRepository.save(producto);
        }

        return "redirect:/productos";
    }

}