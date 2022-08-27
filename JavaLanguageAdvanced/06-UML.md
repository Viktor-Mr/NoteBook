

# UML

1) UML——Unified modeling language UML(**统一建模语言**)，是一种用于软件系统分析和设计的语言工具，它用于帮助软件开发人员进行思考和记录思路的结果

2) UML本身是一套符号的规定，就像数学符号和化学符号一样，这些符号用于描述软件模型中的各个元素和他们之间的关系，比如类图中有类、接口、实现、泛化、依赖、组合、聚合等

3) 使用UML来建模，常用的工具有 Rational Rose , 也可以使用一些插件来建模

# 类型

在UML系统开发中有三个主要的模型：

## 功能模型

从用户的角度展示系统的功能，包括[用例图](https://baike.baidu.com/item/用例图)。

## 对象模型

采用对象、属性、操作、关联等概念展示系统的结构和基础，包括[类图](https://baike.baidu.com/item/类图)、对象图、包图。

## 动态模型

展现系统的内部行为。 包括序列图、[活动图](https://baike.baidu.com/item/活动图)、[状态图](https://baike.baidu.com/item/状态图)。





# 1.类图

## 1.1定义

​	类图描述类及类与类之间的静态关系。<font color='red'>**类图是一种静态模型，它是创建其他UML图的基础。**</font>一个系统可以由多张类图来描述，一个类也可以出现在几张类图中。

​	UML中类的图形符号为长方形，分为三个区域为<font color='red'>**类名、属性、操作**</font>。

![](http://mk-images.tagao.top/img/202206261150589.png?imageslim)

## 1.2关系

类图由类及类与类之间的关系组成 。<font color='red'>**依赖、泛化（继承类）、实现、关联、聚合与组合**</font>

![](http://mk-images.tagao.top/img/202206261205339.png?imageslim)

### 2.1依赖

只要是在**类中用到了对方**，那么他们之间就存在依赖关系。如果没有对方，连编绎都通过不了。

![](http://mk-images.tagao.top/img/202206261226839.png?imageslim)

```java
public class PersonDao {
}
public class IDCard {
}
public class Person {
}
public class Department {
}
```

```java
public class PersonServiceBean {
    
	private PersonDao personDao;
    
	public void save(Person person) {
	}
    
	public IDCard getIDCard(Integer personid) {
		return null;
	}
    
	public void modify() {
		Department department = new Department();
	}
}
```

![](http://mk-images.tagao.top/img/202206261223615.png?imageslim)

​	

当出现以上几种类型的关联关系时（只要用到对方），我们均可将它称之为依赖关系。在我们无法具体明确类与类之间的联系时（真实类所代表的含义之间的联系），说它们是依赖准没错的。**后文出现的关联关系可以看作是依赖关系的强化版本。**



**小结**

**1)** **类中用到了对方**

**2)** **类的成员属性**

**3)** **方法的返回类型**

**4)** **方法接收的参数类型**

**5)** **方法中使用到**



### 2.2泛化

泛化关系实际上就是继承关系，是**依赖关系的特例**

![](http://mk-images.tagao.top/img/202206261233385.png?imageslim)

```java
public class DaoSupport {
    public void save(Object entity){
    }
    public void delete(Object id){
    }
}

```

```java
public class PersonServiceBean extends DaoSupport{
}
```



![](http://mk-images.tagao.top/img/202206261232602.png?imageslim)

小结:

1) 泛化关系实际上就是继承关系

2) 如果A类继承了B类，我们就说A和B存在泛化关系



### 2.3实现

实现关系实际上就是A类实现B接口，是**依赖关系的特例**

在代码层面为一个类实现某个接口，使用关键字implements



![](http://mk-images.tagao.top/img/202206261233491.png?imageslim)

```java
public interface PersonService {
    public void delete(Integer id);
}
```

```java
public class PersonServiceBean implements PersonService{
    @Override
    public void delete(Integer id) {
    }
}
```

![](http://mk-images.tagao.top/img/202206261236956.png?imageslim)



总结：

1. 实现关系实际上就是Java中接口的实现
2. 如果 A 类实现了了 B 接口，我们就说 A 和 B 存在实现关系





### 2.4关联

关联关系实际上就是<font color='red'>**类与类之间的联系，他是依赖关系的特例**</font>。<font color='red'>**这种关系比依赖更强**</font>、不存在依赖关系的偶然性、关系也不是临时性的，一般是长期性的，而且双方的关系一般是平等的。如人和身份证是一种长期关系。

<font color='red'>**关联具有导航性：**</font>即双向关系或单向关系，表现在代码层面，为被关联类B以类属性的形式出现在关联类A中，也可能是关联类A引用了一个类型为被关联类B的全局变量

<font color='red'>**关系具有多重性：**</font>如“1”（表示有且仅有一个），“0...” 表示0个或者多个，“0，1”表示0个或者一个，“n...m” 表示n到 m个都可以 ,“m...*” 表示至少m个。

![](http://mk-images.tagao.top/img/202206261314930.png?imageslim)





单向一对一关系：

```java
public class IDCard {}

public class Person {	
    private IDCard idCard;
}
```

![](http://mk-images.tagao.top/img/202206261316079.png?imageslim)

双向一对一关系：

```java
public class IDCard {	
    private Person person;
}
```

```java
public class Person {	
    private IDCard idCard;
}
```

![](http://mk-images.tagao.top/img/202206261319959.png?imageslim)

注意：

​	理论上这里是关联关系，但是IDEA生成的关系包含组合关系（实心菱形箭头）。 相比起用关联关系描述类图，组合关系更加能说明类图的关系。因为组合关系是关联关系的特例。




### 2.5聚合

聚合关系（Aggregation）表示的是**整体和部分**的关系，整体与部分可以分开。聚合关系是关联关系的特例，所以他具有关联的导航性与多重性。

如：一台电脑由键盘(keyboard)、显示器(monitor)，鼠标等组成；组成电脑的各个

配件是可以从电脑上分离出来的，使用带空心菱形的实线来表示：

![](http://mk-images.tagao.top/img/202206261314500.png?imageslim)



```java
public class Mouse {}
```

```java
public class Monitor {}
```

```java
public class Computer {
    private Mouse mouse;
    private Monitor monitor;

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
}

```

<font color='red'>**类关系图：（应将实心菱形换为空心菱形）IDEA的问题**</font>

![](http://mk-images.tagao.top/img/202206261331051.png?imageslim)



### 2.6组合 

组合关系：<font color='red'>**也是整体与部分的关系，但是整体与部分不可以分开**</font>。 

在程序中我们定义实体：Person与IDCard、Head, 那么 Head 和Person 就是 组合，IDCard 和 Person 就是聚合。

![](http://mk-images.tagao.top/img/202206261314154.png?imageslim)



```java
public class Head {}
```

```java
public class IDCard {}
```

```java
public class Person {
    private IDCard card;
    private Head head = new Head();
}
```

![](http://mk-images.tagao.top/img/202206261334575.png?imageslim)

补充：

​	由代码可得 Head 和 Person 就是组合，IDCard 和 Person 是聚合。但是能否一定说Person和IDCard没有组合关系呢（本例中已有信息所展示的是聚合），其实是不能的。如果在我们的程序中Person实体类中定义了对IDCard进行级联删除，即删除Person时连同IDCard一起删除，那么我们就可以认为IDCard和Person就是组合关系。





## 1.3总结

**泛化**和**实现**在代码层面可以根据关键字（extends、implements）进行区分，只要记住这个点一般不会和其他四个搞混淆。

依赖、关联、聚合、组合，它们由弱到强关系是: **依赖 < 关联 < 聚合 < 组合。**

