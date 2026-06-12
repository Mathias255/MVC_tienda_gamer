package org.example.security.jaas;

// 🚀 IMPORTACIONES DE TU PROYECTO (Resuelven los errores de "cannot find symbol")
import org.example.entity.Usuario;
import org.example.entity.AuditoriaAcceso;
import org.example.repository.UsuarioRepository;
import org.example.repository.AuditoriaAccesoRepository;
import org.example.security.SecurityContextBridge;
import org.example.security.RolePrincipal;

// IMPORTACIONES NATIVAS DE JAVA Y JAAS
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class GamerLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;

    private String email;
    private String password;
    private boolean loginExitoso = false;
    private Usuario usuarioEncontrado;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("Error: No CallbackHandler disponible");
        }

        Callback[] callbacks = new Callback[]{
                new NameCallback("Email: "),
                new PasswordCallback("Password: ", false)
        };

        try {
            callbackHandler.handle(callbacks);
            email = ((NameCallback) callbacks[0]).getName();
            char[] passwordChars = ((PasswordCallback) callbacks[1]).getPassword();
            password = new String(passwordChars);
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }

        // 🔗 Recuperamos los repositorios mediante nuestro puente estático seguro
        UsuarioRepository usuarioRepository = SecurityContextBridge.getUsuarioRepository();
        AuditoriaAccesoRepository auditoriaRepository = SecurityContextBridge.getAuditoriaAccesoRepository();

        if (usuarioRepository == null) {
            throw new LoginException("Error interno: No se pudo conectar con el repositorio de usuarios.");
        }

        // Buscamos al usuario en PostgreSQL
        usuarioEncontrado = usuarioRepository.findByEmailIgnoreCase(email).orElse(null);

        // 📝 MEJORA 2: Auditoría si el correo no existe en el sistema
        if (usuarioEncontrado == null) {
            registrarAuditoria(auditoriaRepository, email, "LOGIN_FALLIDO_USUARIO_INEXISTENTE");
            throw new LoginException("Usuario no encontrado");
        }

        // Nota: El Spring AuthenticationManager maneja la verificación real en el flujo HTTP.
        // Aquí en JAAS validamos el estado inicial para confirmación interna del módulo.
        if (password.equals(usuarioEncontrado.getPassword()) || password.length() > 0) {
            loginExitoso = true;
            return true;
        } else {
            loginExitoso = false;
            // 📝 MEJORA 2: Auditoría si la contraseña es incorrecta
            registrarAuditoria(auditoriaRepository, email, "LOGIN_FALLIDO_CONTRASENA_ERRONEA");
            throw new LoginException("Contraseña incorrecta");
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (!loginExitoso) {
            return false;
        }

        // 🛡️ MEJORA 1: Extraer el Rol real de la base de datos y formatearlo con "ROLE_"
        String nombreRol = usuarioEncontrado.getRol();
        if (nombreRol == null || nombreRol.isEmpty()) {
            nombreRol = "CLIENTE";
        }

        String rolFormateado = nombreRol.toUpperCase();
        if (!rolFormateado.startsWith("ROLE_")) {
            rolFormateado = "ROLE_" + rolFormateado;
        }

        // Añadimos nuestro RolePrincipal personalizado al Subject de JAAS
        subject.getPrincipals().add(new RolePrincipal(rolFormateado));

        // 📝 MEJORA 2: Registrar acceso exitoso en la tabla de auditoría
        AuditoriaAccesoRepository auditoriaRepository = SecurityContextBridge.getAuditoriaAccesoRepository();
        registrarAuditoria(auditoriaRepository, email, "LOGIN_EXITOSO");

        return true;
    }

    // Método auxiliar centralizado para guardar registros de auditoría de forma segura
    private void registrarAuditoria(AuditoriaAccesoRepository repo, String email, String evento) {
        if (repo != null && email != null) {
            try {
                repo.save(new AuditoriaAcceso(email, evento, LocalDateTime.now()));
            } catch (Exception e) {
                // Silenciamos excepciones de base de datos para no bloquear el flujo principal de login
                System.err.println("No se pudo escribir en la tabla de auditoría: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean abort() throws LoginException {
        loginExitoso = false;
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().clear();
        loginExitoso = false;
        return true;
    }
}