package com.grupocordillera.ms_bff.cart.service.impl;

import com.grupocordillera.ms_bff.cart.client.CartClient;
import com.grupocordillera.ms_bff.cart.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartClient client;

    private CartServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CartServiceImpl(client);
    }

    @Test
    void getCart_delegaAlClient() {
        CartResponseDTO expected = new CartResponseDTO();
        expected.setUserEmail("user@test.com");
        when(client.getCart()).thenReturn(expected);

        CartResponseDTO result = service.getCart();

        assertEquals(expected, result);
        verify(client, times(1)).getCart();
    }

    @Test
    void addProduct_delegaAlClient() {
        AddProductDTO dto = new AddProductDTO();
        dto.setProductCode("ABC123");
        dto.setQuantity(2);

        CartResponseDTO expected = new CartResponseDTO();
        when(client.addProduct(dto)).thenReturn(expected);

        CartResponseDTO result = service.addProduct(dto);

        assertEquals(expected, result);
        verify(client).addProduct(dto);
    }

    @Test
    void updateQuantity_delegaAlClient() {
        UpdateQuantityDTO dto = new UpdateQuantityDTO();
        dto.setQuantity(5);

        CartResponseDTO expected = new CartResponseDTO();
        when(client.updateQuantity("ABC123", dto)).thenReturn(expected);

        CartResponseDTO result = service.updateQuantity("ABC123", dto);

        assertEquals(expected, result);
        verify(client).updateQuantity("ABC123", dto);
    }

    @Test
    void removeProduct_delegaAlClient() {
        CartResponseDTO expected = new CartResponseDTO();
        when(client.removeProduct("ABC123")).thenReturn(expected);

        CartResponseDTO result = service.removeProduct("ABC123");

        assertEquals(expected, result);
        verify(client).removeProduct("ABC123");
    }

    @Test
    void clearCart_delegaAlClient() {
        service.clearCart();

        verify(client, times(1)).clearCart();
    }
}