# rabbitMQ安装
### 1.下载安装包
- erlang下载地址:[https://www.erlang-solutions.com/downloads/](https://www.erlang-solutions.com/downloads/)
- rabbitMQ下载地址:[https://www.rabbitmq.com/download.html](https://www.rabbitmq.com/download.html)

### 2.将下载的安装包上传给服务器解压
```bash
# 解压erlang安装包
[root@VM-16-14-centos rabbitmq]# rpm -Uvh esl-erlang_23.2-1_centos_7_amd64.rpm --force --nodeps
# 安装erlang
[root@VM-16-14-centos rabbitmq]# yum install -y erlang
# 安装内容省略

# 检查是否安装成功
[root@VM-16-14-centos rabbitmq]# erl -v
Erlang/OTP 23 [erts-11.1.4] [source] [64-bit] [smp:2:2] [ds:2:2:10] [async-threads:1] [hipe]

Eshell V11.1.4  (abort with ^G)

# 安装rabbitmq安装所需插件 socat
[root@VM-16-14-centos rabbitmq]# yum install -y socat

# 解压rabbitmq安装包
[root@VM-16-14-centos rabbitmq]# rpm -Uvh rabbitmq-server-3.9.8-1.el7.noarch.rpm
# 安装rabbitmq
[root@VM-16-14-centos rabbitmq]# yum install rabbitmq -y

# 启动rabbitmq服务
[root@VM-16-14-centos rabbitmq]# systemctl start rabbitmq-server
# 查看rabbitmq状态
[root@VM-16-14-centos rabbitmq]# systemctl status rabbitmq-server
● rabbitmq-server.service - RabbitMQ broker
   Loaded: loaded (/usr/lib/systemd/system/rabbitmq-server.service; disabled; vendor preset: disabled)
   Active: active (running) since Tue 2022-01-04 12:17:35 CST; 1min 48s ago
 Main PID: 30537 (beam.smp)
    Tasks: 24
   Memory: 62.1M
   CGroup: /system.slice/rabbitmq-server.service
           ├─30537 /usr/lib/erlang/erts-11.1.4/bin/beam.smp -W w -MBas ageffcbf -MHas ageffcbf -MBlmbcs 512 -MHlmbcs 512 -MMmcs 30 -P 1048576 -t 5000000 -stbt db -zdbbl 128000 ...
           ├─30552 erl_child_setup 32768
           ├─30579 /usr/lib/erlang/erts-11.1.4/bin/epmd -daemon
           ├─30602 inet_gethost 4
           └─30603 inet_gethost 4

Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Doc guides:  https://rabbitmq.com/documentation.html
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Support:     https://rabbitmq.com/contact.html
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Tutorials:   https://rabbitmq.com/getstarted.html
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Monitoring:  https://rabbitmq.com/monitoring.html
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Logs: /var/log/rabbitmq/rabbit@VM-16-14-centos.log
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: /var/log/rabbitmq/rabbit@VM-16-14-centos_upgrade.log
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: <stdout>
Jan 04 12:17:32 VM-16-14-centos rabbitmq-server[30537]: Config file(s): (none)
Jan 04 12:17:35 VM-16-14-centos rabbitmq-server[30537]: Starting broker... completed with 0 plugins.
Jan 04 12:17:35 VM-16-14-centos systemd[1]: Started RabbitMQ broker.

# 设置rabbitmq开机自启动，看情况设置
[root@VM-16-14-centos rabbitmq]# systemctl enable rabbitmq-server

# 停止服务
[root@VM-16-14-centos rabbitmq]# systemctl stop rabbitmq-server
```

### 安装rabbitMQ图形界面
```bash
[root@VM-16-14-centos rabbitmq]# rabbitmq-plugins enable rabbitmq_management

# 需要开启云服务器防火墙和linux防火墙的端口 15672，默认账号密码 guest,默认账号密码 仅限于localhost
[root@VM-16-14-centos rabbitmq]# firewall-cmd --zone=public --add-port=15672/tcp --permanent
success
[root@VM-16-14-centos rabbitmq]# systemctl restart firewalld.service

# 授权账号密码
# 新增用户
[root@VM-16-14-centos rabbitmq]# rabbitmqctl add_user 用户名 密码
# 设置用户权限
# administrator：可以登录控制台，查看所有信息，可以对rabbitmq进行管理
# monitoring：监控者。可以登录控制台，查看所有信息
# policymaker：策略制定者。登录控制台，指定策略
# management：普通管理员。登录控制台

# 设置权限
[root@VM-16-14-centos rabbitmq]# rabbitmqctl set_user_tags 用户名 administrator
Setting tags for user "mildlamb" to [administrator] ...
```
![image](https://user-images.githubusercontent.com/92672384/148029192-73dd347f-adc0-45eb-9913-0455c1efc144.png)



- 问题1：rpm 安装erlang时提示rpm: Header V4 DSA/SHA1 Signature, key ID 442df0f8: NOKEY  
解决办法：在rpm 语句后面加上 --force --nodeps就可以了。
```bash
[root@VM-16-14-centos rabbitmq]# rpm -Uvh esl-erlang_23.2-1_centos_7_amd64.rpm --force --nodeps
```


### 了解卸载
```bash
# 卸载erlang
yum list | grep erlang
yum -y remove erlang-*
rm -rf /usr/lib64/erlang

# 卸载RabbitMQ
yum list | grep rabbitmq
yum -y remove rabbitmq-server.noarch
```
