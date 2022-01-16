package com.mildlamb.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
// 将消费者与消息队列绑定
@RabbitListener(queues = {"msg_direct.queue"})
public class DirectMsgConsumer {
    @RabbitHandler
    public void getMessage(String message){
        System.out.println("message direct --> " + message);
    }
}
