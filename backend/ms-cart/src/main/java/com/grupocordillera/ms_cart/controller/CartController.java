package com.grupocordillera.ms_cart.controller;

import com.grupocordillera.ms_cart.dto.AddProductDTO;
import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.dto.UpdateQuantityDTO;
import com.grupocordillera.ms_cart.service.CartService;
import io.sentry.Sentry;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addProduct(@Valid @RequestBody AddProductDTO dto) {
        return ResponseEntity.ok(service.addProduct(dto));
    }

    @PutMapping("/update/{code}")
    public ResponseEntity<CartResponseDTO> updateQuantity(
            @PathVariable String code,
            @Valid @RequestBody UpdateQuantityDTO dto
    ) {
        return ResponseEntity.ok(service.updateQuantity(code, dto));
    }

    @DeleteMapping("/remove/{code}")
    public ResponseEntity<CartResponseDTO> removeProduct(@PathVariable String code) {
        return ResponseEntity.ok(service.removeProduct(code));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.noContent().build();
    }

}