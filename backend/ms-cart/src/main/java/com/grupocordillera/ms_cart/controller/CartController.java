package com.grupocordillera.ms_cart.controller;

import com.grupocordillera.ms_cart.dto.AddProductDTO;
import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.dto.UpdateQuantityDTO;
import com.grupocordillera.ms_cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones del carrito de compras.
 * <p>
 * Expone los endpoints necesarios para obtener el estado del carrito,
 * agregar productos, actualizar cantidades, eliminar productos y limpiar el carrito.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    /**
     * Constructor del controlador del carrito.
     *
     * @param service el servicio que administra la lógica del carrito
     */
    public CartController(CartService service) {
        this.service = service;
    }

    /**
     * Obtiene el contenido actual del carrito.
     *
     * @return respuesta HTTP con el carrito actual
     */
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    /**
     * Agrega un producto al carrito.
     *
     * @param dto los datos del producto a agregar
     * @return respuesta HTTP con el carrito actualizado
     */
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addProduct(@Valid @RequestBody AddProductDTO dto) {
        return ResponseEntity.ok(service.addProduct(dto));
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param code el código del producto a actualizar
     * @param dto  los datos de la nueva cantidad
     * @return respuesta HTTP con el carrito actualizado
     */
    @PutMapping("/update/{code}")
    public ResponseEntity<CartResponseDTO> updateQuantity(
            @PathVariable String code,
            @Valid @RequestBody UpdateQuantityDTO dto
    ) {
        return ResponseEntity.ok(service.updateQuantity(code, dto));
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param code el código del producto a eliminar
     * @return respuesta HTTP con el carrito actualizado
     */
    @DeleteMapping("/remove/{code}")
    public ResponseEntity<CartResponseDTO> removeProduct(@PathVariable String code) {
        return ResponseEntity.ok(service.removeProduct(code));
    }

    /**
     * Limpia todos los productos del carrito.
     *
     * @return respuesta HTTP sin contenido cuando el carrito queda vacío
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.noContent().build();
    }
}