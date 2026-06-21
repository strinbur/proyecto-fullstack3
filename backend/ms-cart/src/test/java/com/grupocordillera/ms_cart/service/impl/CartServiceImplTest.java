package com.grupocordillera.ms_cart.service.impl;

import com.grupocordillera.ms_cart.dto.*;
import com.grupocordillera.ms_cart.inventory.client.InventoryClient;
import com.grupocordillera.ms_cart.inventory.client.InventoryResponseDTO;
import com.grupocordillera.ms_cart.model.Cart;
import com.grupocordillera.ms_cart.model.CartItem;
import com.grupocordillera.ms_cart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository repository;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private CartServiceImpl service;

    private void mockAuth() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@test.com");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void getCart_shouldReturnCart() {

        mockAuth();

        Cart cart = new Cart();
        cart.setUserEmail("user@test.com");

        when(repository.findByUserEmail("user@test.com"))
                .thenReturn(Optional.of(cart));

        CartResponseDTO result = service.getCart();

        assertNotNull(result);
        assertEquals("user@test.com", result.getUserEmail());

        verify(repository).findByUserEmail("user@test.com");
    }

    @Test
    void addProduct_shouldAddItem() {

        mockAuth();

        AddProductDTO dto = new AddProductDTO();
        dto.setProductCode("ABC");
        dto.setQuantity(2);

        InventoryResponseDTO inv = new InventoryResponseDTO();
        inv.setCode("ABC");
        inv.setName("Product");
        inv.setPrice(10);
        inv.setQuantity(10);
        inv.setCategory("CAT");

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        when(repository.findByUserEmail("user@test.com"))
                .thenReturn(Optional.of(cart));

        when(inventoryClient.getByCode("ABC"))
                .thenReturn(inv);

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CartResponseDTO result = service.addProduct(dto);

        assertNotNull(result);
        verify(inventoryClient).getByCode("ABC");
        verify(repository).save(any());
    }

    @Test
    void updateQuantity_shouldUpdateItem() {

        mockAuth();

        UpdateQuantityDTO dto = new UpdateQuantityDTO();
        dto.setQuantity(2);

        CartItem item = new CartItem();
        item.setProductCode("ABC");
        item.setPrice(10);
        item.setQuantity(1);
        item.setSubtotal(10);

        Cart cart = new Cart();
        cart.setUserEmail("user@test.com");
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item);

        InventoryResponseDTO inv = new InventoryResponseDTO();
        inv.setCode("ABC");
        inv.setQuantity(10);

        when(repository.findByUserEmail("user@test.com"))
                .thenReturn(Optional.of(cart));

        when(inventoryClient.getByCode("ABC"))
                .thenReturn(inv);

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CartResponseDTO result = service.updateQuantity("ABC", dto);

        assertNotNull(result);
        assertEquals(2, cart.getItems().get(0).getQuantity());

        verify(repository).save(any());
    }

    @Test
    void removeProduct_shouldRemoveItem() {

        mockAuth();

        CartItem item = new CartItem();
        item.setProductCode("ABC");

        Cart cart = new Cart();
        cart.setUserEmail("user@test.com");
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item);

        when(repository.findByUserEmail("user@test.com"))
                .thenReturn(Optional.of(cart));

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CartResponseDTO result = service.removeProduct("ABC");

        assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    void clearCart_shouldEmptyCart() {

        mockAuth();

        Cart cart = new Cart();
        cart.setUserEmail("user@test.com");
        cart.setItems(new ArrayList<>());

        when(repository.findByUserEmail("user@test.com"))
                .thenReturn(Optional.of(cart));

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.clearCart();

        verify(repository).save(any());
    }
}