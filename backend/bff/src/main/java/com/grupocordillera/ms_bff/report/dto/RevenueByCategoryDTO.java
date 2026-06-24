package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class RevenueByCategoryDTO {


    /** Lista de categorías con su ingreso agregado. */
    private List<CategoryRevenueDTO> categories;


    @Data
    public static class CategoryRevenueDTO {
        private String category;
        private double totalRevenue;
        private int totalUnitsSold;
        private double percentageOfRevenue;
    }
}
