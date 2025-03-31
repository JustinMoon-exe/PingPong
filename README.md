# PingPong: Real-Time Signal Distribution Platform

## Overview
PingPong is a high-performance platform demo designed to disseminate real-time signals across a microservices architecture. Built using Java (Spring Boot) and Kotlin, the system leverages asynchronous messaging with RabbitMQ to ensure low-latency, fault-tolerant communication between services. Containerized with Docker and orchestrated via Docker Compose, SignalFlow offers a scalable and reliable solution for real-time signal distribution.

## Architecture
- **Service 1 (Java, Spring Boot):** Publishes and receives signal messages.
- **Service 2 (Kotlin):** Consumes signals, processes them, and responds accordingly.
- **RabbitMQ:** Acts as the message broker to facilitate asynchronous, low-latency communication.
- **Docker & Docker Compose:** Containerizes and orchestrates the services for seamless deployment.

## Features
- Asynchronous messaging for real-time signal exchange
- Low-latency and fault-tolerant communication between services
- Scalable microservices architecture
- Containerized deployment with Docker and Docker Compose
- Easy monitoring via service logs and the RabbitMQ management console

## Prerequisites
- [Docker](https://docs.docker.com/get-docker/) (v20+)
- [Docker Compose](https://docs.docker.com/compose/install/) (v1.29+)

