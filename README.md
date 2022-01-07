# Learn_RabbitMQ
学习消息队列

# 消息队列的高可用和高可靠
## 什么是高可用机制？
所谓高可用：是指产品在规定的条件和规定的时刻或者时间内处于可执行规定功能状态的能力

## 什么是高可靠机制？
所谓高可靠是指：是指系统可以无故障的持续运行。

## 模式理解
[https://www.rabbitmq.com/getstarted.html](https://www.rabbitmq.com/getstarted.html)
- topic模式下 :*表示1级目录，#表示0-n级目录

- 代码方式在上方
##  特别讲解Work模式
当有多个消费者时，我们的消息会被哪个消费者消费呢，我们又应该如何均衡消费者消费信息的多少呢?  
主要有两种模式：  
1. 轮询模式的分发：一个消费者一条，按均分配
2. 公平分发：根据消费者的消费能力进行公平分发，处理快的处理的多，处理慢的处理的少，按劳分配

### 轮询分发
- 生产者
```java
package com.mildlamb.rabbitmq.work.lunxun;

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
            for (int i = 0; i < 20; i++) {
                // 5. 准备消息内容
                String message = "mildlamb!!!这是:" + i;

                // 7. 发送消息给队列 queue
                /**
                 * @parames1 交换机
                 * @parames2 发送给哪个队列
                 * @parames3 消息的状态控制
                 * @parames4 消息主体
                 */
                // 可以存在没有交换机的队列吗？不可以，虽然我们交换机参数没有写，但是一定会存在一个默认的交换机
                channel.basicPublish("","queue1",null,message.getBytes());
            }
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

```
- 两个消费者,代码相同，改一下输出
```java
package com.mildlamb.rabbitmq.work.lunxun;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) {
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
            connection = connectionFactory.newConnection("消费者");
            // 3. 通过连接获取通道Channel
            channel = connection.createChannel();

            Channel finalChannel = channel;

//                finalChannel.basicQos(1);

            // 4. 通过通道创建交换机， 声明队列，绑定关系，路由key，发送消息和接收消息
            finalChannel.basicConsume("queue1", true, new DeliverCallback() {
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("Work1 - 收到的消息是" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("接收消息失败了...");
                }
            });

            System.out.println("Work1 - 开始接收消息");
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 8. 关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


```

### 公平分发
