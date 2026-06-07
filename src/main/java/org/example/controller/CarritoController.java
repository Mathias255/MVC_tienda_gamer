package org.example.controller;

import org.example.dto.PedidoDTO;
import org.example.service.interfaces.CarritoService;
import org.example.service.interfaces.FacturaPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private FacturaPdfService facturaPdfService;

    /**
     * Endpoint que procesa la compra del carrito y descarga de forma inmediata
     * el archivo PDF de la factura legal generada por el sistema.
     */
    @PostMapping("/finalizar-factura")
    public ResponseEntity<InputStreamResource> descargarFacturaCompra(@AuthenticationPrincipal UserDetails userDetails) {
        // 1. Ejecuta la lógica transaccional que limpia el carrito y descuenta stock
        PedidoDTO pedidoProcesado = carritoService.finalizarCompra(userDetails.getUsername());

        // 2. Transforma el DTO resultante en un flujo binario PDF
        ByteArrayInputStream pdfFlujo = facturaPdfService.generarFacturaPdf(pedidoProcesado);

        // 3. Configura las cabeceras HTTP de descarga para el navegador
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_pedido_" + pedidoProcesado.getId() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfFlujo));
    }
}