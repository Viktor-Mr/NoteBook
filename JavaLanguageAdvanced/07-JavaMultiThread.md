# MultiThread



![](http://mk-images.tagao.top/img/202203281946233.png?imageslim)



操作系统通常提供两种机制实现<font color='red'>**多任务的同时执行**</font>:多线程和多进程。

​	多线程的应用主要分为两个方面:提高运算速度、缩短响应时间。

​	对于计算量比较大的任务，可以把任务分解成多个可以并行运算的小任务，每个小任务由一个线程执行运算，以提高运算速度。

​		在Java中，创建线程有四种种方式，**一种是实现Runnable接口，另外一种是继承Thread类，Callable接口(了解)，以及线程池** 。线程是驱动任务运行的载体，在Java中，要执行的任务定义在run()方法中，线程启动后将执行run()方法，方法执行完后任务就执行完成。



# 1.基本概念：程序、进程、线程

* **程序(program)**是为完成特定任务、用某种语言编写的一组指令的集合。<font color='red'>**即指一段静态的代码，静态对象。**</font>

* **进程(process)**是程序的一次执行过程，或是正在运行的一个程序。是一个动态的过程：有它自身的产生、存在和消亡的过程。——生命周期

  * 如：运行中的QQ，运行中的MP3播放器

  * 程序是静态的，进程是动态的

  * <font color='red'>**进程作为资源分配的单位**</font>，系统在运行时会为每个进程分配不同的内存区域

* **线程(thread)**，进程可进一步细化为线程，是一个程序内部的一条执行路径。

  * 若一个进程同一时间**并行**执行多个线程，就是支持多线程的

  * <font color='red'>**线程作为调度和执行的单位，每个线程拥有独立的运行栈和程序计数器(pc)，它被包含在进程之中，是进程中的实际运作单位。**</font>线程切换的开销小。
  * 线程(thread)是操作系统能够进行运算调度的<font color='red'>**最小单位**</font>。

  * 一个进程中的**多个线程共享相同的内存单元/内存地址空间** ->它们从同一堆中分配对象，可以访问相同的变量和对象。这就使得线程间通信更简便、高效。但多个线程操作共享的系统资源可能就会带来<font color='red'>**安全的隐患。**</font>
  * 在Unix System V及SunOS中也被称为轻量进程（lightweight processes），但轻量进程更多指内核线程（kernel thread），而把用户线程（user thread）称为线程。







## 1.1多进程优缺点

**优点：**	<font color='red'>**进程拥有独立的内存（堆内存）空间，而线程通常与其他线程共享内存空间**</font>，共享内存空间有利于线程之间的通信、协调配合。相对于进程来说，线程是一种更轻量级的**多任务实现方式，创建、销毁一个线程消耗的** <font color='red'>**计算资源**</font> **比运行要小得多。**

**缺点：**	共享内存空间可能导致多个线程在读写内存时数据不一致, 这是使用多线程必须面对的风险。

<font color='red'>**多线程的安全问题来源于共享系统资源 **</font>

​	



![image-20220328200247903](C:/Users/Administrator/AppData/Roaming/Typora/typora-user-images/image-20220328200247903.png)

## 1.2多线程应用场景

* 程序需要同时执行两个或多个任务。

* 程序需要实现一些需要等待的任务时，如用户输入、文件读写操作、网络操作、搜索等。

* 需要一些后台运行的程序时。



## 1.2并发与并行

* **并行：**多个CPU同时执行多个任务。比如：多个人同时做不同的事。 
*  **并发：**一个CPU(采用时间片)同时执行多个任务。比如：秒杀、多个人做同一件事。

​	



​	并行就是两个或以上任务同时运行，就是A任务运行的同时，B任务也在运行。这需要多核的电脑，比如你的电脑是双核的，假如有两个任务要并行，则两个任务各占用一个CPU即可实现并行。
​	并发是指处理器不足够的情况下两个或以上任务都请求运行，而处理器同--时间只能执行一个任务，就把这两个或以上的任务安排轮流执行，由于**轮流执行的时间间隔(时间片)非常短**，较长时间来看使人感觉两个或以上的任务都在运行。





![](http://mk-images.tagao.top/img/202203291911151.png?imageslim)



## 1.3CPU核心

* **单核CPU和多核CPU的理解**
  * <font color='red'>**单核CPU，其实是一种假的多线程，因为在一个时间单元内，也只能执行一个线程的任务。**</font>例如：虽然有多车道，但是收费站只有一个工作人员在收费，只有收了费才能通过，那么CPU就好比收费人员。如果有某个人不想交钱，那么收费人员可以把他“挂起”（晾着他，等他想通了，准备好了钱，再去收费）。但是因为CPU时间单元特别短，因此感觉不出来。
  * 如果是多核的话，才能更好的发挥多线程的效率。（现在的服务器都是多核的）
  * <font color='red'>**一个Java应用程序java.exe**</font>，其实至少有三个线程：<font color='red'>**main()主线程，gc()垃圾回收线程，异常处理线程**</font>。当然如果发生异常，会影响主线程。 





CPU其他参数 



* 核心
  * CPU进行数据运算的东西，常说的4核，8核，都是说CPU的核心数

* 线程
  * 一个核心只能运行一个线程，后来有了超线程技术，一个核心可以处理两个线程



* 频率 

  CPU的参数里看到 **3.0GHz、3.7GHz等就是CPU的主频，严谨的说他是CPU内核的时钟频率**，可直接理解为运算速度。

  * 基频 也就是 CPU标出的<font color='red'>**主频**</font>
  * 睿频是采用 Intel 睿频加速技术可达到的更高频率，可以理解为<font color='red'>**自动超频**</font>。
  * 超频 是为了实现超过额定频率性能，人为调整各种指标（如电压、散热、外频、电源、BIOS等），属于<font color='red'>**手动超频**</font>。



* 架构

  * CPU的核心[框架](https://so.csdn.net/so/search?q=框架&spm=1001.2101.3001.7020)，影响CPU的整体性能，新的架构，优化了算法，处理能力更强，建议买新的架构

  

* 缓存
  * 缓存也是CPU里一项很重要的参数。由于CPU的运算速度特别快，在内存条的读写忙不过来的时候，CPU就可以把这部分数据存入缓存中，以此来缓解CPU的运算速度与内存条读写速度不匹配的矛盾，所以缓存是越大越好。
  * 一级缓存->二级缓存->三级缓存->内存条->硬盘



<img src="http://mk-images.tagao.top/img/202203282337913.png?imageslim" style="zoom: 67%;" />









# 2.线程的创建和使用

* Java语言的JVM允许程序运行多个线程，它通过**java.lang.Thread**类来体现。 



**API**中创建线程的两种方式 

JDK1.5之前创建新执行线程有两种方法： 

1. 继承Thread类的方式
2. 实现Runnable接口的方式 （需要调用Thread）



* <font color='red'>**Thread类的特性**</font>
  * 每个线程都是通过某个特定<font color='red'>**Thread**</font>对象的run()方法来完成操作的，经常把run()方法的主体称为**线程体**
  * 通过该Thread对象的start()方法来启动这个线程，而非直接调用run()

![](http://mk-images.tagao.top/img/202203282359225.png?imageslim)



## 2.1Thread类

* **构造器** 
  * **Thread()**：创建新的Thread对象
  * **Thread(String threadname)**：**创建线程并指定线程实例名**
  * **Thread(Runnable target)**：指定创建线程的目标对象，它实现了Runnable接口中的run方法
  * **Thread(Runnable target, String name)**：创建新的Thread对象



具体步骤

 **继承Thread类**

1) 定义子类继承Thread类。

2) <font color='red'>**子类中重写Thread类中的run方法。**</font>

3) 创建Thread子类对象，即创建了线程对象。

4) <font color='red'>**调用线程对象start方法：启动线程，调用run方法。**</font>



```java
/**
 * @date 1. 创建一个继承于Thread类的子类
 * 2. 重写Thread类的run()
 */
class CustomThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
```



```java
public static void main(String[] args) {
    //3. 创建Thread类的子类的对象
    CustomThread c1 = new CustomThread();

    //4.通过此对象调用start():①启动当前线程 ② 调用当前线程的run()
    c1.setName("Thread线程 x ");
    c1.start();
    //问题一：我们不能通过直接调用run()的方式启动线程。
    //c1.run();

    //问题二：再启动一个线程，遍历100以内的偶数。不可以还让已经start()的线程去执行。
    //c1.start(); 抛出 IllegalThreadStateException

    //我们需要重新创建一个线程的对象
    CustomThread c2 = new CustomThread();
    c2.start();
}
```





*  **注意点：**

1. 如果自己手动调用run()方法，那么就只是普通方法，没有启动多线程模式。

2. run()方法由JVM调用，什么时候调用，执行的过程控制都有操作系统的CPU调度决定。

3. 想要启动多线程，必须调用start方法。

4. 一个线程对象只能调用一次start()方法启动，如果重复调用了，则将抛出以上的异常“IllegalThreadStateException”。





## 2.2Runnable接口





**方式二：实现Runnable接口**

1) 定义子类，实现Runnable接口。

2) <font color='red'>**子类中重写Runnable接口中的run方法。**</font>

3) 将Runnable接口的子类对象作为参数传递给Thread类的构造器中。
4) <font color='red'>**调用Thread类的start方法：开启线程，调用Runnable子类接口的run方法。**</font>



```java
/**
 * @date 1.  创建一个实现了Runnable接口的类
 * 2. 实现类去实现Runnable中的抽象方法：run()
 */
class CustomRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }

        }
    }
}
```



```java
public static void main(String[] args) {
    //3. 创建实现类的对象
    CustomRunnable c1 = new CustomRunnable();
    //4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
    Thread t1 = new Thread(c1);
    t1.setName("runnable线程 1 ");

    //5. 通过Thread类的对象调用start():①启动线程 ②调用当前线程的run()-->调用了Runnable类型的target的run()
    t1.start();


    //再启动一个线程，遍历100以内的偶数
    Thread t2 = new Thread(c1);
    t2.setName("runnable线程 2");
    t2.start();
}
```





## 2.3继承与实现方式的区别



public class **Thread** extends Object implements Runnable

* **区别**
  * 继承Thread：线程代码存放Thread子类run方法中。
  * 实现Runnable：线程代码存在接口的子类的run方法。

* **实现方式的好处**
  * 避免了单继承的局限性
  * 多个线程可以共享同一个接口实现类的对象，非常适合多个相同线程来处理同一份资源。









## 2.4Thread方法

![](http://mk-images.tagao.top/img/202203290046895.png?imageslim)





* <font color='red'>**void start(): **</font>

  * 启动线程，并执行对象的run()方法

  

* <font color='red'>**run(): **</font>

  * 线程在被调度时执行的操作

  

* <font color='red'>**String getName():**</font> 返回线程的名称

  * 使用 getName( ) 或者Thread.currentThread().getName()  返回当前线程的名称

* <font color='red'>**void setName(String name)**</font>:

  * 设置该线程名称



* <font color='red'>**static Thread currentThread(): **</font>返回当前线程
* 在Thread子类中就是this，通常用于主线程和Runnable实现类
  



### 线程的让步

* <font color='red'>**static void yield()：**</font>线程让步

  * 用于正在执行的线程，在某些情况下让出CPU资源，让给其它线程执行。

  * 暂停当前正在执行的线程，把执行机会让给优先级相同或更高的线程
  
  * 若队列中没有同优先级的线程，忽略此方法
  
    

​	yield( ) 的作用是让步: 它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同或更高优先级的等待线程获取执行权;但是，并不能保证在当前线程调用yield()之后，其它具有相同或更高优先级的线程就一定能获得执行权; 也有可能是当前线程又进入到“运行状态”继续运行。

**让步不一定成功，可能礼让的线程继续执行**



```java
public class Way_01_yield {
    public static void main(String[] args) {
        CustomRunnable01 yieldTest = new CustomRunnable01();
        new Thread( yieldTest,"a").start();
        new Thread( yieldTest,"b").start();
    }
}

class CustomRunnable01 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + "结束");
    }
}
```





### 线程的插队



* <font color='red'>**void join()：**</font> 线程将被阻塞
* 当某个程序执行中调用其他线程 join() 方法，调用线程将阻塞，直到 join 线程执行完为止。
  * 低优先级的线程也可以获得执行。

  * <font color='red'>**join();**</font> 等待调用该方法的线程终止，只有当指定的线程结束后当前线程才能继续。
  * <font color='red'>**join(long millisecond):**</font>等待调用该方法的线程终止的时间最长为millisecond毫秒。
  * <font color='red'>**join(long millisecond,int nanosecond );**</font>等待调用该方法的线程终止的时间最长为millisecond毫秒加纳秒。
  * 如果有线程中断了运行join()方法的线程，则抛出Interrupted Exception异常。



```java
public class Way_03_Join implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("线程 " + i);
        }
    }
}
```



```java
public static void main(String[] args) throws InterruptedException {
    Way_03_Join joinTest = new Way_03_Join();
    Thread t = new Thread(joinTest);
    t.start();
    // 当主线程跑到200  让 thread插队跑完，主线程再继续
    for (int i = 0; i < 500; i++) {
        System.out.println("主线程" + i);
        if(i == 200){
            t.join();
        }
    }
}
```





### 线程的休眠

* <font color='red'>**static void sleep(long millis)**</font>:(指定时间:毫秒) 
* 令当前活动线程在指定时间段内放弃对CPU控制,使其他线程有机会被执行,时间到后重排队。
  
* 抛出InterruptedException异常
  
* sleep时间达到后线程进入就绪状态;
  * sleep可以模拟网络延时，倒计时等；
  * 每一个对象都有一个锁，sleep不会释放锁;



```java
public static void main(String[] args) throws InterruptedException {
    // 测试一 倒计时10秒
    tenDown();
    //测试二
    Date date = new Date(System.currentTimeMillis());
    while (true) {
        Thread.sleep(1000);
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(date));
        date = new Date(System.currentTimeMillis());
    }
}
```



```java
//@date  倒计时十秒 
public static void tenDown() throws InterruptedException {
    for (int i = 10; i > 0; i--) {
        System.out.println(i);
        Thread.sleep(1000);
    }
}
```







* <font color='red'>**boolean isAlive()：**</font>判断线程是否还活着
  * 返回值为：boolean



* <font color='red'>**stop():**</font> 强制线程生命期结束，不推荐使用
* 1.建议线程正常停止--->利用次数,不建议死循环。
  
* 2.建议使用标志位--->设置一个标志位
  * 3.不要使用stop或者destroy等过时或者JDK不建议使用的方法

![](http://mk-images.tagao.top/img/202203290054249.png?imageslim)





## 2.5线程的调度



* **调度策略**
* 先来先服务
  * 高响应比优先
  * 短进程优先
  * 多线反馈队列 
  
* 时间片轮训
  * 优先权调度



* **优先权调度 ：线程的优先级等级**
* MAX_PRIORITY：10
  
* MIN _PRIORITY：1
  
* NORM_PRIORITY：5 （默认）
  
* **涉及的方法**

  * **getPriority()** **：**返回线程优先值

  * **setPriority(int newPriority)** **：**改变线程的优先级

* **说明**

  * 线程创建时继承父线程的优先级

  * 低优先级只是获得调度的概率低，并非一定是在高优先级线程之后才被调用



## 2.6线程的守护

Java中的线程分为两类：一种是**守护线程**，一种是**用户线程**。 

*  它们在几乎每个方面都是相同的，唯一的区别是判断JVM何时离开。

*  守护线程是用来服务用户线程的，通过在start()方法前调用**thread.setDaemon(true**)可以把一个用户线程变成一个守护线程。
* Java垃圾回收就是一个典型的守护线程。
* 若JVM中都是守护线程，当前JVM将退出。 



​	守护线程如： 后台记录操作日志,监控内存,垃圾回收等待..



# 3.线程的生命周期

 

**JDK中用Thread.State类定义了线程的几种状态**要想实现多线程，必须在主线程中创建新的线程对象。Java语言使用Thread类及其子类的对象来表示线程，在它的一个完整的生命周期中通常要经历如下的**五种状态**： 

* **新建：** 当一个Thread类或其子类的对象被声明并创建时，新生的线程对象处于新建

状态

* **就绪：**处于新建状态的线程被start()后，将进入线程队列等待CPU时间片，此时它已具备了运行的条件，只是没分配到CPU资源

* **运行：**当就绪的线程被调度并获得CPU资源时,便进入运行状态， run()方法定义了线

程的操作和功能

* **阻塞：**在某种特殊情况下，被人为挂起或执行输入输出操作时，让出 CPU 并临时中止自己的执行，进入阻塞状态

* **死亡：**线程完成了它的全部工作或线程被提前强制性地中止或出现异常导致结束



![](http://mk-images.tagao.top/img/202203290028789.png?imageslim)





# 4.线程的同步

![](http://mk-images.tagao.top/img/202203292121448.png?imageslim)







![](http://mk-images.tagao.top/img/202203292228981.png?imageslim)

* 这套机制就是synchronized关键字，它包括两种用法:**synchronized方法和synchronized块。**
  由于我们可以通过专用关键字来保证数据对象只能被方法访问，所以我们只需要针对方法提出一套机制，这套机制就是同步关键字，它包括两种用法：同步方法和同步块。

* **注意：**
  
* 必须确保使用同一个资源的**多个线程共用一把锁**，这个非常重要，否则就无法保证共享资源的安全
  
  *  <font color='red'>**一个线程类中的所有静态方法共用同一把锁（类名.class），所有非静态方法共用同一把锁（this），同步代码块（指定需谨慎）**</font>
  
  

<font color='red'>**synchronized**</font>是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：

| 修饰范围 | 作用范围                                                     |
| -------- | ------------------------------------------------------------ |
| 代码块   | 大括号{}括起来的代码，作用的对象是调用这个代码块的对象       |
| 方法     | 整个方法，作用的对象是调用这个方法的对象                     |
| 静态方法 | 整个静态方法，作用的对象是这个类的所有对象                   |
| 类       | synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象 |



![image-20210426213445247](D:\笔记\Java\JavaSE\多线程.assets\image-20210426213445247.png)



## 1.同步块

* 同步块: synchronized (Obj ){}

* **Obj **称之为**同步监视器**
* Obj可以是任何对象，但是推荐使用共享资源作为同步监视器
  * 同步方法中无需指定同步监视器﹐因为同步方法的同步监视器就是this ,就是这个对象本身,或者是class[反射中讲解]
  * **同步静态方法：就是同步代码块，同步锁对象是类的class对象**
  
* 同步监视器的执行过程

  * 1．第一个线程访问，锁定同步监视器﹐执行其中代码．

  * 2．第二个线程访问，发现同步监视器被锁定﹐无法访问．
  * 3．第一个线程访问完毕,解锁同步监视器．
  * 4．第二个线程访问,发现同步监视器没有锁﹐然后锁定并访问

* **synchronized (Obj ){} 锁的是变化的量，既需要增删改的**



<font color='red'>**继承Thread类**</font>

![](http://mk-images.tagao.top/img/202203292204352.png?imageslim)



<font color='red'>**实现Runnable接口**</font>

![](http://mk-images.tagao.top/img/202203292206939.png?imageslim)



## 2.同步方法

* 同步方法: `public synchronized void method(int args){}`

* synchronized方法控制对“对象”的访问, 每个对象对应一把锁,每个同步方法控制对“对象”的访问，每个对象对应一把锁，每个synchronized方法都必须获得调用该方法的对象的锁才能执行,否则线程会阻塞，方法一旦执行,就独占该锁，直到该方法返回才释放锁﹐后面被阻塞的线程才能获得这个锁，继续执行

  

**缺陷:若将一个大的方法申明为synchronized将会影响效率**

<font color='red'>**继承Thread类**</font>

![](http://mk-images.tagao.top/img/202203292208173.png?imageslim)



<font color='red'>**实现Runnable接口**</font>

![](http://mk-images.tagao.top/img/202203292208839.png?imageslim)



**资料参考 **

http://www.blogjava.net/tscfengkui/archive/2010/11/10/337709.html 

https://blog.csdn.net/yudianxiaoxiao/article/details/107592447?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_baidulandingword-1&spm=1001.2101.3001.4242 





## 3.同步的问题

**1**、如何找问题，即代码是否存在线程安全？（非常重要）

（1）明确哪些代码是多线程运行的代码

（2）明确多个线程是否有共享数据

（3）明确多线程运行代码中是否有多条语句操作共享数据

**2**、如何解决呢？（非常重要）

对多条操作共享数据的语句，只能让一个线程都执行完，在执行过程中，其他线程不可以参与执行。

即所有操作共享数据的这些语句都要放在同步范围中

**3**、切记：

*  范围太小：没锁住所有有安全问题的代码

* 范围太大：没发挥多线程的功能。





<font color='red'>**释放锁的操作**</font>

* 当前线程的同步方法、同步代码块<font color='red'>**执行结束**</font>。

* 当前线程在同步代码块、同步方法中遇到<font color='red'>**break、return终止了该代码块**</font>、该方法的继续执行。

* 当前线程在同步代码块、同步方法中出现了未处理的<font color='red'>**Error或Exception**</font>，导致异常结束。

* 当前线程在同步代码块、同步方法中执行了线程对象的**wait()**方法，当前线程暂停，并释放锁。



<font color='red'>**不会释放锁的操作**</font>

* 线程执行同步代码块或同步方法时，程序调用<font color='red'>**Thread.sleep()、Thread.yield()**</font>方法暂停当前线程的执行

* 线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起，该线程不会释放锁（同步监视器）。
  * 应尽量避免使用suspend()和resume()来控制线程







## 4.Lock锁



* 从JDK 5.0开始，Java提供了更强大的线程同步机制——通过显式定义同步锁对象来实现同步。同步锁使用Lock对象充当。 

  * <font color='red'>**java.util.concurrent.locks.Lock接口是控制多个线程对共享资源进行访问的工具。**</font>锁提供了对共享资源的独占访问，每次只能有一个线程对Lock对象加锁，线程开始访问共享资源之前应先获得Lock对象。 

  *  ReentrantLock 类实现了 Lock ，它拥有与 **synchronized** 相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是**ReentrantLock**，可以**显式加锁、释放锁**

    

<font color='red'>**一个线程类中的所有静态方法共用同一把锁（类名.class），所有非静态方法共用同一把锁（this），同步代码块（指定需谨慎）**</font>

![](http://mk-images.tagao.top/img/202203292223565.png?imageslim)

* Lock是显式锁（手动开启和关闭锁，别忘记关闭锁) synchronized是隐式锁，出了作用域自动释放

* Lock只有代码块锁，synchronized有代码块锁和方法锁

* 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性(提供更多的子类)

  

  **优先使用顺序：**

Lock >同步代码块（已经进入了方法体，分配了相应资源)>同步方法（在方法体之外)





# 5.线程的死锁

​	

​	死锁是指两个或两个以上的线程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。



产生死锁的四个必要条件:

* 1.互斥条件:一个资源每次只能被一个进程使用.

* 2．请求与保持条件:<font color='red'>**一个进程因请求资源而阻塞时，对已获得的资源保持不放。**</font>

* 3．不剥夺条件:进程已获得的资源，在末使用完之前，不能强行剥夺。

* 4.循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系.


上面列出了死锁的四个必要条件，我们只要想办法破其中的任意一个或多个条件就可以避免死锁发生

<font color='red'>**死锁避免方法**</font>

* 专门的算法、原则

* 尽量减少同步资源的定义

* 尽量避免嵌套同步



![](http://mk-images.tagao.top/img/202203292257397.png?imageslim)



# 6.线程的通信

**不同线程之间的通信，使用等待和唤醒**

通信只能是出现在同步代码块 或者 同步方法中 ，lock不行

* **wait()** **与** **notify()** **和** **notifyAll()**

  * **wait()**：令当前线程挂起并放弃CPU、同步资源并等待，使别的线程可访问并修改共享资源，而当前线程排队等候其他线程调用notify()或notifyAll()方法唤醒，唤醒后等待重新获得对监视器的所有权后才能继续执行。

  * **notify()**：唤醒正在排队等待同步资源的线程中优先级最高者结束等待

  * **notifyAll ()**：唤醒正在排队等待资源的所有线程结束等待. 

*  这三个方法只有在synchronized方法或synchronized代码块中才能使用，否则会<font color='red'>**llegalMonitorStateException**</font>异常。

* 因为这三个方法必须有锁对象调用，而任意对象都可以作为synchronized的同步锁，因此这三个方法只能在Object类中声明。





**wait()方法**

* 在当前线程中调用方法： 对象名.wait()
* <font color='red'>**wait方法使当前线程放弃同步锁转换为阻塞状态，直到被其他线程进入此同步锁唤醒为止。**</font>
* 调用方法的必要条件：当前线程必须具有对该对象的监控权（加锁）
* **调用此方法后，当前线程将释放对象监控权 ，然后进入等待**
* 在当前线程被notify后，要重新获得监控权，然后从断点处继续代码的执行。



**notify()/notifyAll()**

* 在当前线程中调用方法： 对象名.notify()

* 功能：唤醒等待该对象监控权的一个/所有线程。 

* 调用方法的必要条件：<font color='red'>**当前线程必须具有对该对象的监控权（加锁）**</font>



**例 题**

**使用两个线程打印 1-100。线程1, 线程2 交替打印**

![image-20220329234852170](C:/Users/Administrator/AppData/Roaming/Typora/typora-user-images/image-20220329234852170.png)



 * <font color='red'>**面试题：sleep() 和 wait()的异同？**</font>
 * 1.相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态。
 * 2.不同点：1）两个方法声明的位置不同：<font color='red'>**Thread类中声明sleep() , Object类中声明wait()**</font>
 * 2）调用的要求不同：sleep()可以在任何需要的场景下调用。 wait()必须使用在同步代码块或同步方法中
 * 3）关于是否释放同步监视器：如果两个方法都使用在同步代码块或同步方法中，<font color='red'>**sleep()不会释放锁，wait()会释放锁。**</font>



**经典例题：生产者/消费者问题**

* 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。

* 这里可能出现两个问题：

  * 生产者比消费者快时，消费者会漏掉一些数据没有取到。

  * 消费者比生产者快时，消费者会取相同的数据。



```markdown
* 分析：
* 1. 是否是多线程问题？是，生产者线程，消费者线程
* 2. 是否有共享数据？是，店员（或产品）
* 3. 如何解决线程的安全问题？同步机制,有三种方法
* 4. 是否涉及线程的通信？是
```



```java
public class ProductThread {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        
        new Thread(new Product(clerk),"生产者").start();
        new Thread(new Consumer(clerk),"消费者").start();
    }

}

```

<font color='red'>**生产者**</font>

```java
class Product implements Runnable{
    Clerk clerk ;
    public Product(Clerk clerk){this.clerk = clerk;}
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":开始生产产品.....");
        while (true){
            try {
                clerk.productClerk();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```



<font color='red'>**消费者**</font>

```java
class Consumer implements Runnable{
    Clerk clerk ;
    public Consumer(Clerk clerk){this.clerk = clerk;}
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":开始消费产品.....");
        while (true) {
            try {
                clerk.consumeClerk();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

<font color='red'>**商品：**</font>

```java
class Clerk{
    private  int  productCount = 0;

    /**
     * @date 生产产品
     */
    public synchronized void productClerk() throws InterruptedException {
        if (productCount < 20) {
            productCount++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + productCount + "个产品");
            notify();
        }else {
            wait();
        }
    }

    /**
     * @date  消费产品
     */
    public synchronized void consumeClerk() throws InterruptedException {
        if (productCount > 0 ){
            System.out.println(Thread.currentThread().getName() + ":开始消费第" + productCount + "个产品");
            productCount--;
            notify();
        }else{
            wait();
        }
    }

}
```







# 7.JDK5.0新增线程创建方式



## 1.实现Callable接口

* 与使用Runnable相比， Callable功能更强大些

  * 相比run()方法，可以有返回值

  * 方法可以抛出异常

  * 支持泛型的返回值

  * 需要借助FutureTask类，比如获取返回结果



* Future接口

  * 可以对具体Runnable、Callable任务的执行结果进行取消、查询是否完成、获取结果等。

  * <font color='red'>**FutrueTask是Futrue接口的唯一的实现类**</font>

  * FutureTask 同时实现了Runnable, Future接口。它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值



```java
class MyThread implements Callable{
    /**      
     * @date  
     * 1.创建一个CallBall实现类
     * 2.实现call方法，将此线程需要执行的操作声明在call()中
     */
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if(i % 2 == 0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}
```



```java
public class JDK5_01_Callback {
    public static void main(String[] args) {
        //3.创建Callable接口实现类的对象
        MyThread numThread = new MyThread();
        //4.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask task = new FutureTask(numThread);
        //5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(task).start();
	
        try {
            //6.获取Callable中call方法的返回值
            //get()返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值。
            Object sum = task.get();
            System.out.println("总和为：" + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```





## 2.线程池

* **背景：**经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对性能影响很大。 
* **思路：**提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中。可以避免频繁创建销毁、实现重复利用。类似生活中的公共交通工具。

*  **好处：**
  * 提高响应速度（减少了创建新线程的时间）
  * 降低资源消耗（重复利用线程池中线程，不需要每次都创建）
  * 便于线程管理
    * `corePoolSize`：核心池的大小
    *  `maximumPoolSize`：最大线程数 
    * ` keepAliveTime`：线程没有任务时最多保持多长时间后会终止





**线程池相关API**

* JDK 5.0起提供了线程池相关API：**ExecutorService** 和 **Executors**
* ExecutorService：真正的线程池接口。常见子类<font color='red'>**ThreadPoolExecutor**</font>
  * <font color='red'>**void execute(Runnable command) ：执行任务/命令，没有返回值，一般用来执行Runnable**</font>
  *  <font color='red'>**<T> Future<T> submit(Callable<T> task)：执行任务，有返回值，一般又来执行Callable**</font>
  *  void shutdown() ：关闭连接池

* <font color='red'>**Executors：工具类**</font>、线程池的工厂类，用于创建并返回不同类型的线程池
* Executors.newCachedThreadPool()：创建一个可根据需要创建新线程的线程池
  * <font color='red'>**Executors.newFixedThreadPool(n); 创建一个可重用固定线程数的线程池**</font>
  * Executors.newSingleThreadExecutor() ：创建一个只有一个线程的线程池
  
* Executors.newScheduledThreadPool(n)：创建一个线程池，它可安排在给定延迟后运



```java
 public static void main(String[] args) throws ExecutionException, InterruptedException {
     //1. 提供指定数量的线程池
     ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    // 2.设置线程池的属性
     service.setCorePoolSize(15);
     //3.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象
     service.execute(new NumberThread1());
     service.execute(new NumberThread2());
     Future future = service.submit(new MyThread()); // MyThread()实现的是Callable接口
     System.out.println(future.get());
     
     //4.关闭连接池
     service.shutdown();
 }
```

```java
class NumberThread2 implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

class NumberThread1 implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}
```



## 3.线程池策略

**五种线程池，四种拒绝策略，三种阻塞队列**

```java
三种阻塞队列：
BlockingQueue workQueue = null;
workQueue = new ArrayBlockingQueue<>(5);//基于数组的先进先出队列，有界
workQueue = new LinkedBlockingQueue<>();//基于链表的先进先出队列，无界
workQueue = new SynchronousQueue<>();//无缓冲的等待队列，无界
```



```java
四种拒绝策略：
RejectedExecutionHandler rejected = null;
rejected = new ThreadPoolExecutor.AbortPolicy();//默认，队列满了丢任务抛出异常
rejected = new ThreadPoolExecutor.DiscardPolicy();//队列满了丢任务不异常
rejected = new ThreadPoolExecutor.DiscardOldestPolicy();//将最早进入队列的任务删，之后再尝试
rejected = new ThreadPoolExecutor.CallerRunsPolicy();//如果添加到线程池失败，那么主线程会自己去执行该任务
```



```java
五种线程池：
ExecutorService threadPool = null;
threadPool = Executors.newCachedThreadPool();//有缓冲的线程池，线程数 JVM 控制
threadPool = Executors.newFixedThreadPool(3);//固定大小的线程池
threadPool = Executors.newScheduledThreadPool(2);
threadPool = Executors.newSingleThreadExecutor();//单线程的线程池，只有一个线程在工作
threadPool = new ThreadPoolExecutor();//默认线程池，可控制参数比较多
```

```java
public static void main (String[] args) throws Exception {
    testThreadPoolExecutor();
}
public static void testThreadPoolExecutor() throws Exception {
    //基础参数
    int corePoolSize=2;//最小活跃线程数
    int maximumPoolSize=5;//最大活跃线程数
    int keepAliveTime=5;//指定线程池中线程空闲超过 5s 后将被回收
    TimeUnit unit = TimeUnit.SECONDS;//keepAliveTime 单位
    //阻塞队列
    BlockingQueue<Runnable> workQueue = null;
    workQueue = new ArrayBlockingQueue<>(5);//基于数组的先进先出队列，有界
    workQueue = new LinkedBlockingQueue<>();//基于链表的先进先出队列，无界
    workQueue = new SynchronousQueue<>();//无缓冲的等待队列，无界
    //拒绝策略
    RejectedExecutionHandler rejected = null;
    rejected = new ThreadPoolExecutor.AbortPolicy();//默认，队列满了丢任务抛出异常
    rejected = new ThreadPoolExecutor.DiscardPolicy();//队列满了丢任务不异常
    rejected = new ThreadPoolExecutor.DiscardOldestPolicy();//将最早进入队列的任务删，之后再尝试加入队列
    rejected = new ThreadPoolExecutor.CallerRunsPolicy();//如果添加到线程池失败，那么主线程会自己去执行该任务
    //使用的线程池
    ExecutorService threadPool = null;
    threadPool = Executors.newCachedThreadPool();//有缓冲的线程池，线程数 JVM 控制
    threadPool = Executors.newFixedThreadPool(3);//固定大小的线程池
    threadPool = Executors.newScheduledThreadPool(2);
    threadPool = Executors.newSingleThreadExecutor();//单线程的线程池，只有一个线程在工作
    threadPool = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            unit,
            workQueue,
            rejected);//默认线程池，可控制参数比较多
    //执行无返回值线程
    TaskRunnable taskRunnable = new TaskRunnable();
    threadPool.execute(taskRunnable);
    List<Future<String>> futres = new ArrayList<>();
    for(int i=0;i<10;i++) {
        //执行有返回值线程
        TaskCallable taskCallable = new TaskCallable(i);
        Future<String> future = threadPool.submit(taskCallable);
        futres.add(future);
    }
    for(int i=0;i<futres.size();i++){
        String result = futres.get(i).get();
        System.out.println(i+" result = "+result);
    }
}
/**
    * 返回值的线程，使用 threadpool.execut() 执行
    */
public static class TaskRunnable implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " runnable result!");
    }
}
/**
    * 有返回值的线程，使用 threadpool.submit() 执行
    */
public static class TaskCallable implements Callable<String>{
    public TaskCallable(int index){
        this.i=index;
    }
    private int i;
    @Override
    public String call() throws Exception {
        int r = new Random().nextInt(5);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("callable result!");
        return Thread.currentThread().getName()+" callable index="+i +",sleep="+r;
    }
}

```

