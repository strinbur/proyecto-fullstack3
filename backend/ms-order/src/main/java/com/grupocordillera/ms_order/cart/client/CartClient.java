package com.grupocordillera.ms_order.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupocordillera.ms_order.dto.CartResponseDTO;

@FeignClient(name = "ms-cart", url = "${cart.url}", path = "/cart")
public interface CartClient {

    @GetMapping
    CartResponseDTO getCart();

    @DeleteMapping("/clear")
    void clearCart();
}