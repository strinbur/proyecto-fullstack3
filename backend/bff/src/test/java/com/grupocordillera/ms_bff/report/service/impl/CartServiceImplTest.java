package com.grupocordillera.ms_bff.report.service.impl;

import com.grupocordillera.ms_bff.report.client.ReportClient;
import com.grupocordillera.ms_bff.report.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportClient client;

    @InjectMocks
    private ReportServiceImpl service;

    @Test
    void getFullReport() {
        when(client.getFullReport("token"))
                .thenReturn(new FullAnalyticsReportDTO());

        assertNotNull(service.getFullReport("token"));
    }

    @Test
    void getHistory() {
        when(client.getHistory("token"))
                .thenReturn(List.of());

        assertNotNull(service.getHistory("token"));
    }

    @Test
    void getSalesSummary() {
        when(client.getSalesSummary())
                .thenReturn(new SalesSummaryDTO());

        assertNotNull(service.getSalesSummary());
    }

    @Test
    void getTopProducts() {
        when(client.getTopProducts(5))
                .thenReturn(new TopProductsDTO());

        assertNotNull(service.getTopProducts(5));
    }

    @Test
    void getCriticalStock() {
        when(client.getCriticalStock())
                .thenReturn(new CriticalStockDTO());

        assertNotNull(service.getCriticalStock());
    }

    @Test
    void getRevenueByCategory() {
        when(client.getRevenueByCategory())
                .thenReturn(new RevenueByCategoryDTO());

        assertNotNull(service.getRevenueByCategory());
    }

    @Test
    void getTopCustomers() {
        when(client.getTopCustomers(5))
                .thenReturn(new TopCustomersDTO());

        assertNotNull(service.getTopCustomers(5));
    }
}