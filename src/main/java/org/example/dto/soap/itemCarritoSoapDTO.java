package org.example.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemCarritoSoapDTO", propOrder = {
        "id",
        "productoId",
        "nombreProducto",
        "precioUnitario",
        "cantidad"
})
public class itemCarritoSoapDTO {

    @XmlElement(required = true, nillable = true)
    protected Long id;
    protected Long productoId;
    protected String nombreProducto;
    protected double precioUnitario;
    protected int cantidad;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long value) { this.productoId = value; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String value) { this.nombreProducto = value; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double value) { this.precioUnitario = value; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int value) { this.cantidad = value; }
}