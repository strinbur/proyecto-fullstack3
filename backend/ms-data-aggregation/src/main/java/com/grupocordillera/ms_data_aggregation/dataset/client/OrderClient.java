package com.grupocordillera.ms_data_aggregation.dataset.client;

import com.grupocordillera.ms_data_aggregation.dataset.dto.OrderDatasetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
    name = "ms-order",
    url = "${order.url}",
    path = "/orders"
)
public interface OrderClient {

    @GetMapping
    List<OrderDatasetDTO> getAllOrders();
}