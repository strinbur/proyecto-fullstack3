package com.grupocordillera.ms_bff.report.client;

import com.grupocordillera.ms_bff.report.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "ms-reporting",
    url = "${ms.reporting.url}",
    path = "/reports",
    configuration = com.grupocordillera.ms_bff.config.FeignConfig.class
)
public interface ReportClient {

    @GetMapping
    FullAnalyticsReportDTO getFullReport(@RequestHeader("Authorization") String token);

    @GetMapping("/history")
    List<ReportLogDTO> getHistory(@RequestHeader("Authorization") String token);

    @GetMapping("/sales-summary")
    SalesSummaryDTO getSalesSummary();

    @GetMapping("/top-products")
    TopProductsDTO getTopProducts(@RequestParam("topN") int topN);

    @GetMapping("/critical-stock")
    CriticalStockDTO getCriticalStock();

    @GetMapping("/revenue-by-category")
    RevenueByCategoryDTO getRevenueByCategory();

    @GetMapping("/top-customers")
    TopCustomersDTO getTopCustomers(@RequestParam("topN") int topN);
}