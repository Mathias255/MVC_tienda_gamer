package org.example.service.interfaces;

import org.example.dto.PedidoDTO;
import java.io.ByteArrayInputStream;

/**
 * Servicio encargado de la generación y exportación de comprobantes
 * comerciales en formato PDF para la tienda gamer.
 *
 * @author Mathias
 * @version 1.0
 */
public interface FacturaPdfService {

    /**
     * Genera un archivo PDF en memoria con el detalle completo del pedido.
     *
     * @param pedido El DTO con la información de la compra procesada.
     * @return Un {@link ByteArrayInputStream} que contiene el archivo binario del PDF listo para su descarga.
     */
    ByteArrayInputStream generarFacturaPdf(PedidoDTO pedido);
}