





# 9 对象流



**<font color='red'>ObjectInputStream和OjbectOutputSteam</font>**

* 用于存储和读取**基本数据类型**数据或**对象**的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。



*  **序列化：**用ObjectOutputStream类**保存**基本类型数据或对象的机制

*  **反序列化：**用ObjectInputStream类**读取**基本类型数据或对象的机制



* ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量

  **static属于类的资源，transient是瞬态的**



## 9.1对象的序列化

* **对象序列化机制**允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。//当其它程序获取了这种二进制流，就可以恢复成原来的Java对象

* 序列化的好处在于可将任何实现了Serializable接口的对象转化为**字节数据**，使其在保存和传输时可被还原

* 序列化是 RMI（Remote Method Invoke – 远程方法调用）过程的参数和返回值都必须实现的机制，而 RMI 是 JavaEE 的基础。因此序列化机制是JavaEE 平台的基础





## 9.2序列化条件

* **条件一：** 如果需要让某个对象支持序列化机制，则必须让对象所属的类及其属性是可序列化的，为了让某个类是可序列化的，该类必须实现如下两个接口之一。否则，会抛出NotSerializableException异常

  * <font color='red'>**Serializable**</font>

  * Externalizable

* **条件二：** 凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量：

  ​	<font color='red'> **private static final long serialVersionUID;**</font>

  * serialVersionUID用来表明类的不同版本间的兼容性。简言之，其目的是以序列化对象进行版本控制，有关各版本反序列化时是否兼容。

  * 如果类没有显示定义这个静态常量，它的值是Java运行时环境根据类的**内部细节自动生成的**。若类的实例变量做了修改，serialVersionUID 可能发生变化。故建议，显式声明。

  * 简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，**JVM会把传来的字节流中的serialVersionUID与本地相应实体类的serialVersionUID进行比较**，如果相同就认为是一致的，可以进行反序列化，否则就会出现**序列化版本不一致的异常**。(InvalidCastException)

* **条件三：** 

  ​	除了当前Person类需要实现Serializable接口之外，还必须保证其内部所有属性也必须是可序列化的。

  ​	（默认情况下，基本数据类型可序列化）







## 9.3使用序列化



**<font color='red'>实体类</font>**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person implements Serializable {

    public static final long serialVersionUID = 475463534532L;
    private String name;
    private int age;
    private int id;
    private Account acct;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Account  implements Serializable{
    public static final long serialVersionUID = 4754534532L;
    private double balance;
}
```

<font color='red'>**使用实例**</font>

```java
/**
* @date 序列化过程：将内存中的java对象保存到磁盘中或通过网络传输出去
*   使用ObjectOutputStream实现
*/
@Test
public void objectOutUse(){
    ObjectOutputStream oos = null;
    try {
        //1. 创建对象流
        oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
        //2. 写入
        oos.writeObject(new String("我爱北京天安门"));
        oos.writeObject(new Person("王铭",23));
        oos.writeObject(new Person("张学良",23,1001,new Account(5000)));
        //3.刷新
        oos.flush();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if(oos != null){
            //3.关闭流
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

```



```java
/**
* @date 反序列化：将磁盘文件中的对象还原为内存中的一个java对象
*      使用ObjectInputStream来实现
*/
@Test
public void objectInUse(){
    ObjectInputStream ois = null;
    try {
        //1.创建对象读入流
        ois = new ObjectInputStream(new FileInputStream("object.dat"));
		//2.读出对象
        Object obj = ois.readObject();
        Person p1 = (Person) ois.readObject();
        Person p2 = (Person) ois.readObject();
		String str = (String) obj;
        System.out.println(str);
        System.out.println(p1);
        System.out.println(p2);

    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        if(ois != null){
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```



## 9.4面试考点

**谈谈你对java.io.Serializable接口的理解，我们知道它用于序列化，是空方法接口，还有其它认识吗？**



* **实现了Serializable接口的对象，可将它们转换成一系列字节，并可在以后完全恢复回原来的样子。这一过程亦可通过网络进行。这意味着序列化机制能自动补偿操作系统间的差异。换句话说，可以先在Windows机器上创*建一个对象，对其序列化，然后通过网络发给一台Unix机器，然后在那里准确无误地重新“装配”。不必关心数据在不同机器上如何表示，也不必*关心字节的顺序或者其他任何细节。**

* **由于大部分作为参数的类如String、Integer等都实现了java.io.Serializable的接口，也可以利用多态的性质，作为参数使接口更灵活**

