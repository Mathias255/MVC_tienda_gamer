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
        "email",
        "rol",
        "token"
})
@XmlRootElement(name = "UsuarioRepuestaSoapDTO")
public class UsuarioRepuestaSoapDTO {

    protected long id;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String rol;
    protected String token;

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getRol() { return rol; }
    public void setRol(String value) { this.rol = value; }

    public String getToken() { return token; }
    public void setToken(String value) { this.token = value; }
}