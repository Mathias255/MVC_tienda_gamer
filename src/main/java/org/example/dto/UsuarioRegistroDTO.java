package org.example.dto;

public class UsuarioRegistroDTO {

    private String nombre;
    private String email;
    private String password;
    private String rol;

    // 🚀 OBLIGATORIO: Constructor vacío para que Jackson procese el JSON
    public UsuarioRegistroDTO() {
    }

    public UsuarioRegistroDTO(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}