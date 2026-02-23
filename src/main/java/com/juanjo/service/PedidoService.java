package com.juanjo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanjo.modelo.LineaPedido;
import com.juanjo.modelo.Pedido;
import com.juanjo.modelo.Producto;
import com.juanjo.repository.PedidoRepository;
import com.juanjo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Pedido guardarPedido(List<LineaPedido> lineas) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");

        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getPrecioUnitario() * linea.getCantidad();
            linea.setPedido(pedido);

            // Actualizar stock
            Producto producto = linea.getProducto();
            if (producto.getStock() < linea.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);
        }
        pedido.setTotal(total);
        pedido.setLineas(lineas);

        return pedidoRepository.save(pedido);
    }
}
