package com.example.OrderService.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private String productName;
    private int quantity;
    private double price;
    private String region;
}
