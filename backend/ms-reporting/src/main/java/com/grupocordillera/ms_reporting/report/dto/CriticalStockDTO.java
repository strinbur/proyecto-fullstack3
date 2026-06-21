package com.grupocordillera.ms_reporting.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class CriticalStockDTO {


    private int threshold;
    private int totalCriticalItems;
    private List<CriticalStockItemDTO> items;


    @Data
    public static class CriticalStockItemDTO {
        private String code;
        private String name;
        private String category;
        private int currentQuantity;
        private double price;
    }
}
