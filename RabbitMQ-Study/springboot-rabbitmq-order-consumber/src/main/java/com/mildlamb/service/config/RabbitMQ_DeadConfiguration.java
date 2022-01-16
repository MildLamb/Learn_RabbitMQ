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
