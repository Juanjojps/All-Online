package com.juanjo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.juanjo.modelo.Producto;
import com.juanjo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<String> findDistinctCategorias() {
        return productoRepository.findDistinctCategorias();
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findAll(Sort sort) {
        return productoRepository.findAll(sort);
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> buscar(String nombre, String categoria) {
        if (nombre != null && !nombre.isEmpty() && categoria != null && !categoria.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCaseAndCategoria(nombre, categoria);
        } else if (nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (categoria != null && !categoria.isEmpty()) {
            return productoRepository.findByCategoria(categoria);
        }
        return findAll();
    }

    // Sobrecarga para incluir ordenación en la búsqueda
    public List<Producto> buscar(String nombre, String categoria, Sort sort) {
        if (nombre != null && !nombre.isEmpty() && categoria != null && !categoria.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCaseAndCategoria(nombre, categoria, sort);
        } else if (nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre, sort);
        } else if (categoria != null && !categoria.isEmpty()) {
            return productoRepository.findByCategoria(categoria, sort);
        }
        return productoRepository.findAll(sort);
    }
}
