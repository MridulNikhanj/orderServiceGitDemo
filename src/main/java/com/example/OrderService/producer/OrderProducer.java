package com.example.OrderService.producer;

import com.example.OrderService.config.RabbitMQConfig;
import com.example.OrderService.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    // used to send message to RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    public void sendToDirectExchange( OrderCreatedEvent event ){
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.DIRECT_ROUTING_KEY , event );
    }

    public void sendToTopicExchange( OrderCreatedEvent event , String region){
        String routingKey = "order." + region;
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey , event );
    }

}
