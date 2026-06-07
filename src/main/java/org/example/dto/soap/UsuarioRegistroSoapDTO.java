package org.example.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "nombre",
        "apellido",
        "email",
        "password",
        "rol"
})
@XmlRootElement(name = "UsuarioRegistroSoapDTO")
public class UsuarioRegistroSoapDTO {

    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String apellido;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String password;
    protected String rol;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }

    public String getApellido() { return apellido; }
    public void setApellido(String value) { this.apellido = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getRol() { return rol; }
    public void setRol(String value) { this.rol = value; }
}