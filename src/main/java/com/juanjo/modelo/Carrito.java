package com.juanjo.modelo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class Carrito {
    private List<LineaPedido> lineas = new ArrayList<>();

    public void agregarProducto(Producto producto, int cantidad) {
        // Buscar si ya existe el producto en el carrito
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId().equals(producto.getId())) {
                linea.setCantidad(linea.getCantidad() + cantidad);
                return;
            }
        }

        // Si no existe, crear nueva línea
        LineaPedido linea = new LineaPedido();
        linea.setProducto(producto);
        linea.setCantidad(cantidad);
        linea.setPrecioUnitario(producto.getPrecio()); // Precio al momento de agregar
        lineas.add(linea);
    }

    public void eliminarProducto(Long productoId) {
        lineas.removeIf(l -> l.getProducto().getId().equals(productoId));
    }

    public void actualizarCantidad(Long productoId, int cantidad) {
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId().equals(productoId)) {
                linea.setCantidad(cantidad);
                return;
            }
        }
    }

    public void vaciar() {
        lineas.clear();
    }

    public Double getTotal() {
        return lineas.stream()
                .mapToDouble(l -> l.getPrecioUnitario() * l.getCantidad())
                .sum();
    }

    public Integer getCantidadTotal() {
        return lineas.stream().mapToInt(LineaPedido::getCantidad).sum();
    }
}
