

# Redis



## 应用场景

Redis应用场景


* 1.利用redis 中字符串类型<font color='red'>**完成项目中手机验证码存储的实现**</font>
* 2.利用redis中字符串类型<font color='red'>**完成具有失效性业务功能 **</font> 12306\淘宝  订单失效时间40分钟
* 3.利用redis<font color='red'>**分布式集群系统中Session共享 **</font> 
  * <font color='blue'>**mencagche 内存数据存储上限 数据类型比较简单**</font>
  * <font color='blue'>**Redis 内存数据上限数据类型丰富**</font>
* 4.利用redis zset类型可排序set类型 (<font color='red'>**元素分数～排行榜之类功能**</font>)  销量排行统计 (zset)[商品id,商品销量」
* 5.利用redis <font color='red'>**分布式缓存实现**</font>

* 6.利用redis存储认证之后token信息微信小程序微信公众号「用户openid --->令牌(token) redis超时

* 7.利用redis<font color='red'>**解决分布式集群系统中分布式锁问题**</font> (jvm的synchronize，只能锁住当前jvm)

## 1.  NoSQL的引言

**NoSQL**(` Not Only SQL` )，意即**不仅仅是SQL**, 泛指非关系型的数据库。Nosql这个技术门类,早期就有人提出,发展至2009年趋势越发高涨。

## 2. 为什么是NoSQL

​	随着互联网网站的兴起，传统的关系数据库在应付动态网站，特别是超大规模和高并发的纯动态网站已经显得力不从心，暴露了很多难以克服的问题。如`商城网站中对商品数据频繁查询`、`对热搜商品的排行统计`、`订单超时问题`、以及微信朋友圈（音频，视频）存储等相关使用传统的关系型数据库实现就显得非常复杂，虽然能实现相应功能但是在性能上却不是那么乐观。nosql这个技术门类的出现，更好的解决了这些问题，它告诉了世界不仅仅是sql。

## 3. NoSQL的四大分类	

### 3.1 键值(Key-Value)存储数据库

```markdown
# 1.说明: 
- 这一类数据库主要会使用到一个哈希表，这个表中有一个特定的键和一个指针指向特定的数据。

# 2.特点
- Key/value模型对于IT系统来说的优势在于简单、易部署。  
- 但是如果DBA只对部分值进行查询或更新的时候，Key/value就显得效率低下了。
— 由于是内存数据库，所以单台机器存储的数据量跟机器本身的内存大小有关

# 3.相关产品
- Tokyo Cabinet/Tyrant,
- Redis  存放在内存
- SSDB	 存放在硬盘
- Voldemort 
- Oracle BDB
```

### 3.2 列存储数据库

```markdown
# 1.说明
- 这部分数据库通常是用来应对分布式存储的海量数据。

# 2.特点
- 键仍然存在，但是它们的特点是指向了多个列。这些列是由列家族来安排的。

# 3.相关产品
- Cassandra、HBase、Riak.
```

### 3.3 文档型数据库

```markdown
# 1.说明
- 文档型数据库的灵感是来自于Lotus Notes办公软件的，而且它同第一种键值存储相类似该类型的数据模型是版本化的文档，半结构化的文档以特定的格式存储，比如JSON。文档型数据库可 以看作是键值数据库的升级版，允许之间嵌套键值。而且文档型数据库比键值数据库的查询效率更高

# 2.特点
- 以文档形式存储

# 3.相关产品
- MongoDB、CouchDB、 MongoDb(4.x). 国内也有文档型数据库SequoiaDB，已经开源。
```

### 3.4 图形(Graph)数据库

 ```markdown
# 1.说明
- 图形结构的数据库同其他行列以及刚性结构的SQL数据库不同，它是使用灵活的图形模型，并且能够扩展到多个服务器上。
- NoSQL数据库没有标准的查询语言(SQL)，因此进行数据库查询需要制定数据模型。许多NoSQL数据库都有REST式的数据接口或者查询API。

# 2.特点

# 3.相关产品
- Neo4J、InfoGrid、 Infinite Graph、
 ```

----

## 4. NoSQL应用场景

- 数据模型比较简单

- 需要灵活性更强的IT系统（系统设计灵活，性能要求高）

- 对数据库性能要求较高

- 不需要高度的数据一致性 (NoSQL产品对于事务支持不是很好)

## 5. 什么是Redis

![](http://mk-images.tagao.top/img/202204202017016.png?imageslim)



> Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker.

Redis 开源  遵循BSD  基于内存数据存储 被用于作为 数据库 缓存  消息中间件

- 总结: redis是一个内存型的数据库

## 6. Redis特点

-  Redis是一个高性能key/value内存型数据库
-  Redis支持丰富的数据类型 
-  Redis支持持久化 
- Redis单线程,单进程





<font color='red'>**Redis 5.0.5 是单线程**</font>   并发时，<font color='red'>**串行执行 + 异步处理**</font> 解决并发的问题，效率不比<font color='red'>**线程锁**</font>低。 

<font color='red'>**memechache  是多线程**</font>   key - value 多线程，通过 <font color='red'>**线程锁**</font> 解决并发的问题。



 <font color='red'>**Redis 6.0.5 是多线程**</font>   IO操作是多线程，数据处理是单线程。



<font color='red'>**分布式锁**</font>

Synchronized

jvm 01 -> synchronized 01 

jvm 02 -> synchronized 02 

分布式部署的不同服务器上的不同jvm只能锁住当前jvm的对象，不能跨对象进行加锁。

---



## 7. Redis安装

```markdown
# 0.准备环境
- vmware15.x+
- centos7.x+

# 1.下载redis源码包
- https://redis.io/
```



![](http://mk-images.tagao.top/img/202204222250767.png?imageslim)

```markdown
# 2.下载完整源码包
- redis-4.0.10.tar.gz
```

![](http://mk-images.tagao.top/img/202204222250427.png?imageslim)

```markdown
# 3.将下载redis资料包上传到Linux中
```

![](http://mk-images.tagao.top/img/202204202018809.png?imageslim)

```markdown
# 4.解压缩文件
[root@localhost ~]# tar -zxvf redis-4.0.10.tar.gz
[root@localhost ~]# ll
```

![](http://mk-images.tagao.top/img/202204202018646.png?imageslim)

```markdown
# 5.安装gcc  
- yum install -y gcc

# 6.进入解压缩目录执行如下命令
- make MALLOC=libc

# 7.编译完成后执行如下命令
- make install PREFIX=/usr/redis

# 8.进入/usr/redis目录启动redis服务 
- ./redis-server

读取配置文件  后台运行&
```

![image-20230108144824671](http://mk-images.tagao.top/img/image-20230108144824671.png?imageslim)

![](http://mk-images.tagao.top/img/202204202018866.png?imageslim)

```markdown
# 9.Redis服务端口默认是 6379

# 10.进入bin目录执行客户端连接操作
- ./redis-cli –p 6379
```

![](http://mk-images.tagao.top/img/202204202018192.png?imageslim)

```markdown
# 11.连接成功出现上面界面连接成功
```

![](http://mk-images.tagao.top/img/202204202020301.png?imageslim)

## 8. Redis数据库相关指令



```shell
- help @set 
- help @string
```





### 8.1 数据库操作指令

```markdown
# 1.Redis中库说明
- 使用redis的默认配置器动redis服务后,默认会存在16个库,编号从0-15
- 可以使用select 库的编号 来选择一个redis的库

# 2.Redis中操作库的指令
- 清空当前的库  FLUSHDB
- 清空全部的库  FLUSHALL

# 3.redis客户端显示中文
-	./redis-cli  -p 7000 --raw 
```

### 8.2 操作key相关指令

```markdown
# 1.DEL指令
- 语法 :  DEL key [key ...] 
- 作用 :  删除给定的一个或多个key 。不存在的key 会被忽略。
- 可用版本： >= 1.0.0
- 返回值： 被删除key 的数量。 

# 2.EXISTS指令
- 语法:  EXISTS key
- 作用:  检查给定key 是否存在。
- 可用版本： >= 1.0.0
- 返回值： 若key 存在，返回1 ，否则返回0。

# 3.EXPIRE
- 语法:  EXPIRE key seconds
- 作用:  为给定key 设置生存时间，当key 过期时(生存时间为0 )，它会被自动删除。
- 可用版本： >= 1.0.0
- 时间复杂度： O(1)
- 返回值：设置成功返回1 。

# 4.KEYS
- 语法 :  KEYS pattern
- 作用 :  查找所有符合给定模式pattern 的key 。
- 语法:
	KEYS * 匹配数据库中所有key 。
	KEYS h?llo 匹配hello ，hallo 和hxllo 等。
	KEYS h*llo 匹配hllo 和heeeeello 等。
	KEYS h[ae]llo 匹配hello 和hallo ，但不匹配hillo 。特殊符号用 "\" 隔开
- 可用版本： >= 1.0.0
- 返回值： 符合给定模式的key 列表。

# 5.MOVE
- 语法 :  MOVE key db
- 作用 :  将当前数据库的key 移动到给定的数据库db 当中。
- 可用版本： >= 1.0.0
- 返回值： 移动成功返回1 ，失败则返回0 。

# 6.PEXPIRE
- 语法 :  PEXPIRE key milliseconds
- 作用 :  这个命令和EXPIRE 命令的作用类似，但是它以毫秒为单位设置key 的生存时间，而不像EXPIRE 命令那样，以秒为单位。
- 可用版本： >= 2.6.0
- 时间复杂度： O(1)
- 返回值：设置成功，返回1  key 不存在或设置失败，返回0

# 7.PEXPIREAT
- 语法 :  PEXPIREAT key milliseconds-timestamp
- 作用 :  这个命令和EXPIREAT 命令类似，但它以毫秒为单位设置key 的过期unix 时间戳，而不是像EXPIREAT那样，以秒为单位。
- 可用版本： >= 2.6.0
- 返回值：如果生存时间设置成功，返回1 。当key 不存在或没办法设置生存时间时，返回0 。(查看EXPIRE 命令获取更多信息)

# 8.TTL
- 语法 :   TTL key
- 作用 :   以秒为单位，返回给定key 的剩余生存时间(TTL, time to live)。
- 可用版本： >= 1.0.0
- 返回值：
	当key 不存在时，返回-2 。
	当key 存在但没有设置剩余生存时间时，返回-1 。
	否则，以秒为单位，返回key 的剩余生存时间。
- Note : 在Redis 2.8 以前，当key 不存在，或者key 没有设置剩余生存时间时，命令都返回-1 。

# 9.PTTL
- 语法 :  PTTL key
- 作用 :  这个命令类似于TTL 命令，但它以毫秒为单位返回key 的剩余生存时间，而不是像TTL 命令那样，以秒为单位。
- 可用版本： >= 2.6.0
- 返回值： 当key 不存在时，返回-2 。当key 存在但没有设置剩余生存时间时，返回-1 。
- 否则，以毫秒为单位，返回key 的剩余生存时间。
- 注意 : 在Redis 2.8 以前，当key 不存在，或者key 没有设置剩余生存时间时，命令都返回-1 。

# 10.RANDOMKEY
- 语法 :  RANDOMKEY
- 作用 :  从当前数据库中随机返回(不删除) 一个key 。
- 可用版本： >= 1.0.0
- 返回值：当数据库不为空时，返回一个key 。当数据库为空时，返回nil 。

# 11.RENAME
- 语法 :  RENAME key newkey
- 作用 :  将key 改名为newkey 。当key 和newkey 相同，或者key 不存在时，返回一个错误。当newkey 已经存在时，RENAME 命令将覆盖旧值。
- 可用版本： >= 1.0.0
- 返回值： 改名成功时提示OK ，失败时候返回一个错误。

# 12.TYPE
- 语法 :  TYPE key
- 作用 :  返回key 所储存的值的类型。
- 可用版本： >= 1.0.0
- 返回值：
	none (key 不存在)
	string (字符串)
	list (列表)
	set (集合)
	zset (有序集)
	hash (哈希表)
```

### 8.3 String类型

#### 1. 内存存储模型

![](http://mk-images.tagao.top/img/202204202019049.png?imageslim)

#### 2. 常用操作命令

| 命令                                       | 说明                                       |
| ------------------------------------------ | ------------------------------------------ |
| set                                        | 设置一个key/value                          |
| get                                        | 根据key获得对应的value                     |
| mset                                       | 一次设置多个key value                      |
| mget                                       | 一次获得多个key的value                     |
| getset                                     | 获得原始key的值，同时设置新值              |
| strlen                                     | 获得对应key存储value的长度                 |
| append                                     | 为对应key的value追加内容                   |
| getrange 索引0开始                         | 截取value的内容                            |
| setex                                      | 设置一个key存活的有效期（秒）              |
| psetex                                     | 设置一个key存活的有效期（毫秒）            |
| setnx                                      | 存在不做任何操作,不存在添加                |
| msetnx原子操作(只要有一个存在不做任何操作) | 可以同时设置多个key,只有有一个存在都不保存 |
| decr                                       | 进行数值类型的-1操作                       |
| decrby                                     | 根据提供的数据进行减法操作                 |
| Incr                                       | 进行数值类型的+1操作                       |
| incrby                                     | 根据提供的数据进行加法操作                 |
| Incrbyfloat                                | 根据提供的数据加入浮点数                   |

###  8.4 List类型

list 列表 相当于java中list 集合  特点  元素有序  且 可以重复

#### 1.内存存储模型



![](http://mk-images.tagao.top/img/202204202022223.png?imageslim)



#### 2.常用操作指令

| 命令                                           | 说明                                               |
| ---------------------------------------------- | -------------------------------------------------- |
| lpush                                          | 将某个值加入到一个key列表头部                      |
| lpushx                                         | 同lpush,但是必须要保证这个key存在                  |
| rpush                                          | 将某个值加入到一个key列表末尾                      |
| rpushx                                         | 同rpush,但是必须要保证这个key存在                  |
| lpop                                           | 返回和移除列表左边的第一个元素                     |
| rpop                                           | 返回和移除列表右边的第一个元素                     |
| lrange                                         | 获取某一个下标区间内的元素                         |
| llen                                           | 获取列表元素个数                                   |
| lset                                           | 设置某一个指定索引的值(索引必须存在)               |
| lindex                                         | 获取某一个指定索引位置的元素                       |
| lrem    listname  count value                  | 删除元素  （正数从左边开始删，负数从右边开始删除） |
| ltrim                                          | 保留列表中特定区间内的元素                         |
| linsert     listname  before/after pivot value | 在某一个元素之前，之后插入新元素                   |

### 8.5 Set类型

特点: Set类型 Set集合 元素无序  不可以重复

#### 1.内存存储模型



![](http://mk-images.tagao.top/img/202204202112970.png?imageslim)



#### 2.常用命令

| 命令                         | 说明                                                      |
| ---------------------------- | --------------------------------------------------------- |
| sadd                         | 为集合添加元素                                            |
| smembers                     | 显示集合中所有元素 无序                                   |
| scard                        | 返回集合中元素的个数                                      |
| spop                         | 随机返回一个元素 并将元素在集合中删除                     |
| smove  (formset toset value) | 从一个集合中向另一个集合移动元素  必须是同一种类型        |
| srem                         | 从集合中删除一个元素                                      |
| sismember                    | 判断一个集合中是否含有这个元素                            |
| srandmember                  | 随机返回元素                                              |
| sdiff                        | 去掉第一个集合中其它集合含有的相同元素 (第一个集合的补集) |
| sinter                       | 求交集                                                    |
| sunion                       | 求和集                                                    |

###  8.6 ZSet类型

特点: 可排序的set集合  排序  不可重复 

ZSET 官方  可排序SET  sortSet   

#### 1.内存模型

![](http://mk-images.tagao.top/img/202204211153842.png?imageslim)



#### 2.常用命令

| 命令                            | 说明                                                      |
| ------------------------------- | --------------------------------------------------------- |
| zadd                            | 添加一个有序集合元素                                      |
| zcard                           | 返回集合的元素个数                                        |
| zrange 升序                     | 返回一个范围内的元素                                      |
| zrevrange 降序 ( reversal反转 ) | 返回一个范围内的元素                                      |
| zrangebyscore                   | <font color='red'>**按照分数查找一个范围内的元素**</font> |
| zrank                           | 返回排名                                                  |
| zrevrank                        | 倒序排名                                                  |
| zscore                          | 显示某一个元素的分数                                      |
| zrem                            | 移除某一个元素                                            |
| zincrby                         | 给某个特定元素加分                                        |



###  8.7 hash类型

特点: value 是一个map结构 存在key value  key 无序的  

#### 1.内存模型

![](http://mk-images.tagao.top/img/202204202225750.png?imageslim)

#### 2.常用命令

| 命令         | 说明                    |
| ------------ | ----------------------- |
| hset         | 设置一个key/value对     |
| hget         | 获得一个key对应的value  |
| hgetall      | 获得所有的key/value对   |
| hdel         | 删除某一个key/value对   |
| hexists      | 判断一个key是否存在     |
| hkeys        | 获得所有的key           |
| hvals        | 获得所有的value         |
| hmset        | 设置多个key/value       |
| hmget        | 获得多个key的value      |
| hsetnx       | 设置一个不存在的key的值 |
| hincrby      | 为value进行加法运算     |
| hincrbyfloat | 为value加入浮点值       |

---

## 9. 持久化机制

client  redis[内存] ----->  内存数据- 数据持久化-->磁盘

Redis官方提供了两种不同的持久化方法来将数据存储到硬盘里面分别是:

- <font color='red'>**快照(Snapshot) RDB**</font>
- <font color='red'>**AOF (Append Only File) 只追加日志文件**</font>

### 9.1 快照(Snapshot)

#### 1. 特点

这种方式可以将某一时刻的所有数据都写入硬盘中,当然这也是**redis的默认开启持久化方式**,保存的文件是以.rdb形式结尾的文件因此这种方式也称之为RDB方式。

![](http://mk-images.tagao.top/img/202204211153708.png?imageslim)

#### 2.快照生成方式

- 客户端方式: BGSAVE 和 SAVE指令
- 服务器配置自动触发

```markdown
# 1.客户端方式之BGSAVE
- a.客户端可以使用BGSAVE命令来创建一个快照,当接收到客户端的BGSAVE命令时,redis会调用fork¹来创建一个子进程,然后子进程负责将快照写入磁盘中,而父进程则继续处理命令请求。
	
	`名词解释: fork当一个进程创建子进程的时候,底层的操作系统会创建该进程的一个副本,在类unix系统中创建子进程的操作会进行优化:在刚开始的时候,父子进程共享相同内存,直到父进程或子进程对内存进行了写之后,对被写入的内存的共享才会结束服务`
```

```markdown
# 2.客户端方式之SAVE
- b.客户端还可以使用SAVE命令来创建一个快照,接收到SAVE命令的redis服务器在快照创建完毕之前将不再响应任何其他的命令
```

![](http://mk-images.tagao.top/img/202204211154527.png?imageslim)

- **注意: SAVE命令并不常用,使用SAVE命令在快照创建完毕之前,redis处于阻塞状态,无法对外服务**

```markdown
# 3.服务器配置方式之满足配置自动触发
- 如果用户在redis.conf中设置了save配置选项,redis会在save选项条件满足之后自动触发一次BGSAVE命令,如果设置多个save配置选项,当任意一个save配置选项条件满足,redis也会触发一次BGSAVE命令
```

![](http://mk-images.tagao.top/img/202204211154659.png?imageslim)

```markdown
# 4.服务器接收客户端shutdown指令
- 当redis通过shutdown指令接收到关闭服务器的请求时,会执行一个save命令,阻塞所有的客户端,不再执行客户端执行发送的任何命令,并且在save命令执行完毕之后关闭服务器

- shutdown 命令
- 停止所有客户端
- 如果有至少一个保存点在等待，执行 SAVE 命令
- 如果 AOF 选项被打开，更新 AOF 文件
- 关闭 redis 服务器(server)
```

#### 3.配置生成快照名称和位置

```markdown
#1.修改生成快照名称
- dbfilename dump.rdb

# 2.修改生成位置
- dir ./
```

![](http://mk-images.tagao.top/img/202204211154106.png?imageslim)

----



### 9.2 AOF 只追加日志文件



#### 1.AOF实现方式

​	<font color='red'>**AOF(append only file)持久化是以独立日志的方式记录每次写，删除命令**</font>，重启时再重新执行AOF文件中命令达到恢复数据的目的。

AOF的主要作用是解决了数据持久化的实时性，目前已经是Redis持久化的主流方式

![](http://mk-images.tagao.top/img/202204211154897.png?imageslim)



![](http://mk-images.tagao.top/img/202204211206374.png?imageslim)

#### 2.开启AOF持久化

在redis的默认配置中AOF持久化机制是没有开启的，需要在配置中开启

```markdown
# 1.开启AOF持久化
- a.修改 appendonly yes 开启持久化
- b.修改 appendfilename "appendonly.aof" 指定生成文件名称
```

![](http://mk-images.tagao.top/img/202204211154003.png?imageslim)

#### 3.日志追加频率

```markdown
# 1.always 【谨慎使用】
- 说明: 每个redis写命令都要同步写入硬盘,严重降低redis速度
- 解释: 如果用户使用了always选项,那么每个redis写命令都会被写入硬盘,从而将发生系统崩溃时出现的数据丢失减到最少;遗憾的是,因为这种同步策略需要对硬盘进行大量的写入操作,所以redis处理命令的速度会受到硬盘性能的限制;
- 注意: 转盘式硬盘在这种频率下200左右个命令/s ; 固态硬盘(SSD) 几百万个命令/s;
- 警告: 使用SSD用户请谨慎使用always选项,这种模式不断写入少量数据的做法有可能会引发严重的写入放大问题,导致将固态硬盘的寿命从原来的几年降低为几个月。

# 2.everysec 【推荐】
- 说明: 每秒执行一次同步显式的将多个写命令同步到磁盘
- 解释： 为了兼顾数据安全和写入性能,用户可以考虑使用everysec选项,让redis每秒一次的频率对AOF文件进行同步;redis每秒同步一次AOF文件时性能和不使用任何持久化特性时的性能相差无几,而通过每秒同步一次AOF文件,redis可以保证,即使系统崩溃,用户最多丢失一秒之内产生的数据。

# 3.no	【不推荐】
- 说明: 由操作系统决定何时同步 
- 如果设置为no，则write后不会有fsync调用，由操作系统自动调度刷磁盘，性能是最好的。

- 解释：最后使用no选项,将完全有操作系统决定什么时候同步AOF日志文件,这个选项不会对redis性能带来影响但是系统崩溃时,会丢失不定数量的数据,另外如果用户硬盘处理写入操作不够快的话,当缓冲区被等待写入硬盘数据填满时,redis会处于阻塞状态,并导致redis的处理命令请求的速度变慢。
```

#### 4.修改同步频率

```markdown
# 1.修改日志同步频率
- 修改appendfsync everysec|always|no 指定
```

![](http://mk-images.tagao.top/img/202204211154655.png?imageslim)

----



### 9.3 AOF文件的重写

#### 1. AOF带来的问题

AOF的方式也同时带来了另一个问题。持久化文件会变的越来越大。例如我们调用incr test命令100次，文件中必须保存全部的100条命令，其实有99条都是多余的。因为要恢复数据库的状态其实文件中保存一条set test 100就够了。为了压缩aof的持久化文件Redis提供了AOF重写(ReWriter)机制。

#### 2. AOF重写

用来在一定程度上减小AOF文件的体积

#### 3. 触发重写方式

```markdown
# 1.客户端方式触发重写
- 执行BGREWRITEAOF命令  不会阻塞redis的服务

# 2.服务器配置方式自动触发
- 配置redis.conf中的auto-aof-rewrite-percentage选项 参加下图↓↓↓
- 如果设置auto-aof-rewrite-percentage值为100和auto-aof-rewrite-min-size 64mb,并且启用的AOF持久化时,那么当AOF文件体积大于64M,并且AOF文件的体积比上一次重写之后体积大了至少一倍(100%)时,会自动触发,如果重写过于频繁,用户可以考虑将auto-aof-rewrite-percentage设置为更大
```

![](http://mk-images.tagao.top/img/202204211154362.png?imageslim)

#### 4. 重写原理

<font color='red'>**Redis将使用与快照类似的方式将内存中的数据以命令的方式保存到临时文件中**</font>，最后替换原来的文件，以此来实现控制AOF文件的合并重写（会将重写过程中接收的的新的指令和生成新的重写后AOF文件中的指令进行合并）。

> <font color='red'>**注意:在重写AOF文件时并没有读取旧的AOF文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的AOF文件 （模拟快照的过程）**</font>

```markdown
# 重写流程
- 1. redis调用fork ，现在有父子两个进程 子进程根据内存中的数据库快照，往临时文件中写入重建数据库状态的命令
- 2. 父进程继续处理client请求，除了把写命令写入到原来的aof文件中。同时把收到的写命令缓存起来。这样就能保证如果子进程重写失败的话并不会出问题。
- 3. 当子进程把快照内容写入已命令方式写到临时文件中后，子进程发信号通知父进程。然后父进程把缓存的写命令也写入到临时文件。
- 4. 现在父进程可以使用临时文件替换老的aof文件，并重命名，后面收到的写命令也开始往新的aof文件中追加。
```

![](http://mk-images.tagao.top/img/202204211155443.png?imageslim)

----



#### 5.工作流程

<font color='red'>**AOF的执行流程**</font>

> **开启AOF持久化后每执行一条会更改Redis中的数据的命令，Redis就会将该命令写入硬盘中的AOF文件。**

<font color='red'>**AOF的工作流程操作有：**</font>

- 命令写入（append）
- 文件同步（sync）
- 文件重写（rewrite）
- 重启加载（load）

1. <font color='red'>**AOF流程如下：**</font>

```markdown
- 1. 所有的写入命令会追加到aof_buf（缓冲区）中。
- 2. AOF缓冲区根据对应的策略向硬盘做同步操作。
- 3. 随着AOF文件越来越大，需要定期对AOF文件进行重写，达到压缩的目的。
- 4. 当Redis服务重启时，可以加载AOF文件进行数据恢复。
```



#### 6.AOF重启加载

> AOF和RDB文件都可以用于服务器重启时的数据恢复。AOF持久化开启且存在AOF文件时，<font color='red'>**优先加载AOF文件**</font>；AOF关闭或者AOF文件不存在时，加载RDB文件；加载AOF/RDB文件城后，Redis启动成功；AOF/RDB文件存在错误时，Redis启动失败并打印错误信息。





7.AOF文件恢复

​	正常情况下，将appendonly.aof 文件拷贝到redis的安装目录的bin目录下，重启redis服务即可。但在实际开发中，可能因为某些原因导致appendonly.aof 文件格式异常，从而导致数据还原失败，可以通过命令

`redis-check-aof --fix appendonly.aof` 进行修复 。







### 9.4 持久化总结





​	无论使用AOF还是快照机制持久化,将数据持久化到硬盘都是有必要的,除了持久化外,用户还应该对持久化的文件进行备份(最好备份在多个不同地方)。



> 每秒同步策略的效率是比较高的，同步禁用策略的效率和RDB一样高效。

- <font color='red'>**AOF 的数据完整性比RDB高，但记录内容多了，会影响数据恢复的效率**</font>。

- RDB与AOF二者选择的标准，就是看系统是愿意牺牲一些性能，换取更高的缓存一致性（aof），还是愿意写操作频繁的时候，不启用备份来换取更高的性能，待手动运行save的时候，再做备份（rdb）。

- <font color='red'>**Redis允许同时开启AOF和RDB**</font>，既保证了数据安全又使得进行备份等操作十分容易。此时重新启动Redis后Redis会使用AOF文件来恢复数据，因为AOF方式的持久化可能丢失的数据更少。

- <font color='red'>**若只打算用Redis 做缓存，可以关闭持久化。**</font>

  

---

## 10. java操作Redis

### 10.1 环境准备

#### 1. 引入依赖

```xml
<!--引入jedis连接依赖-->
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
  <version>2.9.0</version>
</dependency>
```

#### 2.创建jedis对象

```java
 public static void main(String[] args) {
   //1.创建jedis对象
   Jedis jedis = new Jedis("192.168.40.4", 6379);//1.redis服务必须关闭防火墙  2.redis服务必须开启远程连接
   jedis.select(0);//选择操作的库默认0号库
   //2.执行相关操作
   //....
   //3.释放资源
   jedis.close();
 }
```

![](http://mk-images.tagao.top/img/202204211155736.png?imageslim)

### 10.2 操作key相关API

```java
private Jedis jedis;
    @Before
    public void before(){
        this.jedis = new Jedis("192.168.202.205", 7000);
    }
    @After
    public void after(){
        jedis.close();
    }

    //测试key相关
    @Test
    public void testKeys(){
        //删除一个key
        jedis.del("name");
        //删除多个key
        jedis.del("name","age");

        //判断一个key是否存在exits
        Boolean name = jedis.exists("name");
        System.out.println(name);

        //设置一个key超时时间 expire pexpire
        Long age = jedis.expire("age", 100);
        System.out.println(age);

        //获取一个key超时时间 ttl
        Long age1 = jedis.ttl("newage");
        System.out.println(age1);

        //随机获取一个key
        String s = jedis.randomKey();

        //修改key名称
        jedis.rename("age","newage");

        //查看可以对应值的类型
        String name1 = jedis.type("name");
        System.out.println(name1);
        String maps = jedis.type("maps");
        System.out.println(maps);
    }
```

![](http://mk-images.tagao.top/img/202204211155460.png?imageslim)

### 10.3操作String相关API

```java
//测试String相关
    @Test
    public void testString(){
        //set
        jedis.set("name","小陈");
        //get
        String s = jedis.get("name");
        System.out.println(s);
        //mset
        jedis.mset("content","好人","address","海淀区");
        //mget
        List<String> mget = jedis.mget("name", "content", "address");
        mget.forEach(v-> System.out.println("v = " + v));
        //getset
        String set = jedis.getSet("name", "小明");
        System.out.println(set);

        //............
    }
```

![](http://mk-images.tagao.top/img/202204211155745.png?imageslim)

### 10.4操作List相关API

```java
//测试List相关
    @Test
    public void testList(){

        //lpush
        jedis.lpush("names1","张三","王五","赵柳","win7");

        //rpush
        jedis.rpush("names1","xiaomingming");

        //lrange

        List<String> names1 = jedis.lrange("names1", 0, -1);
        names1.forEach(name-> System.out.println("name = " + name));

        //lpop rpop
        String names11 = jedis.lpop("names1");
        System.out.println(names11);

        //llen
        jedis.linsert("lists", BinaryClient.LIST_POSITION.BEFORE,"xiaohei","xiaobai");

      	//........

    }

```

![](http://mk-images.tagao.top/img/202204211156766.png?imageslim)

### 10.5操作Set的相关API

```java
//测试SET相关
@Test
public void testSet(){

  //sadd
  jedis.sadd("names","zhangsan","lisi");

  //smembers
  jedis.smembers("names");

  //sismember
  jedis.sismember("names","xiaochen");

  //...
}
```

![](http://mk-images.tagao.top/img/202204211156099.png?imageslim)

### 10.6 操作ZSet相关API

```java
//测试ZSET相关
@Test
public void testZset(){

  //zadd
  jedis.zadd("names",10,"张三");

  //zrange
  jedis.zrange("names",0,-1);

  //zcard
  jedis.zcard("names");

  //zrangeByScore
  jedis.zrangeByScore("names","0","100",0,5);

  //..

}
```

![](http://mk-images.tagao.top/img/202204211156401.png?imageslim)

### 10.7 操作Hash相关API

```java
//测试HASH相关
@Test
public void testHash(){
  //hset
  jedis.hset("maps","name","zhangsan");
  //hget
  jedis.hget("maps","name");
  //hgetall
  jedis.hgetAll("mps");
  //hkeys
  jedis.hkeys("maps");
  //hvals
  jedis.hvals("maps");
  //....
}
```

![](http://mk-images.tagao.top/img/202204211156571.png?imageslim)

----

## 11.SpringBoot整合Redis

​	Spring Boot Data(数据) Redis 中提供了**RedisTemplate和StringRedisTemplate**



**StringRedisTemplate与RedisTemplate区别点**

- 二者的关系是StringRedisTemplate<font color='red'>**继承**</font>RedisTemplate,  两者方法基本一致，。 

  不同之处主要体现在操作的数据类型不同: 

  <font color='red'>**RedisTemplate中的两个泛型都是Object，意味着存储的key和value都可以是一个对象**</font>

  <font color='red'>**StringRedisTemplate的两个泛型都是String，意味着StringRedisTemplate的key和value都只能是字符串。**</font>



​		



- 二者的数据是**不共通**的；

  * StringRedisTemplate只能管理StringRedisTemplate里面的数据
  * RedisTemplate只能管理RedisTemplate中的数据。

  

- **<font color='red'>区别主要在于他们使用的序列化类:</font>**

　　　　RedisTemplate使用的是<font color='red'>**JdkSerializationRedisSerializer  **</font>存入数据会将数据先序列化成**字节数组**而后在存入Redis数据库。 

　　 　 StringRedisTemplate使用的是<font color='red'>**StringRedisSerializer **</font>进行序列化

![](http://mk-images.tagao.top/img/202204212247461.png?imageslim)

<font color='red'>**默认RedisTemplate的键 值 都会进行JDK XXX 序列化， 使用避免键也被序列化，修改配置**</font>

```java
//在使用RedisTemplate时，避免key使用jdk xxx 序列化方式， 改用string序列化方式
redisTemplate.setKeySerializer(new StringRedisSerializer())；
//在使用RedisTemplate时，避免hash key使用jdk xxx 序列化方式， 改用string序列化方式
redisTemplate.setHashKeySerializer(new StringRedisSerializer());
```





- RedisTemplate使用时常见问题：

　　　　RedisTemplate 中存取数据都是<font color='red'>字节数组</font>。当redis中存入的数据是可读形式而非字节数组时，使用redisTemplate取值的时候会没法获取导出数据，得到的值为null。能够使用 StringRedisTemplate 试试。



​	

### 11.1 环境准备



#### 1.引入依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

#### 2.配置application

```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.database=0
```

### 11.2 使用Template

<font color='red'>**5种数据结构的操作**</font>

```java
(String)redisTemplate.opsForValue();　　//操做字符串
(String)redisTemplate.opsForHash();　　 //操做hash
(String)redisTemplate.opsForList();　　 //操做list
(String)redisTemplate.opsForSet();　　   //操做set
(String)redisTemplate.opsForZSet();　 　//操做有序set
```

```java
@Autowired
private StringRedisTemplate stringRedisTemplate;  //对字符串支持比较友好,不能存储对象
@Autowired
private RedisTemplate redisTemplate;  //存储对象
```

```markdown
key的绑定操作 对某一个key的操作及其频繁,可以将这个key绑定到对应redistemplate中,基于绑定操作都是操作这个key
boundValueOps 用来对String值绑定key
boundListOps 用来对List值绑定key
boundSetOps 用来对Set值绑定key
boundZsetOps 用来对Zset值绑定key
boundHashOps 用来对Hash值绑定key
```

```java
@Test
public void testBoundKey(){
    BoundValueOperations<String, String> nameValueOperations = stringRedisTemplate.boundValueOps("name");
    nameValueOperations.set("1");
    nameValueOperations.set("2");
    String s = nameValueOperations.get();
    System.out.println(s);
}
```



```java
//hash相关操作 opsForHash
@Test
public void testHash(){
    stringRedisTemplate.opsForHash().put("maps","name","小黑");
    Object o = stringRedisTemplate.opsForHash().get("maps", "name");
    System.out.println(o);
}
```

```java
//zset相关操作 opsForZSet
@Test
public void testZSet(){
    stringRedisTemplate.opsForZSet().add("zsets","小黑",10);
    Set<String> zsets = stringRedisTemplate.opsForZSet().range("zsets", 0, -1);
    zsets.forEach(value-> System.out.println(value));
}
```

```java
//set相关操作 opsForSet
@Test
public void testSet(){
    stringRedisTemplate.opsForSet().add("sets","xiaosan","xiaosi","xiaowu");
    Set<String> sets = stringRedisTemplate.opsForSet().members("sets");
    sets.forEach(value-> System.out.println(value));
}
```

```java
//list相关的操作opsForList
@Test
public void testList(){
    // stringRedisTemplate.opsForList().leftPushAll("lists","张三","李四","王五");
    List<String> lists = stringRedisTemplate.opsForList().range("lists", 0, -1);
    lists.forEach(key -> System.out.println(key));
}
```

```java
//String相关的操作 opsForValue
@Test
public void testString(){
    //stringRedisTemplate.opsForValue().set("166","好同学");
    String s = stringRedisTemplate.opsForValue().get("166");
    System.out.println(s);
    Long size = stringRedisTemplate.opsForValue().size("166");
    System.out.println(size);
}
```

```java
//key相关的操作
@Test
public void test(){
    Set<String> keys = stringRedisTemplate.keys("*");//查看所有key
    Boolean name = stringRedisTemplate.hasKey("name");//判断某个key是否存在
    stringRedisTemplate.delete("age");//根据指定key删除
    stringRedisTemplate.rename("","");//修改key的名称
    stringRedisTemplate.expire("key",10, TimeUnit.HOURS);
    //设置key超时时间 参数1:设置key名 参数2:时间 参数3:时间的单位
    stringRedisTemplate.move("",1);//移动key
}
```







## 12.分布式缓存



### 1.缓存

​	定义：存储在计算机内存的数据

   特点是  <font color='red'>**1. 读写快， 2.断电立即丢失**</font>



### 2.缓存优点

1. <font color='red'>**提高网站吞吐量，提高网站的响应速度。**</font>

2. <font color='red'>**核心解决问题：通过缓存可以缓解对数据库的访问压力**</font>

   

### 3.本地和分布式(缓存)

```markdown
- 本地缓存： 存在应用服务器内存中的数据为本地缓存（local cache）
- 分布式缓存：存储在当前应用服务器内存之外的数据为分布式缓存（distribute cache）
```



集群：将<font color='red'>**同一种服务的多个节点放在一起共同对系统提供服务**</font>过程称之为集群。

分布式：有<font color='red'>**多个不同服务集群共同对系统系统提供服务**</font>这个系统称之为分布式系统（distribute system），即<font color='red'>**分布式是建立在集群**</font>之上的。



### 4. MyBatis缓存

​	MyBatis中的缓存相关类都在cache包下面，而且定义了<font color='red'>**一个顶级接口Cache，默认只有一个实现类PerpetualCache**</font>，PerpetualCache中是内部维护了一个HashMap来实现缓存。

![](http://mk-images.tagao.top/img/202204222059867.png?imageslim)



decorators包下面的所有类也实现了Cache接口，但是除了<font color='red'>**PerpetualCache**</font>都是修饰器。

![](http://mk-images.tagao.top/img/202204222101010.png?imageslim)



```markdown
# 一级缓存

​	**一级缓存也叫本地缓存**，在MyBatis中，**一级缓存是在会话(SqlSession)层面实现的**</font>，这就说明一级缓存作用范围只能在同一个SqlSession中，跨SqlSession是无效的。

- 一级缓存是默认实现的，一级缓存作用域是SqlSession级别，所以它存储的SqlSession中的BaseExecutor之中。

## 一级缓存的清除：

1、就是获取缓存之前会先进行判断用户是否配置了flushCache=true属性（参考一级缓存的创建代码截图），如果配置了则会清除一级缓存。

2、MyBatis全局配置属性localCacheScope配置为Statement时，那么完成一次查询就会清除缓存。

3、在执行commit，rollback，update方法时会清空一级缓存。
```

<font color='red'>**二级缓存还是本地缓存**</font>

```markdown
# 二级缓存
​  一级缓存因为只能在同一个SqlSession中共享，所以会存在一个问题，在分布式或者多线程的环境下，不同会话之间对于相同的数据可能会产生不同的结果，因为跨会话修改了数据是不能互相感知的，所以就有可能存在脏数据的问题。

- 二级缓存的作用域是namespace，也就是作用范围是同一个命名空间，很显然二级缓存是需要存储在SqlSession之外的，实现跨会话共享的。
- 在MyBatis中为了实现二级缓存，专门用了一个装饰器来维护，这就是：CachingExecutor。

## 开启方式 

在Mapper映射文件内需要配置缓存标签：
`<cache/>`
为避免出现脏读的情况，指定共享的NameSpace
`<cache-ref namespace="className"/>`


## 注意：

1、需要commit事务之后才会生效

2、如果使用的是默认缓存，那么结果集对象需要实现序列化接口(Serializable)如果不实现序列化接口则会报如下错误
```



### 5.Redis缓存策略

<font color='red'>**自定义RedisCache实现**</font>

![](http://mk-images.tagao.top/img/202204222119568.png?imageslim)



1. <font color='red'>**自定义Redis缓存实现类。**</font>

![](http://mk-images.tagao.top/img/202204222201002.png?imageslim)





2. <font color='red'>**开启二级缓存，指向自定义Redis缓存类，并且实例化。**</font>

```xml
<!--开启mybatis二级缓存-->
<cache type="top.tagao.redis.Cache.RedisCache"/>
```



3. <font color='red'>**注意：**</font>以为需要在自定义类使用<font color='red'>**RedisTemplate**</font>，但是 RedisCache自定义类是通过 MyBatis 实例化，Mybatis并没有把cache的自定义类交由Spring工厂管理，所以RedisTemplate不能直接通过@Autowir注入到RedisCache中。 解决方案，实现 `ApplicationContextAware` 接口，得到工厂，通过 名称从工厂拿到RedisTemplate。

![](http://mk-images.tagao.top/img/202204222210799.png?imageslim)





4. 当存在对多表时进行查询SQL语句，如果<font color='red'>**增删改**</font>，只会清空当前的nameSpace，其余关联的表的namespace并没有被清空，此时就会出现<font color='red'>**脏读**</font>问题。 

   解决方法：多个cache指向同一个namespace

   ```xml
   <cache-ref />
   ```

   ![](http://mk-images.tagao.top/img/202204222227739.png?imageslim)

### 6.缓存优化策略

​	

1. <font color='red'>**对放入redis中的可以进行优化：key的长度不能太长**</font>

```markdown
- 尽可能将key设计的简洁一些，对key进行md5
```



![](http://mk-images.tagao.top/img/202204222229220.png?imageslim)

 



### 7.缓存穿透(恶意)

缓存穿透，是指查询<font color='red'>**一个数据库不存在，Redis缓存也不存在的数据**</font>。

例如数据库中没有id=-1的数据，这时如果外界疯狂查id=-1这个数据，先查询redis没有这个数据，再查询数据库也不存在这个数据即返回空，因此一直重复这个步骤，导致数据库压力过大，而出现宕机。

解决方案：

> - <font color='red'>**不存在的数据缓存到redis中，设置key，value值为null**</font>(不管是数据未null还是系统bug问题)，并设置一个短期过期时间段，避免过期时间过长影响正常用户使用。
> - <font color='red'>**拉黑该IP地址**</font>
> - <font color='red'>**布隆过滤器 **</font>将所有可能存在的数据哈希到一个足够大的bitmap(位图)中，一个一定不存在的数据会被 这个bitmap拦截掉，从而避免了对底层存储系统的查询压力。





### 8.缓存击穿

缓存穿透，是指查询<font color='red'>**一个数据库存在，Redis缓存不存在的数据（失效）**</font>。

某一个热点key，在不停地扛着高并发，当这个<font color='red'>**热点key在失效的一瞬间**</font>，<font color='red'>**持续的高并发访问就击破缓存**</font>直接访问数据库，导致数据库宕机。

解决方案：

> - <font color='red'>**设置热点数据"永不过期"**</font>
> - <font color='red'>**加上互斥锁**</font>：上面的现象是多个线程同时去查询数据库的这条数据，那么我们可以在第一个查询数据的请求上使用一个互斥锁来锁住它，其他的线程走到这一步拿不到锁就等着，等第一个线程查询到了数据，然后将数据放到redis缓存起来。后面的线程进来发现已经有缓存了，就直接走缓存





### 9.缓存雪崩

在<font color='cornflowerblue'>**高并发下**</font>，<font color='red'>**大量缓存key在同一时间失效**</font>，大量请求直接落在数据库上，导致数据库宕机。

解决方案：

> - 根据对不同的业务的数据，根据吞吐量来设置随机因子来生成缓存时间，高并发的业务随机因子较大。
> - <font color='red'>**缓存永久存储（不推荐）。**</font>
> - 若是集群部署，可将热点数据均匀分布在不同的Redis库中也能够避免key全部失效问题。



---





## 13. Redis 主从复制

### 13.1 主从复制

 	主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点（Master/Leader）,后者称为从节点（Slave/Follower）， 数据的复制是单向的！只能由主节点复制到从节点（主节点以写为主、从节点以读为主）。

​	默认情况下，每台Redis服务器都是主节点，一个主节点可以有0个或者多个从节点，但每个从节点只能由一个主节点。

​	<font color='red'>**主从复制架构仅仅用来解决数据的冗余备份,从节点仅仅用来同步数据**</font>

**无法解决: 1.master节点出现故障的自动故障转移**



![](http://mk-images.tagao.top/img/202204211156943.png?imageslim)

### 13.2环境配置

查看当前库的信息：<font color='red'>**info replication**</font>  客户端命令关于主从配置的信息

![](http://mk-images.tagao.top/img/202204231759108.png?imageslim)



### 13.3 搭建主从复制

```markdown
# 1.准备3台机器并修改配置
- master
	port 6379
	bind 0.0.0.0
	
- slave1
	port 6380
	bind 0.0.0.0
	replicaof masterip masterport
	
- slave2
	port 6381
	bind 0.0.0.0
	replicaof masterip masterport
```

- 端口号

- pid文件名

- 日志文件名

- rdb文件名

  

![](http://mk-images.tagao.top/img/202204231725678.png?imageslim)

```markdown
# 2.启动3台机器进行测试
- cd /usr/redis/bin
- ./redis-server /root/master/redis.conf
- ./redis-server /root/slave1/redis.conf
- ./redis-server /root/slave2/redis.conf
```



### 13.4使用规则

阅读Redis.conf，关于 Replication章节

```markdown
- 1.从机只能读，不能写，主机可读可写但是多用于写。 
-
- 2.当主机断电宕机后，默认情况下从机的角色不会发生变化 ，集群中只是失去了写操作，当主机恢复以后，又会连接上从机恢复原状。
-
- 3.当从机断电宕机后，若不是使用配置文件配置的从机，再次启动后作为主机是无法获取之前主机的数据的，若此时重新配置称为从机，又可以获取到主机的所有数据。这里就要提到一个同步原理。
-
- 4.第二条中提到，默认情况下，主机故障后，不会出现新的主机，有两种方式可以产生新的主机：
-
	4.1 命令slaveof no one,这样执行以后从机会独立出来成为一个主机
	4.2 使用哨兵模式（自动选举）
```

---



## 14. Redis哨兵机制

### 14.1 哨兵Sentinel机制 

[参考文章](https://www.jianshu.com/p/06ab9daf921d)



  Sentinel（哨兵）是Redis 的高可用性解决方案：由一个或多个Sentinel 实例 组成的Sentinel 系统可以监视任意多个主服务器，以及这些主服务器属下的所有从服务器，并在被监视的主服务器进入下线状态时，自动将下线主服务器属下的某个从服务器升级为新的主服务器。简单的说哨兵就是带有**自动故障转移功能的主从架构**。

**无法解决: 1.单节点并发压力问题   2.单节点内存和磁盘物理上限**



### 14.2 哨兵架构原理

<font color='red'>**单机单个哨兵**</font>

![](http://mk-images.tagao.top/img/202204211157594.png?imageslim)

- <font color='red'>**哨兵的作用：**</font>
  - 通过发送命令，让Redis服务器返回监控其运行状态，包括主服务器和从服务器。
  - 当哨兵监测到master宕机，会自动将slave切换成master，然后通过**发布订阅模式**通知其他的从服务器，修改配置文件，让它们切换主机。





<font color='red'>**单机多哨兵模式**</font>

![](http://mk-images.tagao.top/img/202204232004406.png?imageslim)





### 14.3哨兵模式优缺点

**优点：**

1. 哨兵集群，基于主从复制模式，所有主从复制的优点，它都有
2. 主从可以切换，故障可以转移，系统的可用性更好
3. 哨兵模式是主从模式的升级，手动到自动，更加健壮

**缺点：**

1. Redis不好在线扩容，集群容量一旦达到上限，在线扩容就十分麻烦
2. 实现哨兵模式的配置其实是很麻烦的，里面有很多配置项





### 14.4 搭建哨兵架构

```markdown
# 1.在主节点上创建哨兵配置
- 在Master对应redis.conf同目录下新建sentinel.conf文件，名字绝对不能错；

# 2.配置哨兵，在sentinel.conf文件中填入内容：
 - 参考 《哨兵sentinel.conf 的核心配置》

# 3.启动哨兵模式进行测试	
- redis-sentinel  /root/sentinel/sentinel.conf
```



<font color='red'>**哨兵sentinel.conf 的核心配置**</font>

```shell
# 哨兵服务默认端口
port 26379

# 哨兵模式默认工作目录
dir /tmp

# 配置监听的主服务器
#sentinel monitor代表监控
#mymaster代表服务器的名称，可以自定义
# 192.168.11.128代表监控的主服务器，6379代表端口
# 2代表只有两个或两个以上的哨兵认为主服务器不可用的时候，才会进行failover操作。
sentinel monitor mymaster 192.168.17.130 6379 2

# sentinel author-pass定义服务的密码，mymaster是服务名称，123456是Redis服务器密码
# sentinel auth-pass <master-name> <password>
sentinel auth-pass mymaster 123456
```

![](http://mk-images.tagao.top/img/202204232100728.png?imageslim)

<font color='red'>**当主节点（6379）宕机，选举出新主节点（6380）**</font>

![](http://mk-images.tagao.top/img/202204232109233.png?imageslim)

<font color='red'>**当原主节点(6379)重新上线**</font>

![](http://mk-images.tagao.top/img/202204232115606.png?imageslim)



<font color='red'>**详细的Sentinel.conf配置**</font>

```shell
# Example sentinel.conf
 
# 哨兵sentinel实例运行的端口 默认26379
port 26379
 
# 哨兵sentinel的工作目录
dir /tmp
 
# 哨兵sentinel监控的redis主节点的 ip port 
# master-name  可以自己命名的主节点名字 只能由字母A-z、数字0-9 、这三个字符".-_"组成。
# quorum 当这些quorum个数sentinel哨兵认为master主节点失联 那么这时 客观上认为主节点失联了
# sentinel monitor <master-name> <ip> <redis-port> <quorum>
sentinel monitor mymaster 127.0.0.1 6379 1
 
# 当在Redis实例中开启了requirepass foobared 授权密码 这样所有连接Redis实例的客户端都要提供密码
# 设置哨兵sentinel 连接主从的密码 注意必须为主从设置一样的验证密码
# sentinel auth-pass <master-name> <password>
sentinel auth-pass mymaster 123
 
# 指定多少毫秒之后 主节点没有应答哨兵sentinel 此时 哨兵主观上认为主节点下线 默认30秒
# sentinel down-after-milliseconds <master-name> <milliseconds>
sentinel down-after-milliseconds mymaster 30000

# 这个配置项指定了在发生failover主备切换时最多可以有多少个slave同时对新的master进行同步，
#这个数字越小，完成failover所需的时间就越长，
#但是如果这个数字越大，就意味着越 多的slave因为replication而不可用。
#可以通过将这个值设为 1 来保证每次只有一个slave 处于不能处理命令请求的状态。
# sentinel parallel-syncs <master-name> <numslaves>
sentinel parallel-syncs mymaster 1
 
 
# 故障转移的超时时间 failover-timeout 可以用在以下这些方面： 
#1. 同一个sentinel对同一个master两次failover之间的间隔时间。
#2. 当一个slave从一个错误的master那里同步数据开始计算时间。直到slave被纠正为向正确的master那里同步数据时。
#3.当想要取消一个正在进行的failover所需要的时间。  
#4.当进行failover时，配置所有slaves指向新的master所需的最大时间。不过，即使过了这个超时，slaves依然会被正确配置为指向master，但是就不按parallel-syncs所配置的规则来了
# 默认三分钟
# sentinel failover-timeout <master-name> <milliseconds>
sentinel failover-timeout mymaster 180000
 
# SCRIPTS EXECUTION
 
#配置当某一事件发生时所需要执行的脚本，可以通过脚本来通知管理员，例如当系统运行不正常时发邮件通知相关人员。
#对于脚本的运行结果有以下规则：
#若脚本执行后返回1，那么该脚本稍后将会被再次执行，重复次数目前默认为10
#若脚本执行后返回2，或者比2更高的一个返回值，脚本将不会重复执行。
#如果脚本在执行过程中由于收到系统中断信号被终止了，则同返回值为1时的行为相同。
#一个脚本的最大执行时间为60s，如果超过这个时间，脚本将会被一个SIGKILL信号终止，之后重新执行。
 
#通知型脚本:当sentinel有任何警告级别的事件发生时（比如说redis实例的主观失效和客观失效等等），将会去调用这个脚本，
#这时这个脚本应该通过邮件，SMS等方式去通知系统管理员关于系统不正常运行的信息。调用该脚本时，将传给脚本两个参数，
#一个是事件的类型，
#一个是事件的描述。
#如果sentinel.conf配置文件中配置了这个脚本路径，那么必须保证这个脚本存在于这个路径，并且是可执行的，否则sentinel无法正常启动成功。
#通知脚本
# sentinel notification-script <master-name> <script-path>
  sentinel notification-script mymaster /var/redis/notify.sh
 
# 客户端重新配置主节点参数脚本
# 当一个master由于failover而发生改变时，这个脚本将会被调用，通知相关的客户端关于master地址已经发生改变的信息。
# 以下参数将会在调用脚本时传给脚本:
# <master-name> <role> <state> <from-ip> <from-port> <to-ip> <to-port>
# 目前<state>总是“failover”, 
# <role>是“leader”或者“observer”中的一个。 
# 参数 from-ip, from-port, to-ip, to-port是用来和旧的master和新的master(即旧的slave)通信的
# 这个脚本应该是通用的，能被多次调用，不是针对性的。
# sentinel client-reconfig-script <master-name> <script-path>
sentinel client-reconfig-script mymaster /var/redis/reconfig.sh
```



| 配置项                           | 参数类型                     | 作用                                                         |
| -------------------------------- | ---------------------------- | ------------------------------------------------------------ |
| port                             | 整数                         | 启动哨兵进程端口                                             |
| dir                              | 文件夹目录                   | 哨兵进程服务临时文件夹，默认为/tmp，要保证有可写入的权限     |
| sentinel down-after-milliseconds | <服务名称><毫秒数（整数）>   | 指定哨兵在监控Redis服务时，当Redis服务在一个默认毫秒数内都无法回答时，单个哨兵认为的主观下线时间，默认为30000（30秒） |
| sentinel parallel-syncs          | <服务名称><服务器数（整数）> | 指定可以有多少个Redis服务同步新的主机，一般而言，这个数字越小同步时间越长，而越大，则对网络资源要求越高 |
| sentinel failover-timeout        | <服务名称><毫秒数（整数）>   | 指定故障切换允许的毫秒数，超过这个时间，就认为故障切换失败，默认为3分钟 |
| sentinel notification-script     | <服务名称><脚本路径>         | 指定sentinel检测到该监控的redis实例指向的实例异常时，调用的报警脚本。该配置项可选，比较常用 |

<font color='red'>**sentinel down-after-milliseconds**</font>配置项只是一个哨兵在超过规定时间依旧没有得到响应后，会自己认为主机不可用。<font color='red'>**对于其他哨兵而言，并不是这样认为**</font>。哨兵会记录这个消息，当拥有认为主观下线的哨兵达到sentinel monitor所配置的数量时，就会发起一次投票，进行failover，此时哨兵会<font color='red'>**重写Redis的哨兵配置文件**</font>，以适应新场景的需要。









### 14.5 Springboot哨兵

```properties
# redis sentinel 配置
# master书写是使用哨兵监听的那个名称
spring.redis.sentinel.master=mymaster
# 连接的不再是一个具体redis主机,书写的是多个哨兵节点
spring.redis.sentinel.nodes=192.168.202.206:26379

# 这里需要配置上密码，对应某得redis数据库的密码
spring.redis.password=123
```

- **注意:如果连接过程中出现如下错误:RedisConnectionException: DENIED Redis is running in protected mode because protected mode is enabled, no bind address was specified, no authentication password is requested to clients. In this mode connections are only accepted from the loopback interface. If you want to connect from external computers to Redis you may adopt one of the following solutions: 1) Just disable protected mode sending the command 'CONFIG SET protected-mode no' from the loopback interface by connecting to Redis from the same host the server is running, however MAKE SURE Redis is not publicly accessible from internet if you do so. Use CONFIG REWRITE to make this change permanent. 2)**
- **解决方案:在哨兵的配置文件中加入bind 0.0.0.0 开启远程连接权限**

![](http://mk-images.tagao.top/img/202204222251772.png?imageslim)





## 15. Redis集群

### 15.1 集群

​	Redis在3.0后开始支持Cluster(模式)模式,目前redis的集群支持节点的自动发现,支持slave-master选举和容错,支持在线分片(sharding shard )等特性。reshard

### 15.2 集群架构图



![](http://mk-images.tagao.top/img/202204211157646.jpg?imageslim)

### 15.3 集群细节

```markdown
- 所有的redis节点彼此互联(PING-PONG机制),内部使用二进制协议优化传输速度和带宽.
-
- 节点的fail是通过集群中超过半数的节点检测失效时才生效. 
-
- 客户端与redis节点直连,不需要中间proxy层。客户端不需要连接集群所有节点,连接集群任何一个可用节点即可
-
- redis-cluster把所有的物理节点映射到[0-16383]slot上,cluster 负责维护node<->slot<->value
-
- 集群的节点需要对0-16383的节点进行完全覆盖
-
- 分片时，增加集群节点，对应就要重新分配slot ，slot的值也要迁移到新的节点上
```

![](http://mk-images.tagao.top/img/202204211157319.png?imageslim)





### 15.4 集群搭建

​	判断一个是集群中的节点是否可用,是集群中的所用主节点选举过程,如果半数以上的节点认为当前节点挂掉,那么当前节点就是挂掉了,所以搭建redis集群时建议节点数最好为奇数，**搭建集群至少需要三个主节点,三个从节点,至少需要6个节点**。



> <font color='red'>**redis版本>=5.xxx，直接使用 ./redis-cli --cluster create 指令构建redis集群。**</font>
>
> <font color='red'>**redis版本<5.xxx，需要安装ruby、rubygems环境，使用 ./redis-trib.rb create 指令构建redis集群。**</font>

```markdown
# 1.Redis5.0以下版本 准备环境安装ruby以及redis集群依赖
- yum install -y ruby rubygems
- gem install redis-xxx.gem
```

​	1. Redis官方提供了redis集群方式的工具：redis-trib.rb，位于/usr/local/src/redis-5.0.3/src目录下，它是用ruby写的一个程序，所以需要集群方式部署redis之前，需要安装<font color='red'>**Ruby环境**</font>。

![](http://mk-images.tagao.top/img/202204222251305.png?imageslim)

	2. 安装Ruby的redis接口，输入命令 " gem install redis-xxx.gem " 进行安装。获取文件

[]: http://rubygems.org/downloads/redis-3.3.5.gem

根据自己要下载的版本将redis-3.3.5.gem修改为自己所需要的版本，然后将windows上下载的gem上传到Linux

> 例：若下载4.0.3版本只需将3.3.5改为4.0.3即可
>
> 下载网址：http://rubygems.org/downloads/redis-4.0.3.gem

![](http://mk-images.tagao.top/img/202204222251506.png?imageslim)



```markdown
# 1.Redis5.0以上版本 不需要准备rudy
```



```markdown
# 2.在一台机器创建7个目录
```

![](http://mk-images.tagao.top/img/202204211157541.png?imageslim)

```markdown
# 3.每个目录复制一份配置文件
[root@localhost ~]# cp redis-4.0.10/redis.conf 7000/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7001/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7002/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7003/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7004/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7005/
[root@localhost ~]# cp redis-4.0.10/redis.conf 7006/
```

![](http://mk-images.tagao.top/img/202204211157144.png?imageslim)

```markdown
# 4.修改不同目录配置文件
- port 	6379 .....                		 //修改端口
- bind  0.0.0.0                   		 //开启远程连接
- cluster-enabled  yes 	        			 //开启集群模式
- cluster-config-file  nodes-port.conf //集群节点配置文件
- cluster-node-timeout  5000      	   //集群节点超时时间
- dir "/home/redis-cluster/7000/data"   //修改持久化RDB，AOF文件目录就不用特意去改文件名了
- appendonly  yes   		               //开启AOF持久化
- protected-mode no               （关闭保护模式）
- pidfile /var/run/redis_7000.pid

# 5.指定不同目录配置文件启动七个节点
- [root@localhost bin]# ./redis-server  /root/7000/redis.conf
- [root@localhost bin]# ./redis-server  /root/7001/redis.conf
- [root@localhost bin]# ./redis-server  /root/7002/redis.conf
- [root@localhost bin]# ./redis-server  /root/7003/redis.conf
- [root@localhost bin]# ./redis-server  /root/7004/redis.conf
- [root@localhost bin]# ./redis-server  /root/7005/redis.conf
- [root@localhost bin]# ./redis-server  /root/7006/redis.conf
```

![](http://mk-images.tagao.top/img/202204211157989.png?imageslim)

```markdown
# 6.查看进程
- [root@localhost bin]# ps aux|grep redis
```

![](http://mk-images.tagao.top/img/202204241704997.png?imageslim)



#### 1.创建集群

```markdown
# 方式一 Redis5.0 以下版本
## 1.复制集群操作脚本到bin目录中
- [root@localhost bin]# cp /root/redis-4.0.10/src/redis-trib.rb .

## 2.创建集群
- Redis5.0 以下版本
- ./redis-trib.rb create --replicas 1 192.168.202.205:7000 192.168.202.205:7001 192.168.202.205:7002 192.168.202.205:7003 192.168.202.205:7004 192.168.202.205:7005

# 方式二 Redis5.0 以上版本
- redis-cli --cluster create  节点:端口 --cluster-replicas 1 -a foobared
-
- redis-cli --cluster create  --cluster-replicas 1  192.168.17.130:7000 192.168.17.130:7001 192.168.17.130:7002 192.168.17.130:7003 192.168.17.130:7004 192.168.17.130:7005 -a 123
```

![](http://mk-images.tagao.top/img/202204241715712.png?imageslim)

```markdown
# 3.集群创建成功出现如下提示
```

![](http://mk-images.tagao.top/img/202204241718048.png?imageslim)



#### 2.查看集群状态



##### 2.1客户端内部

<font color='red'>**登录客户端，端口选择集群任一节点 ,-c 代表 cluster**</font>

```shell
redis-cli -p 端口 -a password -c
```

![](http://mk-images.tagao.top/img/202204241828413.png?imageslim)





* <font color='red'>**客户端  cluster help 集群命令查看**</font>

- （简单命令） cluster info 查看集群的基本信息，cluster nodes 查看集群的节点信息
- 注意，因为是集群，使用keys * 只能得到当前Redis<font color='red'>**节点**</font>的所有的key，而不能得到集群中所有的，但是 exists 判断key是否存在可用。（<font color='cornflowerblue'>**Terminal客户端，默认只能读取当前端口节点的Key。**</font><font color='red'>**RDM客户端，默认会加载所有的key**</font>）

![](http://mk-images.tagao.top/img/202204241831347.png?imageslim)





##### 2.2客户端外部

<font color='red'>**查看客户端外部命令帮助手册**</font> :  

```shell
redis-cli --cluster help
```

<font color='red'>**对于check, fix, reshard, del-node, set-timeout，可以指定集群中任何工作节点的主机和端口。**</font>

![](http://mk-images.tagao.top/img/202204241846554.png?imageslim)

* 1.create：创建一个集群环境host1:port1 … hostN:portN
* 2.call：可以执行redis命令
* 3.add-[node](https://so.csdn.net/so/search?q=node&spm=1001.2101.3001.7020)：将一个节点添加到集群里，第一个参数为新节点的ip:port，第二个参数为集群中任意一个已经存在的节点的ip:port
* 4.del-node：移除一个节点
* 5.reshard：重新分片
* 6.check：检查集群状态

```markdown
# 查看集群状态 check [原始集群中任意节点] [无] 
-  需要本机安装了Ruby
## 方式一
- ./redis-trib.rb check 192.168.202.205:7000 -a 123
## 方式二
-  redis-cli --cluster check  192.168.17.130:7000 -a 123
```

```markdown
# 集群节点状态说明
- 主节点 
	主节点存在hash slots,且主节点的hash slots 没有交叉
	主节点不能删除
	一个主节点可以有多个从节点
	主节点宕机时多个副本之间自动选举主节点

- 从节点
	从节点没有hash slots
	从节点可以删除
	从节点不负责数据的写,只负责数据的同步
```





#### 3.添加主节点

```markdown
# 1.添加主节点 add-node [新加入节点] [原始集群中任意节点]
- ./redis-trib.rb  add-node 192.168.1.158:7006  192.168.1.158:7005
- 注意:
	1.该节点必须以集群模式启动
	2.默认情况下该节点就是以master节点形式添加
```

<font color='red'>**注意：当添加主节点成功以后，新增的节点不会有任何数据，客户端目前还不能在这个master写数据，因为它还没有分配任何的slot(hash槽)，我们需要为新节点手工分配hash槽；**</font>

```shell
redis-cli -a zhuge --cluster reshard 192.168.0.61:7006
```

```markdown
输出如下：
… …
How many slots do you want to move (from 1 to 16384)? 600
(ps:需要多少个槽移动到新的节点上，自己设置，比如600个hash槽)
What is the receiving node ID? 2728a594a0498e98e4b83a537e19f9a0a3790f38
(ps:把这600个hash槽移动到哪个节点上去，需要指定节点id)
Please enter all the source node IDs.
Type ‘all’ to use all the nodes as source nodes for the hash slots.
Type ‘done’ once you entered all the source nodes IDs.
Source node 1:all
(ps:输入all为从所有主节点(8001,8002,8003)中分别抽取相应的槽数指定到新节点中，抽取的总槽数为600个)
… …
Do you want to proceed with the proposed reshard plan (yes/no)? yes
(ps:输入yes确认开始执行分片任务)
```

![](http://mk-images.tagao.top/img/202204241903658.png?imageslim)







#### 4.添加从节点

```markdown
# 1 添加从节点没有指定主节点
## 1.1添加从节点 add-node --slave [新加入节点] [集群中任意节点]
- ./redis-trib.rb  add-node --slave 192.168.1.158:7006 192.168.1.158:7000
- 注意:
	当添加副本节点时没有指定主节点,redis会随机给副本节点较少的主节点添加当前副本节点



# 2 添加从节点指定主节点
# 2.为确定的master节点添加主节点 add-node --slave --master-id master节点id [新加入节点] [集群任意节点]
- ./redis-trib.rb  add-node --slave --master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e 127.0.0.1:7006  127.0.0.1:7000
```













#### 5.删除副本节点

```markdown
# 1.删除节点 del-node [集群中任意节点] [删除节点id]
- ./redis-trib.rb  del-node 127.0.0.1:7002 0ca3f102ecf0c888fc7a7ce43a13e9be9f6d3dd1
- 注意:
 1.被删除的节点必须是从节点或没有被分配hash slots的节点
```

#### 6.集群在线分片

```markdown
# 1.在线分片 reshard [集群中任意节点] [无]
- ./redis-trib.rb  reshard  192.168.1.158:7000
```

----





## 16.分布式Session管理



 **Session 共享**

```text
是指在一个浏览器对应多个 Web 服务时，服务端的 Session 数据需要共享。
```

**Session 共享应用场景**

```text
1) 单点登录
2) Web 服务器集群等场景都需要用到
```

**利用 Cookie 记录 SessionID**

```text
SessionId 记录在客户端，每次请求服务器的时候，将 Session 放在请求中发送给服务器，
服务器处理完请求后再将修改后的 Session 响应给客户端。这里的客户端就是 cookie。
利用 cookie 记录 Session 的也有缺点，比如受 cookie 大小的限制，能记录的信息有限，
安全性低，每次请求响应都需要传递 cookie，影响性能，如果用户关闭 cookie，访问就不正
常。
```

### 16.0Session机制

![](http://mk-images.tagao.top/img/202204242226543.png?imageslim)

![](http://mk-images.tagao.top/img/202204242227514.png?imageslim)









### 16.1管理机制

<font color='red'>**Memcache Session Manager**</font> ：基于应用服务器

![](http://mk-images.tagao.top/img/202204242231744.png?imageslim)



<font color='red'>**Redis Session Manager**</font> ：基于应用

**Redis的session管理是利用spring提供的session管理解决方案,将一个应用session交给Redis存储,整个应用中所有session的请求都会去redis中获取对应的session数据。**<font color='red'>**(基于Spring/SpringBoot应用)**</font>

![](http://mk-images.tagao.top/img/202204211158973.png?imageslim)









<font color='red'>**redis相比memcached有哪些优势？**</font>

(1) [memcached](https://so.csdn.net/so/search?q=memcached&spm=1001.2101.3001.7020)所有的值均是简单的字符串，redis作为其替代者，支持更为丰富的数据类型

(2) redis的速度比memcached快很多

(3) redis可以持久化其数据，memcached是不支持数据持久化操作的。



### 16.2 应用Session管理

#### 1. 引入依赖

```xml
<dependency>
  <groupId>org.springframework.session</groupId>
  <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

#### 2. Session配置类

```java
@Configuration
@EnableRedisHttpSession
public class RedisSessionManager {
   
}
```



#### 3.Controller

```java
@Controller
@RequestMapping
public class RedisSessionController {

    @RequestMapping("test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List list = (List<String>)request.getSession().getAttribute("list");
        if(list==null){
            list = new ArrayList<>();
        }
        list.add("第"+list.size()+"次");
        request.getSession().setAttribute("list",list);//每次session变化都要同步session
        response.getWriter().println("size: "+list.size());
        response.getWriter().println("sessionid: "+request.getSession().getId());
    }

    @RequestMapping("logout")
    public void logout(HttpServletRequest request){
        //退出登录
        request.getSession().invalidate();//失效
    }
}
```



![](http://mk-images.tagao.top/img/202204250013601.png?imageslim)



![](http://mk-images.tagao.top/img/202204250027146.png?imageslim)



```markdown
# hash结构记录   
- key格式：spring:session:sessions:[sessionId]，对应的value保存session的所有数据包括：creationTime，maxInactiveInterval，lastAccessedTime，attribute；
- 这个 k 的过期时间为 Session 的最大过期时间 + 5 分钟。


# string结构记录 过期时间记录
key格式：spring:session:sessions:expires:[sessionId]，对应的value为空；该数据的TTL表示sessionId过期的剩余时间；
这个 k-v 不存储任何有用数据，只是表示 Session 过期而设置。
这个 k 在 Redis 中的过期时间即为 Session 的过期时间间隔。



# set结构记录  用户 ttl 过期时间记录
key格式：spring:session:expirations:[过期时间]，对应的value为expires:[sessionId]列表，有效期默认是30分钟，即1800秒；
这个 k 中的值是一个时间戳，根据这个 Session 过期时刻滚动至下一分钟而计算得出。
这个 k 的过期时间为 Session 的最大过期时间 + 5 分钟。
```



<font color='red'>**调用 logout方法 ，只有expires被删除，expires是保存当前有效的SessionId**</font>

![](http://mk-images.tagao.top/img/202204250031971.png?imageslim)





#### 4.打包测试即可

----







## 17.内存淘汰机制



### 17.0配置内存大小





​	根据“八二原理“，即 80% 的请求访问了 20% 的数据，因此如果按照这个原理来配置，将 Redis 内存大小设置为数据总量的 20%，就有可能拦截到 80% 的请求。当然，只是有可能，对于不同的业务场景需要进行不同的配置，一般建议把缓存容量设置为总数据量的 15% 到 30%，兼顾访问性能和内存空间开销。



配置方式（以 5GB 为例，如果不带单位则默认单位是字节）：

* 命令行

```shell
config set maxmemory 5gb
```

- 配置文件

![](http://mk-images.tagao.top/img/202205081525572.png?imageslim)



- 查看 maxmemory 命令

```shell
config get maxmemory
```







### 17.1过期键的删除策略

1、**被动删除**。在访问key时，如果发现key已经过期，那么会将key删除。

2、**主动删除**。定时清理key，每次清理会依次遍历所有DB，从db随机取出20个key，如果过期就删除，如果其中有5个key过期，那么就继续对这个db进行清理，否则开始清理下一个db。 

3、**内存不够时清理**。Redis有最大内存的限制，通过maxmemory参数可以设置最大内存，当使用的内存超过了设置的最大内存，就要进行内存释放， 在进行内存释放的时候，会按照配置的淘汰策略清理内存。



### 17.2内存淘汰策略

​	当Redis的内存超过最大允许的内存之后，Redis 会触发内存淘汰策略，删除一些不常用的数据，以保证Redis服务器正常运行。



Redis 4.0前提供 6 种数据淘汰策略：

Rdus  4.0后新增 2 种数据淘汰策略：LFU（allkeys-lfu，volatile-lfu）

**内存淘汰策略可以通过配置文件来修改**，相应的配置项是 maxmemory-policy ，默认配置是noeviction 。

![](http://mk-images.tagao.top/img/202205081532205.png?imageslim)



```markdown
- no-eviction：禁止删除数据，当内存不足以容纳新写入数据时，新写入操作会报错
-
- volatile-ttl：从已设置过期时间的数据集中挑选将要过期的数据淘汰
-
- allkeys-random：从数据集中任意选择数据淘汰
-
- volatile-random：从已设置过期时间的数据集中任意选择数据淘汰
-

```

以volatile 开头的策略只针对设置了过期时间的数据，即使缓存没有被写满，如果数据过期也会被删除。

以 allkeys 开头的策略是针对所有数据的，如果数据被选中了，即使过期时间没到，也会被删除。当然，如果它过期时间到了但未被策略选中，同样会被删除。



```markdown
- allkeys-lru LRU（ Least Recently Used 时间维度 ）： 从数据集中移除最近最少使用的key
-
- volatile-lru：，最近使用。利用LRU算法移除设置了过期时间的key
-

- allkeys-lfu LFU(Least Frequently Used 频率维度)：从数据集中移除最不经常使用的key。
-
- volatile-lfu：最少使用，从已设置过期时间的数据集中挑选最不经常使用的数据淘汰。
-
```







#### 1.LRU算法

​	LRU 全称是 Least Recently Used，即最近最少使用，会将最不常用的数据筛选出来，保留最近频繁使用的数据。

​	LRU 会把所有数据组成一个链表，链表头部称为 MRU，代表最近最常使用的数据；尾部称为 LRU代表最近最不常使用的数据；

​	链表中的元素按照操作顺序从前往后排列，最新操作的键会被移动到表头，当需要内存淘汰时，只需要删除链表尾部的元素即可。

![](http://mk-images.tagao.top/img/202205081544158.png?imageslim)



**但是，如果直接在 Redis 中使用 LRU 算法也会有一些问题：**

LRU 算法在实现过程中使用链表管理所有缓存的数据，这会给 Redis 带来额外的开销，而且，当有数据访问时就会有链表移动操作，进而降低 Redis 的性能。

于是，Redis 对 LRU 的实现进行了一些改变：

- 记录每个 key 最近一次被访问的时间戳（由键值对数据结构 RedisObject 中的 lru 字段记录）
- 在第一次淘汰数据时，会先随机选择 N 个数据作为一个候选集合，然后淘汰 lru 值最小的。（N 可以通过 config set maxmemory-samples  numbers 命令来配置，默认是5），维护的是一个淘汰池。
- 后续再淘汰数据时，会挑选数据进入候选集合，进入集合的条件是：它的 lru 小于候选集合中最小的 lru。
- 如果候选集合中数据个数达到了 maxmemory-samples，Redis 就会将 lru 值小的数据淘汰出去。



 **Redis 淘汰池，进一步提升了近似 LRU 算法的效果**。淘汰池是一个数组，它的大小是maxmemory_samples，每一次淘汰循环中，新随机出来的 key 列表会和淘汰池中的 key 列表进行融合，淘汰掉最旧的一个 key 之后，保留剩余较旧的 key 列表放入淘汰池中留待下一个循环。





#### 2.LFU算法

​	LFU 全称 Least Frequently Used，即最不经常使用策略，它是基于<font color='red'>**数据访问次数**</font>来淘汰数据的，在 Redis 4.0 时添加进来。它在 LRU 策略基础上，为每个数据增加了一个计数器，来统计这个数据的访问次数。

前面说到，LRU 使用了 RedisObject 中的 lru 字段记录时间戳，lru 是 24bit 的，LFU 将 lru 拆分为两部分：

- ldt 值：lru 字段的前 16bit，表示数据的访问时间戳
- counter 值：lru 字段的后 8bit，表示数据的访问次数 使用 LFU 策略淘汰缓存时，会把访问次数最低的数据淘汰，如果访问次数相同，再根据访问的时间，将访问时间戳最小的淘汰。





#### 3.区别

​	LRU 算法：淘汰最近最少使用的数据，它是根据<font color='red'>**时间维度**</font>来选择将要淘汰的元素，即删除掉最长时间没被访问的元素。

​	LFU 算法：淘汰最不频繁访问的数据，它是根据<font color='red'>**频率维度**</font>来选择将要淘汰的元素，即删除访问频率最低的元素。如果两个元素的访问频率相同，则淘汰最久没被访问的元素。



​	由于 LRU 是基于访问时间的，如果系统对大量数据进行单次查询，大部分的数据的RedisObject记录的时间戳会相近，容易出现避免误删，使用 LFU 算法就不容易被淘汰。



## 18.Redis配置文件



* <font color='red'>**Units**</font>
  * 单位，Redis配置文件中的单位对大小写不敏感

![](http://mk-images.tagao.top/img/202204231955014.png?imageslim)



* <font color='red'>**Includes**</font>
  * 包含，可以在Redis启动的时候再加载一些除了Redis.conf之外的其他的配置文件，
    * 与Spring的import，jsp的include类似

![](http://mk-images.tagao.top/img/202204231956819.png?imageslim)



* <font color='red'>**NETWORK**</font>
  * 网络，表示Redis启动时开放的端口默认与本机绑定

```markdown
bind 127.0.0.1
```

* Redis指定监听端口，默认为6379

```shell
port 6379
```

* 表示服务器闲置多长时间（秒）后被关闭，如果这个这个数值为0，表示这个功能不起作用

```shell
timeout 300
```

* 是否开启保护模式，Redis3.2版本后新增[protected](https://so.csdn.net/so/search?q=protected&spm=1001.2101.3001.7020)-mode配置，默认是yes，即开启。

```shell
protected-mode yes
```

protected-mode设置为不同值时的效果如下：

| protected-mode值 | 效果                                                      |
| ---------------- | --------------------------------------------------------- |
| no               | 关闭protected-mode模式，此时外部网络可以直接访问          |
| yes              | 开启protected-mode保护模式，需配置bind ip或者设置访问密码 |

想要在外部访问服务器中的Redis 除了需要设置 **protected-mode no** ，还需将 **redis.conf** 文件中的**bind:127.0.0.1**语句注释



* <font color='red'>**GENERAL**</font>

是否以守护进程的方式运行，即后台运行，一般默认为no，需要手动改为yes

```shell
daemonize yes
```

如果以守护进程的方式运行，就需要指定一个pid文件，在Redis启动时创建，退出时删除

```shell
pidfile /var/run/redis_6379.pid
```

配置日志等级，日志等级的可选项如下（翻译自配置文件，有改动）：

- debug：打印的信息较多，在工作中主要用于开发和测试
- verbose：打印的信息仅次于debug，但是格式较为工整
- <font color='red'>**notice：Redis默认配置，在生产环境中使用**</font>
- warning：只打印一些重要信息，比如警告和错误

```shell
loglevel notice
```

打印的日志文件名称，如果为空，表示标准输出，在配置守护进程的模式下会将输出信息保存到/dev/null

```shell
logfile ""
```

数据库支持数量，16个

```shell
databases 16
```



* <font color='red'>**SNAPSHOTTING**</font>

  中文翻译为快照，如果在规定的时间内，数据发生了几次更新，那么就会将数据同步备份到一个文件中。

```markdown
# 在900秒内，至少有一个key被修改（添加），就会进行持久化操作
save 900 1
# 在300秒内，至少有10个key被修改，就会进行持久化操作
save 300 10
# 在60秒内，至少有1万个key被修改，就会进行持久化操作
save 60 10000
```

如果Redis在进行持久化的时候出现错误，是否停止写入，默认为是

```shell
top-writes-on-bgsave-error yes
```

是否在进行数据备份时压缩持久化文件，默认为是，这个操作会耗费CPU资源，可以设置为no

```shell
rdbcompression yes
```

在保存持久化文件的同时，对文件内容进行数据校验

```shell
rdbchecksum yes
```

持久化文件保存的目录，默认保存在当前目录下

```shell
dir ./
```

<font color='red'>**REPLICATION**</font>

复制主机上的数据，当前配置所指定的IP和端口号即为主机

```shell
# Redis在配置文件中将此配置注释，默认不使用，下同
# replicaof <masterip> <masterport>
```

如果配置的主机有密码，需要配置此密码以通过master的验证

```shell
# masterauth <master-password>
```

<font color='red'>**SECRULITY**</font>

安全，可以在配置文件中设置Redis的登录密码

<font color='red'>**CLIENT**</font>

Redis允许存在的客户端的最大数量，默认有一万个

```shell
maxclients 10000
```

Redis配置最大的内存容量

```shell
maxmemory <bytes>
```

内存达到上限之后默认的处理策略

```shell
maxmemory-policy noeviction
```

处理策略有以下几种

- noeviction:默认策略，不淘汰，如果内存已满，添加数据是报错。
- allkeys-lru:在所有键中，选取最近最少使用的数据抛弃。(LRU)
- volatile-lru:在设置了过期时间的所有键中，选取最近最少使用的数据抛弃。
- allkeys-random: 在所有键中，随机抛弃。
- volatile-random: 在设置了过期时间的所有键，随机抛弃。
- volatile-ttl:在设置了过期时间的所有键，抛弃存活时间最短的数据

<font color='red'>**APPEND ONLY MODE**</font>

这是Redis持久化的另一种方式，AOF，AOF模式默认不开启，Redis默认开启的是持久化模式是RDB，在大部分情况下，RDB的模式完全够用

```shell
appendonly no
```

AOF持久化的文件名称

```shell
appendfilename "appendonly.aof"
```

每秒执行一次同步，但是可能会丢失这一秒的数据

```shell
# 对于 appendfsync 它有以下几个属性 
# appendfsync always 表示每次修改都会进行数据同步，速度较慢，消耗性能
# appendfsync no 不执行同步，不消耗性能
appendfsync everysec # 数据不同步，每秒记录一次
```







# 参考文章



**[内存淘汰机制](https://zhuanlan.zhihu.com/p/378813918)**