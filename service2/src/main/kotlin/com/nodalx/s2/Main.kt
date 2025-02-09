package com.nodalx.s2

import com.rabbitmq.client.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.net.ConnectException // Add this import

fun main() {
    val factory = ConnectionFactory()
    factory.host = "rabbitmq" // Docker service name
    factory.port = 5672 // Default RabbitMQ port

    var connection: Connection? = null
    var retries = 5
    while (retries > 0) {
        try {
            connection = factory.newConnection()
            println("Connected to RabbitMQ")
            break
        } catch (e: ConnectException) { // Use ConnectException here
            retries--
            println("RabbitMQ not ready, retrying in 5 seconds...")
            Thread.sleep(5000)
        }
    }

    if (connection == null) {
        println("Failed to connect to RabbitMQ")
        return
    }

    val channel = connection.createChannel()

    val exchangeName = "messages_exchange"
    val s1Queue = "s1_queue"
    val s2Queue = "s2_queue"
    val s1RoutingKey = "s1"
    val s2RoutingKey = "s2"

    // Declare exchange and queues
    channel.exchangeDeclare(exchangeName, "direct", true)
    channel.queueDeclare(s2Queue, false, false, false, null)
    channel.queueBind(s2Queue, exchangeName, s2RoutingKey)

    // Consumer for S2
    val consumer = object : DefaultConsumer(channel) {
        override fun handleDelivery(
            consumerTag: String?,
            envelope: Envelope?,
            properties: AMQP.BasicProperties?,
            body: ByteArray?
        ) {
            val message = String(body!!, Charsets.UTF_8)
            
            if (message == "ping") {
                println("S2 Received: $message")
                // Send pong immediately
                println("S2 Sent: pong")
                channel.basicPublish(exchangeName, s1RoutingKey, null, "pong".toByteArray())
                
                // Schedule ping after 10s
                runBlocking {
                    delay(10000) // Wait 10 seconds
                    channel.basicPublish(exchangeName, s1RoutingKey, null, "ping".toByteArray())
                    println("S2 Sent: ping")
                }
            }
        }
    }

    // Start consuming messages
    channel.basicConsume(s2Queue, true, consumer)
}