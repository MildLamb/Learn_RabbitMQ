package com.mildlamb.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
        args.put("x-dead-letter-exchange","dead.queue");
        // 因为我的死信队列是direct模式，所以还需要设置routing-key
        args.put("x-dead-letter-routing-key","dead");
        return new Queue("ttl_direct.queue",true,false,false,args);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding ttlBinding_direct(){
        return BindingBuilder.bind(ttl_Queue_direct()).to(ttl_directExchange()).with("ttl");
    }
}
