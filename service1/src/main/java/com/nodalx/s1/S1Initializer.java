package com.nodalx.s1;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S1Initializer {
    @Bean
    public ApplicationRunner initialize(RabbitTemplate rabbitTemplate) {
        return args -> {
            int retries = 5;
            while (retries > 0) {
                try {
                    Thread.sleep(5000);
                    rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.S2_ROUTING_KEY, "ping");
                    System.out.println("S1 Initialized: Sent ping");
                    break;
                } catch (AmqpConnectException e) {
                    retries--;
                    System.out.println("RabbitMQ not ready, retrying in 5 seconds...");
                    Thread.sleep(5000);
                }
            }
        };
    }
}