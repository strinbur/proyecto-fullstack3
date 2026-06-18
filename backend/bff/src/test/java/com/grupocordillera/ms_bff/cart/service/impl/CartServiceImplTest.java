package com.grupocordillera.ms_bff.cart.service.impl;

import com.grupocordillera.ms_bff.cart.client.CartClient;
import com.grupocordillera.ms_bff.cart.dto.AddProductDTO;
import com.grupocordillera.ms_bff.cart.dto.CartResponseDTO;
import com.grupocordillera.ms_bff.cart.dto.UpdateQuantityDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartServiceImplTest {

    private final CartClient client =
            Mockito.mock(CartClient.class);

    private final CartServiceImpl service =
            new CartServiceImpl(client);

    @Test
    void shouldGetCart() {

        CartResponseDTO dto = new CartResponseDTO();

        Mockito.when(client.getCart())
                .thenReturn(dto);

        CartResponseDTO response =
                service.getCart();

        assertNotNull(response);
    }

    @Test
    void shouldAddProduct() {

        AddProductDTO dto =
                new AddProductDTO();

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(client.addProduct(dto))
                .thenReturn(responseDto);

        CartResponseDTO response =
                service.addProduct(dto);

        assertNotNull(response);
    }

    @Test
    void shouldUpdateQuantity() {

        UpdateQuantityDTO dto =
                new UpdateQuantityDTO();

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(client.updateQuantity("P001", dto))
                .thenReturn(responseDto);

        CartResponseDTO response =
                service.updateQuantity("P001", dto);

        assertNotNull(response);
    }

    @Test
    void shouldRemoveProduct() {

        CartResponseDTO responseDto =
                new CartResponseDTO();

        Mockito.when(client.removeProduct("P001"))
                .thenReturn(responseDto);

        CartResponseDTO response =
                service.removeProduct("P001");

        assertNotNull(response);
    }

    @Test
    void shouldClearCart() {

        service.clearCart();

        Mockito.verify(client)
                .clearCart();
    }
}