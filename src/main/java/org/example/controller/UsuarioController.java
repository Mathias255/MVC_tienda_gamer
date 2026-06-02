package org.example.controller;

import org.example.entity.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Registrar un nuevo usuario / cliente (Separa Nombre Completo en Nombre y Apellido)
    @PostMapping
    public Usuario registrar(@RequestBody Usuario usuario) {
        if ((usuario.getApellido() == null || usuario.getApellido().trim().isEmpty())
                && usuario.getNombre() != null) {

            String nombreCompleto = usuario.getNombre().trim();
            int primerEspacio = nombreCompleto.indexOf(" ");

            if (primerEspacio != -1) {
                String soloNombre = nombreCompleto.substring(0, primerEspacio).trim();
                String soloApellido = nombreCompleto.substring(primerEspacio).trim();

                usuario.setNombre(soloNombre);
                usuario.setApellido(soloApellido);
            } else {
                usuario.setNombre(nombreCompleto);
                usuario.setApellido(".");
            }
        }
        return usuarioRepository.save(usuario);
    }

    // POST: Autenticar un usuario (Login) - Soluciona el Error 405
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginDatos) {
        // Busca el usuario por email
        java.util.Optional<Usuario> usuarioOpt = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(loginDatos.getEmail()))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verifica que la contraseña coincida
            if (usuario.getPassword() != null && usuario.getPassword().equals(loginDatos.getPassword())) {
                return ResponseEntity.ok(usuario); // Login correcto
            }
        }

        // Si falla la contraseña o el correo no existe, devuelve 401 (No autorizado)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Correo electrónico o contraseña incorrectos");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioDetalles.getNombre());
                    usuario.setApellido(usuarioDetalles.getApellido());
                    usuario.setEmail(usuarioDetalles.getEmail());
                    if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()) {
                        usuario.setPassword(usuarioDetalles.getPassword());
                    }
                    usuario.setRol(usuarioDetalles.getRol());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}