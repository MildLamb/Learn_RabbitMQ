# Springboot整合RabbitMQ
### 导入依赖
```xml
<!-- rabbitmq starter依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
### yml配置
```yml
# 服务端口
server:
  port: 8080

spring:
  rabbitmq:
    username: rabbitmq用户名
    password: rabbitmq密码
    virtual-host: /
    host: 服务器ip
    port: 5672
```
### 消息生产者
```java
@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description 模拟用户下单
     * @param user_id
     * @param product_id
     * @param num
     */
    public void makeOrder(String user_id,String product_id,int num){
        // 根据商品id查询库存是否充足
        // 保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功:" + orderId);

        // 通过消息队列完成消息的分发
        // 参数一：交换机   参数二：routingKey   参数三：消息本体
        // Work模式  参数一：“”   参数二：队列名   参数三：消息本体
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,"订单:" + orderId);
    }
}
```
### rabbitmq配置类
```java
@Configuration
public class RabbitMQConfiguration {
    // 声明注册fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_order_exchange",true,false);
    }

    // 声明队列
    @Bean
    public Queue smsQueue(){
        return new Queue("sms_fanout.queue",true);
    }
    @Bean
    public Queue msgQueue(){
        return new Queue("msg_fanout.queue",true);
    }
    @Bean
    public Queue emailQueue(){
        return new Queue("email_fanout.queue",true);
    }

    // 完成交换机和队列的绑定关系
    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding msgBinding(){
        return BindingBuilder.bind(msgQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
}
```

### 测试
```java
@SpringBootTest
class SpringbootRabbitmqOrderProducerApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        orderService.makeOrder("1","1",10);
    }

}
```
