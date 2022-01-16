package com.mildlamb.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"sms_direct.queue"})
public class DirectSmsConsumer {
    @RabbitHandler  // 消息的落脚点
    public void getMessage(String message){
        System.out.println("sms direct --> " + message);
    }
}
