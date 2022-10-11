package com.dh.moviecharacterservice.queue;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.api.service.CharacterServiceImpl;
import com.dh.moviecharacterservice.domain.model.send.SendCharacter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CharacterListener {

    private final CharacterServiceImpl service;

    public CharacterListener(CharacterServiceImpl service) {
        this.service = service;
    }

    @RabbitListener(queues = {"${queue.character.name}"})
    public void receiveCharacter(@Payload SendCharacter sendCharacter)throws ResourceNotFoundException {
        if(sendCharacter.getAction().equals("save")) {
            service.save(sendCharacter.getCharacter());
        } else if (sendCharacter.getAction().equals("delete")){
            service.delete(sendCharacter.getCharacter().getId());
        }
    }
}
