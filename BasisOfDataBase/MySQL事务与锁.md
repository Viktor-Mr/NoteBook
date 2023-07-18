# 	事务



​	维基百科的定义：事务是数据库管理系统（DBMS）执行过程中的一个逻辑单位，由 一个有限的数据库操作序列构成。

1、首先，事务就是要保证一组数据库操作，要么全部成功，要么全部失败；

2、在 MySQL 中，事务支持是在**引擎层**实现的；

3、并不是所有引擎都支持事务，如 MyISAM 就不支持，InnoDB 就支持；





# 1.事务的四大特性



## 1.1原子性 (Atomicity)

​	原子性是指事务必须被视为一个不可分割的最小工作单元，整个事务中的所有操作要么全部提交成功，要么全部失败回滚，对于一个事务来说，不可能只执行其中的一部分操作。

​	原子性，在InnoDB里面是通过<font color='red'>**undo log**</font>来实现的,它记录了数据修改之前的值(逻辑日志)，一旦发生异常，就可以用undo log来实现回滚操作。



## 1.2一致性(Consistency)

​	一致性指的是数据库的完整性约束没有被破坏<font color='red'>**，事务执行的前后都是合法的数据状态**</font>。比如主键必须是唯一的，字段长度符合要求。 <font color='red'>**两次查询的数据没有变化**</font>

除了数据库自身的完整性约束，还有一个是用户自定义的完整性。





## 1.3隔离性(Isolation)



​	通常来说，<font color='red'>**一个事务所做的修改在最终提交以前，对其他事务是不可见的**</font>。注意这里的“通常来说”，后面的事务隔离级级别会说到。隔离性是为了保证业务数据的一致性。



## 1.4 持久性(Durability)

​	 一旦事务提交，则其所做的<font color='red'>**修改就会永久保存到数据库**</font>中。此时即使系统崩溃，修改的数据也不会丢失。（持久性的安全性与刷新日志级别也存在一定关系，不同的级别对应不同的数据安全级别。）

​	持久性是通过<font color='red'>**redo log**</font>来实现的，我们操作数据的时候，会先写到内存的buffer pool里面，同时记录redo log,如果在刷盘之前出现异常,在重启后就可以读取redo log 的内容，写入到磁盘，保证数据的持久性。



## 1.5特征关系

事务的 ACID 特性概念简单，但不是很好理解，主要是因为这几个特性不是一种平级关系：

- 只有满足一致性，事务的执行结果才是正确的。
- 在无并发的情况下，事务串行执行，隔离性一定能够满足。此时只要能满足原子性，就一定能满足一致性。
- 在并发的情况下，多个事务并行执行，事务不仅要满足原子性，还需要满足隔离性，才能满足一致性。
- 事务满足持久化是为了能应对系统崩溃的情况。
- <font color='red'>**原子性，隔离性，持久性，最后都是为了实现一致性。**</font>
- <font color='cornflowerblue'>**原子性，持久性是通过日志实现， 隔离性是通过锁与快照实现。**</font>

![](http://mk-images.tagao.top/img/202204281153434.png?imageslim)



# 2.事务开启与结束

InnoDB里面有一个autocommit的参数（分成两个级别，<font color='red'>**session级别和global**</font> 级别）。

```sql
show variables like 'autocommit':
```

​	它的默认值是ON。autocommit这个参数是什么意思呢？是否自动提交。如果它的 值是true/on的话，我们在操作数据的时候，会自动开启一个事务，和自动提交事务。

```sql
set session autocommit = on/off;
```





MySQL中如何开启与结束事务： -- 设定事务是否自动开启 set session autocommit = on/off;

 -- 手工方式开启 begin / start transaction

 -- 结束事务：提交或回滚 commit / rollback





# 3.事务并发问题

​	在并发环境下，事务的隔离性很难保证，因此会出现很多并发<font color='red'>**一致性问题**</font>。



## 3.0脏写

**一个事务修改了另外一个没提交的事务的值**

​	脏写，意思是说有两个事务，事务 A 和事务 B 同时在更新一条数据，事务 A 先把它更新为 A 值，事务 B 紧接着就把它更新为 B 值。如图：



![img](https://pic3.zhimg.com/80/v2-d5c096c722332efdc187f5af5a0f923e_720w.webp)

可以看到，此时事务 B 是后更新那行数据的值，所以此时那行数据的值是 B。而且此时事务 A 更新之后会记录一条 undo log 日志。因为事务 A 是先更新的，它在更新之前，这行数据的值为 `NULL`。所以此时事务 A 的 undo log 日志大概就是：更新之前这行数据的值为 NULL，主键为 XX

那么此时事务 B 更新完数据的值为 B，此时事务 A 突然回滚了，就会用它的 undo log 日志去回滚。此时事务 A 一回滚，直接就会把那行数据的值更新回 NULL 值。如图：

![img](https://pic1.zhimg.com/80/v2-e047504a8f3ff8d968d04b720fdd0f9c_720w.webp)

然后就尴尬了，事务 B 一看，为什么我更新的 B 值没了？就因为你事务 A 反悔了把数据值回滚成 NULL 了，结果我更新的 B 值也不见 了。所以对于事务 B 看到的场景而言，就是自己明明更新了，结果值却没了，**这就是脏写。**

所谓脏写，就是我刚才明明写了一个数据值，结果过了一会却没了。而它的本质就是事务 B 去修改了事务 A 修改过的值，但是此时事务 A 还没提交，所以事务 A 随时会回滚，导致事务 B 修改的值也没了，这就是脏写的定义。



## 3.1脏读

事务A读取到事务B未提交的事务



![](http://mk-images.tagao.top/img/202204272114634.png?imageslim)





 左右两个事务A、B。事务A首先查询`id=1`的数据，得到`age=16`之后，事务B对`id=1`的数据，将`age=16`更新为`age=18`，然后事务A，再查询`id=1`的数据，这种在一个事务里边，多次读取同一条数据，该数据又是在其他事务中未提交的数据，同样的记录，但是得到的结果前后不一致，称为`脏读`。但是因为是没有提交的数据，将来的某个时间有可能事务B会回滚操作，导致`age`又重新变成`16`。



## 3.2不可重复读

事务A读取 到 事务B提交的数据(更新 删除) 

![](http://mk-images.tagao.top/img/202204272114257.png?imageslim)



  与`脏读`的差别在于`不可重复读`是事务B中已经提交了数据。
   `不可重复读`针对的是更新和删除，即`update`、`delete`。因为修改和删除，是操作的当前数据，并且已提交无法回滚，变化已经定型，已持久化到磁盘，所以叫`不可重复读`。
   如下图，还是左右两个事务A、B。事务A首先查询`id=1`的数据，得到`age=16`之后，事务B对`id=1`的数据，将`age=16`更新为`age=18`，并且进行了`commit`，即提交了。然后事务A，再查询`id=1`的数据，读取的数据是`age=18`。

## 3.3幻读

事务A读取 到 事务B提交的数据(插入) 

不可重复读是查询结果不一样(update), 幻读是数据量不一样(insert)

![](http://mk-images.tagao.top/img/202204272117947.png?imageslim)



​	`脏读`与`不可重复读`的区别在于是否提交，`脏读`意思是未提交，还没有持久化到磁盘，这种的数据有可能会回滚再变回原来的数据，而`不可重复读`就在与已提交的数据，以前的数据不可能再读回来的。
   `insert`带来的问题不属于`不可重复读`，`insert`并没有动到以前的数据，也就是说数据一致性并没有破坏，是新增数据带来的其他事务读不一致问题。









# 4.事务并发解决

事务隔离机制是为了解决<font color='red'>**事务并发的时的读一致性**</font>问题

事务并发带来的三大问题，其实就是数据库读<font color='red'>**一致性的问题，必须由数据库提供一定的事务隔离机制来解决**</font>。

  事务隔离有如下级别，`SQL92 ANSI/ISO标准`通过定义四种隔离级别，来规范数据库厂商按照该功能提供解决不同级别的事务并发问题。



## 4.1SQL标准

标准并不是强制，只是建议。

SQL92 ANSI/ISO标准：http://www.contrib.andrew.cmu.edu/~shadow/sql/sql1992.txt

标准下的定义:



| 事务隔离级别     |                      |                                                              |
| ---------------- | -------------------- | ------------------------------------------------------------ |
| Read Uncommitted | --未解决任何并发问题 | 事务未提交的数据对其他事务也是可见的，会出现脏读             |
| Read Committed   | --解决脏读问题       | 一个事务开始之后，只能看到已提交的事务所做的修改，会出现不可重复读 |
| Repeatable Read  | --解决不可重复读问题 | 在同一个事务中多次读取同样的数据结果是一样的，这种隔离级别未定义解决幻读的问题 |
| Serializable     | --解决所有问题       | 最高的隔离级别，通过强制事务的串行执行                       |





## 4.1MySQL事务隔离



![](http://mk-images.tagao.top/img/202204272144052.png?imageslim)



​	`mysql的InnoDB`中默认事务隔离级别是`可重复读(Repeatable Read)`,在InnoDB存储引擎中，`可重复读(Repeatable Read)`的隔离级别同时也解决了`幻读`问题。`InnoDB` 默认使用 RR 作为事务隔离级别的原因，既保证了数据的一致性，又支持较高的并发度。









# 5.锁





## 5.1锁种类

<font color='red'>**按照锁的种类 ：**</font> 锁一般分为乐观锁和悲观锁两种，InnoDB 存储引擎中使用的就是悲观锁，

​	其实不仅仅是关系型数据库系统中有乐观锁和悲观锁的概念，像hibernate、tair、memcache等都有类似的概念。所以，不应该拿乐观锁、悲观锁和其他的数据库锁等进行对比。

​	虽然乐观锁和悲观锁在本质上并不是同一种东西，乐观锁一个是一种思想。而悲观锁一个是一种真正的锁，但是它们都是一种并发控制机制。

[乐观锁|悲观锁](https://blog.csdn.net/weixin_46075832/article/details/124416835)

![](http://mk-images.tagao.top/img/202204281405857.png?imageslim)

## 5.2锁粒度

 https://draveness.me/mysql-innodb/



MySQL 中提供了两种封锁粒度：<font color='red'>**行级锁以及表级锁**</font>。



* ①在MySQL中有表锁，

  * LOCK TABLE table_name READ; 用读锁锁表，会阻塞其他事务修改表数据。

  * LOCK TABLE table_name WRITE; 用写锁锁表，会阻塞其他事务读和写。

* ②InnoDB引擎又支持行锁，行锁分为
  * 共享锁，一个事务对一行的共享只读锁。
  * 排它锁，一个事务对一行的排他读写锁。

应该尽量只锁定需要修改的那部分数据，而不是所有的资源。锁定的数据量越少，发生锁争用的可能就越小，系统的并发程度就越高。

但是加锁需要消耗资源，锁的各种操作（包括获取锁、释放锁、以及检查锁状态）都会增加系统开销。因此封锁粒度越小，系统开销就越大。

在选择封锁粒度时，需要在锁开销和并发程度之间做一个权衡。

```markdown
# 行锁与表锁的对比
- 锁定粒度： 表锁 > 行锁 
- 加锁效率： 表锁 > 行锁 
- 冲突概率： 表锁 > 行锁 
- 并发性能： 表锁 < 行锁
```



### 2.1共享锁

Shared Locks （共享锁），简称S锁。

共享锁就是多个事务对于同一数据可以共享一把锁，都能访问到数据，但是只能读不能修改;

- 一个事务对数据对象 A 加了 S 锁，可以对 A 进行读取操作，但是不能进行更新操作。加锁期间<font color='red'>**其它事务能对 A 加 S 锁，但是不能加 X 锁**</font>。

* <font color='red'>**加锁**</font>

```sql
select * from  tableName  lock in share mode;
```

释放锁有两种方式，只要事务结束，锁就会自动释放，包括提交事务和结束事务。



### 2.2排它锁

​	Exclusive Locks （排它锁），简称X锁。它是用来操作数据的，所以又叫做写锁。只要一个事务获取了一行数据的排它锁，其他的事务就不能再获取这一行数据的共享锁和排它锁。

* 一个事务对数据对象 A 加了 X 锁，就可以对 A 进行读取和更新。<font color='red'>**加锁期间其它事务不能对 A 加任何锁**</font>。

* <font color='red'>**加锁**</font>

​	 <font color='red'>**自动加锁 ：增删改，都会默认加上一个排它锁**</font>

```mysql
delete / update / insert 
```

​	<font color='red'>**手工加锁，用FOR UPDATE给一行数据加上一个排它锁**</font>

```mysql
select * from tableName FOR UPDATE;
```



释放锁有两种方式，只要事务结束，锁就会自动事务，包括提交事务和结束事务。





读写锁的兼容关系如下：

![](http://mk-images.tagao.top/img/202204281339262.png?imageslim)

​	共享锁代表了读操作、互斥锁代表了写操作，所以我们可以在数据库中**并行读**，但是只能**串行写**，只有这样才能保证不会发生线程竞争，实现线程安全。







### 2.3意向锁

​	意向锁在 InnoDB 中是表级锁,**（属于表级锁,是InnoDB管理，但不同于 Lock  tables table_name Read/Write 这种手动上锁的表锁）**

​	**意向锁是有数据引擎自己维护的，用户无法手动干预，在加行级排它锁或共享锁之前，InooDB先会判断所在数据行的数据表中是否有对应的意向锁。**



为了<font color='red'>**允许行锁和表锁共存，实现多粒度锁机制**</font>，InnoDB 还有两种内部使用的意向锁（Intention Locks），这两种意向锁都是**表锁**：

​	**意向共享锁** ：当我们给一行数据加上共享锁之前，需要在表上先加意向共享锁。

​    **意向互斥锁**：当我们给一行数据加上排他锁之前，需要在表上先加意向互斥锁。

反过来说：

​	如果一张表上面至少有一个意向共享锁，说明有其他的事务给其中的某些数据行加上了共享锁。

​	如果一张表上面至少有一个意向互斥锁,   说明有其他的事务给其中的某些数据行加上了互斥锁。





​	第一个作用，我们有了表级别的锁，在InnoDB里面就可以支持更多粒度的锁。

​	第二个作用，<font color='red'>**当我们准备给一张表加上表锁的时候**</font>，必须先要去判断有没其他的事务锁定了其中了某些行。如果有的话，肯定不能加上表锁。那么这个时候我们就要去扫描整张表才能确定能不能成功加上一个表锁，如果数据量特别大，比如有上千万的数据 的时候，加表锁的效率是不是很低？

​	引入了意向锁之后就不一样了。我只要判断这张表上面有没有意向锁，如果有，就直接返回失败。如果没有，就可以加锁成功。所以InnoDB里面的表锁，我们可以把<font color='red'>**它理解成一个标志**</font>。就像火车上厕所有没有人使用的灯，是用来提高加锁的效率的。<font color='red'>**意向锁是在存在行锁场景下的表锁快速失败机制**</font>





​	通过引入意向锁，事务 T 想要对表 A 加 表级 X 锁，只需要先检测是否有其它事务对表 A 加了 X/IX/S/IS 锁，如果加了就表示有其它事务正在使用这个表或者表中某一行的锁，因此事务 T 加 X 锁失败。

各种锁的兼容关系如下：这里兼容关系针对的是<font color='red'>**表级锁**</font>，

![](http://mk-images.tagao.top/img/202204281629619.png?imageslim)



解释如下：

- 任意 IS/IX 锁之间都是兼容的，因为它们只表示想要对表加锁，而不是真正加锁；
- 这里兼容关系针对的是<font color='red'>**表级锁**</font>，而表级的 IX 锁和行级的 X 锁兼容，两个事务可以对两个数据行加 X 锁。（事务 T1 想要对数据行 R1 加 X 锁，事务 T2 想要对同一个表的数据行 R2 加 X 锁，两个事务都需要对该表加 IX 锁，但是 IX 锁是兼容的，并且 IX 锁与行级的 X 锁也是兼容的，因此两个事务都能加锁成功，对同一个表中的两个数据行做修改。）





## 5.3锁算法

三种算法都是对行数据进行加锁。

### 3.1记录锁



<font color='red'>**行锁:记录锁 (Record Locks)**</font>

MySQL的行锁是通过索引加载的，即是行锁是加在索引响应的行上的，要是对应的SQL语句没有走索引，则会全表扫描。

1. 记录锁, 仅仅锁住索引记录的一行，在单条索引记录上加锁。
2. Record lock锁住的永远是索引，而非记录本身，即使该表上主键，唯一索引，那么InnoDB会在后台创建一个隐藏的聚集主键索引，那么锁住的就是这个隐藏的聚集主键索引。

所以说当一条SQL没有走任何索引时，那么将会在每一条聚集索引后面加X锁，这个类似于表锁，但原理上和表锁应该是完全不同的。

![](http://mk-images.tagao.top/img/202204282224706.png?imageslim)



### 3.2间隙锁

<font color='red'>**行锁:间隙锁(Gap Locks)**</font>

1. 区间锁, 仅仅锁住一个索引区间（开区间，不包括双端端点）。
2. 在索引记录之间的间隙中加锁，或者是在某一条索引记录之前或者之后加锁，并不包括该索引记录本身。比如在 1、4、7，10中，间隙锁的可能值有 (-∞, 1)，(1, 4)，(4, 7)，(7, 10)，(10,+∞)，
3. 间隙锁可用于防止幻读，保证索引间的不会被插入数据。

![](http://mk-images.tagao.top/img/202204282224578.png?imageslim)



### 3.3临键锁

<font color='red'>**行锁：临键锁(Next-Key Locks)**</font>

1. Record Lock +  Gap Lock, 左开右闭区间。
2. 默认情况下，InnoDB使用Next-Key Locks来锁定记录。`select … for update`
3. 但当查询的索引含有唯一属性的时候，Next-Key Lock 会进行优化，将其降级为Record Lock，即仅锁住索引本身，不是范围。
4. Next-Key Lock在不同的场景中会退化:

| 场景                                      | 退化锁类型                               |
| :---------------------------------------- | :--------------------------------------- |
| 使用unique index精确匹配(=)，且记录存在   | Record Locks                             |
| 使用unique index精确匹配(=)，且记录不存在 | Gap Locks                                |
| 使用unique index范围匹配（`< `和`>`）     | Record Locks + Gap Locks，锁上界不锁下界 |



![](http://mk-images.tagao.top/img/202204282225735.png?imageslim)





# 6.隔离级别实施方案

  

## 0.快照读和当前读

​	读取历史数据的方式<font color='red'>**快照读 (snapshot read)**</font>，而读取数据库最新版本数据的方式，叫当前读 (current read)。

* 当前读
  * <font color='red'>**像select lock in share mode(共享锁), select for update ; update, insert ,delete(排他锁)这些操作都是一种当前读**</font>，为什么叫当前读？就是它读取的是记录的最新版本，读取时还要保证其他并发事务不能修改当前记录，会对读取的记录进行加锁。
  * 对于会对数据修改的操作(update、insert、delete)都是采用当前读的模式。在执行这几个操作时会读取最新的记录，即使是别的事务提交的数据也可以查询到。假设要update一条记录，但是在另一个事务中已经delete掉这条数据并且commit了，如果update就会产生冲突，所以在update的时候需要知道最新的数据。也正是因为这样所以才导致上面我们测试的那种情况。

> <font color='red'>**通过加锁的方式实现当前读（LBCC），是悲观锁的实现**</font>



* select 快照读

  * <font color='red'>**当执行select操作是InnoDB默认会执行快照读**</font>，会记录下这次select后的结果，之后select 的时候就会返回这次快照的数据，即使其他事务提交了不会影响当前select的数据，这就实现了可重复读了。快照的生成当在第一次执行select的时候，也就是说假设当A开启了事务，然后没有执行任何操作，这时候B insert了一条数据然后commit,这时候A执行 select，那么返回的数据中就会有B添加的那条数据。之后无论再有其他事务commit都没有关系，因为快照已经生成了，后面的select都是根据快照来的。

  * 像不加锁的select操作就是快照读，即不加锁的非阻塞读；快照读的前提是隔离级别不是串行级别，串行级别下的快照读会退化成当前读；之所以出现快照读的情况，是基于提高并发性能的考虑，快照读的实现是基于多版本并发控制，即MVCC,可以认为MVCC是行锁的一个变种，但它在很多情况下，避免了加锁操作，降低了开销；既然是基于多版本，即<font color='red'>**快照读可能读到的并不一定是数据的最新版本，而有可能是之前的历史版本。**</font>

> **MVCC就是为了实现读-写冲突不加锁**，<font color='red'>**MVCC实现快照读**</font>





## 1.MVCC

https://dev.mysql.com/doc/refman/5.7/en/innodb-multi-versioning.html

<font color='red'>**多版本的并发控制**</font>Multi Version Concurrency Control (MVCC)  是 基于<font color='red'>**”数据版本”对并发事务**</font>进行访问。

MVCC的核心思想是：

​		通过保存数据在某一个<font color='red'>**时间点快照**</font>来实现的。也就是说不管实现时间多长，每个事物看到的数据都是一致的。

​	



### 1.1MVCC的实现原理

> MVCC的目的就是多版本并发控制，在数据库中的实现，就是为了解决读写冲突，它的实现原理主要是依赖记录中的 <font color='red'>**3个隐式字段，undo日志 ，Read View**</font>来实现的。



####  1.隐式字段

每行记录除了我们自定义的字段外，还有数据库隐式定义的DB_TRX_ID,DB_ROLL_PTR,DB_ROW_ID等字段

```markdown
- DB_ROW_ID 6byte, 隐含的自增ID（隐藏主键），如果数据表没有主键，InnoDB会自动以DB_ROW_ID产生一个聚簇索引
-
- DB_TRX_ID 6byte, 最近修改(修改/插入)事务ID：记录创建这条记录/最后一次修改该记录的事务ID,自动递增（创建版本号）
-
- DB_ROLL_PTR 7byte, 回滚指针，指向这条记录的上一个版本（存储于rollback segment里)（删除版本号）
-
- DELETED_BIT 1byte, 记录被更新或删除并不代表真的删除，而是删除flag变了
-
```

![](http://mk-images.tagao.top/img/202205031233179.png?imageslim)

如上图，DB_ROW_ID是数据库默认为该行记录生成的唯一隐式主键；DB_TRX_ID是当前操作该记录的事务ID； 而DB_ROLL_PTR是一个回滚指针，用于配合undo日志，指向上一个旧版本；delete flag没有展示出来。





​	对于`InnoDb`中在两个并发的事物中，每个事务只能查询到创建版本号小于等于自己事务id的，删除版本号大于等于自己事务id的。
  在`insert`操作时，会递增一个`db_trx_id`值，因为原则上是只能查询到创建版本号小于等于自己事务id的，因此会在其他事务开启后未提交之前，是查询不到当前新增的数据。
  在`delete`操作时，当前事务id会递增到`db_roll_ptr`字段上，因为原则上是查删除版本号大于等于自己事务id的，因此当在某个事务删除记录后，以前开启但未关闭的事务，仍旧会查询到在其他事务已经删除的数据。
  在MySQL中`update`操作相当于`insert`和`delete`两个操作。



#### 2.undo日志

InnoDB把这些为了回滚而记录的这些东西称之为undo log。这里需要注意的一点是，由于查询操作（SELECT）并不会修改任何用户记录，所以在查询操作执行时，并不需要记录相应的undo log。undo log主要分为3种：

- **Insert undo log** ：插入一条记录时，至少要把这条记录的主键值记下来，之后回滚的时候只需要把这个主键值对应的记录删掉就好了。
- **Update undo log**：修改一条记录时，至少要把修改这条记录前的旧值都记录下来，这样之后回滚时再把这条记录更新为旧值就好了。
- **Delete undo log**：删除一条记录时，至少要把这条记录中的内容都记下来，这样之后回滚时再把由这些内容组成的记录插入到表中就好了。
  - 删除操作都只是设置一下老记录的DELETED_BIT，并不真正将过时的记录删除。
  - 为了节省磁盘空间，InnoDB有专门的purge线程来清理DELETED_BIT为true的记录。为了不影响MVCC的正常工作，purge线程自己也维护了一个read view（这个read view相当于系统中最老活跃事务的read view）;如果某个记录的DELETED_BIT为true，并且<font color='red'>**DB_TRX_ID相对于purge线程的read view可见，那么这条记录一定是可以被安全清除的**</font>。



对MVCC有帮助的实质是**update undo log** ，undo log实际上就是存在rollback segment中旧记录链，它的执行流程如下：

1. **比如一个有个事务插入person表插入了一条新记录，记录如下，name为Jerry, age为24岁，隐式主键是1，事务ID和回滚指针，我们假设为NULL**

![](http://mk-images.tagao.top/img/202205031233179.png?imageslim)



2. **现在来了一个事务1对该记录的name做出了修改，改为Tom**
   1. 在事务1修改该行(记录)数据时，数据库会先对该行加排他锁
   2. 然后把该行数据拷贝到undo log中，作为旧记录，既在undo log中有当前行的拷贝副本
   3. 拷贝完毕后，修改该行name为Tom，并且修改隐藏字段的事务ID为当前事务1的ID, 我们默认从1开始，之后递增，回滚指针指向拷贝到undo log的副本记录，既表示我的上一个版本就是它
   4. 事务提交后，释放锁

![](http://mk-images.tagao.top/img/202205031249017.png?imageslim)



3.**又来了个事务2修改person表的同一个记录，将age修改为30岁**

1. 在事务2修改该行数据时，数据库也先为该行加锁
2. 然后把该行数据拷贝到undo log中，作为旧记录，发现该行记录已经有undo log了，那么最新的旧数据作为链表的表头，插在该行记录的undo log最前面
3. 修改该行age为30岁，并且修改隐藏字段的事务ID为当前事务2的ID, 那就是2，回滚指针指向刚刚拷贝到undo log的副本记录
4. 事务提交，释放锁

![](http://mk-images.tagao.top/img/202205031250552.png?imageslim)



从上面，我们就可以看出，不同事务或者相同事务的对同一记录的修改，会导致该记录的<font color='red'>**undo log成为一条记录版本线性表，既链表**</font>，undo log的链首就是最新的旧记录，链尾就是最早的旧记录（当然就像之前说的该undo log的节点可能是会purge线程清除掉，比如图中的第一条insert undo log，其实在事务提交之后可能就被删除丢失了，不过这里为了演示，所以还放在这里）



#### 3.Read View(读视图)

> **生成方式 ：** <font color='red'>**Read View就是事务进行快照读操作的时候生产的读视图(Read View)**</font>，在该事务执行的快照读的那一刻，会生成数据库系统当前的一个快照，记录并维护系统当前活跃事务的ID(当每个事务开启时，都会被分配一个ID, 这个ID是递增的，所以最新的事务，ID值越大)

**作用 ：** <font color='red'>**ReadView是“快照读”SQL执行时MVCC提取数据的依据（快照读，没有加锁的select 语句）**</font>



<font color='red'>**ReadView数据结构**</font>

```markdown
- m_ids:当前活跃的事务编号集合 （事务正在执行，且还没有提交）
-
- min_trx_id:最小活跃事务编号（m_ids 里最小的值）
-
- max_trx_id:预分配事务编号，当前最大事务编号+1
-
- creator_trx_id:ReadView创建者的事务编号
-
```



<font color='red'>**读取条件：**</font> 判断该记录在版本链中的某个版本（trx_id）是否可见：

```markdown
- 1. db_trx_id == creator_trx_id ? 成立说明数据就是自己这个事务更改的，可以访问
-
- 2. db_trx_id <  min_trx_id ? 表明生成该版本的事务在生成ReadView前已经提交，所以该版本可以被当前事务访问。
-
- 3.db_trx_id >= max_trx_id ?  表明生成该版本的事务在生成ReadView 后才生成，所以该版本不可以被当前事务访问。
-
- 4. min_trx_id  < db_trx_id < max_trx_id 
-
- 此处比如m_ids为[5,6,7,9,10]
- ①、若db_trx_id在m_ids中，比如是6，说明创建 ReadView 时生成该版本的事务还是活跃的，该版本不可以被访问。
- ②、若db_trx_id不在m_ids中，比如是8：说明创建 ReadView 时生成该版本的事务已经被提交，该版本可以被访问。
```



**一句话说：当trx_id在m_ids中，或者大于m_ids列表中最大的事务id的时候，这个版本就不能被访问。**

如果某个版本的数据对当前事务不可见的话，<font color='red'>**那就顺着版本链找到下一个版本的数据**</font>，继续按照上边的步骤判断可见性，依此类推，直到版本链中的最后一个版本，如果最后一个版本也不可见的话，那么就意味着该条记录对该事务不可见，查询结果就不包含该记录。

<font color='red'>**当前版本链找不到就顺着版本链找下去**</font>



<font color='red'>**判断条件的源码**</font>

![](http://mk-images.tagao.top/img/202205031709762.png?imageslim)





#### 4.整体流程



![](http://mk-images.tagao.top/img/202205031659483.png?imageslim)



<font color='red'>**事务D中第一个select 语句**</font>

![](http://mk-images.tagao.top/img/202205031659110.png?imageslim)

```markdown
# 版本链数据访问规则:

第一轮 trx_id = 3

- 判断 trx_id == creator_trx_id  3 == 4 ? 不成立，继续找
- 判断 trx_id < min_trx_id   3 < 2 ?        不成立，继续找
- 判断 trx_id >= max_trx_id(5)  3 >= 5 ?    不成立，（还能）继续找，如果成立就不能继续找
- 判断 min_trx_id(2) < trx_id < max_trx_id(5)  {2 3 4}，但是3在m_ids中，不满足继续找，到下一轮

第二轮  trx_id = 2

- 判断 trx_id == creator_trx_id  2 == 4  ? 不成立，继续找
- 判断 trx_id < min_trx_id  		 2 < 2  ?  不成立，继续找
- 判断 trx_id >= max_trx_id(5)     2 > 5  ?  不成立，（还能）继续找，如果成立就不能继续找
- 判断 min_trx_id(2) < trx_id < max_trx_id(5)   {2 3 4}，但是2在m_ids中，不满足继续找，到下一轮

第三轮  trx_id = 1

- 判断 trx_id == creator_trx_id吗  1 == 4 ? 不成立，继续找
- 判断 trx_id < min_trx_id  	     1 < 2 ? 成立，访问

**因此根据ReadView可以读取到1号事务提交的数据，张三**
```



<font color='red'>**事务D中第二个select 语句**</font>

![](http://mk-images.tagao.top/img/202205031724198.png?imageslim)

```markdown
# 版本链数据访问规则:

第一轮 trx_id = 3

- 判断 trx_id == creator_trx_id  3 == 4 ? 不成立，继续找
- 判断 trx_id < min_trx_id   3 < 3 ?        不成立，继续找
- 判断 trx_id >= max_trx_id(5)  3 >= 5 ?    不成立，（还能）继续找，如果成立就不能继续找
- 判断 min_trx_id(2) < trx_id < max_trx_id(5)  {2 3 4}，但是3在m_ids中，不满足继续找，到下一轮

第二轮  trx_id = 2

- 判断 trx_id == creator_trx_id  3 == 4  ? 不成立，继续找
- 判断 trx_id < min_trx_id  		 2 < 3  ?  成立，访问

**因此根据ReadView可以读取到1号事务提交的数据，张小三**
```







#### 5.RC,RR级别下快照读

读已提交（RC）：在每一次执行快照读时生成ReadView

可重复读（RR）：仅在第一次执行快照读时生成ReadView，后续快照读复用（例外：当两次快照读之间存在当前读，ReadView会重新生成，导致产生幻读）



## 2.LBCC 锁



  第一种即读取数据前对其加锁，阻止其他事务对数据进行修改，`Lock Based Concurrency Control`(LBCC)。这种方案导致的问题是，如果有人读取数据，不会允许其他事务进行修改，简单粗暴。

https://dev.mysql.eom/doc/refman/5.7/en/innodb-locking.html







# 7.隔离级别具体实现

![](http://mk-images.tagao.top/img/202204272144052.png?imageslim)





## 1.Read Uncommited

​	RU隔离级别：不加锁。





## 2.Read Commited

​	RC隔离级别下，普通的select都是快照读，使用<font color='red'>**MVCC**</font>实现。

​	<font color='red'>**在每一次执行快照读时生成ReadView**</font>



​	在RC隔离级别下，加锁的select<font color='red'>**都使用记录锁**</font>，因为没有Gap Lock。
​	除了两种特殊情况	外键约束检査(foreign-key constraint checking)以及重复键检査(duplicate-key checking)时会使用<font color='red'>**间隙锁**</font>封锁区间。
所以RC会出现<font color='red'>**幻读**</font>的问题。 



## 3.Repeatable Read

​	RR隔离级别下，普通的select使用快照读(snapshot read),底层使用<font color='red'>**MVCC**</font>来实现。

<font color='red'>**仅在第一次执行快照读时生成ReadView，后续快照读复用。**</font>（例外：当两次快照读之间存在当前读，ReadView会重新生成，导致产生幻读）

​	在默认开启的RR下,手动加锁的 select(select ... in share mode / select ... for update)以及更新操作update, delete等语句使用<font color='red'>**当前读(current read)**</font>, 底层<font color='red'>**使用记录锁、或者间隙锁、临键锁。**</font>



**innodb在默认的隔离级别RR下，采用next-key + MVCC去解决幻读问题的：**

**在查询加for update时，会用next-key + MVCC解决幻读问题，新的insert和update会阻塞**
**在查询不加for update时，会用MVCC解决幻读问题，新的insert和update不会阻塞**



**MySQL的可重复读级别能解决幻读吗？**

```markdown
解答一：连续多次快照读，ReadView会产生复用，没有幻读问题
特例：当两次快照读之间存在当前读，ReadView会重新生成，导致产生幻读

解答二：可以解决在快照读下的幻读问题，但是不能解决当前读下的幻读问题。
因为快照读下只会生成一次ReadView， 但是如果事务过程存在当前读就会重新生成ReadView。
```

下图 事务B因为存在当前读，所以更新生成ReadView，新生成的 ReadView读取到事务A提交的事务，导致出现幻读的问题(读取到事务A insert的数据)。

![](http://mk-images.tagao.top/img/202205031612696.png?imageslim)









## 4.Serializable

​	Serializable 所有的select语句都会被隐式的转化为 select... in share mode,会和 update、delete 互斥。









# 8.LOCK TABLES

<font color='red'>**LOCK TABLES 和 UNLOCK TABLES**</font>



MySQL也支持Lock Tables和Unlock Tables，这都是在<font color='red'>**服务器层（MySQL Server层）**</font>实现的，和<font color='red'>**存储引擎无关**</font>，它们有自己的用途，并不能替代事务处理。 （除了禁用了auto commit 后可以使用，其他情况不建议使用）：

- <font color='red'>**LOCK TABLES **</font>可以锁定用于<font color='red'>**当前线程**</font>的表。如果表被其他线程锁定，则当前线程会等待，直到可以获取所有锁定为止。 
- <font color='red'>**UNLOCK TABLES**</font> 可以释放当前线程获得的任何锁定。当前线程执行另一个 LOCK TABLES 时， 或当与服务器的连接被关闭时，所有由当前线程锁定的表被隐含地解锁



**LOCK TABLES语法：**

- 在用 LOCK TABLES 对 InnoDB 表加锁时要注意，要将 AUTOCOMMIT 设为 0，否则MySQL 不会给表加锁；
- <font color='red'>**事务结束前，不要用 UNLOCK TABLES 释放表锁**</font>，因为<font color='red'>**UNLOCK TABLES会隐含地提交事务**</font>；
- COMMIT 或 ROLLBACK 并不能释放用 LOCK TABLES 加的表级锁，必须用UNLOCK TABLES 释放表锁。

正确的方式见如下语句： 例如，如果需要 对表 t1进行写， 对表 t2 进行读，可以按如下做：

![](http://mk-images.tagao.top/img/202204290017810.png?imageslim)

**使用LOCK TABLES的场景：**

给表<font color='red'>**显示加表级锁**</font>（InnoDB表和MyISAM都可以），一般是为了在一定程度模拟事务操作，<font color='red'>**实现对某一时间点多个表的一致性读取。**</font>（与MyISAM默认的表锁行为类似）

在用 LOCK TABLES 给表显式加表锁时，必须同时取得所有涉及到表的锁，并且 MySQL 不支持锁升级。也就是说，在执行 LOCK TABLES 后，只能访问显式加锁的这些表，不能访问未加锁的表；同时，如果加的是读锁，那么只能执行查询操作，而不能执行更新操作。

其实，在MyISAM<font color='red'>**自动加锁（表锁）**</font>的情况下也大致如此，<font color='red'>**MyISAM 总是一次获得 SQL 语句所需要的全部锁，这也正是 MyISAM 表不会出现死锁（Deadlock Free）的原因。**</font>

例如，有一个订单表 orders，其中记录有各订单的总金额 total，同时还有一个 订单明细表 order_detail，其中记录有各订单每一产品的金额小计 subtotal，假设我们需要检 查这两个表的金额合计是否相符，可能就需要执行如下两条 SQL： 

![](http://mk-images.tagao.top/img/202204290023363.png?imageslim)

这时，如果不先给两个表加锁，就可能产生错误的结果，因为第一条语句执行过程中， order_detail 表可能已经发生了改变。因此，正确的方法应该是： 

![](http://mk-images.tagao.top/img/202204290023763.png?imageslim)

（在 LOCK TABLES 时加了“local”选项，其作用就是允许当你持有表的读锁时，其他用户可以在满足 **MyISAM 表并发插入**条件的情况下，在表尾并发插入记录（MyISAM 存储引擎支持“并发插入”））



# 参考资料



[**Mysql的行锁、表锁、间隙锁、意向锁**](http://pomit.cn/tr/3718605322652161)

[**CS-Notes**](https://www.cyc2018.xyz/%E6%95%B0%E6%8D%AE%E5%BA%93/%E6%95%B0%E6%8D%AE%E5%BA%93%E7%B3%BB%E7%BB%9F%E5%8E%9F%E7%90%86.html#acid)

[**MySQL锁总结**](https://mp.weixin.qq.com/s/sSayb346bs7-5IIWTEgV6w?)

[**MVCC**](https://pdai.tech/md/db/sql-mysql/sql-mysql-mvcc.html#%E5%89%8D%E6%8F%90%E6%A6%82%E8%A6%81)