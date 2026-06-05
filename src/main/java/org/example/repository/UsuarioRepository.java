package org.example.repository;

import org.example.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // 🚀 ESTE ES EL QUE EXIGE EL AUTHCONTROLLER EN LA LÍNEA 19
    Optional<Usuario> findByEmail(String email);

    // 🚀 ESTE ES EL QUE USA TU USUARIOSERVICEIMPL
    Optional<Usuario> findByEmailIgnoreCase(String email);

}