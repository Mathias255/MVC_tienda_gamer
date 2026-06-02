package org.example.controller;

import org.example.entity.ItemCarrito;
import org.example.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    // GET: Obtener todos los artículos guardados en un carrito específico
    @GetMapping("/{carritoId}")
    public List<ItemCarrito> obtenerItemsDelCarrito(@PathVariable Long carritoId) {
        return itemCarritoRepository.findByCarritoId(carritoId);
    }

    // POST: Añadir un producto al carrito
    @PostMapping("/item")
    public ItemCarrito añadirProducto(@RequestBody ItemCarrito item) {
        return itemCarritoRepository.save(item);
    }

    // PUT: Actualizar la cantidad de un producto dentro del carrito (Ej: subir de 1 a 2 artículos)
    @PutMapping("/item/{itemId}")
    public ResponseEntity<ItemCarrito> actualizarCantidad(@PathVariable Long itemId, @RequestParam Integer cantidad) {
        return itemCarritoRepository.findById(itemId)
                .map(item -> {
                    item.setCantidad(cantidad);
                    return ResponseEntity.ok(itemCarritoRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Quitar un producto específico del carrito
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        if (itemCarritoRepository.existsById(itemId)) {
            itemCarritoRepository.deleteById(itemId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE: Vaciar por completo el carrito (Útil tras completar un pago)
    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long carritoId) {
        itemCarritoRepository.deleteByCarritoId(carritoId);
        return ResponseEntity.noContent().build();
    }
}