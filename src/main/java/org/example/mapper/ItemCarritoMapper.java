package org.example.mapper;

import org.example.dto.ItemCarritoDTO;
import org.example.entity.ItemCarrito;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ItemCarritoMapper {

    public ItemCarritoDTO toDTO(ItemCarrito item) {
        if (item == null) {
            return null;
        }

        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setCantidad(item.getCantidad());

        if (item.getProducto() != null) {
            dto.setProductoId(item.getProducto().getId());
            dto.setNombreProducto(item.getProducto().getNombre());

            // Si el precio viene de Producto como Double, lo convertimos a BigDecimal
            if (item.getProducto().getPrecio() != null) {
                BigDecimal precioUnitario = BigDecimal.valueOf(item.getProducto().getPrecio());
                dto.setPrecioUnitario(precioUnitario);

                // Calculamos el subtotal dinámicamente (Cantidad * Precio)
                if (item.getCantidad() != null) {
                    dto.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad())));
                }
            }
        }

        return dto;
    }
}