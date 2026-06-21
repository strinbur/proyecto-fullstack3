package com.grupocordillera.ms_bff.cart.controller;

import com.grupocordillera.ms_bff.cart.dto.AddProductDTO;
import com.grupocordillera.ms_bff.cart.dto.CartResponseDTO;
import com.grupocordillera.ms_bff.cart.dto.UpdateQuantityDTO;
import com.grupocordillera.ms_bff.cart.service.CartService;
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

        CartResponseDTO result = controller.getCart();

        assertNotNull(result);
        assertEquals("user@test.com", result.getUserEmail());

        verify(service, times(1)).getCart();
    }

    @Test
    void addProduct_shouldReturnUpdatedCart() {

        AddProductDTO dto = new AddProductDTO();
        dto.setProductCode("ABC123");
        dto.setQuantity(2);

        CartResponseDTO response = new CartResponseDTO();

        when(service.addProduct(dto)).thenReturn(response);

        CartResponseDTO result = controller.addProduct(dto);

        assertNotNull(result);

        verify(service, times(1)).addProduct(dto);
    }

    @Test
    void updateQuantity_shouldReturnUpdatedCart() {

        UpdateQuantityDTO dto = new UpdateQuantityDTO();
        dto.setQuantity(3);

        CartResponseDTO response = new CartResponseDTO();

        when(service.updateQuantity("ABC123", dto))
                .thenReturn(response);

        CartResponseDTO result =
                controller.updateQuantity("ABC123", dto);

        assertNotNull(result);

        verify(service, times(1))
                .updateQuantity("ABC123", dto);
    }

    @Test
    void removeProduct_shouldReturnUpdatedCart() {

        CartResponseDTO response = new CartResponseDTO();

        when(service.removeProduct("ABC123"))
                .thenReturn(response);

        CartResponseDTO result =
                controller.removeProduct("ABC123");

        assertNotNull(result);

        verify(service, times(1))
                .removeProduct("ABC123");
    }

    @Test
    void clearCart_shouldCallService() {

        doNothing().when(service).clearCart();

        controller.clearCart();

        verify(service, times(1)).clearCart();
    }

    @Test
    void getCart_shouldCallServiceTwice() {

        when(service.getCart()).thenReturn(new CartResponseDTO());

        controller.getCart();
        controller.getCart();

        verify(service, times(2)).getCart();
    }
}