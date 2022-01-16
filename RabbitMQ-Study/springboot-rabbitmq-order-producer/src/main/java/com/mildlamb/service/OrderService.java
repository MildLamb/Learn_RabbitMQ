package com.mildlamb.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description 模拟用户下单
     * @param user_id
     * @param product_id
     * @param num
     */
    public void makeOrder(String user_id,String product_id,int num){
        // 根据商品id查询库存是否充足
        // 保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        // 通过消息队列完成消息的分发
        // 参数一：交换机   参数二：routingKey   参数三：消息本体
        // Work模式  参数一：“”   参数二：队列名   参数三：消息本体
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,"订单:" + orderId);
    }

    public void makeOrder_direct(String user_id,String product_id,int num){
        // 根据商品id查询库存是否充足
        // 保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        // 通过消息队列完成消息的分发
        // 参数一：交换机   参数二：routingKey   参数三：消息本体
        // Work模式  参数一：“”   参数二：队列名   参数三：消息本体
        String exchangeName = "direct_order_exchange";
        rabbitTemplate.convertAndSend(exchangeName,"email","订单:" + orderId);
        rabbitTemplate.convertAndSend(exchangeName,"msg","订单:" + orderId);
    }

    public void makeOrder_topic(String user_id,String product_id,int num){
        // 根据商品id查询库存是否充足
        // 保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        // 通过消息队列完成消息的分发
        // 参数一：交换机   参数二：routingKey   参数三：消息本体
        // Work模式  参数一：“”   参数二：队列名   参数三：消息本体
        String exchangeName = "topic_order_exchange";

        /*
            email  #.@.#
            msg    *.msg.#
            sms    *.com.*
         */
        String routeKey = "com.msg.@.qq";
        rabbitTemplate.convertAndSend(exchangeName,routeKey,"订单:" + orderId);
    }

    public void makeOrder_ttl(String user_id,String product_id,int num){

        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        String exchangeName = "ttl_direct_exchange";
        String routeKey = "ttl";
        rabbitTemplate.convertAndSend(exchangeName,routeKey,"订单:" + orderId);
    }

    public void makeOrder_ttlMessage(String user_id,String product_id,int num){

        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        String exchangeName = "ttl_direct_exchange";
        String routeKey = "ttlMessage";

        // 给消息设置过期时间
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息的过期时间
                message.getMessageProperties().setExpiration("10000");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            }
        };

        rabbitTemplate.convertAndSend(exchangeName,routeKey,"订单:" + orderId,messagePostProcessor);
    }
}
