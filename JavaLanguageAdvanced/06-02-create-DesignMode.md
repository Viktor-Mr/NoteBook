# 创建型模式

## （Creational Patterns）



# 1.单例模式

## 定义

​	确保一个类只有一个实例，并提供该实例的全局访问点。

## 特点

单例模式有 3 个特点：

1. 单例类只有一个实例对象；
2. 该单例对象必须由单例类自行创建；
3. 单例类对外提供一个访问该单例的全局访问点。



单例模式的优点：

- 单例模式可以保证内存里只有一个实例，减少了内存的开销。
- 可以避免对资源的多重占用。
- 单例模式设置全局访问点，可以优化和共享资源的访问。


单例模式的缺点：

- 单例模式一般没有接口，扩展困难。如果要扩展，则除了修改原来的代码，没有第二种途径，违背开闭原则。
- 在并发测试中，单例模式不利于代码调试。在调试过程中，如果单例中的代码没有执行完，也不能模拟生成一个新的对象。
- 单例模式的功能代码通常写在一个类中，如果功能设计不合理，则很容易违背单一职责原则。

> 单例模式看起来非常简单，实现起来也非常简单。单例模式在面试中是一个高频面试题。希望大家能够认真学习，掌握单例模式，提升核心竞争力，给面试加分，顺利拿到 Offer。



## 懒汉式

### Ⅰ懒汉式-线程不安全

​	以下实现中，私有静态变量  s1 被延迟实例化，这样做的好处是，如果没有用到该类，那么就不会实例化 s1，从而节约资源。

​	这个实现在多线程环境下是不安全的，如果多个线程能够同时进入 `if (s1 == null)` ，并且此时 s1 为 null，那么会有多个线程执行 `s1= new Singleton();` 语句，这将导致实例化多次 uniqueInstance。

```java
public class Singleton1 {
    private Singleton1() {
    }

    private  static Singleton1 s1;

    public static Singleton1 getInstance(){
        if(s1 == null){
            s1 = new Singleton1();
        }
       return  s1;
    }
}
```

### Ⅱ懒汉式-线程安全

只需要对 getInstance () 方法加锁，那么在一个时间点只能有一个线程能够进入该方法，从而避免了实例化多次 s2。但是当一个线程进入该方法之后，其它试图进入该方法的线程都必须等待，即使 s2已经被实例化了。这会让线程阻塞时间过长，因此该方法有性能问题，不推荐使用。



```java
public class Singleton2 {
    private  Singleton2(){
    }

    private volatile  static  Singleton2 s2;

    public  static synchronized  Singleton2  getInstance(){
        if (s2 == null) {
            s2 = new Singleton2();
        }
        return s2;
    }
}
```



### Ⅲ双重校验锁-线程安全

s3只需要被实例化一次，之后就可以直接使用了。加锁操作只需要对实例化那部分的代码进行，只有当 s3没有被实例化时，才需要进行加锁。

双重校验锁先判断 s3是否已经被实例化，如果没有被实例化，那么才对实例化语句进行加锁。

```java
public class Singleton3 {
    private Singleton3() {
    }

    private static volatile Singleton3 S3;


    public static Singleton3 getInstance() {
        if (S3 == null) {
            synchronized (Singleton3.class){
                if (S3 == null){
                    S3 = new Singleton3();
                }
            }
        }
        return S3;
    }
}

```

​	考虑下面的实现，也就是只使用了一个 if 语句。在 s3 == null 的情况下，如果两个线程都执行了 if 语句，那么两个线程都会进入 if 语句块内。虽然在 if 语句块内有加锁操作，但是两个线程都会执行 `s3= new Singleton();` 这条语句，只是先后的问题，那么就会进行两次实例化。因此必须使用双重校验锁，也就是需要使用两个 if 语句：第一个 if 语句用来避免 s3 已经被实例化之后的加锁操作，而第二个 if 语句进行了加锁，所以只能有一个线程进入，就不会出现 s3 == null 时两个线程同时进行实例化操作。

```java
synchronized (Singleton3.class){
    if (S3 == null){
        S3 = new Singleton3();
    }
}
```



s3 采用 volatile 关键字修饰也是很有必要的， `s3= new Singleton();` 这段代码其实是分为三步执行：

1. 为 s3分配内存空间
2. 初始化 s3
3. 将 s3 指向分配的内存地址

但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1>3>2。指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T1 执行了 1 和 3，此时 T2 调用 getInstance() 后发现 s3不为空，因此返回 s3，但此时 s3还未被初始化。

使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。



### Ⅳ静态内部类实现

当 Singleton 类被加载时，静态内部类 SingletonHolder 没有被加载进内存。只有当调用 `getUniqueInstance()` 方法从而触发 `SingletonHolder.INSTANCE` 时 SingletonHolder 才会被加载，此时初始化 INSTANCE 实例，并且 JVM 能确保 INSTANCE 只被实例化一次。

这种方式不仅具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持。

```java
public class Singleton5 {
    private  Singleton5(){}

    private  static class SingletonHolder{
        private static final Singleton5 S5 = new Singleton5();
    }

    public static Singleton5 getInstance(){
        return  SingletonHolder.S5;
    }
}

```







## 懒汉式



### Ⅴ饿汉式-线程安全

线程不安全问题主要是由于 s4 被实例化多次，采取直接实例化 s4的方式就不会产生线程不安全问题。

但是直接实例化的方式也丢失了延迟实例化带来的节约资源的好处。

```java
public class Singleton4 {
    private  Singleton4(){}

    private  static  Singleton4 singleton4 = new Singleton4();

    public   static  Singleton4 getInstance(){
        return singleton4;
    }
}
```







## PS参考

[CN-NOTES](https://www.cyc2018.xyz/%E5%85%B6%E5%AE%83/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20%20-%20%E5%8D%95%E4%BE%8B.html#%E5%8D%95%E4%BE%8B-singleton)

[剑指Offer（第二版）]()



# 2.工厂模式





## 结构

![](http://mk-images.tagao.top/img/202207252037906.png?imageslim)



1. **产品**（Product）将会对接口进行声明。对于所有由创建者及其子类构建的对象，这些接口都是通用的。
2. **具体产品**（Concrete Products）是产品接口的不同实现。

3. **创建者| 工厂**（Creator）类声明返回产品对象的工厂方法。该方法 的返回对象类型必须与产品接口相匹配。 

   你可以将工厂方法声明为抽象方法，强制要求每个子类以不同方式实现该方法。或者，你也可以在基础工厂方法中返回默认产品类型。

   ​	注意，尽管它的名字是创建者，但他最主要的职责并**不是**创建产品。一般来说，创建者类包含一些与产品相关的核心业务逻辑。工厂方法将这些逻辑处理从具体产品类中分离出来。打个比方，大型软件开发公司拥有程序员培训部门。但是，这些公司的主要工作还是编写代码，而非生产程序员。

4. **具体创建者 | 具体工厂**（Concrete Creators） 将会重写基础工厂方法，使其返回不同类型的产品。注意，并不一定每次调用工厂方法都会**创建**新的实例。工厂方法也可以返回缓存、对象池或其他来源的已有对象。





## 案例





![](http://mk-images.tagao.top/img/202207252057046.png?imageslim)

![](http://mk-images.tagao.top/img/202207252107102.png?imageslim)

### 抽象产品

```java
public interface ButtonProduct {
    void render();
    void onClick();
}
```



### 具体产品

```java
public class HtmlButton implements ButtonProduct{
    @Override
    public void render() {
        System.out.println("HtmlButtonRender");
    }

    @Override
    public void onClick() {
        System.out.println("HtmlButtonOnclick");
    }
}
```

```java
public class WindowsButton implements ButtonProduct {
    @Override
    public void render() {
        System.out.println("windowsRender");
    }

    @Override
    public void onClick() {
        System.out.println("windowsOnClick");
    }
}
```



### 抽象工厂

```java
public interface AbstractFactory {
    public abstract ButtonProduct buttonProduct();
}
```

### 具体工厂

```java
public class HtmlConcreteFactory implements AbstractFactory{
    @Override
    public ButtonProduct buttonProduct() {
        return new HtmlButton();
    }
}
```

```java
public class WindowsConcreteFactory implements AbstractFactory{
    @Override
    public ButtonProduct buttonProduct() {
        return new WindowsButton();
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        //1.创建工厂
        AbstractFactory window = new WindowsConcreteFactory();

        //2.从工厂得到产品
        ButtonProduct buttonProduct = window.buttonProduct();
        buttonProduct.onClick();
        buttonProduct.render();
    }
}

```







# 3.抽象工厂

## 结构

![image-20220725221348968](http://mk-images.tagao.top/img/202207252213031.png?imageslim)

1. **抽象产品**（Abstract Product）为构成系列产品的一组不同但相关的产品声明接口。
2. **具体产品**（Concrete Product）是抽象产品的多种不同类型实现。所有变体（维多利亚/现代）都必须实现相应的抽象产品（椅子/沙发）。
3. **抽象工厂**（Abstract Factory）接口声明了一组创建各种抽象产品的方法。
4. **具体工厂**（Concrete Factory）实现抽象工厂的构建方法。每个具体工厂都对应特定产品变体，且仅创建此种产品变体。
5. 尽管具体工厂会对具体产品进行初始化，其构建方法签名必须返回相应的抽象产品。这样，使用工厂类的客户端代码就不会与工厂创建的特定产品变体耦合。**客户端**（Client）只需通过抽象接口调用工厂和产品对象，就能与任何具体工厂/产品变体交互。



## 案例

![image-20220725221308333](http://mk-images.tagao.top/img/202207252213406.png?imageslim)

![image-20220725223352037](http://mk-images.tagao.top/img/202207252233094.png?imageslim)





