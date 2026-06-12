package org.example.controller;

import org.example.dto.ProductoDTO;
import org.example.service.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            return ResponseEntity.ok(productoService.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener productos: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ProductoDTO producto = productoService.obtenerPorId(id);
            return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al buscar producto: " + e.getMessage()));
        }
    }

    // ➕ CREAR PRODUCTO BLINDADO
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoDTO productoDTO) {
        try {
            System.out.println("[DEBUG POST] Intentando crear producto: " + productoDTO.getNombre());

            // Validación básica preventiva
            if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El nombre del producto es obligatorio"));
            }

            ProductoDTO nuevoProducto = productoService.guardar(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            System.err.println("[ERROR POST] No se pudo crear el producto. Motivo: " + e.getMessage());
            e.printStackTrace(); // Esto imprime el error exacto en la consola de IntelliJ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al crear: " + e.getMessage()));
        }
    }

    // 🔄 ACTUALIZAR PRODUCTO (PUT) BLINDADO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        try {
            System.out.println("[DEBUG PUT] Intentando modificar producto ID: " + id);

            ProductoDTO actualizado = productoService.actualizar(id, productoDTO);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            System.err.println("[ERROR PUT] No se pudo actualizar el producto ID " + id + ". Motivo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al actualizar: " + e.getMessage()));
        }
    }

    // ❌ ELIMINAR PRODUCTO (DELETE) BLINDADO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            System.out.println("[DEBUG DELETE] Intentando eliminar producto ID: " + id);

            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("[ERROR DELETE] No se pudo eliminar el producto ID " + id + ". Motivo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar: El producto podría estar asociado a un carrito o compra activa."));
        }
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> obtenerPorCategoria(@PathVariable Long categoriaId) {
        try {
            return ResponseEntity.ok(productoService.obtenerPorCategoria(categoriaId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al filtrar por categoría: " + e.getMessage()));
        }
    }
}