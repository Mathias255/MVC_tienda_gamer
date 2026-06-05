package org.example.mapper;

import org.example.dto.ProductoDTO;
import org.example.entity.Producto;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());

        // Corrección del tipo de dato del precio
        if (producto.getPrecio() != null) {
            dto.setPrecio(BigDecimal.valueOf(producto.getPrecio()));
        }

        dto.setStock(producto.getStock());
        dto.setImagenUrl(producto.getImagenUrl());

        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
        }

        return dto;
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());

        // Corrección al mapear de regreso a Entity (de BigDecimal a Double)
        if (dto.getPrecio() != null) {
            producto.setPrecio(dto.getPrecio().doubleValue());
        }

        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());

        return producto;
    }
}