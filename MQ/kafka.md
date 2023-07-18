# 面试问题

## 1.生产者原理?

简述： 首先main线程作为消息生产的主线程，经过拦截器（处理消息），再到序列化器（非JDK自带），最后到分区器，分区器维护 Record Accumulator（消息累加器），用于将多个消息合并成一个批次。

 Sender线程是专门用于消息发送的线程，当 Record Accumulator中的 双端队列的batch size 大小达到16k 或者 超出等待时间 就会触发Sender线程，sender线程有一个请求池，请求消息累加器中的消息发送给Kafka Broker 就会得到ack 应答 ，ack应答成功就会生成累加器中的消息批次、失败则会进行重试，默认能接受五个请求没有被应答（避免消息丢失，且异步），没有 拉取消费发送至Kafka集群。



## 2.为什么需要额外实现序列化器

JDK自带的序列化器太重



## 3.数据乱序怎么解决

Kafka单分区内的数据有序，原因是**In Flight Requests**，默认每个broker缓存五个请求，当出现乱序时会自动排序。



## 4.在存储日志的时候，它的索引是按照什么方法存储的？

  日志是按照稀疏索引的方式存储的，每往log文件写入4kb数据，就会往index文件写入一条索引。且保存的是相对的offset，避免占用过多的空间。





## 5.如何高效的读写数据？

- **顺序读写：**磁盘分为顺序读写与随机读写，基于磁盘的随机读写确实很慢，但磁盘的顺序读写性能却很高，kafka 这里采用的就是顺序读写。
- **Page Cache：**为了优化读写性能，Kafka 利用了操作系统本身的 Page Cache。数据直接写入page cache定时刷新脏页到磁盘即可。消费者拉取消息时，如果数据在page cache中，甚至能不需要去读磁盘io。读操作可直接在 Page Cache 内进行。如果**消费和生产速度相当**，甚至不需要通过物理磁盘（直接通过 Page Cache）交换数据。

Broker 收到数据后，写磁盘时只是将数据写入 Page Cache，并不保证数据一定完全写入磁盘。从这一点看，可能会造成机器宕机时，Page Cache 内的数据未写入磁盘从而造成数据丢失。但是这种丢失只发生在机器断电等造成操作系统不工作的场景，而这种场景完全可以由 Kafka 层面的 Replication 机制去解决。如果为了保证这种情况下数据不丢失而强制将 Page Cache 中的数据 Flush 到磁盘，反而会降低性能。也正因如此，Kafka 虽然提供了flush.messages和flush.ms两个参数将 Page Cache 中的数据强制 Flush 到磁盘，但是 Kafka 并不建议使用。

- **零拷贝**：Kafka使用了零拷贝技术，也就是直接将数据从内核空间的读缓冲区直接拷贝到内核空间的 socket 缓冲区，然后再写入到 NIC 缓冲区，避免了在内核空间和用户空间之间穿梭。
- **分区分段+稀疏索引**：Kafka 的 message 是按 topic分 类存储的，topic 中的数据又是按照一个一个的 partition 即分区存储到不同 broker 节点。每个 partition 对应了操作系统上的一个文件夹，partition 实际上又是按照segment分段存储的。通过这种分区分段的设计，Kafka 的 message 消息实际上是分布式存储在一个一个小的 segment 中的，每次文件操作也是直接操作的 segment。为了进一步的查询优化，Kafka 又默认为分段后的数据文件建立了索引文件，就是文件系统上的.index文件。这种分区分段+索引的设计，不仅提升了**数据读取的效率**，同时也**提高了数据操作的并行度**。
- **批量读写**：生产者可以借助累加器，批量发送消息，消费者也可以批量拉取消费。Kafka 数据读写也是批量的而不是单条的,这样可以避免在网络上频繁传输单个消息带来的延迟和带宽开销。假设网络带宽为10MB/S，一次性传输10MB的消息比传输1KB的消息10000万次显然要快得多。
- **数据压缩**：Producer 可将数据压缩后发送给 broker，从而减少网络传输代价，目前支持的压缩算法有：Snappy、Gzip、LZ4。数据压缩一般都是和批处理配套使用来作为优化手段的。

## 6.零拷贝原理是什么？

- **零拷贝：**Kafka使用了零拷贝技术，也就是直接将数据从内核空间的读缓冲区直接拷贝到内核空间的 socket 缓冲区，然后再写入到 NIC 缓冲区，避免了在内核空间和用户空间之间穿梭。

  



## 7.什么是用户态？ 什么是内核态？

用户态（User Mode）：当进程在执行用户自己的代码时，则称其处于用户运行态。

内核态（Kernel Mode）：当一个任务（进程）执行系统调用而陷入内核代码中执行时，我们就称进程处于内核运行态，此时处理器处于特权级最高的内核代码中执行。

![img](http://mk-images.tagao.top/img/b7f9b91102a547159bba5e30f7fd2a17.png?imageslim)

为什么分内核态和用户态？
假设没有内核态和用户态之分，程序就可以随意读写硬件资源了，比如随意读写和分配内存，这样如果程序员一不小心将不适当的内容写到了不该写的地方，很可能就会导致系统崩溃。

而有了用户态和内核态的区分之后，程序在执行某个操作时会进行一系列的验证和检验之后，确认没问题之后才可以正常的操作资源，这样就不会担心一不小心就把系统搞坏的情况了，也就是有了内核态和用户态的区分之后可以让程序更加安全的运行，但同时两种形态的切换会导致一定的性能开销。


## 8.消费者初始化流程是什么样的？

​	通过对GroupId进行Hash得到那台服务器的coordinator ，coordinator负责选出消费组中的Leader ，并且协调信息。真正存储消费记录的是 _consumer_offsets_partition 。

![image-20220903103841994](http://mk-images.tagao.top/img/image-20220903103841994.png?imageslim)



## 9.如何做到精确一次性消费？

开启事务 ，以及幂等性

生产者端 -> 集群

集群 -> 消费者

消费者-> 框架(数据库)





## **10.为什么kafka不做读写分离？**

读写分离是指生产者发送到Leader副本，消费者从Follower副本读取。

1.**延时问题**：数据从leader副本到follow副本是需要过程的，从网络>主节点内存 ->主节点磁盘 -> 网络 -> 从节点内存 -> 从节点磁盘，比较耗时不适合对实时性要求高的应用。

2.**负载均衡**：读写分离，很大一部分原因是怕同一个节点负载过大。但是kafka通过分区的负载均衡，天然的就均衡了各个broker的压力。





## **11.如何保证Kafka消息可靠性？**

- 生产端：ack设置为-1，保证消息同步到follower副本。发送消息方式设置为同步或者异步，做好失败回滚措施
- broker端：页缓存pagecache设置直接刷盘模式，确保不会有消息在页缓存中的时候宕机。
- 消费端：关闭消息自动提交，改为手动提交。避免消息没消费完就提交了offset导致消息丢失

总得来说，要保证严格的可靠性，就会失去很大的可用性，这是一个平衡的过程。





## **11.消息堆积怎么办？**

​	我们都知道，消息的消费速度取决于消费者的速度，在消费速度不变的情况下，增加分组内消费者的个数，能倍速的提高消费速度。而消费者的个数又受限于分区个数，消费者个数超过分区数后，再提高消费者个数就没有意义。

​	为了能够再提高临时的速度，我们还可以设置临时topic在临时主题中，去加大分区数，将所有原消费者直接将消息再次投递到临时topic中，进行更大规模消费群的消费。这是一个**取巧的方案**，适合解决临时大量消息的堆积。



## **12.分区数越多，吞吐量就会越高吗？**

在一定条件下，分区数的数量是和吞吐量成正比的，分区数和性能也是成正比的。但是超过了限度后，不升反降。

从以下四个方面来阐述：

**1.客户端/服务器端需要使用的内存就越多**

- 服务端在很多组件中都维护了分区级别的缓存，比如controller，FetcherManager等，分区数越大，缓存成本也就越大。
- 消费端的消费线程数是和分区数挂钩的，分区数越大消费线程数也就越多，线程的开销成本也就越大
- 生产者发送消息有缓存的概念，会为每个分区缓存消息，当积累到一定程度或者时间时会将消息发送到分区，分区越多，这部分的缓存也就越大

**2.文件句柄的开销**

每个 partition 都会对应磁盘文件系统的一个目录。在 Kafka 的数据日志文件目录中，每个日志数据段都会分配两个文件，一个索引文件和一个数据文件。每个 broker 会为每个日志段文件打开一个 index 文件句柄和一个数据文件句柄。因此，随着 partition 的增多，所需要保持打开状态的文件句柄数也就越多，最终可能超过底层操作系统配置的文件句柄数量限制。

**3.越多的分区可能增加端对端的延迟**

  Kafka 会将分区 HW 之前的消息暴露给消费者。分区越多则副本之间的同步数量就越多，在默认情况下，每个 broker 从其他 broker 节点进行数据副本复制时，该 broker 节点只会为此工作分配一个线程，该线程需要完成该 broker 所有 partition 数据的复制。

**4.降低高可用性**

   Kafka通过副本(replica)机制来保证高可用。具体做法就是为每个分区保存若干个副本(replica_factor指定副本数)。每个副本保存在不同的broker上。其中的一个副本充当leader 副本，负责处理producer和consumer请求。其他副本充当follower角色，由Kafka controller负责保证与leader的同步。如果leader所在的broker挂掉了，contorller会检测到然后在zookeeper的帮助下重选出新的leader——这中间会有短暂的不可用时间窗口，虽然大部分情况下可能只是几毫秒级别。但如果你有10000个分区，10个broker，也就是说平均每个broker上有1000个分区。此时这个broker挂掉了，那么zookeeper和controller需要立即对这1000个分区进行leader选举。比起很少的分区leader选举而言，这必然要花更长的时间，并且通常不是线性累加的。如果这个broker还同时是controller情况就更糟了。





# 第1章.Kafka概述



## 1.1定义



​	Kafka传统定义：Kafka是一个分布式的基于发布/订阅模式的消息队列（MessageQueue），主要应用于大数据实时处理领域。

​	发布/订阅：消息的发布者不会将消息直接发送给特定的订阅者，而是<font color='red'>将发布的消息分为不同的类别</font>，订阅者<font color='red'>只接收感兴趣的消息</font>。

Kafka最新定义：Kafka是一个开源的<font color='red'>分布式事件流平台</font>（Event StreamingPlatform），被数千家公司用于高性能<font color='red'>数据管道、流分析、数据集成</font>和关键任务应用。


![image-20220902124022501](http://mk-images.tagao.top/img/image-20220902124022501.png?imageslim)



## 1.2消息队列





目前企业中比较常见的消息队列产品主要有 Kafka、ActiveMQ 、RabbitMQ 、RocketMQ 等。

在大数据场景主要采用 Kafka 作为消息队列。在 JavaEE 开发中主要采用 ActiveMQ、RabbitMQ、RocketMQ。



### 1.2.1传统消息队列的应用场景

传统的消息队列的主要应用场景包括：**缓存消峰**、**解耦**和**异步通信。**

![image-20220902124743197](http://mk-images.tagao.top/img/image-20220902124743197.png?imageslim)

![image-20220902124806543](http://mk-images.tagao.top/img/image-20220902124806543.png?imageslim)

![image-20220902124830120](http://mk-images.tagao.top/img/image-20220902124830120.png?imageslim)





### 1.2.2 消息队列的两种模式

<font color='red'>消息队列的两种模式</font>

![image-20220902124921601](http://mk-images.tagao.top/img/image-20220902124921601.png?imageslim)

区别: 点对点消费 -> 消息只能发布到一个主题， 消费完成就删除消息，且只有一个消费者

​           发布订阅模式 ->  消息可以发布到多个主题， 消息一般保留七天，且有多个消费者







## 1.3基础架构

![image-20220902125656203](http://mk-images.tagao.top/img/image-20220902125656203.png?imageslim)

在Kafka2.8版本前，Zookeeper的Consumer文件中存放消息被消费的记录（offset）

在Kafka2.8版本走，消息被消费的记录（offset）存放在Kafka中。





（1）Producer：消息生产者，就是向 Kafka broker 发消息的客户端。 

（2）Consumer：消息消费者，向 Kafka broker 取消息的客户端。 

（3）Consumer Group（CG）：消费者组，由多个 consumer 组成。<font color='red'>消费者组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费；消费者组之间互不影响。</font>所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者。

 （4）Broker：一台 Kafka 服务器就是一个 broker。一个集群由多个 broker 组成。一个broker 可以容纳多个 topic。 

（5）Topic：可以理解为一个队列，<font color='red'>生产者和消费者面向的都是一个 topic。</font> 

（6）Partition：为了实现扩展性，一个非常大的 topic 可以分布到多个 broker（即服务器）上，一个 topic 可以分为多个 partition，每个 partition 是一个有序的队列。 

（7）Replica：副本。一个 topic 的每个分区都有若干个副本，<font color='red'>一个 Leader 和若干个Follower</font>。 

（8）Leader：<font color='red'>每个分区多个副本的“主”</font>，**<font color='red'>生产者发送数据的对象，以及消费者消费数据的对象都是 Leader。</font>** 

（9）Follower：<font color='red'>每个分区多个副本中的“从”</font>，实时从 Leader 中同步数据，保持和Leader 数据的同步。Leader 发生故障时，某个 Follower 会成为新的 Leader。



# 第 2 章 Kafka 快速入门

## 2.1 安装部署

### 2.1.1 集群规划

![image-20220902130143941](http://mk-images.tagao.top/img/image-20220902130143941.png?imageslim)

**2.1.2** **集群部署**

0）官方下载地址：http://kafka.apache.org/downloads.html

1）解压安装包

```shell
[atguigu@hadoop102 software]$ tar -zxvf kafka_2.12-3.0.0.tgz -C /opt/module/
```

2)修改解压后的文件名称

```shell
[atguigu@hadoop102 module]$ mv kafka_2.12-3.0.0/ kafka
```

![image-20220902151810746](http://mk-images.tagao.top/img/image-20220902151810746.png?imageslim)

3）进入到/opt/module/kafka 目录，修改配置文件

```shell
[atguigu@hadoop102 kafka]$ cd config/
[atguigu@hadoop102 config]$ vim server.properties
```

输入以下内容：

```shell
#broker 的全局唯一编号，不能重复，只能是数字。
broker.id=0
#处理网络请求的线程数量
num.network.threads=3
#用来处理磁盘 IO 的线程数量
num.io.threads=8
#发送套接字的缓冲区大小
socket.send.buffer.bytes=102400
#接收套接字的缓冲区大小
socket.receive.buffer.bytes=102400
#请求套接字的缓冲区大小
socket.request.max.bytes=104857600
#kafka 运行日志(数据)存放的路径，路径不需要提前创建，kafka 自动帮你创建，可以配置多个磁盘路径，路径与路径之间可以用"，"分隔
log.dirs=/opt/module/kafka/datas
#topic 在当前 broker 上的分区个数
num.partitions=1
#用来恢复和清理 data 下数据的线程数量
num.recovery.threads.per.data.dir=1
# 每个 topic 创建时的副本数，默认时 1 个副本
offsets.topic.replication.factor=1
#segment 文件保留的最长时间，超时将被删除
log.retention.hours=168
#每个 segment 文件的大小，默认最大 1G
log.segment.bytes=1073741824

# 检查过期数据的时间，默认 5 分钟检查一次是否数据过期
log.retention.check.interval.ms=300000
#配置连接 Zookeeper 集群地址（在 zk 根目录下创建/kafka，方便管理）
zookeeper.connect=hadoop102:2181,hadoop103:2181,hadoop104:2181/kafka

```

4）分发安装包

```shell
[atguigu@hadoop102 module]$ xsync kafka/
```

5）分别在 hadoop103 和 hadoop104 上修改配置文件/opt/module/kafka/config/server.properties中的 broker.id=1、broker.id=2
<font color='red'>注：broker.id 不得重复，整个集群中唯一。</font>

```shell
[atguigu@hadoop103 module]$ vim kafka/config/server.properties
```

修改：

```shell
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=1
```



```shell
[atguigu@hadoop104 module]$ vim kafka/config/server.properties
```

修改:

```shell
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=2
```

**6）配置环境变量**
（1）在/etc/profile.d/my_env.sh 文件中增加 kafka 环境变量配置

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



### 2.1.2 启动

<font color='red'>（1） Zookeeper启动 (默认守护进程)</font>

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
./zkCli.sh –server ip:port  //连接ZooKeeper服务端  
quit  			//断开连接    
```



<font color='red'>(2) 启动Kafka </font>

Kafka 守护方式 (环境变量配置前提下)

```sh
 kafka-server-start.sh -daemon /home/environment/kafka/config/server.properties
```

Kafka关闭

```shell
kafka-server-stop.sh 
```



**注意：**<font color='red'>停止 Kafka 集群时，一定要等 Kafka 所有节点进程全部停止后再停止 Zookeeper集群</font>。因为 Zookeeper 集群当中记录着 Kafka 集群相关信息，Zookeeper 集群一旦先停止，Kafka 集群就没有办法再获取停止进程的信息，只能手动杀死 Kafka 进程了。



## 2.2 Kafka命令行操作

**<font color='red'>基础结构</font>**

![image-20220902153447545](http://mk-images.tagao.top/img/image-20220902153447545.png?imageslim)

### **2.2.1** **主题命令行操作**

1）查看操作主题命令参数

```shell
 bin/kafka-topics.sh
```

![image-20220902153652112](http://mk-images.tagao.top/img/image-20220902153652112.png?imageslim)

2）查看当前服务器中的所有 topic

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --list
```

3）创建 first topic

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --create --partitions 1 --replication-factor 3 --topic first
```

选项说明：
`--topic` 定义 topic 名
`--replication-factor `定义副本数
`--partitions` 定义分区数

**4）查看 first 主题的详情**

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --alter --topic first --partitions 3
```

5）修改分区数（注意：分区数只能增加，不能减少）

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --alter --topic first --partitions 3
```

6）再次查看 first 主题的详情

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --describe --topic first
```

7）删除 topic(需要配置信息)

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --delete --topic first
```



### 2.2.2 生产者命令行操作

1）查看操作生产者命令参数

连接kafka生产者

```
kafka-console-producer.sh --bootstrap-server 47.106.86.64:9092 --topic first
```

参数 描述
`--bootstrap-server <String: server toconnect to>` 连接的 Kafka Broker 主机名称和端口号。
`--topic <String: topic> `操作的 topic 名称。



2）发送消息

```shell
[atguigu@hadoop102 kafka]$ bin/kafka-console-producer.sh --
bootstrap-server hadoop102:9092 --topic first
>hello world
>Hi HI
```

### 2.2.3 消费者命令行操作

1）查看操作消费者命令参数

连接kafka消费者

```shell
kafka-console-consumer.sh
```

参数 描述
--bootstrap-server <String: server toconnect to> 连接的 Kafka Broker 主机名称和端口号。
--topic <String: topic> 操作的 topic 名称。
--from-beginning 从头开始消费。
--group <String: consumer group id> 指定消费者组名称。

**2）消费消息**
（1）消费 first 主题中的数据。

```SHELL
kafka-console-consumer.sh --bootstrap-server 47.106.86.64:9092 --topic first
```

（2）把主题中所有的数据都读取出来（包括历史数据）。

```SHELL
kafka-console-consumer.sh --bootstrap-server 47.106.86.64:9092 --from-beginning --topic first
```



# 第 3 章 Kafka 生产者

## 3.1 生产者消息发送流程

### 3.1.1发送原理

​	

​	在消息发送的过程中，涉及到两个线程，**main线程**和**sender线程**，其中main线程是消息的生产线程，而sender线程是jvm单例的线程，专门用于消息的发送。

​	在jvm的内存中开辟了一块缓存空间叫**RecordAccumulator（消息累加器）**，用于将多条消息合并成一个批次，然后由sender线程发送给kafka集群。

​	我们的一条消息在生产过程会调用**send方法**然后经过**拦截器**经过**序列化器**，再经过**分区器**确定消息发送在具体topic下的哪个分区，然后发送到对应的**消息累加器**中，消息累加器是多个双端队列。并且每个队列和主题分区都具有一一映射关系。消息在累加器中，进行合并，达到了对应的size（batch.size）或者等待超过对应的等待时间(linger.ms)，都会触发**sender线程**的发送。sender线程有一个请求池，默认缓存五个请求（ max.in.flight.requests.per.connection  ），发送消息后，会等待服务端的ack，如果没收到ack就会重试默认重试int最大值（ retries ）。如果ack成功就会删除累加器中的消息批次，并相应到生产端。



![image-20220902155220662](http://mk-images.tagao.top/img/image-20220902155220662.png?imageslim)

当双端队列中的DQueue满足 <font color='red'>batch.size  或者 linger.ms</font> 条件时触发sender线程。



![](http://mk-images.tagao.top/img/image-20220902155515664.png?imageslim)



### 3.1.2**生产者重要参数列表**

<img src="http://mk-images.tagao.top/img/image-20220902160217190.png?imageslim" alt="image-20220902160217190" style="zoom:67%;" />



## 3.2发送

### **3.2.1** 普通异步发送

1）需求：创建 Kafka 生产者，采用异步的方式发送到 Kafka Broker

![img](http://mk-images.tagao.top/img/868bd42ba3884541ad578bbdf94b86ac.png?imageslim)



![在这里插入图片描述](http://mk-images.tagao.top/img/f6c1fa242314414e962528a50d39a0d7.png?imageslim)



2）代码编写
（1）创建工程 kafka
（2）导入依赖

```xml
<dependencies>
     <dependency>
         <groupId>org.apache.kafka</groupId>
         <artifactId>kafka-clients</artifactId>
         <version>3.0.0</version>
     </dependency>
</dependencies>
```



（4）编写不带回调函数的 API 代码

```java
public class CustomProducer {

    public static void main(String[] args) {

        // 1. 给 kafka 配置对象添加配置信息：bootstrap.servers
        Properties properties = new Properties();
        //服务信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.106.86.64:9092");
        //配置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 2. 创建 kafka 生产者的配置对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String,String>(properties);

        // 3. 创建 kafka 生产者对象
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord("first", "one" + i));
        }
        kafkaProducer.close();
    }
}
```



测试：在Linux上开启Kafka验证

```shell
kafka-console-consumer.sh --bootstrap-server 47.106.86.64:9092 --topic first
```





### 3.2.2带回调函数的异步发送

​		回调函数会在 producer 收到 ack 时调用，为异步调用，该方法有两个参数，分别是元数据信息（Record Metadata）和异常信息（Exception），如果 Exception 为 null，说明消息发送成功，如果 Exception 不为 null，说明消息发送失败。

![image-20220902161728576](http://mk-images.tagao.top/img/image-20220902161728576.png?imageslim)



<font color='red'>注意：消息发送失败会自动重试，不需要我们在回调函数中手动重试。</font>

![image-20220902160941738](http://mk-images.tagao.top/img/image-20220902160941738.png?imageslim)

```java

public class CustomProducer {

    public static void main(String[] args) {

        // 1. 给 kafka 配置对象添加配置信息：bootstrap.servers
        Properties properties = new Properties();
        //服务信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.106.86.64:9092");
        //配置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 2. 创建 kafka 生产者的配置对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String,String>(properties);

        // 3. 创建 kafka 生产者对象
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord("first", "one" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println( "分区 ： " + recordMetadata.partition() + " 主题： " + recordMetadata.topic() );
                    }
                }
            });
        }
        kafkaProducer.close();
    }
}
```



### 3.2.3同步发送API

1. 先处理已经堆积在DQueue中的数据。
2. RecordAccumulator再处理外部数据。

![image-20220902162557763](http://mk-images.tagao.top/img/image-20220902162557763.png?imageslim)



只需在异步发送的基础上，再调用一下 get()方法即可。

![image-20220902162232873](http://mk-images.tagao.top/img/image-20220902162232873.png?imageslim)

```java
public class CustomProducerSync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. 给 kafka 配置对象添加配置信息：bootstrap.servers
        Properties properties = new Properties();
        //服务信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"47.106.86.64:9092");
        //配置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 2. 创建 kafka 生产者的配置对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String,String>(properties);

        // 3. 创建 kafka 生产者对象
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord("first", "one" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println( "分区 ： " + recordMetadata.partition() + " 主题： " + recordMetadata.topic() );
                    }

                }
            }).get();
            Thread.sleep(100);
        }
        kafkaProducer.close();
    }
}
```



## 3.3生产者拦截器 

**生产者拦截器 （ProducerInterceptor）**

拦截器接口一共有三个方法。三个方法内的实现如果抛出异常，会被ProducerInterceptors内部捕获，并不会抛到上层。



```java
public interface ProducerInterceptor<K, V> extends Configurable {
    ProducerRecord<K, V> onSend(ProducerRecord<K, V> record);
    void onAcknowledgement(RecordMetadata metadata, Exception exception);
    void close();
}
```

`onSend `方法在消息分区之前，可以对消息进行一定的修改，比如给key添加前缀，甚至可以修改我们的topic，如果需要使用kafka实现延时队列高级应用，我们就可以通过拦截器对消息进行判断，并修改，暂时放入我们的延时主题中，等时间达到再放回普通主题队列。

`onAcknowledgement`该方法是在我们服务端对sender线程进行消息确认，或消息发送失败后的一个回调。优先于我们send方法的callback回调。我们可以对发送情况做一个统计。但是该方法在我们的sender线程也就是唯一的IO线程执行，逻辑越少越好。

`close`该方法可以在关闭拦截器时，进行一些资源的释放。



（1） 实现自定义拦截器

```java
public MyInterceptor implements ProducerInterceptor {
    ProducerRecord<K, V> onSend(ProducerRecord<K, V> record);
    void onAcknowledgement(RecordMetadata metadata, Exception exception);
    void close();
}
```

（2）将自定义拦截器加入设置中

````java
properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,MyInterceptor.getClass.getName());
````







## 3.4生产者分区

### 3.4.1分区的好处

从存储的角度  -> 合理使用存储资源，实现负载均衡

从计算的角度 ->  提高并行计算的可行性

![image-20220902162836492](http://mk-images.tagao.top/img/image-20220902162836492.png?imageslim)



### 3.4.2生产者发送消息分区策略

1）默认的分区器 DefaultPartitioner
在 IDEA 中 ctrl +n，全局查找 DefaultPartitioner。

![image-20220902163515987](http://mk-images.tagao.top/img/image-20220902163515987.png?imageslim)

Kafka支持三种分区策略  <font color='red'>1) 指定分区；</font>  <font color='red'>2）指定key，计算hash得分区；</font>   <font color='red'>3）指定随机粘性分区；</font>

![image-20220902163808502](http://mk-images.tagao.top/img/image-20220902163808502.png?imageslim)



### 3.4.3自定义分区器

如果研发人员可以根据企业需求，自己重新实现分区器。

1）需求
 例如我们实现一个分区器实现，发送过来的数据中如果包含 Hi，就发往 0 号分区，不包含 Hi，就发往 1 号分区。
2）实现步骤
（1）定义类实现 Partitioner 接口。
（2）重写 partition()方法。

```java
public class MyPartitioner implements Partitioner {
    /**
     * @param topic 主题
     * @param key 消息的 key
     * @param keyBytes 消息的 key 序列化后的字节数组
     * @param value 消息的 value
     * @param valueBytes 消息的 value 序列化后的字节数组
     * @param cluster 集群元数据可以查看分区信息
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String string = value.toString();
        if (string.contains("vi")){
            return 2;
        }else{
            return 1;
        }
    }
}
```

 （3）使用分区器的方法，在生产者的配置中添加分区器参数。

```java
//自定义分区规则 
properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,MyPartitioner.class.getName());
```

（4）开启测试











## 3.5生产者提高吞吐量

通过提高吞吐量达到<font color='red'>低延迟</font>的效果

![image-20220902164746226](http://mk-images.tagao.top/img/image-20220902164746226.png?imageslim)

**<font color='red'>Batch.size 与 linger.ms  配合使用，根据生成数据的大小指定。</font>**

**<font color='red'>RecordAccumlator：在异步发送并且分区很多的情况下，32M的数据量容易被满足，进程交互加大，可以适当提高到64M。</font>**



```java
 // batch.size：批次大小，默认 16K
properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
// linger.ms：等待时间，默认 0
properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
// RecordAccumulator：缓冲区大小，默认 32M：buffer.memory
properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);

// compression.type：压缩，默认 none，可配置值 gzip、snappy、lz4 和 zstd
properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
```









### 3.5.1消息累加器

**消息累加器（RecordAccumulator）**

![image-20220902174419992](http://mk-images.tagao.top/img/image-20220902174419992.png?imageslim)

​	为了提高生产者的吞吐量，我们通过累加器将多条消息合并成一批统一发送。在broker中将消息批量存入。减少多次的网络IO。

​	消息累加器默认32m，如果生产者的发送速率大于sender发送的速率，消息就会堆满累加器。生产者就会阻塞，或者报错，报错取决于阻塞时间的配置。

​	累加器的存储形式为`ConcurrentMap<TopicPartition, Deque<ProducerBatch>>`，可以看出来就是一个分区对应一个双端队列，队列中存储的是`ProducerBatch`一般大小是16k根据batch.size配置，新的消息会append到`ProducerBatch`中，满16k就会创建新的`ProducerBatch`，并且触发sender线程进行发送。

​	如果消息量非常大，生成了大量的`ProducerBatch`，在发送后，又需要JVM通过GC回收这些`ProducerBatch`就变得非常影响性能，所以kafka通过 `BufferPool`作为内存池来管理`ProducerBatch`的创建和回收，需要申请一个新的`ProducerBatch`空间时，调用 `free.allocate(size, maxTimeToBlock)`找内存池申请空间。

  如果单条消息大于16k，那么就不会复用内存池了，会生成一个更大的`ProducerBatch`专门存放大消息，发送完后GC回收该内存空间。

 	为了进一步减小网络中消息传输的带宽。我们也可以通过**消息压缩**的方式，在生产端将消息追加进`ProducerBatch`就对每一条消息进行压缩了。常用的有Gzip、Snappy、Lz4 和 Zstd，这是时间换空间的手段。压缩的消息会在消费端进行解压。



### 3.5.2**消息发送线程（Sender）**

​	消息保存在内存后，Sender线程就会把符合条件的消息按照批次进行发送。除了发送消息，元数据的加载也是通过Sender线程来处理的。

​	Sender线程发送消息以及接收消息，都是基于java NIO的Selector。通过Selector把消息发出去，并通过Selector接收消息。

​	Sender线程默认容纳5个未确认的消息，消息发送失败后会进行重试。





## 3.6 生产经验—数据可靠性

### 3.6.1消息确认机制-ACK



producer提供了三种消息确认的模式，通过配置`acks`来实现

`acks为0`时， <font color='red'>表示生产者将数据发送出去就不管了</font>，不等待任何返回。这种情况下数据传输效率最高，但是数据可靠性最低，当 server挂掉的时候就会丢数据；

`acks为1`时（默认），<font color='red'>表示数据发送到Kafka后，经过leader成功接收消息的的确认，才算发送成功</font>，如果leader宕机了，就会丢失数据。

`acks为-1/all`时，表示生产者需要等待ISR中的<font color='red'>所有follower都确认接收到数据后才算发送完成</font>，这样数据不会丢失，因此可靠性最高，性能最低。





* <font color='red'>数据完全可靠条件 = ACK级别设置为-1 + 分区副本大于等于2 + ISR里应答的最小副本数量大于等于2</font>

![image-20220902172535966](http://mk-images.tagao.top/img/image-20220902172535966.png?imageslim)

<font color='red'>AR = ISR + ORS </font>

正常情况下，如果所有的follower副本都应该与leader副本保持一定程度的同步，则AR = ISR，OSR = null。

ISR 表示在指定时间内和leader保存数据同步的集合；

ORS表示不能在指定的时间内和leader保持数据同步集合，称为OSR(Out-Sync Relipca set)。



````java
// Ack 设置
properties.put(ProducerConfig.ACKS_CONFIG,"1");

// 重试次数, 默认的重试次数是 Max.Integer
properties.put(ProducerConfig.RETRIES_CONFIG,3);
````



### 3.6.2 数据去重-幂等性

1）幂等性原理

在一般的MQ模型中，常有以下的消息通信概念

- **至少一次（At Least Once）：**<font color='red'> ACK级别设置为-1 + 分区副本大于等于2 + ISR里应答的最小副本数量>=2。</font>可以保证数据不丢失，但是不能保证数据不重复。
- **最多一次（At Most Once）**：ACK级别设置为0 。可以保证数据不重复，但是不能保证数据不丢失。•
- **精确一次（Exactly Once）**：<font color='red'>至少一次 + 幂等性</font> 。 Kafka 0.11版本引入一项重大特性：**幂等性和事务**。

​	幂等性，简单地说就是对接口的多次调用所产生的结果和调用一次是一致的。生产者在进行重试的时候有可能会重复写入消息，而使用Kafka 的幂等性功能之后就可以避免这种情况。（不产生重复数据）



​	**重复数据的判断标准**：具有<font color='red'><PID, Partition, SeqNumber></font>相同主键的消息提交时，Broker只会持久化一条。其 

中ProducerId（pid）是Kafka每次重启都会分配一个新的；Partition 表示分区号；Sequence Number 序列化号，是单调自增的。

​	broker中会在内存维护一个pid+分区对应的序列号。如果收到的序列号正好比内存序列号大一，才存储消息，如果小于内存序列号，意味着消息重复，那么会丢弃消息，并应答。如果远大于内存序列号，意味着消息丢失，会抛出异常。

所以幂等解决的是sender到broker间，由于网络波动可能造成的重发问题。用幂等来标识唯一消息。

<font color='red'>并且幂等性只能保证的是在单分区单会话内不重复</font>。 



2）如何使用幂等性

​	开启幂等性功能的方式很简单，只需要显式地将生产者客户端参数`enable.idempotence`设置为true即可(这个参数的默认值为true)，并且还需要确保生产者客户端的**retries、acks、max.in.filght.request.per.connection**参数不被配置错，默认值就是对的。







### 3.6.3消息事务

![image-20220902183826203](http://mk-images.tagao.top/img/image-20220902183826203.png?imageslim)

由于幂等性不能跨分区运作，为了保证同时发的多条消息，要么全成功，要么全失败。kafka引入了事务的概念。

开启事务需要producer设置`transactional.id`的值并同时开启幂等性。

通过事务协调器，来实现事务，工作流程如下：



```java
// 1 初始化事务
void initTransactions();
// 2 开启事务
void beginTransaction() throws ProducerFencedException;
// 3 在事务内提交已经消费的偏移量（主要用于消费者）
void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets,
 String consumerGroupId) throws 
ProducerFencedException;
// 4 提交事务
void commitTransaction() throws ProducerFencedException;
// 5 放弃事务（类似于回滚事务的操作）
void abortTransaction() throws ProducerFencedException;
```



![image-20220902185538447](http://mk-images.tagao.top/img/image-20220902185538447.png?imageslim)





## 3.7消息顺序

消息在单分区内有序，多分区内无序（如果对多分区进行排序，造成分区无法工作需要等待排序，浪费性能）

![image-20220902184011079](http://mk-images.tagao.top/img/image-20220902184011079.png?imageslim)



kafka只能保证`单分区下的消息顺序性`，为了保证消息的顺序性，需要做到如下几点。

`如果未开启幂等性`，需要 max.in.flight.requests.per.connection  设置为1。（缓冲队列最多放置1个请求）

`如果开启幂等性`，需要 max.in.flight.requests.per.connection  设置为小于5。

这是因为broker端会缓存producer主题分区下的五个request，保证最近5个request是有序的。



![image-20220902184310223](http://mk-images.tagao.top/img/image-20220902184310223.png?imageslim)

如果Request3在失败重试后才发往到集群中，必然会导致乱序，但是集群会重新按照序列号进行排序（最对一次排序5个）。









# 第 4 章 Kafka Broker



## 4.1Broker设计

​	我们都知道kafka能堆积非常大的数据，一台服务器，肯定是放不下的。由此出现的集群的概念，集群不仅可以让消息负载均衡，还能提高消息存取的吞吐量。kafka集群中，会有多台broker，每台broker分别在不同的机器上。

![图片](http://mk-images.tagao.top/img/640?imageslim)



​	为了提高吞吐量，每个topic也会都多个分区，同时为了保持可靠性，每个分区还会有多个副本。这些分区副本被均匀的散落在每个broker上，其中每个分区副本中有一个副本为leader，其他的为follower。

![image-20220902195939625](http://mk-images.tagao.top/img/image-20220902195939625.png?imageslim)



## 4.2Zookeeper

### 4.2.1Zookeeper作用

Zookeeper在Kafka中扮演了重要的角色，kafka使用zookeeper进行元数据管理，保存broker注册信息，包括主题（Topic）、分区（Partition）信息等，选择分区leader。

![image-20220902200249692](http://mk-images.tagao.top/img/image-20220902200249692.png?imageslim)



### 4.2.2Broker选举Leader

​	这里需要先明确一个概念leader选举，因为kafka中涉及多处选举机制，容易搞混，Kafka由三个方面会涉及到选举：

- broker（控制器）选leader
- 分区多副本选leader
- 消费者选Leader

​	<font color='red'>在kafka集群中由很多的broker（也叫做控制器），但是他们之间需要选举出一个leader，其他的都是follower。</font>broker的leader有很重要的作用，诸如：创建、删除主题、增加分区并分配leader分区；集群broker管理，包括新增、关闭和故障处理；分区重分配（auto.leader.rebalance.enable=true，后面会介绍），分区leader选举。

​	每个broker都有唯一的brokerId，他们在启动后会去竞争注册zookeeper上的Controller结点，谁先抢到，谁就是broker leader。而其他broker会监听该结点事件，以便后续leader下线后触发重新选举。

简图：

* broker（控制器）选leader

![image-20220902200901222](http://mk-images.tagao.top/img/image-20220902200901222.png?imageslim)

详细图：  

- broker（控制器）选leader
- 分区多副本选leader

![image-20220902201352868](http://mk-images.tagao.top/img/image-20220902201352868.png?imageslim)



**模拟 Kafka 上下线，Zookeeper 中数据变化**

（1）查看/kafka/brokers/ids 路径上的节点。

```shell
[zk: localhost:2181(CONNECTED) 2] ls /kafka/brokers/ids
[0, 1, 2]
```

（2）查看/kafka/controller 路径上的数据。

```shell
[zk: localhost:2181(CONNECTED) 15] get /kafka/controller
{"version":1,"brokerid":0,"timestamp":"1637292471777"}
```

（3）查看/kafka/brokers/topics/first/partitions/0/state 路径上的数据。

```shell
[zk: localhost:2181(CONNECTED) 16] get  /kafka/brokers/topics/first/partitions/0/state
{"controller_epoch":24,"leader":0,"version":1,"leader_epoch":18," isr":[0,1,2]}
```

（4）停止 hadoop104 上的 kafka。

```shell
kafka-server-stop.sh
```

（5）再次查看/kafka/brokers/ids 路径上的节点。

```shell
[zk: localhost:2181(CONNECTED) 3] ls /kafka/brokers/ids
[0, 1]
```

（6）再次查看/kafka/controller 路径上的数据。

```shell
[zk: localhost:2181(CONNECTED) 15] get /kafka/controller
{"version":1,"brokerid":0,"timestamp":"1637292471777"}
```

（7）再次查看/kafka/brokers/topics/first/partitions/0/state 路径上的数据。

```shell
[zk: localhost:2181(CONNECTED) 16] get  /kafka/brokers/topics/first/partitions/0/state
{"controller_epoch":24,"leader":0,"version":1,"leader_epoch":18," isr":[0,1]}
```

（8）启动 hadoop104 上的 kafka。

```shell
kafka-server-start.sh -daemon ./config/server.properties
```

（9）再次观察（1）、（2）、（3）步骤中的内容。



### 4.2.3Broker重要参数

![image-20220902203152370](http://mk-images.tagao.top/img/image-20220902203152370.png?imageslim)

![image-20220902203207882](http://mk-images.tagao.top/img/image-20220902203207882.png?imageslim)



## 4.3节点服役和退役



### **4.3.1** 服役新节点

(1) 启动一台新的KafKa服务端（加入原有的Zookeeper集群）

(2)  查看原有的 分区信息 describe 

```shell
$ kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --topic first --describe

Topic: first	TopicId: 4DtkHPe4R1KyXNF7QyVqBA	PartitionCount: 3	ReplicationFactor: 3	Configs: segment.bytes=1073741824
	Topic: first	Partition: 0	Leader: 1	Replicas: 2,1,0	Isr: 1,0
	Topic: first	Partition: 1	Leader: 0	Replicas: 0,1,2	Isr: 0,1
	Topic: first	Partition: 2	Leader: 1	Replicas: 1,2,0	Isr: 1,0
```

(3) 指定需要均衡的主题

```shell
$ vim topics-to-move.json
```

```json
{
 "topics": [
 {"topic": "first"}
 ],
 "version": 1
}

```

(4) 生成负载均衡计划(只是生成计划)

```shell
bin/kafka-reassign-partitions.sh --bootstrap-server 47.106.86.64:9092 --topics-to-move-json-file topics-to-move.json --broker-list "0,1,2,3" --generate
```

```shell
Current partition replica assignment
{"version":1,"partitions":[{"topic":"first","partition":0,"replic
as":[0,2,1],"log_dirs":["any","any","any"]},{"topic":"first","par
tition":1,"replicas":[2,1,0],"log_dirs":["any","any","any"]},{"to
pic":"first","partition":2,"replicas":[1,0,2],"log_dirs":["any","
any","any"]}]}
Proposed partition reassignment configuration
{"version":1,"partitions":[{"topic":"first","partition":0,"replic
as":[2,3,0],"log_dirs":["any","any","any"]},{"topic":"first","par
tition":1,"replicas":[3,0,1],"log_dirs":["any","any","any"]},{"to
pic":"first","partition":2,"replicas":[0,1,2],"log_dirs":["any","
any","any"]}]}
```

（3）创建副本存储计划（所有副本存储在 broker0、broker1、broker2、broker3 中）。

```shell
vim increase-replication-factor.json
```

```shell
{"version":1,"partitions":[{"topic":"first","partition":0,"replic
as":[2,3,0],"log_dirs":["any","any","any"]},{"topic":"first","par
tition":1,"replicas":[3,0,1],"log_dirs":["any","any","any"]},{"to
pic":"first","partition":2,"replicas":[0,1,2],"log_dirs":["any","
any","any"]}]}
```

(5) 执行副本计划

```shell
kafka-reassign-partitions.sh --bootstrap-server 47.106.86.64:9092 --reassignment-json-file increase-replication-factor.json --execute
```

(6) 验证计划

```shell
kafka-reassign-partitions.sh --bootstrap-server 47.106.86.64:9092 --reassignment-json-file increase-replication-factor.json --verify
```

```shell
Status of partition reassignment:
Reassignment of partition first-0 is complete.
Reassignment of partition first-1 is complete.
Reassignment of partition first-2 is complete.
Clearing broker-level throttles on brokers 0,1,2,3
Clearing topic-level throttles on topic first
```



### 4.3.2 退役旧节点

**1）执行负载均衡操作**
先按照退役一台节点，生成执行计划，然后按照服役时操作流程执行负载均衡。

不同于服役计划的 `--broker-list "0,1,2"`  退役了 Broker3 ；

```shell
kafka-reassign-partitions.sh --bootstrap-server 47.106.86.64:9092 --topics-to-move-json-file topics-to-move.json --broker-list "0,1,2" --generate
```







## 4.4副本机制

### 4.4.1 副本基本信息

- **Replica** ：副本，同一分区的不同副本保存的是相同的消息，为保证集群中的某个节点发生故障时，该节点上的 partition <font color='red'>**数据不丢失 ，提高副本可靠性**</font>，且 kafka 仍然能够继续工作，kafka 提供了副本机制，一个 topic 的每个分区都有若干个副本，一个 leader 和若干个 follower。
- **Leader** ：每个分区的多个副本中的"主副本"，生产者以及消费者**只与 Leader 交互**。
- **Follower** ：每个分区的多个副本中的"从副本"，负责实时从 Leader 中同步数据，保持和 Leader 数据的同步。Leader 发生故障时，从 Follower 副本中重新选举新的 Leader 副本对外提供服务。

![image-20220902201925254](http://mk-images.tagao.top/img/image-20220902201925254.png?imageslim)

- **AR**:分区中的所有 Replica 统称为 AR  = ISR +OSR 
- **ISR**:所有与 Leader 副本保持一定程度同步的Replica(包括 Leader 副本在内)组成 ISR
- **OSR**:与 Leader 副本同步滞后过多的 Replica 组成了 OSR
- **LEO**:每个副本都有内部的LEO，代表当前队列消息的最后一条偏移量offset + 1。
- **HW**:高水位，代表所有ISR中的LEO最低的那个offset，也是消费者可见的最大消息offset。



### 4.4.2副本选举Leader

​	Kafka 集群中有一个 broker 的 Controller 会被选举为<font color='red'> Controller Leader (4.2.2)</font> ，负责管理集群Broker 的上下线，所有 topic 的<font color='red'>分区副本分配</font>和 <font color='red'>Leader 选举等</font>工作。

​	Broker中Controller 的信息同步工作是依赖于 Zookeeper 的 ./broker/topic 目录下的信息。

![image-20220902205616764](http://mk-images.tagao.top/img/image-20220902205616764.png?imageslim)



**<font color='red'>结论先行： 如果leader副本下线， 会在ISR队列中存活为前提，按照Replicas队列中前面优先的原则。</font>**

↓↓↓

（1）创建一个新的 topic，4 个分区，4 个副本

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --create --topic atguigu1 --partitions 4 --replication-factor 4
```

（2）查看 Leader 分布情况

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --describe --topic atguigu1
```

```shell
Topic: atguigu1 TopicId: awpgX_7WR-OX3Vl6HE8sVg PartitionCount: 4 ReplicationFactor: 4
Configs: segment.bytes=1073741824
Topic: atguigu1 Partition: 0 Leader: 3 Replicas: 3,0,2,1 Isr: 3,0,2,1
Topic: atguigu1 Partition: 1 Leader: 1 Replicas: 1,2,3,0 Isr: 1,2,3,0
Topic: atguigu1 Partition: 2 Leader: 0 Replicas: 0,3,1,2 Isr: 0,3,1,2
Topic: atguigu1 Partition: 3 Leader: 2 Replicas: 2,1,0,3 Isr: 2,1,0,3
```

（3）停止掉 hadoop105 的 kafka 进程，并查看 Leader 分区情况

```shell
kafka-server-stop.sh
```

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --describe --topic atguigu1
```

```shell
Topic: atguigu1 TopicId: awpgX_7WR-OX3Vl6HE8sVg PartitionCount: 4 ReplicationFactor: 4
Configs: segment.bytes=1073741824
Topic: atguigu1 Partition: 0 Leader: 0 Replicas: 3,0,2,1 Isr: 0,2,1
Topic: atguigu1 Partition: 1 Leader: 1 Replicas: 1,2,3,0 Isr: 1,2,0
Topic: atguigu1 Partition: 2 Leader: 0 Replicas: 0,3,1,2 Isr: 0,1,2
Topic: atguigu1 Partition: 3 Leader: 2 Replicas: 2,1,0,3 Isr: 2,1,0
```

（4）停止掉 hadoop104 的 kafka 进程，并查看 Leader 分区情况

```shell
kafka-server-stop.sh
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --describe  --topic atguigu1
```

```shell
Topic: atguigu1 TopicId: awpgX_7WR-OX3Vl6HE8sVg PartitionCount: 4 ReplicationFactor: 4
Configs: segment.bytes=1073741824
Topic: atguigu1 Partition: 0 Leader: 0 Replicas: 3,0,2,1 Isr: 0,1
Topic: atguigu1 Partition: 1 Leader: 1 Replicas: 1,2,3,0 Isr: 1,0
Topic: atguigu1 Partition: 2 Leader: 0 Replicas: 0,3,1,2 Isr: 0,1
Topic: atguigu1 Partition: 3 Leader: 1 Replicas: 2,1,0,3 Isr: 1,0
```





### 4.3.3 副本故障处理

#### **1.follower故障流程**

如果follower落后leader过多，体现在**落后时间** repca.lag.time.max.ms  ，或者**落后偏移量**repca.lag.max.messages(由于kafka生成速度不好界定，后面取消了该参数)，follower就会被移除ISR队列，等待该队列LEO追上HW，才会重新加入ISR中。

![image-20220902210759125](http://mk-images.tagao.top/img/image-20220902210759125.png?imageslim)



#### **2.leader故障流程**

  旧Leader先被从ISR队列中踢出，然后从ISR中选出一个新的Leader来；此时为了保证多个副本之间的数据一致性，其他的follower会先将各自的log文件中**高于HW的部分截取掉**，然后从新的leader同步数据（由此可知这只能保证副本之间数据一致性，并不能保证数据不丢失或者不重复）。体现了设置ACK-all的重要性。



![image-20220902210830344](http://mk-images.tagao.top/img/image-20220902210830344.png?imageslim)



### 4.4.4分区副本分配

如果 kafka 服务器只有 4 个节点，那么设置 kafka 的分区数大于服务器台数，在 kafka底层如何分配存储副本呢？
1）创建 16 分区，3 个副本
（1）创建一个新的 topic，名称为 second。

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --create --partitions 16 --replication-factor 3 --topic second
```

（2）查看分区和副本情况。

```shell
kafka-topics.sh --bootstrap-server 47.106.86.64:9092  --describe --topic second
Topic: second4 Partition: 0 Leader: 0 Replicas: 0,1,2 Isr: 0,1,2
Topic: second4 Partition: 1 Leader: 1 Replicas: 1,2,3 Isr: 1,2,3
Topic: second4 Partition: 2 Leader: 2 Replicas: 2,3,0 Isr: 2,3,0
Topic: second4 Partition: 3 Leader: 3 Replicas: 3,0,1 Isr: 3,0,1
Topic: second4 Partition: 4 Leader: 0 Replicas: 0,2,3 Isr: 0,2,3
Topic: second4 Partition: 5 Leader: 1 Replicas: 1,3,0 Isr: 1,3,0
Topic: second4 Partition: 6 Leader: 2 Replicas: 2,0,1 Isr: 2,0,1
Topic: second4 Partition: 7 Leader: 3 Replicas: 3,1,2 Isr: 3,1,2
Topic: second4 Partition: 8 Leader: 0 Replicas: 0,3,1 Isr: 0,3,1
Topic: second4 Partition: 9 Leader: 1 Replicas: 1,0,2 Isr: 1,0,2
Topic: second4 Partition: 10 Leader: 2 Replicas: 2,1,3 Isr: 2,1,3
Topic: second4 Partition: 11 Leader: 3 Replicas: 3,2,0 Isr: 3,2,0
Topic: second4 Partition: 12 Leader: 0 Replicas: 0,1,2 Isr: 0,1,2
Topic: second4 Partition: 13 Leader: 1 Replicas: 1,2,3 Isr: 1,2,3
Topic: second4 Partition: 14 Leader: 2 Replicas: 2,3,0 Isr: 2,3,0
Topic: second4 Partition: 15 Leader: 3 Replicas: 3,0,1 Isr: 3,0,1

```



![image-20220902211334365](http://mk-images.tagao.top/img/image-20220902211334365.png?imageslim)



### 4.4.5手动调整分区副本

![image-20220902211501656](http://mk-images.tagao.top/img/image-20220902211501656.png?imageslim)

手动调整分区副本存储的步骤如下：
（1）创建一个新的 topic，名称为 three。

```shell
kafka-topics.sh --bootstrap-server  47.106.86.64:9092  --create --partitions 4 --replication-factor 2 --topic three
```

（3）创建副本存储计划（所有副本都指定存储在 broker0、broker1 中）。

```shell
$ vim increase-replication-factor.json
输入如下内容：
{
"version":1,
"partitions":[
{"topic":"three","partition":0,"replicas":[0,1]},
{"topic":"three","partition":1,"replicas":[0,1]},
{"topic":"three","partition":2,"replicas":[1,0]},
{"topic":"three","partition":3,"replicas":[1,0]}]
}
```

（4）执行副本存储计划。

```shell
kafka-reassign-partitions.sh --bootstrap-server  47.106.86.64:9092  --reassignment-json-file increase-replication-factor.json --execute
```

（5）验证副本存储计划。

```shell
kafka-reassign-partitions.sh --bootstrap-server  47.106.86.64:9092  --reassignment-json-file increase-replication-factor.json --verify
```





### 4.4.6**分区自动调整**

​	一般情况下，我们的分区都是平衡散落在broker的，随着一些broker故障，会慢慢出现leader集中在某台broker上的情况，造成集群负载不均衡，这时候就需要分区平衡。

![image-20220902212238315](http://mk-images.tagao.top/img/image-20220902212238315.png?imageslim)



为了解决上述问题kafka出现了自动平衡的机制。kafka提供了下面几个参数进行控制：

- `auto.leader.rebalance.enable`：自动leader parition平衡，默认是true; 
- `leader.imbalance.per.broker.percentage`：每个broker允许的不平衡的leader的比率，默认是10%，如果超过这个值，控制器将会触发leader的平衡
- `leader.imbalance.check.interval.seconds`：检查leader负载是否平衡的时间间隔，默认是300秒
- 但是在生产环境中是不开启这个自动平衡，因为触发leader partition的自动平衡会损耗性能，或者可以将触发自动平和的参数`leader.imbalance.per.broker.percentage`的值调大点。

我们也可以通过修改配置，然后手动触发分区的再平衡。



### 4.3.7增加副本因子

在生产环境当中，由于某个主题的重要等级需要提升，我们考虑增加副本。副本数的增加需要先制定计划，然后根据计划执行。
不能通过命令行的方法添加副本。
1）创建 topic

```shell
bin/kafka-topics.sh --bootstrap-server 47.106.86.64:9092 --create --partitions 3 --replication-factor 1 --topic four
```



2）手动增加副本存储
（1）创建副本存储计划（所有副本都指定存储在 broker0、broker1、broker2 中）。

```shell
vim increase-replication-factor.json
```

```json
{"version":1,"partitions":[
{"topic":"four","partition":0,"replicas":[0,1,2]},
{"topic":"four","partition":1,"replicas":[0,1,2]},
{"topic":"four","partition":2,"replicas":[0,1,2]}]}
```

（2）执行副本存储计划。

```shell
kafka-reassign-partitions.sh --bootstrap-server 47.106.86.64:9092 --reassignment-json-file increase-replication-factor.json --execute
```







## 4.5文件存储

### 4.5.1**存储结构**

  在Kafka中主题（Topic）是一个逻辑上的概念，分区（partition）是物理上的存在的。<font color='red'>每个partition对应一个log文件</font>，该log文件中存储的就是Producer生产的数据。<font color='red'>Producer生产的数据会被不断追加到该log文件末端。</font>为防止log文件过大导致数据定位效率低下，<font color='red'>Kafka采用了分片和索引机制</font>，将每个partition分为多个segment，每个segment默认1G（ `log.segment.bytes` ）， 每个segment包括.**index**文件、**.log**文件和**.timeindex**等文件。这些文件位于文件夹下，该文件命名规则为：topic名称+分区号。

![image-20220902213237928](http://mk-images.tagao.top/img/image-20220902213237928.png?imageslim)

Segment的三个文件需要通过特定工具打开才能看到信息

```shell
kafka-run-class.sh kafka.tools.DumpLogSegments --files ./00000000000000000000.index
 
kafka-run-class.sh kafka.tools.DumpLogSegments --files ./00000000000000000000.log
```



​    当log文件写入4k（这里可以通过`log.index.interval.bytes`设置）数据，就会写入一条索引信息到index文件中，这样的index索引文件就是一个**稀疏索引**，它并不会每条日志都建立索引信息。

​	当Kafka查询一条offset对应实际消息时，可以通过index进行二分查找，获取最近的低位offset，然后从低位offset对应的position开始，从实际的log文件中开始往后查找对应的消息。

![image-20220902213631039](http://mk-images.tagao.top/img/image-20220902213631039.png?imageslim)



`时间戳索引文件`，它的作用是可以查询某一个时间段内的消息，它的数据结构是：时间戳（8byte）+ 相对offset（4byte），如果要使用这个索引文件，先要通过时间范围找到对应的offset，然后再去找对应的index文件找到position信息，最后在遍历log文件，这个过程也是需要用到index索引文件的。

![image-20220902213600127](http://mk-images.tagao.top/img/image-20220902213600127.png?imageslim)



### **4.5.2**文件清理策略

​	Kafka将消息存储在磁盘中，为了控制磁盘占用空间的不断增加就需要对消息做一定的清理操作。Kafka 中每一个分区副本都对应一个Log，而Log又可以分为多个日志分段，这样也便于日志的清理操作。Kafka提供了两种日志清理策略。

1. 日志删除(delete) :按照一定的保留策略直接删除不符合条件的日志分段。
2. 日志压缩(compact) :针对每个消息的key进行整合，对于有相同key的不同value值，只保留最后一个版本。

我们可以通过修改broker端参数 `log.cleanup.policy` 来进行配置

#### 1.日志删除

kafka中默认的日志保存时间为7天，可以通过调整如下参数修改保存时间。

- `log.retention.hours`：最低优先级小时，默认7天
- `log.retention.minutes`：分钟
- `log.retention.ms`：最高优先级毫秒
- `log.retention.check.interval.ms`：负责设置检查周期，默认5分钟
- `file.delete.delay.ms`：延迟执行删除时间
- `log.retention.bytes`：当设置为-1时表示运行保留日志最大值（相当于关闭）；当设置为1G时，表示日志文件最大值

具体的保留日志策略有三种：

**基于时间策略**

​	日志删除任务会周期检查当前日志文件中是否有保留时间超过设定的阈值来寻找可删除的日志段文件集合；这里需要注意log.retention参数的优先级：`log.retention.ms > log.retention.minutes > log.retention.hours`，默认只会配置log.retention.hours参数，值为168即为**7**天。

​	删除过期的日志段文件，并不是简单的根据日志段文件的修改时间计算，而是要根据该<font color='red'>日志段中最大的时间戳来计算的</font>，首先要查询该日志分段所对应的时间戳索引文件，查找该时间戳索引文件的最后一条索引数据，如果时间戳大于0就取值，否则才会使用最近修改时间。

​	在删除的时候先从Log对象所维护的日志段的跳跃表中移除要删除的日志段，用来确保已经没有线程来读取这些日志段；接着将日志段所对应的所有文件，包括索引文件都添加上**.deleted**的后缀；最后交给一个以**delete-file**命名的延迟任务来删除这些以**.deleted**为后缀的文件，默认是1分钟执行一次，可以通过**file.delete.delay.ms**来配置。

**基于日志大小策略**

日志删除任务会周期性检查当前日志大小是否超过设定的阈值（log.retention.bytes，默认是-1，表示无穷大），就从第一个日志分段中寻找可删除的日志段文件集合。如果超过阈值，

**基于日志起始偏移量**

该策略判断依据是日志段的下一个日志段的起始偏移量 baseOffset是否小于等于 logStartOffset，如果是，则可以删除此日志分段。这里说一下logStartOffset，一般情况下，日志文件的起始偏移量 logStartOffset等于第一个日志分段的 baseOffset，但这并不是绝对的，logStartOffset的值可以通过 **DeleteRecordsRequest**请求、使用 **kafka-delete-records.sh 脚本、日志的清理和截断**等操作进行修改。

#### 2.日志压缩

​	日志压缩对于有相同key的不同value值，只保留最后一个版本。如果应用只关心 key对应的最新 value值，则可以开启 Kafka相应的日志清理功能，Kafka会定期将相同 key的消息进行合并，只保留最新的 value值。

* log.cleanup.policy = compact 所有数据启用压缩策略

![image-20220902214504699](http://mk-images.tagao.top/img/image-20220902214504699.png?imageslim)

​	<font color='red'>这种策略只适合特殊场景，比如消息的key是用户ID，value是用户的资料，通过这种压缩策略，整个消息集里就保存了所有用户最新的资料。</font>



## **4.6Kafka高效读数据**

kafka之所以可以快速读写的原因如下：

1. kafka是分布式集群，采用分区方式，并行操作
2. 读取数据采用稀疏索引，可以快速定位消费数据
3. 顺序写磁盘
4. 页缓冲和零拷贝



### 4.6.1顺序写磁盘

Kafka 的 producer 生产数据，要写入到 log 文件中，写的过程是一直追加到文件末端，为顺序写。**官网有数据表明**，同样的磁盘，顺序写能到 600M/s，而随机写只有 100K/s。这与磁盘的机械机构有关，顺序写之所以快，是因为其省去了大量磁头寻址的时间。

![image-20220902214803709](http://mk-images.tagao.top/img/image-20220902214803709.png?imageslim)



### 4.6.2页缓存与零拷贝

kafka高效读写的原因很大一部分取决于**页缓存**和**零拷贝**

#### **1.页缓存**

在 Kafka 中，大量使用了 `PageCache`， 这也是 Kafka 能实现高吞吐的重要因素之一。

​	首先看一下读操作，当一个进程要去读取磁盘上的文件内容时，操作系统会<font color='red'>先查看要读取的数据页是否缓冲在`PageCache `中</font>，如果存在则直接返回要读取的数据，这就减少了对于磁盘 I/O的 操作；但是如果没有查到，操作系统会向磁盘发起读取请求并将读取的数据页存入 `PageCache` 中，之后再将数据返回给进程，就和使用redis缓冲是一个道理。

​	接着写操作和读操作是一样的，如果一个进程需要将数据写入磁盘，操作系统会检查数据页是否在PageCache 中已经存在，如果不存在就在 PageCache中添加相应的数据页，接着将数据写入对应的数据页。另外被修改过后的数据页也就变成了脏页，操作系统会在适当时间将脏页中的数据写入磁盘，以保持数据的一致性。

​	具体的刷盘机制可以通过 `log.flush.interval messages`，`log.flush .interval .ms` 等参数来控制。同步刷盘可以提高消息的可靠性，防止由于机器 掉电等异常造成处于页缓存而没有及时写入磁盘的消息丢失。一般并不建议这么做，刷盘任务就应**交由操作系统去调配**，消息的可靠性应该由多副本机制来保障，而不是由同步刷盘这 种严重影响性能的行为来保障 。

#### **2.零拷贝**

  零拷贝并不是不需要拷贝，而是减少不必要的拷贝次数，通常使用在IO读写过程中。常规应用程序IO过程如下图，会经过四次拷贝：

1. 数据从磁盘经过DMA(直接存储器访问)到内核的Read Buffer；
2. 内核态的Read Buffer到用户态应用层的Buffer
3. 用户态的Buffer到内核态的Socket Buffer
4. Socket Buffer到网卡的NIC Buffer

​	从上面的流程可以知道内核态和用户态之间的拷贝相当于执行两次无用的操作，之间切换也会花费很多资源；当数据从磁盘经过DMA 拷贝到<font color='red'>内核缓存（页缓存）</font>后，为了减少CPU拷贝的性能损耗，操作系统会将该内核缓存与用户层进行共享，减少一次CPU copy过程，同时用户层的读写也会直接访问该共享存储，本身由用户层到Socket缓存的数据拷贝过程也变成了从内核到内核的CPU拷贝过程，更加的快速，这就是零拷贝，IO流程如下图。

甚至如果我们的消息存在页缓存`PageCache`中，还避免了硬盘到内核的拷贝过程，更加一步提升了消息的吞吐量。 <font color='red'>(大概就理解成传输的数据只保存在内核空间，不需要再拷贝到用户态的应用层)</font>



![image-20220902214906599](http://mk-images.tagao.top/img/image-20220902214906599.png?imageslim)

****

Java的JDK NIO中方法transferTo()方法就能够实现零拷贝操作，这个实现依赖于操作系统底层的sendFile()实现的







# 第五章 Kafka消费者



## 5.1**消费模式**

常见的消费模式有两种：

`poll(拉)`：消费者主动向服务端拉取消息。

`push(推)`：服务端主动推送消息给消费者。 

<font color='red'>由于推模式很难考虑到每个客户端不同的消费速率,导致消费者无法消费消息而宕机，因此kafka采用的是poll的模式</font>，该模式有个缺点，如果服务端没有消息，消费端就会<font color='red'>一直空轮询</font>。为了避免过多不必要的空轮询，kafka做了改进，如果没消息服务端就会暂时保持该请求，在一段时间内有消息再回应给客户端。



## 5.2消费工作流程



### 5.2.1消费者总体工作流程

​	消费者对消息进行消费，并且将已经消费的消息加入 _consumer_offsets 中。

![image-20220903110040620](http://mk-images.tagao.top/img/image-20220903110040620.png?imageslim)



### **5.2.2消费者组原理**

Consumer Group（CG）：消费者组，由多个consumer组成。形成一个消费者组的条件，是所有消费者的groupid相同。

- 消费者组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费。
- 消费者组之间互不影响。所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者。

![image-20220903103656937](http://mk-images.tagao.top/img/image-20220903103656937.png?imageslim)

​	对于消息中间件而言，一般有两种消息投递模式：**点对点**(P2P, Point-to-Point)模式和**发布／订阅**(Pub/Sub)模式。点对点模式是基于队列的，消息生产者发送消息到队列，消息消费者从队列中接收消息。发布订阅模式定义了如何向一个内容节点发布和订阅消息，这个内容节点称为主题(Topic) , 主题可以认为是消息传递的中介，消息发布者将消息发布到某个主题， 而消息订阅者从主题中订阅消息。主题使得消息的订阅者和发布者互相保持独立，不需要进行 接触即可保证消息的传递，发布／订阅模式在消息的一对多广播时采用。Kafka同时支待两种消息投递模式，而这正是得益于消费者与消费组模型的契合：

- 如果所有的消费者都隶属于同一个消费组，那么所有的消息都会被均衡地投递给每一个消费者，即每条消息只会被一个消费者处理，这就相当于点对点模式的应用。
- 如果所有的消费者都隶属于不同的消费组，那么所有的消息都会被广播给所有的消费者，即每条消息会被所有的消费者处理，这就相当于发布／订阅模式的应用。



### 5.2.3消费者组选举Leader

具体的消费者组初始化流程：

​	通过对GroupId进行Hash得到那台服务器的coordinator ，coordinator负责选出消费组中的Leader ，并且协调信息。真正存储消费记录的是 _consumer_offsets_partition 。

![image-20220903103841994](http://mk-images.tagao.top/img/image-20220903103841994.png?imageslim)

![image-20220903112720568](http://mk-images.tagao.top/img/image-20220903112720568.png?imageslim)



![image-20220903112925137](http://mk-images.tagao.top/img/image-20220903112925137.png?imageslim)

![image-20220903112936658](http://mk-images.tagao.top/img/image-20220903112936658.png?imageslim)



## 5.3消费者API



消费组单消费者以及消费者组多消费者

![image-20220903113854959](http://mk-images.tagao.top/img/image-20220903113854959.png?imageslim)

<font color='red'>注意：在消费者 API 代码中必须配置消费者组 id。命令行启动消费者不填写消费者组id 会被自动填写随机的消费者组 id。</font>



```java
public class CustomConsumer {
    public static void main(String[] args) {
        //0.配置信息
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.106.86.64:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

        //1.创建消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        ArrayList<String> topic = new ArrayList<>();
        topic.add("first");
        kafkaConsumer.subscribe(topic);


        //2.消费信息
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            records.forEach(record -> {
                System.out.println(record);
            });
        }
        //3.关闭
    }
}
```



## 5.4分区平衡以及再平衡

![image-20220903114711746](http://mk-images.tagao.top/img/image-20220903114711746.png?imageslim)

参数名称 描述
`heartbeat.interval.ms`  Kafka 消费者和 coordinator 之间的心跳时间，默认 3s。该条目的值必须小于 session.timeout.ms，也不应该高于session.timeout.ms 的 1/3。

`session.timeout.ms` Kafka 消费者和 coordinator 之间连接超时时间，默认 45s。超过该值，该消费者被移除，消费者组执行再平衡。

`max.poll.interval.ms ` 消费者处理消息的最大时长，默认是 5 分钟。超过该值，该消费者被移除，消费者组执行再平衡。

`partition.assignment.strategy` 消 费 者 分 区 分 配 策 略 ， 默 认 策 略 是 Range + CooperativeSticky。Kafka 可以同时使用多个分区分配策略。可 以 选 择 的 策 略 包 括 ： Range 、 RoundRobin 、 Sticky 、<font color='red'>CooperativeSticky (协作者粘性)</font>

### **5.4.1分区分配策略**

​	我们知道一个 Consumer Group 中有多个 Consumer，一个 Topic 也有多个 Partition，所以必然会涉及到 Partition 的分配问题: 确定哪个 Partition 由哪个 Consumer 来消费的问题。

Kafka 客户端提供了3 种分区分配策略：**RangeAssignor**、**RoundRobinAssignor** 和 **StickyAssignor**，前两种 分配方案相对简单一些StickyAssignor分配方案相对复杂一些。

### **5.4.2Range**

![image-20220903104029564](http://mk-images.tagao.top/img/image-20220903104029564.png?imageslim)



<font color='red'>Range 分区分配再平衡案例</font>
（1）停止掉 0 号消费者，快速重新发送消息观看结果（<font color='red'>45s 以内</font>，越快越好）。
1 号消费者：消费到 3、4 号分区数据。
2 号消费者：消费到 5、6 号分区数据。
0 号消费者的任务会整体被分配到 1 号消费者或者 2 号消费者。<font color='red'> (被整体分配)</font>
说明：0 号消费者挂掉后，消费者组需要按照超时时间 45s 来判断它是否退出，所以需要等待，时间到了 45s 后，判断它真的退出就会把任务分配给其他 broker 执行。



（2）再次重新发送消息观看结果（<font color='red'>45s 以后</font>）。
1 号消费者：消费到 0、1、2、3 号分区数据。
2 号消费者：消费到 4、5、6 号分区数据。
说明：消费者 0 已经被踢出消费者组，所以重新按照 range 方式分配。





### **5.4.3RoundRobin**

![image-20220903104000261](http://mk-images.tagao.top/img/image-20220903104000261.png?imageslim)

```java
// 修改分区分配策略
properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");
```



（1）停止掉 0 号消费者，快速重新发送消息观看结果（<font color='red'>45s 以内</font>，越快越好）。
1 号消费者：消费到 2、5 号分区数据
2 号消费者：消费到 4、1 号分区数据
0 号消费者的任务会按照 RoundRobin 的方式，把数据轮询分成 0 、6 和 3 号分区数据，分别由 1 号消费者或者 2 号消费者消费。<font color='red'>（采用轮训）</font>
说明：0 号消费者挂掉后，消费者组需要按照超时时间 45s 来判断它是否退出，所以需要等待，时间到了 45s 后，判断它真的退出就会把任务分配给其他 broker 执行。

（2）再次重新发送消息观看结果（<font color='red'>45s 以后</font>）。
1 号消费者：消费到 0、2、4、6 号分区数据
2 号消费者：消费到 1、3、5 号分区数据
说明：消费者 0 已经被踢出消费者组，所以重新按照 RoundRobin 方式分配。

### **5.4.4Sticky：**

**StickyAssignor** 分区分配算法是 Kafka 客户端提供的分配策略中最复杂的一种，可以通过 partition.assignment.strategy 参数去设置，从 0.11 版本开始引入，目的就是在执行新分配时，尽量在上一次分配结果上少做调整，其主要实现了以下2个目标：

1)、**Topic Partition** 的分配要尽量均衡。

2)、当 **Rebalance**(重分配，后面会详细分析) 发生时，尽量与上一次分配结果保持一致。

该算法的精髓在于，重分配后，还能尽量与上一次结果保持一致，进而达到消费者故障下线，故障恢复后的均衡问题，在此就不举例了。



```java
// 修改分区分配策略
properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.StickyAssignor");
```









## **5.5offset位移提交**

### 5.5.1 offset 的默认维护位置

​	<font color='red'>Kafka 0.9 版本之前consumer默认将offset保存在Zookeeper中，从0.9版本之后consumer默认保存在Kafka一个内置的topic中，该topic为_consumer_offsets。</font>

​	消费者提交的offset值维护在**__consumer_offsets**这个Topic中，具体维护在哪个分区中，是由消费者所在的消费者组**groupid**决定，计算方式是：groupid的hashCode值对50取余。当kafka环境正常而消费者不能消费时，有可能是对应的__consumer_offsets分区leader为none或-1，或者分区中的日志文件损坏导致。

​	__consumer_offsets 主题里面采用 key 和 value 的方式存储数据。key 是 group.id+topic+ 分区号，value 就是当前 offset 的值。每隔一段时间，kafka 内部会对这个 topic 进行 compact，也就是每个 group.id+topic+分区号就保留最新数据。

​	一般情况下， 当集群中第一次有消费者消费消息时会自动创建主题_ consumer_ offsets, 不过它的副本因子还受offsets.topic .replication.factor参数的约束，这个参数的默认值为3 (下载安装的包中此值可能为1)，分区数可以通过offsets.topic.num.partitions参数设置，默认为50。



​	在配置文件 config/consumer.properties 中添加配置 exclude.internal.topics=false，默认是 true，表示不能消费系统主题。为了查看该系统主题数据，所以该参数修改为 false。

```shell
kafka-console-consumer.sh --topic __consumer_offsets --bootstrap-server 47.106.86.64:9092 --consumer.config config/consumer.properties --formatter "kafka.coordinator.group.GroupMetadataManager\$OffsetsMessageFormatter" --from-beginning
```

```shell
[offset,atguigu,1]::OffsetAndMetadata(offset=7, 
leaderEpoch=Optional[0], metadata=, commitTimestamp=1622442520203, 
expireTimestamp=None)
[offset,atguigu,0]::OffsetAndMetadata(offset=8, 
leaderEpoch=Optional[0], metadata=, commitTimestamp=1622442520203, 
expireTimestamp=None)
```

消费者提交offset的方式有两种，**自动提交**和**手动提交**

### 5.5.2自动提交

为了使我们能够专注于自己的业务逻辑，Kafka提供了自动提交offset的功能。

![image-20220903122129755](http://mk-images.tagao.top/img/image-20220903122129755.png?imageslim)

- `enable.auto.commit`：是否开启自动提交offset功能，默认是true
- `auto.commit.interval.ms`：自动提交offset的时间间隔，默认是5s

自动提交有可能出现消息消费失败，但是却提交了offset的情况，导致**消息丢失**。为了能够实现消息消费offset的精确控制，更推荐手动提交。

```java
// 自动提交
properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
// 提交时间间隔
properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);
```







### 5.5.3手动提交

虽然自动提交offset十分简单便利，但由于其是基于时间提交的，开发人员难以把握offset提交的时机。因 此Kafka还提供了手动提交offset的API。手动提交offset的方法有两种：分别是commitSync（**同步提交**）和commitAsync（**异步提交**）。两者的相同点是，都会将**本次提交的一批数据最高的偏移量提交**；不同点是，**同步提交阻塞当前线程**，一直到提交成功，并且会自动失败重试（由不可控因素导致，也会出现提交失败）；而**异步提交则没有失败重试机制，故有可能提交失败**。

- commitSync（同步提交）：必须等待offset提交完毕，再去消费下一批数据。 阻塞线程，一直到提交到成功，会进行失败重试
- commitAsync（异步提交） ：发送完提交offset请求后，就开始消费下一批数据了。没有失败重试机制，会提交失败





![image-20220903122747576](http://mk-images.tagao.top/img/image-20220903122747576.png?imageslim)



### **5.5.4指定消费位置**

在kafka中当消费者**查找不到所记录的消费位移**时，会根据auto.offset.reset的配置，决定从何处消费。

`auto.offset.reset = earliest | latest | none` 默认是 latest。

- `earliest`：自动将偏移量重置为最早的偏移量，--from-beginning。
- `latest`（默认值）：自动将偏移量重置为最新偏移量
- `none`：如果未找到消费者组的先前偏移量，则向消费者抛出异常。

![image-20220903123232954](http://mk-images.tagao.top/img/image-20220903123232954.png?imageslim)

​	Kafka中的消费位移是存储在一个内部主题中的， 而我们可以使用**seek()**方法可以突破这一限制：消费位移可以保存在任意的存储介质中， 例如数据库、 文件系统等。以数据库为例， 我们将消费位移保存在其中的一个表中， 在下次消费的时候可以读取存储在数据表中的消费位移并通过seek()方法指向这个具体的位置 。



```java
//配置信息
Properties properties = new Properties();
properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
```





**指定位移消费**

```java
// 指定位置进行消费
Set<TopicPartition> assignment = kafkaConsumer.assignment();

//  保证分区分配方案已经制定完毕
while (assignment.size() == 0){
    kafkaConsumer.poll(Duration.ofSeconds(1));
    assignment = kafkaConsumer.assignment();
}

// 指定消费的offset
for (TopicPartition topicPartition : assignment) {
    kafkaConsumer.seek(topicPartition,600);
}

// 3  消费数据
while (true){

    ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
        System.out.println(consumerRecord);
    }
}
```





**指定时间消费**

​	原理就是查到时间对应的offset再去指定位移消费，为了确保同步到分区信息，我们还需要确保能获取到分区，再去查询分区时间

```java
// 指定位置进行消费
Set<TopicPartition> assignment = kafkaConsumer.assignment();

//  保证分区分配方案已经制定完毕
while (assignment.size() == 0){
    kafkaConsumer.poll(Duration.ofSeconds(1));

    assignment = kafkaConsumer.assignment();
}

// 希望把时间转换为对应的offset
HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();

// 封装对应集合
for (TopicPartition topicPartition : assignment) {
    topicPartitionLongHashMap.put(topicPartition,System.currentTimeMillis() - 1 * 24 * 3600 * 1000);
}

Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = kafkaConsumer.offsetsForTimes(topicPartitionLongHashMap);

// 指定消费的offset
for (TopicPartition topicPartition : assignment) {

    OffsetAndTimestamp offsetAndTimestamp = topicPartitionOffsetAndTimestampMap.get(topicPartition);

    kafkaConsumer.seek(topicPartition,offsetAndTimestamp.offset());
}

// 3  消费数据
while (true){

    ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {

        System.out.println(consumerRecord);
    }
}
```

### 5.5.6 漏消费和重复消费

重复消费：已经消费了数据，但是 offset 没提交。
漏消费：先提交 offset 后消费，有可能会造成数据的漏消费。

![image-20220903123840273](http://mk-images.tagao.top/img/image-20220903123840273.png?imageslim)



## **5.6消费者事务**

![image-20220903123934358](http://mk-images.tagao.top/img/image-20220903123934358.png?imageslim)





## 5.7**数据积压（提高吞吐量）**

![image-20220903124022229](http://mk-images.tagao.top/img/image-20220903124022229.png?imageslim)

参数名称 描述
`fetch.max.bytes` 默认Default: 52428800（50 m）。消费者获取服务器端一批消息最大的字节数。如果服务器端一批次的数据大于该值（50m）仍然可以拉取回来这批数据，因此，这不是一个绝对最大值。一批次的大小受 message.max.bytes （broker config）ormax.message.bytes （topic config）影响。

`max.poll.records` 一次 poll 拉取数据返回消息的最大条数，默认是 500 条

## **5.8拦截器**

与生产者对应，消费者也有拦截器。我们来看看拦截器具体的方法。

```java
public interface ConsumerInterceptor<K, V> extends Configurable, AutoCloseable {

    ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> records);

    void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets);

    void close();
}
```



​	Kafka Consumer会在**poll()**方法返回之前调用拦截器的**onConsume()**方法来对消息进行相应的定制化操作，比如**修改返回的消息内容**、**按照某种规则过滤消息**（可能会减少poll()方法返回 的消息的个数）。如果onConsume()方法中抛出异常， 那么会被捕获并记录到日志中， 但是异常不会再向上传递。

​	Kafka Consumer会在提交完消费位移之后调用拦截器的**onCommit()**方法， 可以使用这个方法来记录跟踪所提交的位移信息，比如当消费者使用commitSync的无参方法时，我们不知道提交的消费位移的具体细节， 而使用拦截器的onCommit()方法却可以做到这 一点。













# 第六章优化



生产者调优

https://zhuanlan.zhihu.com/p/136705624





错误日志

https://stackoverflow.com/questions/56807188/how-to-fix-kafka-common-errors-timeoutexception-expiring-1-records-xxx-ms-has







# 第七章Kafka整合Spring Boot



1. 导包 -除了Spring Boot 之外还需要额外导入 Spring Web、Kafka

![image-20220903182633431](http://mk-images.tagao.top/img/image-20220903182633431.png?imageslim)



2, 编写配置文件

```xm
spring.kafka.bootstrap-servers=47.106.86.64:9092

#序列化器 以及反序列化器
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

spring.kafka.consumer.group-id=test2
```



3、定义简单生产者

```java
@RestController
public class ProducerController {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/hi")
    public String  data(String msg){
        kafkaTemplate.send("first",msg);
        return "ok";
    }
}
```

编写具有回调函数的生产者

```java
@GetMapping("/kafka/callbackOne/{message}")
public void sendMessage2(@PathVariable("message") String callbackMessage) {
    kafkaTemplate.send("first", callbackMessage).addCallback(success -> {
        // 消息发送到的topic
        String topic = success.getRecordMetadata().topic();
        // 消息发送到的分区
        int partition = success.getRecordMetadata().partition();
        // 消息在分区内的offset
        long offset = success.getRecordMetadata().offset();
        System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
    }, failure -> {
        System.out.println("发送消息失败:" + failure.getMessage());
    });
}
```

```java
@GetMapping("/kafka/callbackTwo/{message}")
public void sendMessage3(@PathVariable("message") String callbackMessage) {
    kafkaTemplate.send("first", callbackMessage).addCallback(
        (ListenableFutureCallback<? super SendResult<String, String>>) new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                                   + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });
}
```



4、定义消费者 ->启动监听线程

```JAVA
@Configuration
public class KafkaConfiguration{


    @KafkaListener(topics = {"first"})
    public void message1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("点对点消费1："+record.topic()+"-"+record.partition()+"-"+record.value());
    }

}
```









参考 ：https://blog.csdn.net/prague6695/article/details/123869202

​			https://mp.weixin.qq.com/s/ZG9e6-81cXhDSK4p05gR3A

​			https://blog.csdn.net/weixin_55229531/article/details/125135400
