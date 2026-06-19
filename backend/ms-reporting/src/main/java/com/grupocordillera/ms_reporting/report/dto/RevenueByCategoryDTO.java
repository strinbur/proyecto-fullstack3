package com.grupocordillera.ms_reporting.report.dto;


import lombok.Data;


import java.util.List;


@Data
public class RevenueByCategoryDTO {


    private List<CategoryRevenueDTO> categories;


    @Data
    public static class CategoryRevenueDTO {
        private String category;
        private double totalRevenue;
        private int totalUnitsSold;
        private double percentageOfRevenue;
    }
}
