package org.example.repository;

import org.example.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Obtiene todos los pedidos realizados por un usuario específico
    List<Pedido> findByUsuarioId(Long usuarioId);
}