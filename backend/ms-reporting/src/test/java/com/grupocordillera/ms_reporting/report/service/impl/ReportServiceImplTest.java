package com.grupocordillera.ms_reporting.report.service.impl;

import com.grupocordillera.ms_reporting.report.client.AnalyticsClient;
import com.grupocordillera.ms_reporting.report.document.ReportLog;
import com.grupocordillera.ms_reporting.report.dto.*;
import com.grupocordillera.ms_reporting.report.repository.ReportLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private AnalyticsClient analyticsClient;

    @Mock
    private ReportLogRepository reportLogRepository;

    @InjectMocks
    private ReportServiceImpl service;


    @Test
    void testGetFullReport_shouldReturnAndSaveLog() {

        String user = "test@test.com";

        FullAnalyticsReportDTO dto = new FullAnalyticsReportDTO();

        when(analyticsClient.getFullReport()).thenReturn(dto);
        when(reportLogRepository.save(any(ReportLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FullAnalyticsReportDTO result = service.getFullReport(user);

        assertNotNull(result);

        verify(analyticsClient).getFullReport();
        verify(reportLogRepository).save(any(ReportLog.class));
    }

    @Test
    void testGetSalesSummary() {

        SalesSummaryDTO dto = new SalesSummaryDTO();

        when(analyticsClient.getSalesSummary()).thenReturn(dto);

        SalesSummaryDTO result = service.getSalesSummary();

        assertNotNull(result);

        verify(analyticsClient).getSalesSummary();
    }

    @Test
    void testGetTopProducts() {

        TopProductsDTO dto = new TopProductsDTO();

        when(analyticsClient.getTopProducts(5)).thenReturn(dto);

        TopProductsDTO result = service.getTopProducts(5);

        assertNotNull(result);

        verify(analyticsClient).getTopProducts(5);
    }


    @Test
    void testGetCriticalStock() {

        CriticalStockDTO dto = new CriticalStockDTO();

        when(analyticsClient.getCriticalStock()).thenReturn(dto);

        CriticalStockDTO result = service.getCriticalStock();

        assertNotNull(result);

        verify(analyticsClient).getCriticalStock();
    }


    @Test
    void testGetRevenueByCategory() {

        RevenueByCategoryDTO dto = new RevenueByCategoryDTO();

        when(analyticsClient.getRevenueByCategory()).thenReturn(dto);

        RevenueByCategoryDTO result = service.getRevenueByCategory();

        assertNotNull(result);

        verify(analyticsClient).getRevenueByCategory();
    }


    @Test
    void testGetTopCustomers() {

        TopCustomersDTO dto = new TopCustomersDTO();

        when(analyticsClient.getTopCustomers(5)).thenReturn(dto);

        TopCustomersDTO result = service.getTopCustomers(5);

        assertNotNull(result);

        verify(analyticsClient).getTopCustomers(5);
    }


    @Test
    void testGetReportHistory() {

        List<ReportLog> logs = List.of(new ReportLog(), new ReportLog());

        when(reportLogRepository.findAllByOrderByRequestedAtDesc())
                .thenReturn(logs);

        List<ReportLog> result = service.getReportHistory();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(reportLogRepository).findAllByOrderByRequestedAtDesc();
    }
}