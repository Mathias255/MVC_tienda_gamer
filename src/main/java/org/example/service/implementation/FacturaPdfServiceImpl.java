package org.example.service.implementation;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.dto.PedidoDTO;
import org.example.service.interfaces.FacturaPdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.io.ByteArrayInputStream;

@Service
public class FacturaPdfServiceImpl implements FacturaPdfService {

    @Override
    public ByteArrayInputStream generarFacturaPdf(PedidoDTO pedido) {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.DARK_GRAY);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(0, 128, 255));

            Paragraph titulo = new Paragraph("TIENDA GAMER - COMPROBANTE DE COMPRA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15);
            document.add(titulo);

            Paragraph infoPedido = new Paragraph(
                    "Pedido ID: " + pedido.getId() + "\n" +
                            "Fecha Emisión: " + pedido.getFechaPedido() + "\n" +
                            "Estado de la Transacción: " + pedido.getEstado() + "\n\n",
                    fontSubtitulo
            );
            document.add(infoPedido);

            // Resumen de caja simplificado para compilar directo
            Paragraph totalBlock = new Paragraph("TOTAL PAGADO: $" + pedido.getTotal(), fontTotal);
            totalBlock.setAlignment(Element.ALIGN_LEFT);
            document.add(totalBlock);

            document.close();

        } catch (DocumentException ex) {
            throw new RuntimeException("Error al construir el PDF", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}