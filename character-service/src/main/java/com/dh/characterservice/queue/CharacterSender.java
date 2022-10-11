package com.dh.characterservice.queue;

import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.send.SendCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterSender {

    private final RabbitTemplate rabbitTemplate;

    private final Queue characterQueue;

    public void sendCharacter(Character character, String action) {
        SendCharacter send = new SendCharacter();
        send.setId(1L);
        send.setCharacter(character);
        send.setAction(action);
        this.rabbitTemplate.convertAndSend(this.characterQueue.getName(), send);
    }
}
