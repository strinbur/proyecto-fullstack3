package com.grupocordillera.ms_order.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.grupocordillera.ms_order.dto.InventoryResponseDTO;
import com.grupocordillera.ms_order.dto.InventoryUpdateDTO;


@FeignClient(name = "ms-inventory", url = "${inventory.url}", path = "/inventory")
public interface InventoryClient {

    @GetMapping("/code/{code}")
    InventoryResponseDTO getByCode(@PathVariable("code") String code);

    @PutMapping("/code/{code}")
    InventoryResponseDTO update(@PathVariable("code") String code, @RequestBody InventoryUpdateDTO dto);
}