package com.mildlamb.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"email_fanout.queue"})
public class FanoutEmailConsumer {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("email fanout --> " + msg);
    }
}
