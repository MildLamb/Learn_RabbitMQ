package com.mildlamb.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
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
            channel.basicConsume("MyQueue", true, new DeliverCallback() {
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到的消息是" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("接收消息失败了...");
                }
            });

            System.out.println("开始接收消息");
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭通道
            if (channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 8. 关闭连接
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
