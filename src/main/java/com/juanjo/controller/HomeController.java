package com.juanjo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.juanjo.modelo.Producto;
import com.juanjo.service.ProductoService;

@Controller
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String sort,
            Model model) {

        Sort orden = Sort.unsorted();
        if ("precio_asc".equals(sort)) {
            orden = Sort.by("precio").ascending();
        } else if ("precio_desc".equals(sort)) {
            orden = Sort.by("precio").descending();
        }

        List<Producto> productos = productoService.buscar(q, categoria, orden);

        model.addAttribute("productos", productos);
        model.addAttribute("q", q);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("sortSeleccionado", sort);

        // Categorías obtenidas dinámicamente de la DB
        model.addAttribute("categorias", productoService.findDistinctCategorias());

        return "home";
    }

    @GetMapping("/producto/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto); 
        return "producto/detalle";
    }
}
