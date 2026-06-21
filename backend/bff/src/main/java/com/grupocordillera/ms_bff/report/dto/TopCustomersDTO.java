package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.util.List;


@Data
public class TopCustomersDTO {


    private int topN;
    private List<TopCustomerItemDTO> customers;


    @Data
    public static class TopCustomerItemDTO {
        private String userEmail;
        private String userName;
        private int totalOrders;
        private double totalSpent;
    }
}