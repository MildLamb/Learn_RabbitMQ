package com.mildlamb.service.dead;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"dead.queue"})
public class DirectDeadConsumer {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("dead direct --> " + msg);
    }
}
