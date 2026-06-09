package org.example.mapper;

import org.example.dto.ProductoDTO;
import org.example.dto.soap.ProductoSoapDTO;
import org.example.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

// 🚀 unmappedTargetPolicy = ReportingPolicy.IGNORE hace que MapStruct ignore
// automáticamente cualquier campo que falte en los DTOs (como imagenUrl o categoriaId)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductoMapper {

    // --- MAPEO PARA REST (ProductoDTO) ---
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoDTO toDTO(Producto producto);

    @Mapping(source = "categoriaId", target = "categoria.id")
    Producto toEntity(ProductoDTO dto);

    // --- MAPEO PARA SOAP (ProductoSoapDTO) ---
    ProductoSoapDTO entityToSoapDto(Producto producto);
    Producto soapDtoToEntity(ProductoSoapDTO soapDto);
}