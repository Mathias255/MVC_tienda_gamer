package org.example.mapper;

import org.example.dto.PedidoDTO;
import org.example.entity.Pedido;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());

        // Conversión segura del total a BigDecimal
        if (pedido.getTotal() != null) {
            dto.setTotal(BigDecimal.valueOf(pedido.getTotal()));
        }

        dto.setEstado(pedido.getEstado());

        // Mapeamos los datos del cliente de forma segura
        if (pedido.getUsuario() != null) {
            dto.setUsuarioId(pedido.getUsuario().getId());
            dto.setNombreCliente(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
            dto.setEmailCliente(pedido.getUsuario().getEmail());
        }

        // Inicializamos la lista vacía para que no tire NullPointerException en el Front
        dto.setItems(new ArrayList<>());

        return dto;
    }
}