package com.grupocordillera.ms_bff.report.controller;

import com.grupocordillera.ms_bff.report.dto.*;
import com.grupocordillera.ms_bff.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService service;

    @InjectMocks
    private ReportController controller;

    @Test
    void getFullReport_shouldReturnReport() {

        FullAnalyticsReportDTO response = new FullAnalyticsReportDTO();

        when(service.getFullReport("token"))
                .thenReturn(response);

        FullAnalyticsReportDTO result =
                controller.getFullReport("token");

        assertNotNull(result);

        verify(service, times(1))
                .getFullReport("token");
    }

    @Test
    void getHistory_shouldReturnHistory() {

        List<ReportLogDTO> response = List.of();

        when(service.getHistory("token"))
                .thenReturn(response);

        List<?> result =
                controller.getHistory("token");

        assertNotNull(result);

        verify(service, times(1))
                .getHistory("token");
    }

    @Test
    void getSalesSummary_shouldReturnSummary() {

        SalesSummaryDTO response = new SalesSummaryDTO();

        when(service.getSalesSummary())
                .thenReturn(response);

        SalesSummaryDTO result =
                controller.getSalesSummary();

        assertNotNull(result);

        verify(service, times(1))
                .getSalesSummary();
    }

    @Test
    void getTopProducts_shouldReturnData() {

        TopProductsDTO response = new TopProductsDTO();

        when(service.getTopProducts(5))
                .thenReturn(response);

        TopProductsDTO result =
                controller.getTopProducts(5);

        assertNotNull(result);

        verify(service, times(1))
                .getTopProducts(5);
    }

    @Test
    void getCriticalStock_shouldReturnData() {

        CriticalStockDTO response = new CriticalStockDTO();

        when(service.getCriticalStock())
                .thenReturn(response);

        CriticalStockDTO result =
                controller.getCriticalStock();

        assertNotNull(result);

        verify(service, times(1))
                .getCriticalStock();
    }

    @Test
    void getRevenueByCategory_shouldReturnData() {

        RevenueByCategoryDTO response = new RevenueByCategoryDTO();

        when(service.getRevenueByCategory())
                .thenReturn(response);

        RevenueByCategoryDTO result =
                controller.getRevenueByCategory();

        assertNotNull(result);

        verify(service, times(1))
                .getRevenueByCategory();
    }

    @Test
    void getTopCustomers_shouldReturnData() {

        TopCustomersDTO response = new TopCustomersDTO();

        when(service.getTopCustomers(5))
                .thenReturn(response);

        TopCustomersDTO result =
                controller.getTopCustomers(5);

        assertNotNull(result);

        verify(service, times(1))
                .getTopCustomers(5);
    }
}