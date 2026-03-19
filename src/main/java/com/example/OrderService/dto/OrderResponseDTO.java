package com.example.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDTO {

    private Long orderId;
    private String status;
}
