package org.example.mapper;

import org.example.dto.PedidoDTO;
import org.example.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private ItemCarritoMapper itemCarritoMapper;

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());

        if (pedido.getTotal() != null) {
            dto.setTotal(BigDecimal.valueOf(pedido.getTotal()));
        }

        dto.setEstado(pedido.getEstado());

        if (pedido.getUsuario() != null) {
            dto.setUsuarioId(pedido.getUsuario().getId());
            dto.setNombreCliente(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
            dto.setEmailCliente(pedido.getUsuario().getEmail());
        }

        // 🚀 AHORA SÍ COMPILA: Mapeo real de los ítems usando la nueva relación
        if (pedido.getItems() != null) {
            dto.setItems(pedido.getItems().stream()
                    .map(itemCarritoMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setItems(new ArrayList<>());
        }

        return dto;
    }
}