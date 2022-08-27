# 8-19

## 学习

spring Cloud=分布式微服务架构的一站式解决方案，是多种微服务架构落地技术的集合体 ，俗称微服务全家桶





Spring Cloud 是基于 spring Boot 进行开发。



![image-20210819204244063](D:\笔记\Java\StudyNote.assets\image-20210819204244063.png)



[spring Cloud 和 spring boot 版本对应关系](https://spring.io/projects/spring-cloud) 





![image-20210819210618983](D:\笔记\Java\StudyNote.assets\image-20210819210618983.png)





<img src="D:\笔记\Java\StudyNote.assets\image-20210819213434441.png" alt="image-20210819213434441" style="zoom:150%;" />





![image-20210819223057521](D:\笔记\Java\StudyNote.assets\image-20210819223057521.png)

















## 问题

### 1、Dubbo 和 springcloud 区别

关系： 先后出现

![image-20210820095904424](D:\笔记\Java\StudyNote.assets\image-20210820095904424.png)





### 2、多配置文件

[Nacos配置管理及动态刷新](https://blog.csdn.net/yuanyuan_gugu/article/details/108078478)



1. 读取单个文件

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        namespace: **********下面的配置文件必须在此命名空间下
        name: test-one
        file-extension: yaml
```

2. 读取多个(此处只写了一个)

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        namespace: **********下面的配置文件们必须在此命名空间下
        extension-configs:
          - dataId: test-one.yaml
            group: DEFAULT_GROUP
            refresh: true
            
          - dataId: test-two.yaml
            group: DEFAULT_GROUP
            refresh: true
```



https://www.cnblogs.com/Ddlm2wxm/p/14823569.html











# 8-20





## 学习

1、Spring Cloud Gateway

Spring Cloud Gateway作为 Spring Cloud 生态系统中的网关，目标是替代Zuul。

在Spring Cloud 2.0以上版本中，没有对新版本的Zuul 2.0以上最新高性能版本进行集成，仍然还是使用的Zuul1.x 非Reactor模式的老版本。而为了提升网关的性能，Spring Cloud Gateway是基于Web Flux框保实现的，而**Web Flux框架底层则使用了高性能的Reactor模式通信框架Netty。**


Spring Cloud Gateway的目标提供统一的路由方式且基于Filter链的方式提供了网关基本的功能，例如:安全，监控/指标，和限流。



响应式异步非阻塞框架

nio 是 同步非阻塞 ，nio为基础的netty是异步的

netty是基于事件驱动的异步非阻塞参加





2、

URI = Uniform Resource Identifier 统一资源**标志符**


URL = Uniform Resource Locator 统一资源**定位符**
URN = Uniform Resource Name 统一资源**名称**



**大白话，就是**URI是抽象的定义，不管用什么方法表示，只要能定位一个资源，就叫URI，本来设想的的使用两种方法定位：1，URL，用地址定位；2，URN 用名称定位。

举个例子：去村子找个具体的人（URI），如果用地址：某村多少号房子第几间房的主人 就是URL， 如果用身份证号+名字 去找就是URN了。

结果就是 目前WEB上就URL流行开了，平常见得URI 基本都是URL。



3、

nginx一层lb   gateway第二层    ribbon第三层 分别对应不同的消息阶段 不难理解呀

nginx属于接入层  gateway属于网关层 不是一个维度的东西  接入层在网关层之前



4、gateway自带Netty服务器，不需要使用web的tomcat服务器了





### 路由断言工厂

Spring Cloud Gateway包含许多内置的 Route Predicate 工厂。所有这些断言都匹配 HTTP 请求的不同属性。多路由断言工厂通过 and 组合。





![image-20210820153751471](D:\笔记\Java\StudyNote.assets\image-20210820153751471.png)





### 过滤器工厂

路由过滤器允许以某种方式修改传入的 HTTP 请求或传出的 HTTP 响应。



路径过滤器的范围限定为特定路由。Spring Cloud Gateway 包含许多内置的 Gateway Filter 工厂，而且不断新增，目前有 31 个。



根据过滤器工厂的用途来划分，可以分为以下几种：Header、Parameter、Path、Body、Status、Session、Redirect、Retry、Rate Limiter 和 Hystrix。



![image-20210820154001218](D:\笔记\Java\StudyNote.assets\image-20210820154001218.png)





重点掌握 Prefix Path Gateway Filter Factory









# 8-21	



## 学习

1、CAP



CAP原则又称CAP定理，指的是在一个分布式系统中，[一致性](https://baike.baidu.com/item/一致性/9840083)（Consistency）、[可用性](https://baike.baidu.com/item/可用性/109628)（Availability）、[分区容错性](https://baike.baidu.com/item/分区容错性/23734073)（Partition tolerance）。CAP 原则指的是，这三个[要素](https://baike.baidu.com/item/要素/5261200)最多只能同时实现两点，不可能三者兼顾。



[cap 资料](https://baijiahao.baidu.com/s?id=1650890231453975345&wfr=spider&for=pc)



![image-20210822194057783](D:\笔记\Java\StudyNote.assets\image-20210822194057783.png)

![image-20210822194118805](D:\笔记\Java\StudyNote.assets\image-20210822194118805.png)



<font color='red'>重点</font>

AP  --- > 如果不需要存储服务级别的信息且服务实例是通过nacos-client注册，并能够保持心跳上报，那么就可以选择AP模式。当前主流的服务如Spring cloud 和Dubbo服务，都s适用于AP模式，AP模式为了服务的可能性而减弱了一致性，因此AP模式下只支持注册临时实例。



在CP（zookpeer consul ）模式下支持注册持久化实例，此时是以Raft协议集群运行模式。

在该模式下，注册实例之前必须先注册服务，如果服务不存在，则会返回错误。





nacos 切换cp 模式

curl -X PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP'



`linux 命令`  `whereis Nginx`  判断是否存在Nginx







# 8-25





## 1、ESB与SOA的关系



**一、SOA和ESB一直是没有明确概念的两个缩略词** 

原因是这两个词包含的内涵太丰富了，无法用一两句话说清楚，并且，这个词在不同的地方含义也有所不同。

SOA----面向服务架构，实际上强调的是软件的一种架构，一种支撑软件运行的相对稳定的结构，表面含义如此，其实SOA是一种通过服务整合来解决系统集成的一种思想。不是具体的技术，本质上是一种策略、思想。

ESB----企业服务总线，像一根“聪明”的管道，用来连接各个“愚笨”的节点。为了集成不同系统，不同协议的服务，ESB做了消息的转换解释与路由等工作，让不同的服务互联互通。



目前ESB与SOA的确切概念依然没有。但可以明确的说 <font color='red'>SOA就是一种服务集成思想</font>，它的不同实现方式可能差别很大，目前SOA最常见的实现方式是 SCA 和JBI。

 

**二、ESB究竟是什么**

这个问题在个大厂商之间，认识和观点也存在很大差异。

IBM、Oracle等认为`ESB`是连接服务的一种模式，但一些开源组织和其他厂商认为`ESB`是一种产品，并且提供了`ESB`连接解决方案的实现，这种实现可以认为是中间件，也可以认为是组件工具。

 

对此，我个人的观点更偏向前者，`ESB`是一种模式，`ESB`的实现方式也很多，可以称之为`ESB`产品。当然在不同场合ESB的含义也不同，需要鉴别。

 

**三、为什么ESB总和SOA黏在一块**

 

通常，这两个名词总不分家，谈论的话题中“你中有我，我中有你”。

首先，ESB不是SOA。SOA的最常见的实现方式方式是SCA和JBI，而SCA的实现需要ESB，相反JBI则不需要ESB。

其次，因为IBM和Oracle（收购了BEA和SUN的牛X公司）都推崇SCA模式的SOA，因此SCA实际上已经成为SOA的事实标准，说道SOA，最先想到的就是SCA模式了。

 

最后，ESB是SCA架构实现不可缺少的一部分，ESB产品脱离了具体的应用外，没有任何意义。ESB的作用在于实现服务间智能化集成与管理的中介。通过ESB可以访问所集成系统的所有已注册服务。

 

**四、ESB的特点**

ESB是一种在松散耦合的服务和应用之间标准的集成方式。它可以作用于：

面向服务的架构 - 分布式的应用由可重用的服务组成

面向消息的架构 - 应用之间通过ESB发送和接受消息

事件驱动的架构 - 应用之间异步地产生和接收消息

ESB就是在SOA架构中实现服务间智能化集成与管理的中介。





## 2、ribbon和feign的区别

spring cloud的Netflix中提供了两个组件实现软负载均衡调用：ribbon和feign。



**Ribbon**
是一个基于 HTTP 和 TCP 客户端的负载均衡器
它可以在客户端配置 ribbonServerList（服务端列表），然后轮询请求以实现均衡负载。

**

**Feign**
Spring Cloud Netflix 的微服务都是以 HTTP 接口的形式暴露的，所以可以用 Apache 的 HttpClient 或 Spring Cloud的 RestTemplate 或者 Feign Clinet 去调用，而 Feign 是一个使用起来更加方便的 HTTP 客戶端，使用起来就像是调用自身工程的方法，而感觉不到是调用远程方法。



```
注意：spring-cloud-starter-feign 里面已经包含了 spring-cloud-starter-ribbon（Feign 中也使用了 Ribbon）
```

