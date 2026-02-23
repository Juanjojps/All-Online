package com.juanjo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juanjo.modelo.Carrito;
import com.juanjo.modelo.Producto;
import com.juanjo.service.PedidoService;
import com.juanjo.service.ProductoService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private Carrito carrito;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carrito.getTotal());
        return "carrito/ver_carrito";
    }

    @PostMapping("/add/{id}")
    public String agregarProducto(@PathVariable Long id, @RequestParam Integer cantidad,
            RedirectAttributes redirectAttributes) {
        Producto producto = productoService.findById(id).orElse(null);
        if (producto != null) {
            if (producto.getStock() >= cantidad) {
                carrito.agregarProducto(producto, cantidad);
                redirectAttributes.addFlashAttribute("mensaje", "Producto añadido al carrito");
            } else {
                redirectAttributes.addFlashAttribute("error", "No hay suficiente stock");
            }
        }
        return "redirect:/producto/" + id;
    }

    @GetMapping("/remove/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        carrito.eliminarProducto(id);
        return "redirect:/carrito";
    }

    @PostMapping("/update/{id}")
    public String actualizarCantidad(@PathVariable Long id, @RequestParam Integer cantidad) {
        if (cantidad <= 0) {
            carrito.eliminarProducto(id);
        } else {
            // Validar stock antes de actualizar? Sería ideal.
            carrito.actualizarCantidad(id, cantidad);
        }
        return "redirect:/carrito";
    }

    @GetMapping("/checkout")
    public String confirmarPedido(RedirectAttributes redirectAttributes) {
        if (carrito.getLineas().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
            return "redirect:/carrito";
        }

        try {
            pedidoService.guardarPedido(carrito.getLineas());
            carrito.vaciar();
            redirectAttributes.addFlashAttribute("mensaje", "Pedido confirmado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al confirmar pedido: " + e.getMessage());
            return "redirect:/carrito";
        }

        return "redirect:/";
    }
}
