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
/**
 * Controlador REST que expone los endpoints para generar y consultar reportes analíticos.
 */
public class ReportController {


    private final ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    /**
     * Genera un reporte analítico completo y lo devuelve como DTO.
     *
     * @param authentication contexto de seguridad con información del usuario que solicita el reporte
     * @return reporte analítico completo
     */
    @GetMapping
    public FullAnalyticsReportDTO getFullReport(Authentication authentication) {
        String requestedBy = authentication.getName();
        return reportService.getFullReport(requestedBy);
    }


    /**
     * Recupera el historial de ejecuciones de reportes.
     *
     * @return lista de registros de reportes ordenados por fecha
     */
    @GetMapping("/history")
    public List<ReportLog> getReportHistory() {
        return reportService.getReportHistory();
    }


    /**
     * Obtiene un resumen de ventas con métricas clave.
     *
     * @return resumen de ventas
     */
    @GetMapping("/sales-summary")
    public SalesSummaryDTO getSalesSummary() {
        return reportService.getSalesSummary();
    }


    /**
     * Obtiene los productos más vendidos.
     *
     * @param topN número de productos a retornar
     * @return DTO con los productos más vendidos
     */
    @GetMapping("/top-products")
    public TopProductsDTO getTopProducts(
            @RequestParam(name = "topN", defaultValue = "5") int topN
    ) {
        return reportService.getTopProducts(topN);
    }


    /**
     * Devuelve los productos con stock crítico.
     *
     * @return DTO con información de stock crítico
     */
    @GetMapping("/critical-stock")
    public CriticalStockDTO getCriticalStock() {
        return reportService.getCriticalStock();
    }


    /**
     * Calcula los ingresos agrupados por categoría.
     *
     * @return DTO con ingresos por categoría
     */
    @GetMapping("/revenue-by-category")
    public RevenueByCategoryDTO getRevenueByCategory() {
        return reportService.getRevenueByCategory();
    }


    /**
     * Devuelve los clientes que más han gastado.
     *
     * @param topN número de clientes a retornar
     * @return DTO con los clientes top
     */
    @GetMapping("/top-customers")
    public TopCustomersDTO getTopCustomers(
            @RequestParam(name = "topN", defaultValue = "5") int topN
    ) {
        return reportService.getTopCustomers(topN);
    }
}
