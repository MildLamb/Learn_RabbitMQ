package com.mildlamb.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ_DirectConfiguration {
    // 声明注册fanout模式的交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct_order_exchange",true,false);
    }

    // 声明队列
    @Bean
    public Queue smsQueue_direct(){
        return new Queue("sms_direct.queue",true);
    }
    @Bean
    public Queue msgQueue_direct(){
        return new Queue("msg_direct.queue",true);
    }
    @Bean
    public Queue emailQueue_direct(){
        return new Queue("email_direct.queue",true);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding smsBinding_direct(){
        return BindingBuilder.bind(smsQueue_direct()).to(directExchange()).with("sms");
    }
    @Bean
    public Binding msgBinding_direct(){
        return BindingBuilder.bind(msgQueue_direct()).to(directExchange()).with("msg");
    }
    @Bean
    public Binding emailBinding_direct(){
        return BindingBuilder.bind(emailQueue_direct()).to(directExchange()).with("email");
    }
}
