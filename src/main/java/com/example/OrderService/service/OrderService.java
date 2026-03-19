package com.example.OrderService.service;

import com.example.OrderService.dto.OrderCreatedEvent;
import com.example.OrderService.dto.OrderRequestDTO;
import com.example.OrderService.dto.OrderResponseDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.producer.OrderProducer;
import com.example.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderProducer orderProducer;

    public OrderResponseDTO createOrder(OrderRequestDTO dto){
        Order order = new Order();
        order.setProductName(dto.getProductName());
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());
        order.setStatus("CREATED");

        Order saved = repository.save(order);

        //build the event payload to send via RabbitMQ
        OrderCreatedEvent event = new OrderCreatedEvent(saved.getId(), saved.getPrice());

        //publish to direct exchange
        orderProducer.sendToDirectExchange(event);

        orderProducer.sendToTopicExchange(event, dto.getRegion());

        return new OrderResponseDTO(saved.getId(), saved.getStatus());
    }

}
