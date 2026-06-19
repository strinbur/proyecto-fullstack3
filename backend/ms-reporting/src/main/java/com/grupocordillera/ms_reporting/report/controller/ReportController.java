package com.grupocordillera.ms_reporting.report.controller;


import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.CriticalStockDTO;
import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import com.grupocordillera.ms_reporting.report.dto.RevenueByCategoryDTO;
import com.grupocordillera.ms_reporting.report.dto.SalesSummaryDTO;
import com.grupocordillera.ms_reporting.report.dto.TopCustomersDTO;
import com.grupocordillera.ms_reporting.report.dto.TopProductsDTO;
import com.grupocordillera.ms_reporting.report.service.ReportService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/reports")
public class ReportController {


    private final ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping
    public FullAnalyticsReportDTO getFullReport(Authentication authentication) {
        String requestedBy = authentication.getName();
        return reportService.getFullReport(requestedBy);
    }


    @GetMapping("/history")
    public List<ReportLog> getReportHistory() {
        return reportService.getReportHistory();
    }


    @GetMapping("/sales-summary")
    public SalesSummaryDTO getSalesSummary() {
        return reportService.getSalesSummary();
    }


    @GetMapping("/top-products")
    public TopProductsDTO getTopProducts(
            @RequestParam(name = "topN", defaultValue = "5") int topN
    ) {
        return reportService.getTopProducts(topN);
    }


    @GetMapping("/critical-stock")
    public CriticalStockDTO getCriticalStock() {
        return reportService.getCriticalStock();
    }


    @GetMapping("/revenue-by-category")
    public RevenueByCategoryDTO getRevenueByCategory() {
        return reportService.getRevenueByCategory();
    }


    @GetMapping("/top-customers")
    public TopCustomersDTO getTopCustomers(
            @RequestParam(name = "topN", defaultValue = "5") int topN
    ) {
        return reportService.getTopCustomers(topN);
    }
}
