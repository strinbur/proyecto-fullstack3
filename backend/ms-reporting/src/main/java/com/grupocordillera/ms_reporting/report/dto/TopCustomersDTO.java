package com.grupocordillera.ms_reporting.report.dto;


import lombok.Data;
import java.util.List;


@Data
public class TopCustomersDTO {

    /**
     * Número de clientes top incluidos.
     */
    private int topN;

    /**
     * Lista de clientes con métricas de gasto.
     */
    private List<TopCustomerItemDTO> customers;


    @Data
    public static class TopCustomerItemDTO {
        private String userEmail;
        private String userName;
        private int totalOrders;
        private double totalSpent;
    }
}
