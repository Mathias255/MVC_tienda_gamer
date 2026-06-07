package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // 🚀 RELACIÓN 1: Con el Carrito activo (Resuelve el error en CarritoServiceImpl)
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    // 🚀 RELACIÓN 2: Con el Pedido final (Resuelve el error de Hibernate anterior)
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}