package com.grupocordillera.ms_reporting.report.dto;


import lombok.Data;


@Data
public class SalesSummaryDTO {


    private int totalOrders;
    private int completedOrders;
    private int pendingOrders;
    private int cancelledOrders;
    private double totalRevenue;
    private double averageOrderValue;
}
