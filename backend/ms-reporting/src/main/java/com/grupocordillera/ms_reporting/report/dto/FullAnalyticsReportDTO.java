package com.grupocordillera.ms_reporting.report.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class FullAnalyticsReportDTO {

    /**
     * Fecha y hora de generación del reporte.
     */
    private LocalDateTime generatedAt;

    /**
     * Resumen de ventas.
     */
    private SalesSummaryDTO salesSummary;

    /**
     * Lista de productos top.
     */
    private TopProductsDTO topProducts;

    /**
     * Productos con stock crítico.
     */
    private CriticalStockDTO criticalStock;

    /**
     * Ingresos por categoría.
     */
    private RevenueByCategoryDTO revenueByCategory;

    /**
     * Clientes destacados.
     */
    private TopCustomersDTO topCustomers;
}
