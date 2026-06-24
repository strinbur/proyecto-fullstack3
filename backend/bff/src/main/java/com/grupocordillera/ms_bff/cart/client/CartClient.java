package com.grupocordillera.ms_bff.cart.client;

import com.grupocordillera.ms_bff.cart.dto.AddProductDTO;
import com.grupocordillera.ms_bff.cart.dto.CartResponseDTO;
import com.grupocordillera.ms_bff.cart.dto.UpdateQuantityDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Cliente Feign que proxya las llamadas al microservicio `ms-cart`.
 * El BFF utiliza este cliente para delegar operaciones de carrito.
 */
@FeignClient(
        name = "ms-cart",
        url = "${spring.cloud.openfeign.client.config.ms-cart.url}",
        path = "/cart",
        configuration = com.grupocordillera.ms_bff.config.FeignConfig.class
)
public interface CartClient {

    @GetMapping
    CartResponseDTO getCart();

    @PostMapping("/add")
    CartResponseDTO addProduct(
            @RequestBody AddProductDTO dto
    );

    @PutMapping("/update/{code}")
    CartResponseDTO updateQuantity(
            @PathVariable("code") String code,
            @RequestBody UpdateQuantityDTO dto
    );

    @DeleteMapping("/remove/{code}")
    CartResponseDTO removeProduct(
            @PathVariable("code") String code
    );

    @DeleteMapping("/clear")
    void clearCart();
}