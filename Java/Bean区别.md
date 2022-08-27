VO、PO、BO、Pojo、Entity





Java Bean：一种可重用组件，即“一次编写，任何地方执行，任何地方重用”。满足三个条件

- 类必须是具体的和公共的
- 具有无参构造器
- 提供一致性设计模式的公共方法将内部域或暴露成员属性





#### VO

`value object`：值对象 
通常用于业务层之间的数据传递，由new创建，由GC回收 
和PO一样也是仅仅包含数据而已，但应是抽象出的业务对象，可以和表对应，也可以不是。



#### PO

`persistant object`：持久层对象是ORM(Objevt Relational Mapping)框架中Entity，PO属性和数据库中表的字段形成一一对应关系 。
VO和PO，都是属性加上属性的get和set方法；表面看没什么不同，但代表的含义是完全不同的。





#### DTO

`data transfer object`：数据传输对象是一种设计模式之间传输数据的软件应用系统，数据传输目标往往是数据访问对象从数据库中检索数据 。
数据传输对象与数据交互对象或数据访问对象之间的差异是一个以不具任何行为除了存储和检索的数据（访问和存取器） 简而言之，就是接口之间传递的数据封装 
表里面有十几个字段： id，name，gender（M/F)，age…… 
页面需要展示三个字段：    name，gender(男/女)，age 
DTO由此产生，一是能提高数据传输的速度(减少了传输字段)，二能隐藏后端表结构 

![image-20210805133722678](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210805133722678.png)





#### BO

`business object`：业务对象 
BO把业务逻辑封转为一个对象，通过调用DAO方法，结合PO或VO进行业务操作 
PO组合，如投保人是一个PO，被保险人是一个PO，险种信息是一个PO等等，他们组合气来是第一张保单的BO

#### POJO

`plian ordinary java object`：简单无规则java对象纯的传统意义的java对象，最基本的Java Bean只有属性加上属性的get和set方法可以额转化为PO、DTO、VO；比如POJO在传输过程中就是DTO

#### DAO

`data access object`：数据访问对象是sun的一个标准j2ee设计模式，这个模式中有个接口就是DAO，负责持久层的操作主要用来封装对数据的访问，注意，是对数据的访问，不是对数据库的访问 



#### Entity

实体，和PO的功能类似，和数据表一一对应，一个实体一张表



------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



pojo,javabean,ejb的概念很好理解,就是复杂程度逐渐上升而已.

domain是个比较虚的概念,只要是一个范围概念,都可以用domian来括住

vo,dto,po,entity的内容容量逐渐上升,vo是界面显示的数据,dto是从po中抽取的数据,po是数据库中的记录,entity是对象对应数据库中的记录

dao对象是数据库操作的集合

bo综合多个po对应一个对象,比如一个简历，有教育经历、工作经历、 关系等等。教育经历对应一个po,工作经历对应一个po,关系对应一个po,

这三个综合起来就是一个简历,对应一个bo,





| 类型         | 定义                                                         | 作用                                                         |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| **pojo**     | （Plain Ordinary Java Object）简单的Java对象                 | 普通JavaBeans,有时可以作为VO（value-object）或DTO（Data Transfer Object）来使用 |
| **javabean** | 为写成JavaBean，类必须是具体和公共的，并且具有无参数的构造器。 | 简单的就是pojo,复杂的就是ejb                                 |
| **ejb**      | EJB(Enterprise JavaBean)，定义用于开发基于组件的企业多重应用程序标准 | java的核心代码,分别是回话Bean（Session Bean）、实体Bean（Entity Bean）、和消息驱动Bean（MessageDriven Bean） |
| **domain**   | domain是范围,界线,也被用来批一个变量的有效作用域             | 也常用这个词做一个包,然后在里面放些DAO类文件.  net.baidu.domain.AddUserDao |
| **dto**      | 数据传输对象(Data Transfer Object),是一种设计模式之间传输数据的对象 | 数据传输目标往往是数据访问对象从数据库中检索数据，从PO中取值进行传输 |
| **entity**   | 实体bean，一般是用于ORM对象关系映射                          | 一个实体映射成一张表，一般无业务逻辑代码。                   |
| **po**       | persistant object持久对象                                    | 最形象的理解就是一个PO就是数据库中的一条记录,如hibernate中的一个持久化对象 |
| **vo**       | ViewObject表现层对象                                         | 主要对应界面显示的数据对象。对于一个WEB页面，用一个VO对象对应整个界面的值。 |
| **dao**      | data access object数据访问对象                               | 主要用来封装对数据库的访问，包含了各种数据库的操作方法。通过它可以把POJO持久化为PO，用PO组装出来VO、DTO |
| **bo**       | business object业务对象                                      | 主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象。 |





https://www.cnblogs.com/difs/p/8903446.html