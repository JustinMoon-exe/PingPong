package com.nodalx.s1;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "messages_exchange";
    public static final String S1_QUEUE = "s1_queue";
    public static final String S2_QUEUE = "s2_queue";
    public static final String S1_ROUTING_KEY = "s1";
    public static final String S2_ROUTING_KEY = "s2";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue s1Queue() {
        return new Queue(S1_QUEUE);
    }

    @Bean
    public Binding s1Binding(Queue s1Queue, DirectExchange exchange) {
        return BindingBuilder.bind(s1Queue).to(exchange).with(S1_ROUTING_KEY);
    }
}