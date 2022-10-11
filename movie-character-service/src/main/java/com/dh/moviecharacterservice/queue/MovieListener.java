package com.dh.moviecharacterservice.queue;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.api.service.MovieServiceImpl;
import com.dh.moviecharacterservice.domain.model.send.SendMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieListener {

    private final MovieServiceImpl service;

    public MovieListener(MovieServiceImpl service) {
        this.service = service;
    }

    @RabbitListener(queues = {"${queue.movie.name}"})
    public void receiveMovie(@Payload SendMovie sendMovie) throws ResourceNotFoundException{
        if(sendMovie.getAction().equals("save")) {
            service.save(sendMovie.getMovie());
        } else if (sendMovie.getAction().equals("delete")){
            service.delete(sendMovie.getMovie().getId());
        }
    }
}
