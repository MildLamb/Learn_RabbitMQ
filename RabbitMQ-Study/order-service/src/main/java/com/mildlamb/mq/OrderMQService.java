package com.mildlamb.mq;

import com.alibaba.fastjson.JSON;
import com.mildlamb.pojo.Order;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@SuppressWarnings("all")
public class OrderMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // @PostConstruct 注解
    // @PostConstruct注解用来修饰一个非静态的void方法，被@PostConstruct修饰的方法会在实例化后，初始化前执行
    @PostConstruct
    public void regCallback(){
        // 消息发送成功以后，给予生产者的消息回执，来确保生产者的可靠性
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("cause:" + cause);
                // 如果ack为true代表消息已经收到
                String orderId = correlationData.getId();
                if (!ack){
                    // 这里可能要进行其他方式的存储
                    System.out.println("MQ队列应答失败，orderId是:" + orderId);
                    return;
                }
                try {
                    String updateSql = "update ksd_order_message set status = 1 where order_id = ?";
                    int count = jdbcTemplate.update(updateSql,orderId);
                    if (count == 1){
                        System.out.println("本地消息状态修改成功，消息成功投递到消息队列中...");
                    }
                }catch (Exception e){
                    System.out.println("本地消息状态修改失败，出现异常:" + e.getMessage());
                }
            }
        });
    }

    public void sendMessage(Order order) throws InterruptedException {
        // 通过MQ发送消息
        System.out.println("order的id是:" + order.getOrderId());
        rabbitTemplate.convertAndSend("order_fanout_exchange","", JSON.toJSONString(order),new CorrelationData(order.getOrderId()));
        Thread.sleep(1000);
    }
}
