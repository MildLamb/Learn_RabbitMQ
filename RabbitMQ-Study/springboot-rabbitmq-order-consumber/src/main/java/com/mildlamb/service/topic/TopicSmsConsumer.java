package com.mildlamb.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        value = @Queue(value = "sms_topic.queue",durable = "true",autoDelete = "false"),
        key = "*.com.*"
))
public class TopicSmsConsumer {
    @RabbitHandler  // 消息的落脚点
    public void getMessage(String message){
        System.out.println("sms topic --> " + message);
    }
}
