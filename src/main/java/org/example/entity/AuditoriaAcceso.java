package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_accesos")
public class AuditoriaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String evento; // "LOGIN_EXITOSO" o "LOGIN_FALLIDO"
    private LocalDateTime fechaHora;

    // Constructores, Getters y Setters
    public AuditoriaAcceso() {}

    public AuditoriaAcceso(String email, String evento, LocalDateTime fechaHora) {
        this.email = email;
        this.evento = evento;
        this.fechaHora = fechaHora;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getEvento() { return evento; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}