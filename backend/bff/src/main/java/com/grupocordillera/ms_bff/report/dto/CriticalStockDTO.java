package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class CriticalStockDTO {


    /** Umbral para considerar stock crítico. */
    private int threshold;

    /** Total de ítems en estado crítico. */
    private int totalCriticalItems;

    /** Ítems cuyo stock está por debajo del umbral. */
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