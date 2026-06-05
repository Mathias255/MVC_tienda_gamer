package org.example.endpoints;

import org.example.dto.soap.GetProductoRequest;
import org.example.dto.soap.GetProductoResponse;
import org.example.entity.Producto;
import org.example.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductoSoapEndpoint {

    // Este URI debe coincidir exactamente con el targetNamespace de tu productos.xsd
    private static final String NAMESPACE_URI = "http://org.example/soap";

    @Autowired
    private ProductoRepository productoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductoRequest")
    @ResponsePayload
    public GetProductoResponse getProducto(@RequestPayload GetProductoRequest request) {
        GetProductoResponse response = new GetProductoResponse();

        // Buscamos el producto en la BD por el ID que viene en la petición XML
        productoRepository.findById(request.getId()).ifPresent(producto -> {
            response.setId(producto.getId());
            response.setNombre(producto.getNombre());
            response.setDescripcion(producto.getDescripcion());

            // Mapeo seguro de valores numéricos
            if (producto.getPrecio() != null) {
                response.setPrecio(producto.getPrecio());
            }
            if (producto.getStock() != null) {
                response.setStock(producto.getStock());
            }

            response.setImagenUrl(producto.getImagenUrl());
        });

        return response;
    }
}