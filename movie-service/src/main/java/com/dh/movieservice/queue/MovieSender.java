package com.dh.movieservice.queue;

import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.send.SendMovie;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSender {

    private final RabbitTemplate rabbitTemplate;

    private final Queue movieQueue;

    public void sendMovie(Movie movie, String action){
            SendMovie send = new SendMovie();
            send.setId(1L);
            send.setMovie(movie);
            send.setAction(action);
            this.rabbitTemplate.convertAndSend(this.movieQueue.getName(), send);
    }

}
