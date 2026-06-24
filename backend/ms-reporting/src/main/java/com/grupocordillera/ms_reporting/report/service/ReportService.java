package com.grupocordillera.ms_reporting.report.service;


import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.CriticalStockDTO;
import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import com.grupocordillera.ms_reporting.report.dto.RevenueByCategoryDTO;
import com.grupocordillera.ms_reporting.report.dto.SalesSummaryDTO;
import com.grupocordillera.ms_reporting.report.dto.TopCustomersDTO;
import com.grupocordillera.ms_reporting.report.dto.TopProductsDTO;


import java.util.List;


/**
 * Servicio que provee operaciones para generar y consultar reportes analíticos.
 */
public interface ReportService {

    /**
     * Genera el reporte analítico completo y persiste un registro en el historial.
     *
     * @param requestedBy identificador del solicitante (p.ej. email)
     * @return DTO con el reporte completo
     */
    FullAnalyticsReportDTO getFullReport(String requestedBy);

    /**
     * Obtiene métricas resumidas de ventas.
     *
     * @return DTO con resumen de ventas
     */
    SalesSummaryDTO getSalesSummary();

    /**
     * Obtiene los productos más vendidos.
     *
     * @param topN número de productos a devolver
     * @return DTO con productos top
     */
    TopProductsDTO getTopProducts(int topN);

    /**
     * Recupera los productos con stock por debajo de un umbral crítico.
     *
     * @return DTO con stock crítico
     */
    CriticalStockDTO getCriticalStock();

    /**
     * Calcula ingresos agregados por categoría.
     *
     * @return DTO con ingresos por categoría
     */
    RevenueByCategoryDTO getRevenueByCategory();

    /**
     * Devuelve los clientes que más han gastado.
     *
     * @param topN número de clientes a devolver
     * @return DTO con clientes top
     */
    TopCustomersDTO getTopCustomers(int topN);

    /**
     * Recupera el historial de reportes generados.
     *
     * @return lista de registros de reportes
     */
    List<ReportLog> getReportHistory();
}
