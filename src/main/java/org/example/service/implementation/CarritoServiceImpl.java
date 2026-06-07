package org.example.service.implementation;

import org.example.dto.PedidoDTO;
import org.example.entity.*;
import org.example.mapper.PedidoMapper;
import org.example.repository.*;
import org.example.service.interfaces.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository; // 🔥 Inyectamos esto para buscar los ítems directamente

    @Autowired
    private PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoDTO finalizarCompra(String username) {
        // 1. Buscar al usuario autenticado
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.toString().contains(username) || username.equals(u.getId().toString()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el sistema"));

        // 2. Buscar el carrito activo del usuario
        Carrito carrito = carritoRepository.findAll().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getId().equals(usuario.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró un carrito activo para el usuario"));

        // 3. 🔥 SOLUCIÓN DEFINTIVA: Buscamos los ítems en el repositorio filtrando por el ID del carrito
        List<ItemCarrito> itemsCompra = itemCarritoRepository.findAll().stream()
                .filter(item -> item.getCarrito() != null && item.getCarrito().getId().equals(carrito.getId()))
                .toList();

        if (itemsCompra.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // 4. Crear la cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);

        // Usamos la fecha del sistema (Si te da error setFechaPedido, cámbialo a setFecha)
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PROCESADO");

        double total = 0;
        List<DetallePedido> detalles = new ArrayList<>();

        // 5. Recorrer los artículos obtenidos directamente del repositorio
        for (ItemCarrito item : itemsCompra) {
            Producto producto = item.getProducto();

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el hardware: " + producto.getNombre());
            }

            // Restamos stock y guardamos
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // Creamos el detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            total += producto.getPrecio() * item.getCantidad();
            detalles.add(detalle);
        }

        pedido.setTotal(total);

        // 6. Guardar Pedido y Detalles
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        detallePedidoRepository.saveAll(detalles);

        // 7. 🔥 Limpiar el carrito eliminando los ítems de la base de datos
        itemCarritoRepository.deleteAll(itemsCompra);

        // 8. Convertir a DTO de salida
        return pedidoMapper.toDTO(pedidoGuardado);
    }
}