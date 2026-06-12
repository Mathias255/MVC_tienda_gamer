package org.example.service.implementation;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 5;
    // Bloqueo de 15 minutos (en milisegundos)
    private final long BLOCK_TIME = TimeUnit.MINUTES.toMillis(15);

    // Guardará: "email" -> Información del intento (Intentos, Tiempo de bloqueo)
    private final ConcurrentHashMap<String, AttemptInfo> attemptsCache = new ConcurrentHashMap<>();

    // Registra un intento fallido
    public void loginFailed(String email) {
        String key = email.toLowerCase();
        AttemptInfo info = attemptsCache.getOrDefault(key, new AttemptInfo(0, 0));

        info.attempts++;
        if (info.attempts >= MAX_ATTEMPT) {
            info.lockTime = System.currentTimeMillis() + BLOCK_TIME;
        }
        attemptsCache.put(key, info);
    }

    // Resetea los intentos cuando el usuario logra loguearse con éxito
    public void loginSucceeded(String email) {
        attemptsCache.remove(email.toLowerCase());
    }

    // Verifica si el email está bloqueado actualmente
    public boolean isBlocked(String email) {
        String key = email.toLowerCase();
        AttemptInfo info = attemptsCache.get(key);
        if (info == null) {
            return false;
        }

        // Si ya pasó el tiempo de bloqueo, lo liberamos automáticamente
        if (info.lockTime > 0 && System.currentTimeMillis() > info.lockTime) {
            attemptsCache.remove(key);
            return false;
        }

        return info.attempts >= MAX_ATTEMPT;
    }

    // Clase interna para estructurar la información en memoria
    private static class AttemptInfo {
        int attempts;
        long lockTime;

        AttemptInfo(int attempts, long lockTime) {
            this.attempts = attempts;
            this.lockTime = lockTime;
        }
    }
}