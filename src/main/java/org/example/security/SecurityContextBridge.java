package org.example.security;

import org.example.repository.UsuarioRepository;
import org.example.repository.AuditoriaAccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class SecurityContextBridge {

    private static SecurityContextBridge instance;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuditoriaAccesoRepository auditoriaAccesoRepository;

    @PostConstruct
    public void init() {
        instance = this;
    }

    public static UsuarioRepository getUsuarioRepository() {
        return instance.usuarioRepository;
    }

    public static AuditoriaAccesoRepository getAuditoriaAccesoRepository() {
        return instance.auditoriaAccesoRepository;
    }
}