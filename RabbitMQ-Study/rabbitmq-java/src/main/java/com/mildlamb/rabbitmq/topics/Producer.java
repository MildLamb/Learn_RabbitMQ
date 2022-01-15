package com.mildlamb.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式
 */

public class Producer {
    public static void main(String[] args){
        // 所有的中间件技术都是基于TCP/IP协议基础之上重新构建新型协议规范，只不过rabbitmq遵循的是amqp

        // 1. 创建连接工厂,设置账号密码，ip等等
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("150.158.46.233");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("mildlamb");
        connectionFactory.setPassword("W2kindred");
        connectionFactory.setVirtualHost("/");

        // 连接
        Connection connection = null;
        // 通道
        Channel channel = null;

        try {
            // 2. 创建连接Connection
            connection = connectionFactory.newConnection("生产者");
            // 3. 通过连接获取通道Channel
            channel = connection.createChannel();
            // 4. 通过通道创建交换机， 声明队列，绑定关系，路由key，发送消息和接收消息
            /**
             * @params1 队列名称
             * @Params2 队列是否要持久化 false-不持久化 true-持久化，没持久化的队列，服务器重启会消失；非持久化队列会存盘吗？会，但会随着服务器的重启而丢失
             * @Params3 排他性，是否独占独立
             * @Params4 是否自动删除，随着最后一个消费者消息完毕消息之后是否把队列自动删除
             * @Params5 携带的一些额外的参数
             */
            // 5. 准备消息内容
            String message = "mildlamb!!!";

            // 6.准备要绑定的交换机
            String exchangeName = "topic_exchange";
            // 指定路由key，fanout没有路由key，指定也不会生效
            String routeKey = "com.order.test.xxxx";
            // 指定交换机类型
            String type = "topic";

            // 7. 发送消息给队列 queue
            /**
             * @parames1 交换机
             * @parames2 发送给哪个队列
             * @parames3 消息的状态控制
             * @parames4 消息主体
             */
            // 可以存在没有交换机的队列吗？不可以，虽然我们交换机参数没有写，但是一定会存在一个默认的交换机
            channel.basicPublish(exchangeName,routeKey,null,message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 8. 关闭通道
            if (channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 9. 关闭连接
            if (connection != null && connection.isOpen()){
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
