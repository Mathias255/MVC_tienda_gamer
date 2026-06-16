package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metodos_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // Ej: "Tarjeta de Crédito", "PayPal", "Transferencia"

    @Column(length = 255)
    private String descripcion; // Detalles adicionales (ej: "Acepta todas las tarjetas")

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(length = 50)
    private String tipo; // CATEGORÍA: "TARJETA", "DIGITAL", "EFECTIVO"

    @Column(name = "comision_porcentaje")
    private Double comisionPorcentaje = 0.0; // Posible recargo por usar este método
}