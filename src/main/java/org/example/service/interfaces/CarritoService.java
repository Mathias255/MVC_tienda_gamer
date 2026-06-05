package org.example.service.interfaces;

import org.example.entity.ItemCarrito;
import java.util.List;

public interface CarritoService {
    List<ItemCarrito> obtenerItems(Long carritoId);
    ItemCarrito agregarProducto(Long carritoId, Long productoId, Integer cantidad);
    ItemCarrito actualizarCantidad(Long itemId, Integer cantidad);
    void eliminarItem(Long itemId);
    void vaciarCarrito(Long carritoId);
}