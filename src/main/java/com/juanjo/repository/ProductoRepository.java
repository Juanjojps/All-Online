package com.juanjo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.juanjo.modelo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();

    List<Producto> findByNombreContainingIgnoreCase(String nombre, Sort sort);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria(String categoria, Sort sort);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByNombreContainingIgnoreCaseAndCategoria(String nombre, String categoria, Sort sort);

    List<Producto> findByNombreContainingIgnoreCaseAndCategoria(String nombre, String categoria);
}
