package com.mildlamb.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"sms_fanout.queue"})
public class FanoutSmsConsumer {
    @RabbitHandler  // 消息的落脚点
    public void getMessage(String message){
        System.out.println("sms fanout --> " + message);
    }
}
