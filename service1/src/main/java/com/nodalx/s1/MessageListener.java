package com.nodalx.s1;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Component
public class MessageListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    @RabbitListener(queues = RabbitConfig.S1_QUEUE)
    public void receiveMessage(String message) {
        if ("ping".equals(message)) {
            System.out.println("S1 Received: ping");

            // Send pong immediately
            System.out.println("S1 Sent: pong");
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.S2_ROUTING_KEY, "pong");
            

            // Schedule next ping after 10 seconds
            scheduledExecutor.schedule(() -> {
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.S2_ROUTING_KEY, "ping");
                System.out.println("S1 Sent: ping");
            }, 10, TimeUnit.SECONDS);
        }
    }
}