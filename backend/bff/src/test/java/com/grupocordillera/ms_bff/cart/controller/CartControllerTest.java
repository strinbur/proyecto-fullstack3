package com.grupocordillera.ms_bff.cart.controller;

import com.grupocordillera.ms_bff.cart.dto.AddProductDTO;
import com.grupocordillera.ms_bff.cart.dto.CartResponseDTO;
import com.grupocordillera.ms_bff.cart.dto.UpdateQuantityDTO;
import com.grupocordillera.ms_bff.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartControllerTest {

    private final CartService service =
            Mockito.mock(CartService.class);

    private final CartController controller =
            new CartController(service);

    @Test
    void shouldGetCart() {

        CartResponseDTO dto = new CartResponseDTO();

        Mockito.when(service.getCart())
                .thenReturn(dto);

        CartResponseDTO response =
                controller.getCart();

        assertNotNull(response);
    }

    @Test
    void shouldAddProduct() {

        AddProductDTO dto = new AddProductDTO();
        dto.setProductCode("P001");
        dto.setQuantity(1);

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(service.addProduct(dto))
                .thenReturn(responseDto);

        CartResponseDTO response =
                controller.addProduct(dto);

        assertNotNull(response);
    }

    @Test
    void shouldUpdateQuantity() {

        UpdateQuantityDTO dto =
                new UpdateQuantityDTO();

        dto.setQuantity(2);

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(service.updateQuantity("P001", dto))
                .thenReturn(responseDto);

        CartResponseDTO response =
                controller.updateQuantity("P001", dto);

        assertNotNull(response);
    }

    @Test
    void shouldRemoveProduct() {

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(service.removeProduct("P001"))
                .thenReturn(responseDto);

        CartResponseDTO response =
                controller.removeProduct("P001");

        assertNotNull(response);
    }

    @Test
    void shouldClearCart() {

        controller.clearCart();

        Mockito.verify(service)
                .clearCart();
    }
}