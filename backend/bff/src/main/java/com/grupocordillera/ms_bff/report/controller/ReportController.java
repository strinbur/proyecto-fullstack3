package com.grupocordillera.ms_bff.report.controller;

import com.grupocordillera.ms_bff.report.dto.*;
import com.grupocordillera.ms_bff.report.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/reports")
@CrossOrigin
/**
 * BFF controller para exponer endpoints de reporte al frontend. Actúa como
 * gateway hacia `ms-reporting` y delega al `ReportService`.
 */
public class ReportController {

    private static final Logger log =
            LoggerFactory.getLogger(ReportController.class);

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public FullAnalyticsReportDTO getFullReport(
            @RequestHeader String Authorization
    ) {
        log.info("BFF GET FULL REPORT");

        // Pasa el token de autorización al servicio que llama al microservicio
        return service.getFullReport(Authorization);
    }

    @GetMapping("/history")
    public List<?> getHistory(@RequestHeader String Authorization) {
        log.info("BFF GET REPORT HISTORY");

        return service.getHistory(Authorization);
    }

    @GetMapping("/sales-summary")
    public SalesSummaryDTO getSalesSummary() {
        return service.getSalesSummary();
    }

    @GetMapping("/top-products")
    public TopProductsDTO getTopProducts(
            @RequestParam int topN
    ) {
        return service.getTopProducts(topN);
    }

    @GetMapping("/critical-stock")
    public CriticalStockDTO getCriticalStock() {
        return service.getCriticalStock();
    }

    @GetMapping("/revenue-by-category")
    public RevenueByCategoryDTO getRevenueByCategory() {
        return service.getRevenueByCategory();
    }

    @GetMapping("/top-customers")
    public TopCustomersDTO getTopCustomers(
            @RequestParam int topN
    ) {
        return service.getTopCustomers(topN);
    }
}