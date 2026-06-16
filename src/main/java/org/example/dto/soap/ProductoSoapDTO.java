package org.example.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id",
        "nombre",
        "descripcion",
        "precio",
        "stock",
        "categoriaId",
        "proveedorId"
})
@XmlRootElement(name = "ProductoSoapDTO")
public class ProductoSoapDTO {

    @XmlElement(required = true, nillable = true)
    protected Long id;
    @XmlElement(required = true)
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected int stock;
    @XmlElement(required = true, nillable = true)
    protected Long categoriaId;
    @XmlElement(required = true, nillable = true)
    protected Long proveedorId;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }

    public double getPrecio() { return precio; }
    public void setPrecio(double value) { this.precio = value; }

    public int getStock() { return stock; }
    public void setStock(int value) { this.stock = value; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long value) { this.categoriaId = value; }

    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long value) { this.proveedorId = value; }
}