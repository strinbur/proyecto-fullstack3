package com.grupocordillera.ms_cart.service;

import com.grupocordillera.ms_cart.dto.AddProductDTO;
import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.dto.UpdateQuantityDTO;

/**
 * Interfaz del servicio de carrito que define las operaciones disponibles
 * para administrar el carrito de compras del usuario autenticado.
 */
public interface CartService {

    /**
     * Obtiene el carrito actualmente activo del usuario autenticado.
     *
     * @return el DTO de respuesta del carrito
     */
    CartResponseDTO getCart();

    /**
     * Agrega un producto al carrito del usuario autenticado.
     *
     * @param dto los datos del producto a agregar
     * @return el DTO de respuesta del carrito actualizado
     */
    CartResponseDTO addProduct(AddProductDTO dto);

    /**
     * Actualiza la cantidad de un producto existente en el carrito.
     *
     * @param code el código del producto a actualizar
     * @param dto  los datos de la nueva cantidad
     * @return el DTO de respuesta del carrito actualizado
     */
    CartResponseDTO updateQuantity(
            String code,
            UpdateQuantityDTO dto
    );

    /**
     * Elimina un producto del carrito del usuario autenticado.
     *
     * @param code el código del producto a eliminar
     * @return el DTO de respuesta del carrito actualizado
     */
    CartResponseDTO removeProduct(String code);

    /**
     * Limpia todos los productos del carrito.
     */
    void clearCart();
}