package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class FullAnalyticsReportDTO {


    /** Fecha y hora de generación del reporte. */
    private LocalDateTime generatedAt;

    /** Resumen agregado de ventas. */
    private SalesSummaryDTO salesSummary;

    /** Datos de productos más vendidos. */
    private TopProductsDTO topProducts;

    /** Estado de stock crítico. */
    private CriticalStockDTO criticalStock;

    /** Ingresos por categoría. */
    private RevenueByCategoryDTO revenueByCategory;

    /** Clientes destacados por gasto. */
    private TopCustomersDTO topCustomers;
}