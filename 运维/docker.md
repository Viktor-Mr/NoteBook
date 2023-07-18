



## 使用Docker

Docker有以下基本用法：

- 管理Docker守护进程。

  ```bash
  systemctl start docker     #运行Docker守护进程
  systemctl stop docker      #停止Docker守护进程
  systemctl restart docker   #重启Docker守护进程
  systemctl enable docker    #设置Docker开机自启动
  systemctl status docker    #查看Docker的运行状态
  ```







- 查看已有镜像。

  ```undefined
  docker images
  ```

- 强制删除镜像。

  ```bash
  docker rmi -f registry.cn-hangzhou.aliyuncs.com/lxepoo/apache-php5
  ```





```
docker volume create rabbitMq
```

```shell
docker run -d --name rabbitmq3.9 -p 5672:5672 -p 15672:15672 -v rabbitMq:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.9-management
```



```
docker run -d --name rabbitmq3.6 -p 5672:5672 -p 15672:15672 -v rabbitMq:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.7.7-management

-d 后台运行容器；

--name 指定容器名；

-p 指定服务运行的端口（5672：应用访问端口；15672：控制台Web端口号）；

-v 映射目录或文件；

--hostname  主机名（RabbitMQ的一个重要注意事项是它根据所谓的 “节点名称” 存储数据，默认为主机名）；

-e 指定环境变量；（RABBITMQ_DEFAULT_VHOST：默认虚拟机名；RABBITMQ_DEFAULT_USER：默认的用户名；RABBITMQ_DEFAULT_PASS：默认用户名的密码）
```

```
docker volume create portainer
```



```
docker run -d -p 8000:8000 -p 9000:9000 --name portainer \
    --restart=always \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v portainer:/data \
    cr.portainer.io/portainer/portainer-ce:2.11.0			

```









https://www.yuque.com/wukong-zorrm/xwas40

