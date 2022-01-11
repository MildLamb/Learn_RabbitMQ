# 概述
DLX,全称为Dead-Letter-Exchange，也可以称为死信交换机。当消息在一个队列中变成死信(dead message)之后，它能被重新发送到另一个交换机中，  
这个交换机就是DLX，绑定DLX的队列就称之为死信队列。  
消息变成死信，可能有一下几种原因:  
1. 消息被拒绝
2. 消息过期
3. 队列达到最大长度

DLX也是一个正常的交换机，和一般的交换机没有区别，它能在任何的队列上被指定，实际上就是设置某一个队列的属性。当这个队列中存在死信时，RabbitMQ  
就会自动地将这个消息重新发布到设置的DLX上去，进而被路由到另一个队列，即死信队列。   
要使用死信队列，只需要在定义队列的时候设置队列参数，x-dead-letter-exchange，指定交换机即可。  

# 测试
- 死信配置类
```java
@Configuration
public class RabbitMQ_DeadConfiguration {
    // 声明注册fanout模式的交换机
    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange("dead_direct_exchange",true,false);
    }

    // 声明队列
    @Bean
    public Queue deadQueue(){
        return new Queue("dead.queue",true);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding deadBinds(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }
}
```
- 过期队列绑定死信队列
```java
@Configuration
public class RabbitMQ_TTLConfiguration {
    // 声明注册fanout模式的交换机
    @Bean
    public DirectExchange ttl_directExchange(){
        return new DirectExchange("ttl_direct_exchange",true,false);
    }

    // 声明队列
    // 设置队列的过期时间
    @Bean
    public Queue ttl_Queue_direct(){
        // 设置队列过期时间
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",(10*1000));
        args.put("x-dead-letter-exchange","dead_direct_exchange");
        // 因为我的死信队列是direct模式，所以还需要设置routing-key
        args.put("x-dead-letter-routing-key","dead");
        return new Queue("ttl_direct_dead.queue",true,false,false,args);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding ttlBinding_direct(){
        return BindingBuilder.bind(ttl_Queue_direct()).to(ttl_directExchange()).with("ttl");
    }
}
```
- 注意，给已经创建好的队列添加新的参数，不会覆盖信的队列，而是直接创建失败，因此，我们需要先删除原来的队列
