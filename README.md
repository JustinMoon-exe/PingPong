
# Nodal Exchange Take-Home Assessment: Spring + Kotlin + RabbitMQ  
**Candidate: Justin Moonjeli**  
**Submission Date: Feb 9, 2025**  

---

## Overview  
This project demonstrates two containerized services (**S1** in Spring/Java and **S2** in Kotlin) communicating via RabbitMQ in a "ping-pong" loop. The implementation includes retry logic for RabbitMQ startup delay and routing configuration. Below, I detail my process, challenges, and how AI tools accelerated my learning.  

---

## Key Features  
- **Service-1 (S1)**:  
  - Spring Boot app using `RabbitTemplate` for publishing messages.  
  - Retry logic to handle RabbitMQ connection startup delays.  
  - Schedules periodic "ping" messages after 10-second delays.  

- **Service-2 (S2)**:  
  - Kotlin app with RabbitMQ connection retries.  
  - Coroutine-based 10-second delay using `delay(10000)`.  

- **RabbitMQ**:  
  - Direct exchange `messages_exchange` with routing keys `s1` and `s2`.  
  - Queues: `s1_queue`, `s2_queue`
---

## Setup  
### Prerequisites  
- Docker & Docker Compose (latest versions).  

### Run the Project  
```bash  
# Start services  
docker-compose up -d  

# View logs  
docker logs -f s1  # Spring service  
docker logs -f s2  # Kotlin service  
```  

---

## 📝 Implementation Details  

### Service-1 (Spring/Java)  
- **RabbitConfig.java**:  
  - Declares `messages_exchange`, queues (`s1_queue`, `s2_queue`), and bindings.  
  - Uses `@RabbitListener` to consume messages from `s1_queue`.  

- **MessageListener.java**:  
  - On receiving "ping", sends "pong" to S2 via `s2` routing key.  
  - Uses `ScheduledExecutorService` to schedule the next "ping" after 10 seconds.  

- **S1Initializer.java**:  
  - Retries RabbitMQ connection 5 times with 5-second intervals.  

### Service-2 (Kotlin)  
- **Main.kt**:  
  - Retries RabbitMQ connection until successful.  
  - On receiving "ping", sends "pong" immediately and schedules the next "ping" after 10 seconds using coroutines.  

---

## Learning Process & Challenges  
### Initial Knowledge Gaps  
- **Docker**:  
  - Minor prior experience (basic `Dockerfile` usage).  
  - **AI Assistance**:  
    - Learned about multi-stage builds, service dependencies, and Docker networking (e.g., using `rabbitmq` as the hostname).  

- **RabbitMQ**:  
  - Zero prior experience.  
  - **AI Assistance**:  
    - Learned about configuration, routing, queues and exhanges, as well as interfacing with Docker 

### Key Challenges & Solutions  
1. **RabbitMQ Connection Retries**:  
   - S1/S2 failed to start if RabbitMQ wasn’t ready.  
   - **Solution**:  
     - Added retry loops (5 attempts, 5-second intervals) in both services.  
     - AI-generated code snippets for retry logic in Spring (`S1Initializer`) and Kotlin.  

2. **Docker Networking Issues**:  
   - S2 couldn’t connect to `rabbitmq` host.  
   - **Solution**:  
     - AI advice: *"Ensure services are in the same Docker network via `docker-compose`."* → Verified `depends_on` and network aliases.  

---

## Time Investment  
- **Total Time**: ~2.5 hours.  
  - **Learning Docker/RabbitMQ**: 1 hour (AI + official docs).  
  - **Coding & Debugging**: 1.5 hours.  

---

## AI Tools Used  
- **ChatGPT**:  
  - Generated boilerplate code for Spring `@RabbitListener` and Kotlin coroutines.  
  - Explained RabbitMQ concepts (exchanges, bindings, routing keys).  
  - Debugged Docker errors (e.g., port conflicts).  

- **DeepSeek**:  
  - Provided information on build tools and Java version mismatch with Gradle  

---

## Conclusion  
This project was a crash course in Docker and RabbitMQ. With **AI as a mentor**, I quickly bridged knowledge gaps, debugged issues, and implemented a robust messaging system. The result is a scalable, containerized setup that meets all requirements. 
