# Docker



​	Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的容器中,然后发布到任何流行的[Linux](https://baike.baidu.com/item/Linux)机器或Windows 机器上,也可以实现虚拟化,容器是完全使用沙箱机制,相互之间不会有任何接口。

一个完整的Docker有以下几个部分组成：

1. Docker  客户端
2. Docker Daemon 守护进程
3. Docker Image 镜像
4. Docker Container 容器



## 基础知识

基础总结为一张图

<font color='red'>关系图</font>

![image-20210824105031342](D:\笔记\Java\Docker.assets\image-20210824105031342.png)





## 容器数据卷

是什么



> docker的理念将运行的环境打包形成容器运行，运行可以伴随容器，但是我们对数据的要求是希望持久化，容器之间可以共享数据，Docker容器产生的数据，如果不通过docker commit生成新的镜像，使得数据作为容器的一部分保存下来，那么当容器被删除之后，数据也就没了，为了能够保存数据，在docker容器中使用卷。卷就是目录或者文件，存在于一个或者多个容器中，但是不属于联合文件系统，因此能够绕过Union File System提供一些用于持久化数据或共享数据的特点



能干嘛？

> 卷的设计目的就是数据的持久化，完全独立与容器的生命周期，因此Docker不会在容器删除时删除其挂载的数据卷。
>  特点：
>
>    1. 数据卷可以在容器之间共享和重用数据。
>    2. 卷的更改可以直接生效。
>    3. 数据卷的更改不会包含在镜像的更新中。
>    4. 数据卷的生命周期一直持续到没有容器使用它为止。
>  容器的持久化
>   容器间继承+共享数据



​	





## 具名和匿名挂载

```shell
# 第一种：匿名挂载
-v 容器内路径!
docker run -d -P --name nginx01 -v /etc/nginx  nginx

# 查看所有的volume(卷)的情况

docker volume ls   

DRIVER              VOLUME NAME 
local               21159a8518abd468728cdbe8594a75b204a10c26be6c36090cde1ee88965f0d0
local               b17f52d38f528893dd5720899f555caf22b31bf50b0680e7c6d5431dbda2802c

     
# 这里发现，这种就是匿名挂载，我们在 -v只写了容器内的路径，没有写容器外的路径！
# 所以匿名挂载的缺点，就是不好维护，通常使用命令 docker volume维护     


#—————————————————————————————————————————————————————————————————————————————————


# 第二种：具名挂载 -P:表示随机映射端口
docker run -d -P --name nginx02 -v nginxconfig:/etc/nginx  nginx

# 查看所有的volume(卷)的情况，可以直接根据卷名，得到对应信息
$ docker volume ls                  
DRIVER              VOLUME NAME
local               nginxconfig 


# 通过 -v 卷名：查看容器内路径
# 查看一下这个卷
$ docker volume inspect nginxconfig
[
    {
        "CreatedAt": "2020-05-23T13:55:34+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/nginxconfig/_data", 
        "Name": "nginxconfig",
        "Options": null,
        "Scope": "local"
    }
]


#—————————————————————————————————————————————————————————————————————————————————

#第三种：直接通过本机目录名字
docker run -d -p 3310:3306  -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root --name mysql01 mysql:5.7

#优点： 在本机任意目录下均可作为 卷 的存放地址
#缺点： 没有一个固定的 卷 存放点， /var/lib/docker/volumes/  外人难以查找

```

**区分三种挂载方式**

```shell
# 三种挂载： 匿名挂载、具名挂载、指定路径挂载
-v 容器内路径			#匿名挂载
-v 卷名：容器内路径		  #具名挂载
-v /宿主机路径：容器内路径 #指定路径挂载 docker volume ls 是查看不到的


# 怎么判断挂载的是卷名而不是本机目录名？ 
# 1、不是/开始就是卷名  #2、 是/开始就是目录名
```



拓展：

```shell
# 通过 -v 容器内路径： ro rw 改变读写权限
ro #readonly 只读
rw #readwrite 可读可写
$ docker run -d -P --name nginx05 -v juming:/etc/nginx:ro nginx
$ docker run -d -P --name nginx05 -v juming:/etc/nginx:rw nginx

# ro 只要看到ro就说明这个路径只能通过宿主机来操作，容器内部是无法操作！
# 指定容器对我们挂载出来的内容的读写权限
```



## 数据卷容器



dockerFile 初解：

1、第一步编写 dockerFile文件

```shell
#docker File文件
FROM centos 
VOLUME ["/dataVolumeContainer01","/dataVolumeContainer02"]
CMD echo "-----end---"
CMD /bin/bash
```

2、build文件

```shell
docker build -f /home/docker-volume/dockerfile1 -t mydfcentos . 
# 最后一个点表示，上下文环境。 既：dockerFile 文件在当前目录下
```



<font color='red'>匿名挂载的脚本：VOLUME ["/volume01"]，</font>

<font color='red'>具名挂载在脚本：VOLUME ["卷名:/volume01"]</font>





**<font color='red'>数据卷容器</font>**

​	命名的容器挂载数据卷，其他容器通过挂载这个（父容器）实现数据共享，挂载数据卷的容器，称为数据卷容器。



**容器间传递共享**

1、先启动一个父容器docker01，然后在dataVolumeContainer2新增文件



![image-20210826160728138](D:\笔记\Java\Docker.assets\image-20210826160728138.png)



退出不停止：ctrl+P+Q

2、创建docker02，docker03 让他们继承docker01  <font color='red'> **--volumes-from** </font>



```shell
[root@kuangshen docker-volume]# docker run -it --name docker02 -- volumes-from docker01  mydfcentos
[root@ea4c82779077 /]# cd /dataVolumeContainer2 

[root@ea4c82779077 dataVolumeContainer2]# ls
docker01.txt 

[root@95164598b306 dataVolumeContainer2]# touch 
docker02.txt 

[root@95164598b306 dataVolumeContainer2]# ls 
docker01.txt docker02.txt 

[root@kuangshen docker-volume]# docker run -it --name docker03 -- volumes-from docker01 kuangshen/centos 

[root@ea4c82779077 /]# cd /dataVolumeContainer2 

[root@ea4c82779077 dataVolumeContainer2]# ls 
docker01.txt   docker02.txt

[root@95164598b306 dataVolumeContainer2]# touch
docker03.txt 

[root@95164598b306 dataVolumeContainer2]# ls 
docker01.txt docker02.txt docker03.txt
```



**容器之间配置信息的传递，数据卷的生命周期一直持续到没有容器使用它为止。**

**存储在本机的文件则会一直保留！**





## DockerFile

微服务打包成镜像，任何装了Docker的地方，都可以下载使用，极其的方便。

流程：开发应用=>DockerFile=>打包为镜像=>上传到仓库（私有仓库，公有仓库）=> 下载镜像 => 启动

运行。

<font color='red'>通过DockerFile 可以打包镜像</font>



### 什么是DockerFile

docker file 是用来构建Docker镜像的构建文件，是由一系列命令和参数构成的脚本。

构建步骤：

1、编写DockerFile文件

2、docker build 构建镜像

3、docker run 运行镜像 

4、docker  push 发布镜像（DockerHub）



### DockerFile构建过程



**基础知识：**

1、每条保留字指令都必须为大写字母且后面要跟随至少一个参数

2、指令按照从上到下，顺序执行

3、# 表示注释

4、每条指令都会创建一个新的镜像层，并对镜像进行提交



**流程：**

1、docker从基础镜像运行一个容器

2、执行一条指令并对容器做出修改

3、执行类似 docker commit 的操作提交一个新的镜像层

4、Docker再基于刚提交的镜像运行一个新容器

5、执行dockerfile中的下一条指令直到所有指令都执行完成！



**说明：**

从应用软件的角度来看，DockerFile，docker镜像与docker容器分别代表软件的三个不同阶段。

DockerFile 是软件的原材料 （代码）

Docker 镜像则是软件的交付品 （.apk）

Docker 容器则是软件的运行状态 （客户下载安装执行）

DockerFile 面向开发，Docker镜像成为交付标准，Docker容器则涉及部署与运维，三者缺一不可！



![image-20210826163422073](D:\笔记\Java\Docker.assets\image-20210826163422073.png)





DockerFile：需要定义一个DockerFile，DockerFile定义了进程需要的一切东西。DockerFile涉及的内容

包括执行代码或者是文件、环境变量、依赖包、运行时环境、动态链接库、操作系统的发行版、服务进

程和内核进程（当引用进行需要和系统服务和内核进程打交道，这时需要考虑如何设计 namespace的权

限控制）等等。



Docker镜像：在DockerFile 定义了一个文件之后，Docker build 时会产生一个Docker镜像，当运行Docker 镜像时，会真正开始提供服务；



Docker容器：容器是直接提供服务的。



### DockerFile指令

**关键字：**



```shell
FROM   # 基础镜像，当前新镜像是基于哪个镜像的

MAINTAINER # 镜像维护者的姓名邮箱地址 

RUN # 容器构建时需要运行的命令 

EXPOSE # 当前容器对外保留出的端口

WORKDIR # 指定在创建容器后，终端默认登录的进来工作目录，一个落脚点 

ENV # 用来在构建镜像过程中设置环境变量 

ADD # 将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包 

COPY # 类似ADD，拷贝文件和目录到镜像中！  add 可以解压添加 tar 包，这是和 copy 的区别

VOLUME # 容器数据卷，用于数据保存和持久化工作 

CMD # 指定一个容器启动时要运行的命令，dockerFile中可以有多个CMD指令，但只有最后一个生效

ENTRYPOINT # 指定一个容器启动时要运行的命令！和CMD一样 

ONBUILD # 当构建一个被继承的DockerFile时运行命令，父镜像在被子镜像继承后，父镜像的 ONBUILD被触发
```



ONBUILD是一个特殊的指令它后面跟的是其它指令，比如 RUN, COPY 等，而这些指令，在当前镜像构建时并不会被执行。只有当以当前镜像为基础镜像，去构建下一级镜像的时候才会被执行





![image-20210826164017896](D:\笔记\Java\Docker.assets\image-20210826164017896.png)





![image-20210826164028179](D:\笔记\Java\Docker.assets\image-20210826164028179.png)





### 自定义镜像



1、准备编写DockerFlie文件

![image-20210826164631879](D:\笔记\Java\Docker.assets\image-20210826164631879.png)



2、**构建 运行**

```shell
docker build -f dockerfile-centos-02 -t mycentos:0.1 .
```

![image-20210826180938338](D:\笔记\Java\Docker.assets\image-20210826180938338.png)

进入容器工作路劲为 /usr/local



3、**镜像历史变更**

`docker history 镜像名`

![image-20210826181321981](D:\笔记\Java\Docker.assets\image-20210826181321981.png)







**CMD 和 ENTRYPOINT 的区别**



**CMD**：Dockerfifile 中可以有多个CMD 指令，但只有最后一个生效，CMD 会被 docker run 之后的参数替换！

**ENTRYPOINT**： docker run 之后的参数会被当做参数传递给 ENTRYPOINT，之后形成新的命令组合。



1、构造dockerfile  <font color='red'>[ ] 执行多条命令， 逗号分割</font>

![image-20210826181934623](D:\笔记\Java\Docker.assets\image-20210826181934623.png)



2、build生成镜像

```shell
[root@localhost docker-volume]# docker build -f dockerfile-centos-03 -t cmd .
Sending build context to Docker daemon   5.12kB
Step 1/3 : FROM centos
 ---> 300e315adb2f
Step 2/3 : CMD echo "-----start-----"
 ---> Running in a307e7126733
Removing intermediate container a307e7126733
 ---> 3027d0077e48
Step 3/3 : CMD ["ls","-a"]
 ---> Running in 53fedca001fd
Removing intermediate container 53fedca001fd
 ---> a4e7d4b8646f
Successfully built a4e7d4b8646f
Successfully tagged cmd:latest



[root@localhost docker-volume]# docker build -f dockerfile-centos-04 -t entrypoint .
Sending build context to Docker daemon   5.12kB
Step 1/3 : FROM centos
 ---> 300e315adb2f
Step 2/3 : ENTRYPOINT echo "===========start====="
 ---> Running in 5a484f367928
Removing intermediate container 5a484f367928
 ---> 314e50dbec7f
Step 3/3 : ENTRYPOINT ["ls","-a"]
 ---> Running in 653c096dc374
Removing intermediate container 653c096dc374
 ---> eac0086b528e
Successfully built eac0086b528e
Successfully tagged entrypoint:latest
```



3、创建容器



cmd 容器

![image-20210826182359911](D:\笔记\Java\Docker.assets\image-20210826182359911.png)



entrypoint 容器

![image-20210826182443180](D:\笔记\Java\Docker.assets\image-20210826182443180.png)



<font color='red'>区别</font>   cmd 不可以追加， 但是entrypoint 可以追加

![image-20210826182643953](D:\笔记\Java\Docker.assets\image-20210826182643953.png)















![image-20210827171538054](D:\笔记\Java\Docker.assets\image-20210827171538054.png)







两个-p开启两个端口映射，一个外网交互一个通信，集群模式

16379是集群内部通讯port，默认是服务端口+10000



```shell
for port in $(seq 1 6); 
do 
mkdir -p /mydata/redis/node-${port}/conf
touch /mydata/redis/node-${port}/conf/redis.conf 
cat << EOF >/mydata/redis/node-${port}/conf/redis.conf 
port 6379
bind 0.0.0.0
cluster-enabled yes
cluster-config-file nodes.conf 
cluster-node-timeout 5000
cluster-announce-ip 172.38.0.1${port}
cluster-announce-port 6379
cluster-announce-bus-port 16379
appendonly yes 
EOF
done
```





```SHELL
docker run -p 6371:6379 -p 16371:16379 --name redis-1  -v /mydata/redis/node-1/data:/data -v /mydata/redis/node-1/conf/redis.conf:/etc/redis/redis.conf  -d --net redis --ip 172.38.0.11 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf


docker run -p 6376:6379 -p 16376:16379 --name redis-6  -v /mydata/redis/node-6/data:/data -v /mydata/redis/node-6/conf/redis.conf:/etc/redis/redis.conf  -d --net redis --ip 172.38.0.16 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
```



```SHELL
# 创建集群 
redis-cli --cluster create 172.38.0.11:6379 172.38.0.12:6379 172.38.0.13:6379 172.38.0.14:6379 172.38.0.15:6379 172.38.0.16:6379 --cluster-replicas 1
```



