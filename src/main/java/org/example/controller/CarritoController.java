package org.example.controller;

import org.example.dto.ItemCarritoDTO;
import org.example.entity.ItemCarrito;
import org.example.mapper.ItemCarritoMapper;
import org.example.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ItemCarritoMapper itemCarritoMapper;

    // Obtener todos los items del carrito activos
    @GetMapping
    public List<ItemCarritoDTO> obtenerItems() {
        return itemCarritoRepository.findAll().stream()
                .map(item -> itemCarritoMapper.toDTO(item))
                .collect(Collectors.toList());
    }

    // Eliminar un producto específico del carrito por su ID de item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        if (itemCarritoRepository.existsById(id)) {
            itemCarritoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}