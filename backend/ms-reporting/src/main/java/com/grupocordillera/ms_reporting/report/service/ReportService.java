package com.grupocordillera.ms_reporting.report.service;


import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.CriticalStockDTO;
import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import com.grupocordillera.ms_reporting.report.dto.RevenueByCategoryDTO;
import com.grupocordillera.ms_reporting.report.dto.SalesSummaryDTO;
import com.grupocordillera.ms_reporting.report.dto.TopCustomersDTO;
import com.grupocordillera.ms_reporting.report.dto.TopProductsDTO;


import java.util.List;


public interface ReportService {


    FullAnalyticsReportDTO getFullReport(String requestedBy);

    SalesSummaryDTO getSalesSummary();

    TopProductsDTO getTopProducts(int topN);

    CriticalStockDTO getCriticalStock();

    RevenueByCategoryDTO getRevenueByCategory();

    TopCustomersDTO getTopCustomers(int topN);

    List<ReportLog> getReportHistory();
}
