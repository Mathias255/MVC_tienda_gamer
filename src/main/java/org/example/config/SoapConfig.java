package org.example.config;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.example.endpoints.ProductoSoapEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private ProductoSoapEndpoint productoSoapEndpoint;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, productoSoapEndpoint);

        // Esto publica tu servicio SOAP en: http://localhost:8080/services/ws/productos
        endpoint.publish("/ws/productos");

        return endpoint;
    }
}