package com.grupocordillera.ms_bff.cart.controller;

import com.grupocordillera.ms_bff.cart.dto.*;
import com.grupocordillera.ms_bff.cart.service.CartService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/cart")
@CrossOrigin
public class CartController {

    private static final Logger log =
            LoggerFactory.getLogger(CartController.class);

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public CartResponseDTO getCart() {

        log.info("BFF GET CART");

        return service.getCart();
    }

    @PostMapping("/add")
    public CartResponseDTO addProduct(
            @Valid @RequestBody AddProductDTO dto
    ) {

        log.info("BFF ADD PRODUCT TO CART: {}", dto.getProductCode());

        return service.addProduct(dto);
    }

    @PutMapping("/update/{code}")
    public CartResponseDTO updateQuantity(
            @PathVariable String code,
            @Valid @RequestBody UpdateQuantityDTO dto
    ) {

        log.info("BFF UPDATE CART PRODUCT: {}", code);

        return service.updateQuantity(code, dto);
    }

    @DeleteMapping("/remove/{code}")
    public CartResponseDTO removeProduct(
            @PathVariable String code
    ) {

        log.info("BFF REMOVE PRODUCT FROM CART: {}", code);

        return service.removeProduct(code);
    }

    @DeleteMapping("/clear")
    public void clearCart() {

        log.info("BFF CLEAR CART");

        service.clearCart();
    }
}