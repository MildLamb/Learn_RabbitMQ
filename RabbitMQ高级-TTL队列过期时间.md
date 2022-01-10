# TTL过期时间
过期时间TTL表示可以对消息设置预期的时间，在这个时间内都可以被消费者接收获取，过了时间之后消息将自动被删除。  
RabbitMQ可以对消息和队列设置TTL。有两种方法可以设置。  

# 实践
## 生产者
- TTL配置类
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
        // 使用map集合设置一些队列参数
        // 设置队列过期时间
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",(10*1000));
        return new Queue("ttl_direct.queue",true,false,false,args);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding ttlBinding_direct(){
        return BindingBuilder.bind(ttl_Queue_direct()).to(ttl_directExchange()).with("ttl");
    }
}
```
- TTL消息生产方法
```java
    public void makeOrder_ttl(String user_id,String product_id,int num){

        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        String exchangeName = "ttl_direct_exchange";
        String routeKey = "ttl";
        rabbitTemplate.convertAndSend(exchangeName,routeKey,"订单:" + orderId);
    }
```
