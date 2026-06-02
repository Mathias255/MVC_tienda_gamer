package org.example.repository;

import org.example.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    // Obtiene todos los productos que componen a un pedido específico
    List<DetallePedido> findByPedidoId(Long pedidoId);
}