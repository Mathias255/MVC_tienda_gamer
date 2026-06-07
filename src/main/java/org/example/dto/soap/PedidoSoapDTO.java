package org.example.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id",
        "usuarioId",
        "fecha",
        "estado",
        "total",
        "items"
})
@XmlRootElement(name = "PedidoSoapDTO")
public class PedidoSoapDTO {

    @XmlElement(required = true, nillable = true)
    protected Long id;
    protected long usuarioId;
    protected String fecha;
    @XmlElement(required = true)
    protected String estado;
    protected double total;
    protected List<itemCarritoSoapDTO> items;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long value) { this.usuarioId = value; }

    public String getFecha() { return fecha; }
    public void setFecha(String value) { this.fecha = value; }

    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }

    public double getTotal() { return total; }
    public void setTotal(double value) { this.total = value; }

    public List<itemCarritoSoapDTO> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return this.items;
    }
    public void setItems(List<itemCarritoSoapDTO> items) { this.items = items; }
}