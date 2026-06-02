package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 1. Aplica a todas las rutas de la API de videojuegos

                        // 2. CONEXIÓN CON ANGULAR: Autoriza explícitamente el puerto por defecto de Angular (4200)
                        // Esto evita que el navegador bloquee las peticiones HTTP que haga tu frontend
                        .allowedOrigins("http://localhost:4200")

                        // 3. MÉTODOS PERMITIDOS: Permite que Angular realice todas las operaciones necesarias
                        // GET (Ver juegos), POST (Crear), PUT (Modificar stock/precio), DELETE (Borrar)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // 4. CABECERAS: Permite enviar datos especiales en las peticiones (como JSON o tokens)
                        .allowedHeaders("*")

                        // 5. CREDENCIALES: Permite el envío de cookies o sesiones si Angular lo requiere en el futuro
                        .allowCredentials(true);
            }
        };
    }
}
