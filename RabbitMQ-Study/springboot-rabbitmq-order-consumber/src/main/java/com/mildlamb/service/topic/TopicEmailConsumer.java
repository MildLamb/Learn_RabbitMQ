package com.mildlamb.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        value = @Queue(value = "email_topic.queue",durable = "true",autoDelete = "false"),
        key = "#.@.#"
))
public class TopicEmailConsumer {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("email topic --> " + msg);
    }
}
