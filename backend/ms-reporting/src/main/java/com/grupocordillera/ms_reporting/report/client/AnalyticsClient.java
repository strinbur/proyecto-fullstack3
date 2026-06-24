package com.grupocordillera.ms_reporting.report.client;


import com.grupocordillera.ms_reporting.config.FeignConfig;
import com.grupocordillera.ms_reporting.report.dto.CriticalStockDTO;
import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import com.grupocordillera.ms_reporting.report.dto.RevenueByCategoryDTO;
import com.grupocordillera.ms_reporting.report.dto.SalesSummaryDTO;
import com.grupocordillera.ms_reporting.report.dto.TopCustomersDTO;
import com.grupocordillera.ms_reporting.report.dto.TopProductsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
    name = "ms-analytics",
    url = "${analytics.url}",
    path = "/analytics",
    configuration = FeignConfig.class
)
/**
 * Cliente Feign para consumir los endpoints de analytics (ms-analytics).
 */
public interface AnalyticsClient {


    @GetMapping("/report")
    FullAnalyticsReportDTO getFullReport();


    @GetMapping("/sales-summary")
    SalesSummaryDTO getSalesSummary();


    @GetMapping("/top-products")
    TopProductsDTO getTopProducts(@RequestParam("top_n") int topN);


    @GetMapping("/critical-stock")
    CriticalStockDTO getCriticalStock();


    @GetMapping("/revenue-by-category")
    RevenueByCategoryDTO getRevenueByCategory();


    @GetMapping("/top-customers")
    TopCustomersDTO getTopCustomers(@RequestParam("top_n") int topN);
}
