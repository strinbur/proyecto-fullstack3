package com.grupocordillera.ms_cart.controller;

import com.grupocordillera.ms_cart.dto.*;
import com.grupocordillera.ms_cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService service;

    @InjectMocks
    private CartController controller;

    @Test
    void getCart_shouldReturnCart() {

        CartResponseDTO response = new CartResponseDTO();
        response.setUserEmail("user@test.com");

        when(service.getCart()).thenReturn(response);

        CartResponseDTO result = controller.getCart().getBody();

        assertNotNull(result);
        assertEquals("user@test.com", result.getUserEmail());

        verify(service, times(1)).getCart();
    }

    @Test
    void addProduct_shouldReturnCart() {

        AddProductDTO dto = new AddProductDTO();
        dto.setProductCode("ABC");
        dto.setQuantity(2);

        CartResponseDTO response = new CartResponseDTO();

        when(service.addProduct(dto)).thenReturn(response);

        CartResponseDTO result = controller.addProduct(dto).getBody();

        assertNotNull(result);

        verify(service, times(1)).addProduct(dto);
    }

    @Test
    void updateQuantity_shouldReturnCart() {

        UpdateQuantityDTO dto = new UpdateQuantityDTO();
        dto.setQuantity(3);

        CartResponseDTO response = new CartResponseDTO();

        when(service.updateQuantity("ABC", dto)).thenReturn(response);

        CartResponseDTO result = controller.updateQuantity("ABC", dto).getBody();

        assertNotNull(result);

        verify(service, times(1)).updateQuantity("ABC", dto);
    }

    @Test
    void removeProduct_shouldReturnCart() {

        CartResponseDTO response = new CartResponseDTO();

        when(service.removeProduct("ABC")).thenReturn(response);

        CartResponseDTO result = controller.removeProduct("ABC").getBody();

        assertNotNull(result);

        verify(service, times(1)).removeProduct("ABC");
    }

    @Test
    void clearCart_shouldCallService() {

        doNothing().when(service).clearCart();

        controller.clearCart();

        verify(service, times(1)).clearCart();
    }

    @Test
    void getCart_calledTwice_shouldWork() {

        when(service.getCart()).thenReturn(new CartResponseDTO());

        controller.getCart();
        controller.getCart();

        verify(service, times(2)).getCart();
    }
}