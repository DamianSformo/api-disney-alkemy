package com.dh.characterservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQSenderConfig {

    @Value("${queue.character.name}")
    private String characterQueue;

    @Bean
    public Queue getCharacterQueue() {
        return new Queue(this.characterQueue, true);
    }

}
