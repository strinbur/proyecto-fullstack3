package com.grupocordillera.ms_cart.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-inventory",
        url = "${inventory.url}",
        path = "/inventory"
)
public interface InventoryClient {

    @GetMapping("/code/{code}")
    InventoryResponseDTO getByCode(
            @PathVariable("code") String code
    );
}