# 结构型模式

## （Structural Patterns）







# 1.代理设计模式

<font color='red'>**代理是一种常用的设计模式，代理模式可以对原有的类进行扩展，即通过代理对象的模式来访问目标类。**</font>

最通俗的例子就是假设我们想邀请一位明星,那么并不是直接连接明星,而是联系明星的经纪人,来达到同样的目的.

明星就是一个目标对象,他只要负责活动中的节目,而其他琐碎的事情就交给他的代理人(经纪人)来解决.

这就是代理思想在现实中的一个例子。

## 1.1静态代理



<font color='red'>**接口**</font>

```java
public interface ClothFactory {
    void produceCloth();
}
```



<font color='red'>**被代理类： 实现接口**</font>

```java
public class NikeClothFactory implements ClothFactory{
    @Override
    public void produceCloth() {
        System.out.println("Nike工厂生产一批运动服");
    }
}
```



<font color='red'>**代理类：需要提过构造方法，传入被代理类，调用被代理类的方法**</font>

```java
public class ProxyClothFactory implements ClothFactory{
    ClothFactory obj;

    /**
     * @date    用被代理类对象进行实例化
     */
    public ProxyClothFactory(ClothFactory obj) {
            this.obj = obj;
    }
    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作");
        obj.produceCloth();
        System.out.println("代理工厂做一些后续的收尾工作");
    }
}
```



<font color='red'>**静态代理测试**</font>

```java
public static void main(String[] args) {
    //1.创建被代理类
    NikeClothFactory clothFactory = new NikeClothFactory();
    //2.创建代理类
    ProxyClothFactory proxyClothFactory = new ProxyClothFactory(clothFactory);
    //3.通过被代理类执行代理类的方法
    proxyClothFactory.produceCloth();
}
```





## 1.2动态代理





### 1.2.1JDK动态代理

#### 1. 介绍

​	从静态代理中可以看出: 静态代理只能代理一个具体的类，如果要代理一个接口的多个实现的话需要定义不同的代理类。

需要解决这个问题就可以用到 JDK 的动态代理。

其中有两个非常核心的类:

- <font color='red'>**`java.lang.reflect.Proxy`类。**</font>
  * `Proxy` 类中使用频率最高的方法是：`newProxyInstance()` ，这个方法主要用来生成一个代理对象。
- <font color='red'>**`java.lang.reflect.InvocationHandle`接口。**</font>
  * `InvocationHandler` 接口主要自定义处理逻辑。当我们的动态代理对象调用一个方法时，这个方法的调用就会被转发到实现`InvocationHandler` 接口类的 `invoke` 方法来调用。



#### 2.  JDK 使用步骤

1. 定义一个接口及其实现类；
2. 自定义 `InvocationHandler` 并重写`invoke`方法，在 `invoke` 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；
3. 通过 `Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)` 方法创建代理对象；



#### 3.代码示例

<font color='red'>**1.接口**</font>

```java
public interface ISubject {
    void execute();
}
```



<font color='red'>**2.被代理类**</font>

```java
public class ISubjectImpl implements ISubject{
    @Override
    public void execute() {
        System.out.println("execute ISubjectImpl");
    }
}
```





**3.实现接口InvocationHandler 重写invoke方法，处理执行代码逻辑**

```java
public class CustomizeHandle implements InvocationHandler {
    private Object tager;

    public CustomizeHandle(Object tager) {
        this.tager = tager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        System.out.println("proxy class :" + proxy.getClass());
        System.out.println("arg : "+ Arrays.toString(args));
        System.out.println("method : "+method.getName());

        Object result = method.invoke(tager, args);
        after();
        return result;
    }

    private void before() {
        System.out.println("代理类 前期工作 handle before");
    }

    private void after() {
        System.out.println("代理类 后期工作 handler after");
    }
}
```



**4.首先传入被代理类的类类型构建代理处理器。接着使用 `Proxy` 的`newProxyInstance` 方法动态创建代理类。第一个参数为类加载器，第二个参数为代理类需要实现的接口列表，最后一个则是处理器。**

```java
public static void main(String[] args) {
    //1.通过反射生成 被代理类的类加载器（Use by: 3-创建代理类的对象）
    ClassLoader classLoader = ISubject.class.getClassLoader();

    //2.生成能处理执行的逻辑的类
    CustomizeHandle customizeHandle = new CustomizeHandle(new ISubjectImpl());
    //3.创建代理类的对象
    ISubject iSubject = (ISubject)Proxy.newProxyInstance(classLoader, new Class[]{ISubject.class}, customizeHandle);
    //4.执行被代理类的方法
    iSubject.execute();
}
```





#### 4.工厂提取



**代理对象的工厂类**

```java
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new DebugInvocationHandler(target)   // 代理对象对应的自定义 InvocationHandler
        );
    }
}
```



### 1.2.2CGLIB动态代理



#### 1. 介绍

**JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。**

**为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。**

CGLI是一个基于 <font color='red'>**ASM **</font>的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。CGLIB 通过继承方式实现代理。很多知名的开源框架都使用到了[CGLIBopen in new window](https://github.com/cglib/cglib)， 例如 Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。



<font color='red'>**其中有两个非常核心的类:**</font>

* <font color='red'>**`MethodInterceptor` 接口**</font>
* <font color='red'>**`Enhancer` 类是核心。**</font>

需要自定义 `MethodInterceptor` 并重写 `intercept` 方法，`intercept` 用于拦截增强被代理类的方法。



```java
public interface MethodInterceptor extends Callback{
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                               MethodProxy proxy) throws Throwable;
}
```

1. **obj** :被代理的对象（需要增强的对象）
2. **method** :被拦截的方法（需要增强的方法）
3. **args** :方法入参
4. **proxy** :用于调用原始方法

可以通过 `Enhancer`类来动态获取被代理类，当代理类调用方法的时候，实际调用的是 `MethodInterceptor` 中的 `intercept` 方法。



#### 2. CGLIB 使用步骤

1. 定义一个类；
2. 自定义 `MethodInterceptor` 并重写 `intercept` 方法，`intercept` 用于拦截增强被代理类的方法，和 JDK 动态代理中的 `invoke` 方法类似；
3. 通过 `Enhancer` 类的 `create()`创建代理类；





#### 3.代码示例



不同于 JDK 动态代理不需要额外的依赖。CGLIB实际是属于一个开源项目，如果你要使用它的话，需要手动添加相关依赖。

```xml
<dependency>
  <groupId>cglib</groupId>
  <artifactId>cglib</artifactId>
  <version>3.3.0</version>
</dependency>
```



**1.实现一个使用阿里云发送短信的类**

```java
public class SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
```







**2.自定义 `MethodInterceptor`（方法拦截器）**

```java
public class DebugMethodInterceptor implements MethodInterceptor {

    /**
     * @param obj           代理对象（增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param args        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method " + method.getName());
        Object object = methodProxy.invokeSuper(obj, args);
        System.out.println("after method " + method.getName());
        return object;
    }

}
```





**3.创建代理类，调用代理类的方法**

```java
public static void main(String[] args) {
    Class clazz = SmsService.class;

    //1.创建动态代理增强类
    Enhancer enhancer = new Enhancer();
    //2.设置类加载器
    enhancer.setClassLoader(clazz.getClassLoader());
    //3.设置被代理类
    enhancer.setSuperclass(clazz);
    //4.设置方法拦截
    enhancer.setCallback(new DebugMethodInterceptor());

    SmsService smsService = (SmsService)enhancer.create();
    smsService.send("你好，中国");
}
```



#### 4.工厂提取



**代理对象的工厂类**

```java
public class CglibProxyFactory {

    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}
```





### 1.2.3JDK 和 CGLIB 对比

1. **JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。** 另外， CGLIB 动态代理是<font color='red'>**通过生成一个被代理类的子类来拦截被代理类的方法调用**</font>，因此不能代理声明为 final 类型的类和方法。
2. 就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。





## 1.3. 静态代理和动态代理的对比

1. **灵活性** ：动态代理更加灵活，不需要必须实现接口，可以直接代理实现类，并且可以不需要针对每个目标类都创建一个代理类。另外，静态代理中，接口一旦新增加方法，目标对象和代理对象都要进行修改，这是非常麻烦的！
2. **JVM 层面** ：静态代理在编译时就将接口、实现类、代理类这些都变成了一个个实际的 class 文件。而动态代理是在运行时动态生成类字节码，并加载到 JVM 中的。









# 2.适配器模式

## 2.1定义

适配器  Adapter Pattern（Wrapper，包装器）

<font color='red'>**将一个接口转换成客户希望的另一个接口**</font>，适配器模式使接口不兼容的那些类可以一起工作，适配器模式分为类结构型模式（继承）和对象结构型模式（组合）两种，前者（继承）类之间的耦合度比后者高，且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。



适配器模式（Adapter）包含以下主要角色。

* 目标（Target）接口：可以是抽象类或接口。客户希望直接用的接口
* 适配者（Adaptee）类：隐藏的转换接口
* 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口。



## 2.2类图







# 3.桥接模式





## 源码应用

## JDBC

```java
try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "";
            String user = "";
            String password = "";
            Connection con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
}
```



```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    // Register ourselves with the DriverManager
    //
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
}
```







![image-20220731214846596](http://mk-images.tagao.top/img/202207312148685.png?imageslim)





![image-20220731215601026](http://mk-images.tagao.top/img/202207312156127.png?imageslim)