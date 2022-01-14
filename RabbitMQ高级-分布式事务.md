# 简述
分布式事务指事务的操作位于不同的节点上，需要保证事务的ACID特性  
例如一个下单的场景，库存和订单如果在不同的节点上，就涉及到分布式事务  

# 分布式事务的方式
## 两阶段提交(2PC)需要数据库厂商的支持，java组件有atomikos等  
两阶段提交(Two-phase Commit,2PC)，通过引入协调者(Coordinator)来协调参与者的行为，并最终决定这些参与者  
是否要真正执行事务。  

## 补偿事务(TCC) 
TCC其实就是采用的补偿机制，其核心思想是：针对每个操作，都要注册一个与其对应的确认和补偿(撤销)操作，它  
分为三个阶段：  
- Try阶段主要是对业务系统做检测及资源预留
- Confirm阶段主要是对业务系统做确认提交，Try阶段执行成功并开始执行Comfirm阶段时，默认 --- Comfirm阶段   
是不会出错的。即：只要Try成功，Comfirm一定成功  
- Cancel阶段主要是在业务执行错误，需要回滚的状态下的业务取消，预留资源释放

# 遇到的问题
## 1.在完成可靠生产时，clean channel shutdown(消息发送成功，ConfirmCallback ack却返回为false)
```java
// 问题原因（代码提示）
clean channel shutdown; protocol method: #method<channel.close>(reply-code=200, reply-text=OK, class-id=0, method-id=0)
// 原因分析
我们是在测试方法中进行测试，当测试方法结束，rabbitmq相关的资源也就关闭了，虽然我们的消息发送出去，但异步的ConfirmCallback却由于资源关闭而出现了上面的问题
// 解决方案：发送消息的时候添加一个延迟
public void sendMessage(Order order) throws InterruptedException {
    // 通过MQ发送消息
    System.out.println("order的id是:" + order.getOrderId());
    rabbitTemplate.convertAndSend("order_fanout_exchange","", JSON.toJSONString(order),new CorrelationData(order.getOrderId()));
    Thread.sleep(1000);
}
```
