package com.grupocordillera.ms_data_aggregation.dataset.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDatasetDTO {

    private String id;
    private String userEmail;
    private String status;
    private LocalDateTime createdAt;

    private List<String> productCodes;
    private List<Integer> quantities;
    private List<Double> prices;
    private List<String> categories;
}