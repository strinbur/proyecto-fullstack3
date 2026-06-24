package com.grupocordillera.ms_reporting.report.service.impl;


import com.grupocordillera.ms_reporting.report.client.AnalyticsClient;
import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.CriticalStockDTO;
import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import com.grupocordillera.ms_reporting.report.dto.RevenueByCategoryDTO;
import com.grupocordillera.ms_reporting.report.dto.SalesSummaryDTO;
import com.grupocordillera.ms_reporting.report.dto.TopCustomersDTO;
import com.grupocordillera.ms_reporting.report.dto.TopProductsDTO;
import com.grupocordillera.ms_reporting.report.repository.ReportLogRepository;
import com.grupocordillera.ms_reporting.report.service.ReportService;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
/**
 * Implementación de {@link ReportService} que delega en el cliente de analytics
 * y persiste un registro en el historial de reportes.
 */
public class ReportServiceImpl implements ReportService {


    private final AnalyticsClient analyticsClient;
    private final ReportLogRepository reportLogRepository;


    public ReportServiceImpl(
            AnalyticsClient analyticsClient,
            ReportLogRepository reportLogRepository
    ) {
        this.analyticsClient = analyticsClient;
        this.reportLogRepository = reportLogRepository;
    }


    @Override
    public FullAnalyticsReportDTO getFullReport(String requestedBy) {

        // Genera el reporte completo consultando el cliente de analytics
        FullAnalyticsReportDTO report = analyticsClient.getFullReport();

        ReportLog log = new ReportLog();
        log.setRequestedBy(requestedBy);
        log.setRequestedAt(LocalDateTime.now());
        log.setSnapshot(report);

        reportLogRepository.save(log);

        return report;
    }


    @Override
    public SalesSummaryDTO getSalesSummary() {
        return analyticsClient.getSalesSummary();
    }


    @Override
    public TopProductsDTO getTopProducts(int topN) {
        return analyticsClient.getTopProducts(topN);
    }


    @Override
    public CriticalStockDTO getCriticalStock() {
        return analyticsClient.getCriticalStock();
    }


    @Override
    public RevenueByCategoryDTO getRevenueByCategory() {
        return analyticsClient.getRevenueByCategory();
    }


    @Override
    public TopCustomersDTO getTopCustomers(int topN) {
        return analyticsClient.getTopCustomers(topN);
    }


    @Override
    public List<ReportLog> getReportHistory() {
        return reportLogRepository.findAllByOrderByRequestedAtDesc();
    }
}
