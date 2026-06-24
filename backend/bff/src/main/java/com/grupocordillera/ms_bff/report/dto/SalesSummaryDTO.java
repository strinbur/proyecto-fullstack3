package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;

@Data
public class SalesSummaryDTO {


    /** Cantidad total de órdenes registradas. */
    private int totalOrders;

    /** Órdenes completadas. */
    private int completedOrders;

    /** Órdenes pendientes. */
    private int pendingOrders;

    /** Órdenes canceladas. */
    private int cancelledOrders;

    /** Ingreso total generado. */
    private double totalRevenue;

    /** Valor promedio por orden. */
    private double averageOrderValue;
}
