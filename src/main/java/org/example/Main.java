package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 🚀 Forzamos a Spring a escanear explícitamente tanto el paquete principal como el de configuración
@SpringBootApplication(scanBasePackages = {"org.example", "org.example.config"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}