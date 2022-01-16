package com.mildlamb.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"email_direct.queue"})
public class DirectEmailConsumer {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("email direct --> " + msg);
    }
}
