# RabbitMQ的核心组成部分
![image](https://user-images.githubusercontent.com/92672384/148183388-9674addd-57e4-48e6-b7b8-f88824715bd9.png)

# 核心概念
- Server：又称Broker，接收客户端的连接，实现AMQP实体服务。安装rabbitmq-server
- Connection：连接，应用程序与Broker的网络连接TCP/IP/三次握手和四次挥手
- Channel：网络信道，几乎所有的操作都是在Channel中进行，Channel是进行消息读写的通道，每个Channel代表一个会话任务
- Message：消息，服务于应用程序之间传输的数据，由Properties和body组成，Properties是对消息进行修饰，比如消息的优先级  
，延迟等高级特性，Body则是消息体的内容。
- Virtual Host：虚拟地址，用于进行逻辑隔离，最上层的消息路由，一个虚拟主机由若干个Exchange和Queue组成，同一个  
虚拟主机里面不能有相同名字的Exchange
- Exchange：交换机，接收消息，根据路由发送消息到绑定的队列(==不具备存储消息的能力==)
- Bindings：Exchange和Queue之间的虚拟连接，binding中可以保护多个routing key
- Routing key：是一个路由规则，虚拟机可以用它来确定如何路由一个特定消息
- Queue：队列，也称为Message Queue，消息队列，保存消息并将它们转发给消费者
