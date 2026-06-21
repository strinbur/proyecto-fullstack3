package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class FullAnalyticsReportDTO {


    private LocalDateTime generatedAt;
    private SalesSummaryDTO salesSummary;
    private TopProductsDTO topProducts;
    private CriticalStockDTO criticalStock;
    private RevenueByCategoryDTO revenueByCategory;
    private TopCustomersDTO topCustomers;
}