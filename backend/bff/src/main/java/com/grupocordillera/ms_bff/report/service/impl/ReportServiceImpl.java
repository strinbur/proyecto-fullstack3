package com.grupocordillera.ms_bff.report.service.impl;

import com.grupocordillera.ms_bff.report.client.ReportClient;
import com.grupocordillera.ms_bff.report.dto.*;
import com.grupocordillera.ms_bff.report.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportClient client;

    public ReportServiceImpl(ReportClient client) {
        this.client = client;
    }

    @Override
    public FullAnalyticsReportDTO getFullReport(String token) {
        return client.getFullReport(token);
    }

    @Override
    public List<ReportLogDTO> getHistory(String token) {
        return client.getHistory(token);
    }

    @Override
    public SalesSummaryDTO getSalesSummary() {
        return client.getSalesSummary();
    }

    @Override
    public TopProductsDTO getTopProducts(int topN) {
        return client.getTopProducts(topN);
    }

    @Override
    public CriticalStockDTO getCriticalStock() {
        return client.getCriticalStock();
    }

    @Override
    public RevenueByCategoryDTO getRevenueByCategory() {
        return client.getRevenueByCategory();
    }

    @Override
    public TopCustomersDTO getTopCustomers(int topN) {
        return client.getTopCustomers(topN);
    }
}