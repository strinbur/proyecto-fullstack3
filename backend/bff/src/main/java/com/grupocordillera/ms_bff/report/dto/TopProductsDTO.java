package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class TopProductsDTO {


    /** Número de productos top incluidos. */
    private int topN;

    /** Lista de productos con métricas de venta. */
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