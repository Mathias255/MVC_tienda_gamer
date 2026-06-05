package org.example.service.implementation;

import org.example.entity.Carrito;
import org.example.entity.ItemCarrito;
import org.example.entity.Producto;
import org.example.repository.CarritoRepository;
import org.example.repository.ItemCarritoRepository;
import org.example.repository.ProductoRepository;
import org.example.service.interfaces.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ItemCarrito> obtenerItems(Long carritoId) {
        return itemCarritoRepository.findByCarritoId(carritoId);
    }

    @Override
    public ItemCarrito agregarProducto(Long carritoId, Long productoId, Integer cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Lógica Gamer: Validamos que haya stock suficiente disponible
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock disponible de: " + producto.getNombre());
        }

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ItemCarrito item = new ItemCarrito();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);

        return itemCarritoRepository.save(item);
    }

    @Override
    public ItemCarrito actualizarCantidad(Long itemId, Integer cantidad) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (item.getProducto().getStock() < cantidad) {
            throw new RuntimeException("No puedes agregar esa cantidad, supera el stock disponible.");
        }

        item.setCantidad(cantidad);
        return itemCarritoRepository.save(item);
    }

    @Override
    public void eliminarItem(Long itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    @Override
    @Transactional // Asegura que se borren todos los registros juntos en PostgreSQL
    public void vaciarCarrito(Long carritoId) {
        itemCarritoRepository.deleteByCarritoId(carritoId);
    }
}