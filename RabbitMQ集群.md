# 单机多实例搭建
场景：假设有两个rabbitmq节点，分别为rabbit-1和rabbit-2，rabbit-1作为主节点，rabbit-2为从节点。  
启动命令：RABBITMQ_NODE_PROT=5672 RABBITMQ_NODENAME=rabbit-1 rabbitmq-server -detached  
结束命令：rabbitmqctl -n rabbit-1 stop  

- 启动第一个节点rabbit-1
```bash
sudo RABBITMQ_NODE_PROT=5672 RABBITMQ_NODENAME=rabbit-1 rabbitmq-server start &
```
- 启动第二个节点rabbit-2
注意：web管理插件端口占用，所以还要指定其web插件占用的端口号  
RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15673}]"  
```bash
sudo RABBITMQ_NODE_PORT=5673 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15673}]" RABBITMQ_NODENAME=rabbit-2 rabbitmq-server start &
```
- 将rabbit-1作为主节点
```bash
# 停止应用
sudo rabbitmqctl -n rabbit-1 stop_app
# 目的是清除节点上的历史数据(如果不清除，无法将节点加入到集群)
sudo rabbitmqctl -n rabbit-1 reset
# 启动应用
sudo rabbitmqctl -n rabbit-1 start_app
```
- 将rabbit-2加入集群
```bash
# 停止应用
sudo rabbitmqctl -n rabbit-2 stop_app
# 目的是清除节点上的历史数据(如果不清除，无法将节点加入到集群) 
sudo rabbitmqctl -n rabbit-2 reset
# 将rabbit-2节点加入到rabbit-1(主节点)集群当中[Server-node是服务器的主机名]
sudo rabbitmqctl -n rabbit-2 join_cluster rabbit-1@VM-16-14-centos   # @的后面就是服务器的主机名称
# 启动应用
sudo rabbitmqctl -n rabbit-2 start_app
```
- 验证集群状态
```bash
sudo rabbitmqctl cluster_status -n rabbit-1
```

# 使用web监控页面
### rabbitmq监控页面默认是关闭的,先打开监控页面插件
```bash

```
