package com.grupocordillera.ms_cart.service.impl;

import com.grupocordillera.ms_cart.dto.AddProductDTO;
import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.dto.UpdateQuantityDTO;
import com.grupocordillera.ms_cart.factory.CartFactory;
import com.grupocordillera.ms_cart.inventory.client.InventoryClient;
import com.grupocordillera.ms_cart.inventory.client.InventoryResponseDTO;
import com.grupocordillera.ms_cart.model.Cart;
import com.grupocordillera.ms_cart.model.CartItem;
import com.grupocordillera.ms_cart.repository.CartRepository;
import com.grupocordillera.ms_cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final InventoryClient inventoryClient;

    public CartServiceImpl(CartRepository repository, InventoryClient inventoryClient) {
        this.repository = repository;
        this.inventoryClient = inventoryClient;
    }

    private String getAuthenticatedEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private double calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    @Override
    public CartResponseDTO getCart() {
        String email = getAuthenticatedEmail();
        Cart cart = repository.findByUserEmail(email)
                .orElseGet(() -> repository.save(CartFactory.createCart(email)));
        return CartFactory.toResponse(cart);
    }

    @Override
    public CartResponseDTO addProduct(AddProductDTO dto) {
        String email = getAuthenticatedEmail();
        Cart cart = repository.findByUserEmail(email)
                .orElseGet(() -> CartFactory.createCart(email));

        InventoryResponseDTO product = inventoryClient.getByCode(dto.getProductCode());
        if (product.getQuantity() < dto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductCode().equals(product.getCode()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + dto.getQuantity());
            existingItem.setSubtotal(existingItem.getPrice() * existingItem.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setProductCode(product.getCode());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(dto.getQuantity());
            item.setCategory(product.getCategory());
            item.setSubtotal(product.getPrice() * dto.getQuantity());
            cart.getItems().add(item);
        }

        cart.setTotal(calculateTotal(cart));
        return CartFactory.toResponse(repository.save(cart));
    }

    @Override
    public CartResponseDTO updateQuantity(String code, UpdateQuantityDTO dto) {
        String email = getAuthenticatedEmail();
        Cart cart = repository.findByUserEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado en carrito"));

        InventoryResponseDTO product = inventoryClient.getByCode(code);
        if (product.getQuantity() < dto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        item.setQuantity(dto.getQuantity());
        item.setSubtotal(item.getPrice() * item.getQuantity());
        cart.setTotal(calculateTotal(cart));
        return CartFactory.toResponse(repository.save(cart));
    }

    @Override
    public CartResponseDTO removeProduct(String code) {
        String email = getAuthenticatedEmail();
        Cart cart = repository.findByUserEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        cart.getItems().removeIf(item -> item.getProductCode().equals(code));
        cart.setTotal(calculateTotal(cart));
        return CartFactory.toResponse(repository.save(cart));
    }

    @Override
    public void clearCart() {
        String email = getAuthenticatedEmail();
        Cart cart = repository.findByUserEmail(email)
                .orElseGet(() -> repository.save(CartFactory.createCart(email)));

        cart.getItems().clear();
        cart.setTotal(0);
        repository.save(cart);
    }
}