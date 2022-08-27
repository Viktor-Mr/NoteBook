

# 1.KafKa





## 1.1 启动

Zookeeper启动 (默认守护进程)

```sh
./zkServer.sh start
```



Zookeeper状态

```sh
./zkServer.sh status
```



Zookeeper停止

```java
./zkServer.sh stop
```

```java
Zookeeper客户端-常⽤命令
    
    连接ZooKeeper服务端 ./zkCli.sh –server ip:port
    断开连接quit    
```





配置环境变量

```sh
 sudo vim /etc/profile.d/my_env.sh
```

```java
#KAFKA_HOME
export KAFKA_HOME=/opt/module/kafka
export PATH=$PATH:$KAFKA_HOME/bin
```

刷新环境变量

```sh
source /etc/profile
```





Kafka 守护方式 (环境变量配置前提下)

```sh
 kafka-server-start.sh -daemon /home/environment/kafka/config/server.properties
```





连接kafka生产者

```

```

