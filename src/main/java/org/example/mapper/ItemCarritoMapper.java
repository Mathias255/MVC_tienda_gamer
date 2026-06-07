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
        dto.setId(item.getId()); // Inyectamos el ID propio del registro
        dto.setCantidad(item.getCantidad());

        if (item.getProducto() != null) {
            dto.setProductoId(item.getProducto().getId());
            dto.setNombreProducto(item.getProducto().getNombre());

            if (item.getProducto().getPrecio() != null) {
                BigDecimal precioUnitario = BigDecimal.valueOf(item.getProducto().getPrecio());
                dto.setPrecioUnitario(precioUnitario);

                if (item.getCantidad() != null) {
                    dto.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad())));
                }
            }
        }

        return dto;
    }

    public ItemCarrito toEntity(ItemCarritoDTO dto) {
        if (dto == null) {
            return null;
        }

        ItemCarrito item = new ItemCarrito();
        item.setId(dto.getId());
        item.setCantidad(dto.getCantidad());
        // El amarre de las relaciones complejas (Producto y Carrito) lo maneja el Service

        return item;
    }
}