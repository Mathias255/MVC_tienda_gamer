package org.example.endpoints;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.example.dto.ProductoDTO;
import org.example.dto.soap.ProductoSoapDTO;
import org.example.entity.Producto;
import org.example.mapper.ProductoMapper;
import org.example.service.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebService(serviceName = "ProductoService", targetNamespace = "http://org.example/soap")
public class ProductoSoapEndpoint {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    // 🔍 1. CONSULTAR PRODUCTO POR ID
    @WebMethod(operationName = "getProducto")
    @WebResult(name = "producto", targetNamespace = "http://org.example/soap")
    public ProductoSoapDTO getProducto(
            @WebParam(name = "id", targetNamespace = "http://org.example/soap") Long id) {

        ProductoDTO productoDtoRest = productoService.obtenerPorId(id);

        if (productoDtoRest == null) {
            return null;
        }

        Producto productoEntity = productoMapper.toEntity(productoDtoRest);
        return productoMapper.entityToSoapDto(productoEntity);
    }

    // 💾 2. GUARDAR / CREAR NUEVO PRODUCTO
    @WebMethod(operationName = "guardarProducto")
    @WebResult(name = "productoGuardado", targetNamespace = "http://org.example/soap")
    public ProductoSoapDTO guardarProducto(
            @WebParam(name = "productoInput", targetNamespace = "http://org.example/soap") ProductoSoapDTO productoInput) {

        // Convertimos el DTO de SOAP que viene en el XML a la entidad intermedia
        Producto productoEntity = productoMapper.soapDtoToEntity(productoInput);

        // La pasamos al formato DTO que entiende tu capa de servicio REST
        ProductoDTO productoDtoRest = productoMapper.toDTO(productoEntity);

        // Guardamos usando la lógica de tu negocio
        ProductoDTO productoGuardadoRest = productoService.guardar(productoDtoRest);

        // Convertimos el resultado de vuelta a formato SOAP para responder el XML
        Producto resultadoEntity = productoMapper.toEntity(productoGuardadoRest);
        return productoMapper.entityToSoapDto(resultadoEntity);
    }

    // 🔄 3. ACTUALIZAR STOCK DE UN PRODUCTO
    @WebMethod(operationName = "actualizarStock")
    @WebResult(name = "actualizadoExitosamente", targetNamespace = "http://org.example/soap")
    public boolean actualizarStock(
            @WebParam(name = "id", targetNamespace = "http://org.example/soap") Long id,
            @WebParam(name = "nuevoStock", targetNamespace = "http://org.example/soap") int nuevoStock) {

        // Buscamos si el producto gamer existe en la base de datos
        ProductoDTO productoDtoRest = productoService.obtenerPorId(id);

        if (productoDtoRest != null) {
            // Modificamos solo la cantidad de stock
            productoDtoRest.setStock(nuevoStock);

            // Reutilizamos el método guardar para hacer el UPDATE en PostgreSQL
            productoService.guardar(productoDtoRest);
            return true;
        }

        return false; // Retorna false si el producto no existía
    }

    // ❌ 4. ELIMINAR UN PRODUCTO POR ID (Agregado)
    @WebMethod(operationName = "eliminarProducto")
    @WebResult(name = "eliminadoExitosamente", targetNamespace = "http://org.example/soap")
    public boolean eliminarProducto(
            @WebParam(name = "id", targetNamespace = "http://org.example/soap") Long id) {
        try {
            // Buscamos si existe antes de intentar removerlo
            ProductoDTO productoDtoRest = productoService.obtenerPorId(id);
            if (productoDtoRest != null) {
                productoService.eliminar(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Retorna false si ocurre un fallo (ej. la restricción por clave foránea que vimos)
            return false;
        }
    }
}