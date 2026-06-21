package com.grupocordillera.ms_reporting.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class TopProductsDTO {


    private int topN;
    private List<TopProductItemDTO> products;


    @Data
    public static class TopProductItemDTO {
        private String productCode;
        private String name;
        private String category;
        private int totalQuantitySold;
        private double totalRevenue;
    }
}
