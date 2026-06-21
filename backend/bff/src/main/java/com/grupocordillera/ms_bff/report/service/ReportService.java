package com.grupocordillera.ms_bff.report.service;

import com.grupocordillera.ms_bff.report.dto.*;
import java.util.List;

public interface ReportService {

    FullAnalyticsReportDTO getFullReport(String token);

    List<ReportLogDTO> getHistory(String token);

    SalesSummaryDTO getSalesSummary();

    TopProductsDTO getTopProducts(int topN);

    CriticalStockDTO getCriticalStock();

    RevenueByCategoryDTO getRevenueByCategory();

    TopCustomersDTO getTopCustomers(int topN);
}