package com.grupocordillera.ms_reporting.report.controller;

import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.*;
import com.grupocordillera.ms_reporting.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReportController controller;

    @Test
    void getFullReport_shouldReturnData() {

        FullAnalyticsReportDTO response = new FullAnalyticsReportDTO();

        when(authentication.getName()).thenReturn("test@test.com");
        when(reportService.getFullReport("test@test.com")).thenReturn(response);

        FullAnalyticsReportDTO result = controller.getFullReport(authentication);

        assertNotNull(result);
        verify(reportService).getFullReport("test@test.com");
    }

    @Test
    void getReportHistory_shouldReturnList() {

        List<ReportLog> logs = List.of(new ReportLog());

        when(reportService.getReportHistory()).thenReturn(logs);

        List<ReportLog> result = controller.getReportHistory();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(reportService).getReportHistory();
    }

    @Test
    void getSalesSummary_shouldReturnData() {

        SalesSummaryDTO dto = new SalesSummaryDTO();

        when(reportService.getSalesSummary()).thenReturn(dto);

        SalesSummaryDTO result = controller.getSalesSummary();

        assertNotNull(result);

        verify(reportService).getSalesSummary();
    }

    @Test
    void getTopProducts_shouldReturnData() {

        TopProductsDTO dto = new TopProductsDTO();

        when(reportService.getTopProducts(5)).thenReturn(dto);

        TopProductsDTO result = controller.getTopProducts(5);

        assertNotNull(result);

        verify(reportService).getTopProducts(5);
    }

    @Test
    void getCriticalStock_shouldReturnData() {

        CriticalStockDTO dto = new CriticalStockDTO();

        when(reportService.getCriticalStock()).thenReturn(dto);

        CriticalStockDTO result = controller.getCriticalStock();

        assertNotNull(result);

        verify(reportService).getCriticalStock();
    }

    @Test
    void getRevenueByCategory_shouldReturnData() {

        RevenueByCategoryDTO dto = new RevenueByCategoryDTO();

        when(reportService.getRevenueByCategory()).thenReturn(dto);

        RevenueByCategoryDTO result = controller.getRevenueByCategory();

        assertNotNull(result);

        verify(reportService).getRevenueByCategory();
    }

    @Test
    void getTopCustomers_shouldReturnData() {

        TopCustomersDTO dto = new TopCustomersDTO();

        when(reportService.getTopCustomers(5)).thenReturn(dto);

        TopCustomersDTO result = controller.getTopCustomers(5);

        assertNotNull(result);

        verify(reportService).getTopCustomers(5);
    }
}