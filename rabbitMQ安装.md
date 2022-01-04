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
```

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
