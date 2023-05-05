package com.example.client.demo.consume;

import com.example.client.demo.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class Consume {
    @Bean
    public Function<Message<OrderCreatedEvent>,Message<OrderCreatedEvent>> orderChannel() {
        return message -> {


            String routingKey = message.getHeaders().get(AmqpHeaders.RECEIVED_ROUTING_KEY, String.class);
            OrderCreatedEvent payload = message.getPayload();
            log.info("===order rabbitMQ event Start===");
            log.info("order rounting key: {}", routingKey);
            log.info("order id : {}",payload.getOrderId());
            log.info("ammount : {}",payload.getAmount());
            log.info("===order rabbitMQ event End===");

            payload.setProcessing(true);
            routingKey = "processing1";
            return MessageBuilder.withPayload(payload)
                    .setHeader("routingKey",routingKey)
                    .build();
        };
    }
}
