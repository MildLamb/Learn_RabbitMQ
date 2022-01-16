package com.mildlamb.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
// 将消费者与消息队列绑定
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        value = @Queue(value = "msg_topic.queue",durable = "true",autoDelete = "false"),
        key = "*.msg.#"
))
public class TopicMsgConsumer {
    @RabbitHandler
    public void getMessage(String message){
        System.out.println("message topic --> " + message);
    }
}
