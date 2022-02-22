### 1.简单模式 Simple
### 2.工作模式 Work
- 类型：无
- 特点：分发机制
### 3.发布订阅模式
- 类型：fanout
- 特点：Fanout - 发布与订阅模式，是一种广播机制，他是没有路由key的模式
### 4.路由模式
- 类型：direct
- 特点：有routing-key的匹配模式
### 5，主题Topic模式
- 类型：topic
- 特点：模糊的routing-key的匹配模式

Topic 类型与 Direct 相比，都是可以根据 RoutingKey 把消息路由到不同的队列。只不过 Topic 类型Exchange 可以让队列在绑定 Routing key 的时候使用通配符！
Routingkey 一般都是有一个或多个单词组成，多个单词之间以”.”分割，例如： item.insert
通配符规则：# 匹配0个或多个词，* 匹配不多不少恰好1个词，例如：item.# 能够匹配 item.insert.abc 或者 item.insert，item.* 只能匹配 item.inser

### 6.参数模式
- 类型：headers
- 特点：参数匹配模式
