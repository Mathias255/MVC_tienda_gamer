package org.example.service.interfaces;

import org.example.dto.PedidoDTO;

public interface CarritoService {
    // Este método buscará el carrito del usuario, validará stock,
    // restará las cantidades, creará el Pedido/Detalle y vaciará el carrito.
    PedidoDTO finalizarCompra(String username);
}