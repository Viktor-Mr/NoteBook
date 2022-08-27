

# Java Reflection 



# 面试

**疑问1：通过直接new的方式或反射的方式都可以调用公共的结构，开发中到底用那个？**

```mark
建议：直接new的方式。更加高效
```

**疑问2：什么时候会使用：反射的方式。** 

```markdown
反射的特征：动态性
在编码时不确定需要得到那个Class，在运行时需要根据特定的情况来得到Class
```

**疑问3：反射机制与面向对象中的封装性是不是矛盾的？如何看待两个技术？**

```markdown
不矛盾
封装作用是 强制使用public修饰的成员变量、成员方法，而对于private的修饰的方法一边都以及被整合到了public，典型的就是get set方法。
反射可以调用private修饰的变量、方法，是出于吗某种特殊的需要。 
```



**疑问4：关于java.lang.Class类的理解**

```markdown
1.类的加载过程：
	程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
    接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件
    加载到内存中。此过程就称为类的加载。加载到内存中的类，我们就称为运行时类，此运行时类，就作为Class的一个实例。

2.换句话说，Class的实例就对应着一个运行时类。
3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式来获取此运行时类。
```





# 1.Java反射机制概述



* Reflection（反射）是被视为<font color='red'>动态语言</font>的关键，反射机制允许程序在执行期借助于Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。 

* 加载完类之后，在堆内存的方法区中就产生了一个Class类型的对象（一个类只有一个Class对象），这个对象就包含了完整的类的结构信息。<font color='red'>我们可以通过这个对象看到类的结构。这个对象就像一面镜子，透过这个镜子看到类的结构，所以，我们形象的称之为：**反射**。</font>

![](http://mk-images.tagao.top/img/image-20210624214155420.png)





## 1.1动态语言

- **是一类在运行时可以改变其结构的语言：例如新的函数、对象、甚至代码可以被引进，已有的函数可以被删除或是其他结构上的变化。<font color='red'>通俗点说就是在运行时代码可以根据某些条件改变自身结构</font>**
- **主要动态语言：Object-C、JavaScript、PHP、Python等**

## 1.2静态语言

- **与动态语言相对应的，运行时结构<font color='red'>不可变的语言就是静态语言</font>。如Java、C、C++、C#**
- **Java不是动态语言，但Java可以称之为 “<font color='red'>准动态语言</font>” 。即Java有一定的动态性，我们可以利用反射机制获得类似动态语言的特性。Java的动态性让编程的时候更加灵活！**





## **1.3反射提供的功能**

- **在运行时判断任意一个对象所属的类**
- **在运行时构造任意一个类的对象**
- **在运行时判断任意一个类所具有的成员变量和方法**
- **在运行时获取泛型信息**
- **在运行时调用任意一个对象的成员变量和方法**
- **在运行时处理注解**
- **生成动态代理**



## 1.4反射优点和缺点

**优点：**

- **可以实现动态创建对象和编译，体现出很大的灵活性**

**缺点：**

- **对性能有影响。使用反射基本上是一种解释操作，我们可以告诉JVM，我们希望做什么并且它满足我们的要求。这类操作总是慢于直接执行相同的操作**



## 1.5反射相关的主要API

- **java.lang.<font color='red'>Class</font>：代表一个类**
- **java.lang.reflect.<font color='red'>Method</font>：代表类的方法**
- **java.lang.reflect.<font color='red'>Field</font>：代表类的成员变量**
- **java.lang.reflect.<font color='red'>Constructor</font>：代表类的构造器**
- **… ...**



```java
Class clazz = Person.class;
//1.通过反射，创建Person类的对象
Constructor cons = clazz.getConstructor(String.class, int.class);
Object obj = cons.newInstance("Tom", 12);
Person person = (Person) obj;
System.out.println(person.toString());

System.out.println("*****************通过反射，调用Person类的公有结构的***************");

//2.通过反射调用对象指定的属性，方法
Field age = clazz.getDeclaredField("age");
age.set(person,10);
System.out.println(person.toString());

//3.调用方法
Method show = clazz.getDeclaredMethod("show");
show.invoke(person);

System.out.println("*****************通过反射，调用Person类的私有结构的***************");
//01.调用私有的构造器
Constructor cons01 = clazz.getDeclaredConstructor(String.class);
cons01.setAccessible(true);
Person person01 = (Person) cons01.newInstance("Jerry");
System.out.println(person01);


//02.调用私有的属性
Field name = clazz.getDeclaredField("name");
name.setAccessible(true);
name.set(person01,"HanMeimei");
System.out.println(person01);

//03.调用私有的方法
Method showNation = clazz.getDeclaredMethod("showNation", String.class);
showNation.setAccessible(true);
//相当于String nation = p1.showNation("中国")
String nation = (String) showNation.invoke(person01,"中国");
System.out.println(nation);
```





# 2.理解Class类并获取Class实例



## 2.1Class类

**在Object类中定义了以下的方法，此方法还将被所有子类继承**

<font color='red'>`public final Class getClass()`</font>

- **以上的方法返回值的类型是一个Class类，此类是Java反射的源头，实际上所谓反射从程序的运行结果来看也很好理解，即：<font color='red'>可以通过对象反射求出类的名称</font>**

  ![](http://mk-images.tagao.top/img/image-20210624222942615.png)







* **对象照镜子后可以得到的信息：某个类的属性、方法和构造器、某个类到底实现了哪些接口。对于每个类而言，JRE都为其保留一个不变的 Class 类型的对象。一个 Class 对象包含了特定某个结构（class/interface/enum/annotation/primitive/type/void/[]）的有关信息。**
  *  **Class 本身也是一个类**
  * **Class 对象只能有系统建立对象**
  * **一个加载的类在 JVM 中只会有一个 Class 实例**
  * **一个 Class 对象对应的是一个加载到JVM中的一个 .class文件**
  * **每个类的实例都会记得自己是由哪个 Class 实例所生成**
  * **通过 Class 可以完整地得到一个类中的所有被加载的结构**
  * **Class 类是Reflection的根源，针对任何你想动态加载、运行的类，唯有先获得相应的 Class 对象**



## 2.2Class类的常用方法



![](http://mk-images.tagao.top/img/20220324151046.png)





## 2.3获取Class类的实例(方法)



* <font color='red'>第一种：</font>**若已知具体的类，通过类的class属性获取，该方法最为安全可靠，程序性能最高**

​									`Class clazz = Person.class;`

* <font color='red'>第二种：</font>已知某个类的实例，调用该实例的getClass()方法获取Class对象**

​									`Class clazz = person.getClass();`

* <font color='red'>第三种：</font>**<font color='red'>（最常用）</font>已知一个类的全名，且该类在类路径下，可通过Class类的静态方法forName()获取**

​								`Class clazz = Class.forName("java.lang.String");`

* <font color='red'>第四种：</font> 利用ClassLoader

  ​					 `ClassLoader cl = this.getClass().getClassLoader();`

  ​					`Class clazz = cl.loadClass(“类的全类名”);`

  

* <font color='red'>第五种：</font> **内置基本数据类型的包装类型可以直接用类名 .Type**



```java
//方式一：调用运行时类的属性：.class
Class clazz1 = Person.class;
System.out.println(clazz1);
//方式二：通过运行时类的对象,调用getClass()
Person p1 = new Person();
Class clazz2 = p1.getClass();
System.out.println(clazz2);

//方式三：调用Class的静态方法：forName(String classPath)
Class clazz3 = Class.forName("top.tagao.Person");
System.out.println(clazz3);

//方式四：使用类的加载器：ClassLoader  (了解)
ClassLoader classLoader = ClassUse.class.getClassLoader();
Class clazz4 = classLoader.loadClass("top.tagao.Person");
System.out.println(clazz4);

//内置基本数据类型的包装类型可以直接用类名 .Type
Class clazz5 = Integer.TYPE;
System.out.println(clazz5);
```





## 2.4拥有Class对象的类型



- **class：外部类，成员（成员内部类，静态内部类），局部内部类，匿名内部类**
- **interface：接口**
- **[]：数组**
- **enum：枚举**
- **annotation：注解@interface**
- **primitive type：基本数据类型**
- **void**

```java
Class c1  = Object.class; //类
Class c2 = Comparable.class; //接口
Class c3 = String[].class;  //一维数组
Class c4 = int[][].class;   //二维数组
Class c5 = Override.class;  // 注解
Class c6 = ElementType.class; //枚举
Class c7 = Integer.class;   //基本数据类型
Class c8 = void.class;    //void
Class c9 = Class.class;   //Class

int[] a = new int[10];
int[] b = new int[100];
System.out.println(a.getClass().hashCode());
System.out.println(b.getClass().hashCode());
```



![](http://mk-images.tagao.top/img/image-20210625000351034.png)



# 3.类的加载与ClassLoader的理解



## 3.1Java内存分析

![](http://mk-images.tagao.top/img/image-20210625000151954.png)



![](http://mk-images.tagao.top/img/image-20210622231344612.png)





## 3.2类的加载过程

**当程序主动使用某个类时，如果该类还未被加载到内存中，则系统会通过如下三个步骤来对该类进行初始化。**

![](http://mk-images.tagao.top/img/image-20210625000232875.png)







- <font color='red'>**加载**</font>：将 class 文件字节码内容加载到内存中，并将这些静态数据转换成方法区的运行时数据结构，然后生成一个代表这个类的 java.lang.Class 对象
- <font color='red'>**链接**</font>：将 Java 类的二进制代码合并到 JVM 的运行状态之中的过程
  - 验证：确保加载的类信息符合 JVM 规范，没有安全方面的问题
  - 准备：正式为类变量（static）分配内存并设置类变量<font color='red'>**默认初始值**</font>的阶段，这些内存都将在方法区中进行分配
  - 解析：虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程
- <font color='red'>**初始化：**</font>
  - 执行类构造器< clint>() 方法的过程，<font color='red'>**类构造器< clint>() 方法是由编译期自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的（类构造器是构造类信息的，不是构造该类对象的构造器）**</font>
  - 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化
  - 虚拟机会保证一个类的< clint>() 方法在多线程环境中被正确加锁和同步



![](http://mk-images.tagao.top/img/202206021211474.png?imageslim)





## 3.3类初始化



- <font color='red'>**类的主动引用（一定会发生类的初始化）**</font>
  - **当虚拟机启动，先初始化main方法所在的类**
  - **new一个类的对象**
  - **调用类的静态成员（除了final常量）和静态方法**
  - **使用java.lang.refect包的方法对类进行反射调用**
  - **当初始化一个类，如果其父类没有被初始化，则先会初始化它的父类**
- <font color='red'>**类的被动引用（不会发生类的初始化）**</font>
  - **当访问一个静态域时，只有真正声明这个域的类才会被初始化。**
    * **如：当通过子类引用父类的静态变量，不会导致子类初始化**
  - **通过数组定义类引用，不会触发此类的初始化**
  - **引用常量不会触发此类的初始化（常量在连接阶段就存入调用类的常量池中了）**





## 3.4类加载器的作用

- **类加载器的作用：将class文件字节码内容加载到内存中，并将这些**<font color='red'>**静态数据转换成方法区的运行时数据结构**</font>，**然后在堆中生成一个代表这个类的java.lang.Class对象，作为方法区中数据的访问入口**
- **类缓存：标准的JavaSE类加载器可以按要求查找类，但一旦某个类被加载到类加载器中，它将维持加载（缓存）一段时间。不过JVM垃圾回收机制可以回收这些Class对象**

![](http://mk-images.tagao.top/img/image-20210625004924420.png)



![](http://mk-images.tagao.top/img/image-20210625003116573.png)

## 3.5不同的ClassLoader

**类加载器作用是用来把类(class)装载进内存的。JVM规范定义了如下类型的类的加载器**



<font color='red'>**负责java平台核心类库，D:\Java\jre\lib\rt.jar中**</font>

![](http://mk-images.tagao.top/img/image-20210625004940364.png)





```java
//1. 对于自定义类，使用系统类加载器进行加载
ClassLoader classLoader = ClassLoaderUse.class.getClassLoader();
System.out.println(classLoader);

//2. 调用系统类加载器的getParent()：获取扩展类加载器
ClassLoader classLoader1 = classLoader.getParent();
System.out.println(classLoader1);

//3. 调用扩展类加载器的getParent()：无法获取引导类加载器
//引导类加载器主要负责加载java的核心类库，无法加载自定义类的。
ClassLoader classLoader2 = classLoader1.getParent();
System.out.println(classLoader2);

//4. 无法直接获取引导类加载器
ClassLoader classLoader3 = String.class.getClassLoader();
System.out.println(classLoader3);


//5.判断JDK内置类是那个加载器
ClassLoader classLoader4 = Class.forName("java.lang.Object").getClassLoader();
System.out.println(classLoader4);

//6.获取系统类加载器可以加载的路径
String property = System.getProperty("java.class.path");
String[] splits = property.split(";");
Stream.of(splits).forEach(System.out::println);
```



<font color='red'>**类加载器的一个主要方法：getResourceAsStream(String str):获取类路径下的指定文件的输入流**</font>



```java
Properties pros = new Properties();
//此时的文件默认在当前的module下。
//读取配置文件的方式一：
FileInputStream fis = new FileInputStream("src\\main\\resources\\jdbc.properties");
pros.load(fis);
System.out.println("user = " + pros.getProperty("user") + ",password = " + pros.getProperty("password"));


//读取配置文件的方式二：使用ClassLoader
//配置文件默认识别为：当前module的src下
ClassLoader classLoader = ClassLoaderUse.class.getClassLoader();
InputStream is = classLoader.getResourceAsStream("jdbc.properties");
pros.load(is);
System.out.println("user = " + pros.getProperty("user") + ",password = " + pros.getProperty("password"));

```





# 4.创建运行时类的对象(重点)

**创建类的对象：**调用Class对象的<font color='red'>**newInstance()**</font>方法

要 求： 

<font color='red'>**1）类必须有一个无参数的构造器。**</font>

<font color='red'>**2）类的构造器的访问权限需要足够。**</font>



<font color='red'>**疑问：**</font> **难道没有无参的构造器就不能创建对象了吗？**

```markdown
不是！没有无参构造器，可以调用有参构造器。 
只要在操作的时候明确的调用类中的构造器，并将参数传递进去之后，才可以实例化操作。
```



步骤如下：

1）通过Class类的**getDeclaredConstructor(Class … parameterTypes)**取得本类的指定形参类

型的构造器

2）向构造器的形参中传递一个对象数组进去，里面包含了构造器中所需的各个参数。

3）通过Constructor实例化对象。





## 4.1具体使用（重要）

创建运行时对象与<font color='red'>**2.3创建Class类的实例**</font>大同小异。

在实际编码中通过Class.forName的方式最为常用，因为可以根据具体需要来动态获取Class对象。

**已知一个类的全名，且该类在类路径下，可通过Class类的静态方法forName()获取** 

​								`Class clazz = Class.forName("java.lang.String");`



实例代码使用的是通过 类.class 获取实例对象

```java
Class<Person> clazz = Person.class;
Person person = clazz.getDeclaredConstructor().newInstance();
System.out.println(person);
```



# 5.获取运行时类的完整结构

**通过反射获取运行时类的完整结构**

<font color='red'>**Field、Method、Constructor、Superclass、Interface、Annotation**</font>

- **实现的全部接口**
- **所继承的父类**
- **全部的构造器**
- **全部的方法**
- **全部的Field**
- **注解**



## 5.1获取结构



<font color='red'>**成员变量**</font>

```java
Class clazz = Person.class;
//获取属性结构
//getFields():获取当前运行时类及其父类中声明为public访问权限的属性
Field[] fields = clazz.getFields();
for(Field f : fields){
    System.out.println(f);
}
System.out.println();

//getDeclaredFields():获取当前运行时类中声明的所有属性。（不包含父类中声明的属性）
Field[] declaredFields = clazz.getDeclaredFields();
for(Field f : declaredFields){
    System.out.println(f);
}
```

<font color='red'>**成员变量  的权限修饰符、数据类型、变量名**</font>

```java
Class clazz = Person.class;
Field[] declaredFields = clazz.getDeclaredFields();
for(Field f : declaredFields){
    //1.权限修饰符
    int modifier = f.getModifiers();
    System.out.print(Modifier.toString(modifier) + "\t");

    //2.数据类型
    Class type = f.getType();
    System.out.print(type.getName() + "\t");

    //3.变量名
    String fName = f.getName();
    System.out.print(fName);
    System.out.println();
}
```



<br>

<font color='red'>**成员方法**</font>

```java
Class clazz = Person.class;
//getMethods():获取当前运行时类及其所有父类中声明为public权限的方法
Method[] methods = clazz.getMethods();
for(Method m : methods){
System.out.println(m);
}
System.out.println();
//getDeclaredMethods():获取当前运行时类中声明的所有方法。（不包含父类中声明的方法）
Method[] declaredMethods = clazz.getDeclaredMethods();
for(Method m : declaredMethods){
System.out.println(m);
}
```





<font color='red'>**成员方法的    权限修饰符  返回值类型  方法名(参数类型1 形参名1,...) throws XxxException{}**</font>



```java
Class clazz = Person.class;
Method[] declaredMethods = clazz.getDeclaredMethods();
for (Method method : declaredMethods) {

    //.权限修饰符
    System.out.print("权限修饰符为 "+Modifier.toString(  method.getModifiers()) + "\t");

    //. 方法名
    System.out.print("方法名为 " + method.getName() + " ");

    //. 获取方法声明的注解
    Annotation[] annotations = method.getAnnotations();
    Stream.of(annotations).forEach( s-> System.out.print(" "+ s));

    //. 返回值类型
    System.out.print("返回值为 " + method.getReturnType().getName() + "\t");



    //.获取形参
    Class<?>[] parameterTypes = method.getParameterTypes();
    System.out.print("(");
    if(!(parameterTypes == null && parameterTypes.length == 0)){
        for(int i = 0;i < parameterTypes.length;i++){

            if(i == parameterTypes.length - 1){
                System.out.print(parameterTypes[i].getName() + " args_" + i);
                break;
            }
            System.out.print(parameterTypes[i].getName() + " args_" + i + ",");
        }
    }
    System.out.print(")");



    //6.抛出的异常
    Class[] exceptionTypes = method.getExceptionTypes();
    if(exceptionTypes.length > 0){
        System.out.print("throws ");
        for(int i = 0;i < exceptionTypes.length;i++){
            if(i == exceptionTypes.length - 1){
                System.out.print(exceptionTypes[i].getName());
                break;
            }

            System.out.print(exceptionTypes[i].getName() + ",");
        }
    }


    System.out.println();
}
```



<font color='red'>**成员方法的具体方法 参数类型 以及 参数泛形**</font>

```java
public class Candy3 {
    public static void main(String[] args) throws Exception {
    
        Method test = Candy3.class.getMethod("way1", List.class, Map.class);
        Type[] types = test.getGenericParameterTypes();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                System.out.println("原始类型 - " + parameterizedType.getRawType());
                Type[] arguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < arguments.length; i++) {
                    System.out.printf("泛型参数[%d] - %s\n", i, arguments[i]);
                }
            }
        }
    }

    public Set<Integer> way1(List<String> list, Map<Integer, Object> map) {
        return null;
    }
}
```







```java
/**
     * @date 获取构造器结构
     */
@Test
public void test1() {
    Class clazz = Person.class;
    //getConstructors():获取当前运行时类中声明为public的构造器
    Constructor[] constructors = clazz.getConstructors();
    for (Constructor c : constructors) {
        System.out.println(c);
    }

    System.out.println();
    //getDeclaredConstructors():获取当前运行时类中声明的所有的构造器
    Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
    for (Constructor c : declaredConstructors) {
        System.out.println(c);
    }
}
```



```java

/**
 * @date 获取运行时类的父类
 */
@Test
public void test2() {
    Class clazz = Person.class;

    Class superclass = clazz.getSuperclass();
    System.out.println(superclass);
}
```



```java
/**
  * @date 获取运行时类的带泛型的父类
 */
@Test
public void test03() {
    Class clazz = Person.class;
    Type genericSuperclass = clazz.getGenericSuperclass();
    System.out.println(genericSuperclass);
}

```



```java
/**
 * @date 获取运行时类的带泛型的父类的泛型
 */
@Test
public void test04() {
    Class clazz = Person.class;

    Type genericSuperclass = clazz.getGenericSuperclass();
    ParameterizedType paramType = (ParameterizedType) genericSuperclass;
    //获取泛型类型
    Type[] actualTypeArguments = paramType.getActualTypeArguments();
    System.out.println(((Class) actualTypeArguments[0]).getName());
}
```



```java

/**
 * @return null
 * @date 获取运行时类实现的接口
*/
@Test
public void test5() {
    Class clazz = Person.class;

    Class[] interfaces = clazz.getInterfaces();
    for (Class c : interfaces) {
        System.out.println(c);
    }
    System.out.println();

    //获取运行时类的父类实现的接口
    Class[] interfaces1 = clazz.getSuperclass().getInterfaces();
    for (Class c : interfaces1) {
        System.out.println(c);
    }
}
```



```java
/**
  * @date  获取运行时类的包
 */
@Test
public void test6(){
    Class clazz = Person.class;

    Package pack = clazz.getPackage();
    System.out.println(pack.getName());
}
```



```java
/**
 * @date  获取运行时类声明的注解
 */
@Test
public void test7(){
    Class clazz = Person.class;

    Annotation[] annotations = clazz.getAnnotations();
    for(Annotation annos : annotations){
        System.out.println(annos);
    }
}
```







## 5.2小结

- **在实际的操作中，取得类的信息的操作代码，并不会经常开发**
- **一定要熟悉java.lang.reflect包的作用，反射机制**
- **如何取得属性、方法、构造器的名称，修饰符等**





# 6.调用运行时类的指定结构



## 6.1调用指定的方法

**通过反射，调用类中的方法，通过Method类完成**

- **通过Class类的getMethod(String name,Class…parameterTypes)方法取得一个Method对象，并设置此方法操作时所需要的参数类型**
- **之后使用Object invoke(Object obj,Object[] age)进行调用，并向方法中传递要设置的obj对象的参数信息**







<font color='red'>**Object invoke(Object obj,Object…args)**</font>

- **Object 对应原方法的返回值，若原方法无返回值，此时返回null**
- **若原方法为静态方法，此时形参Object obj可为null**
- **若原方法形参列表为空，则Object[] args 为null**
- **若原方法声明为private，则需要在调用此invoke()方法前，显式调用方法对象的setAccessible(true)方法，将可访问private的方法**



<font color='red'>**setAccessible(boolean arg)**</font>

- **Method 和 Field、Constructor 对象都有setAccessible()方法**
- **setAccessible作用是启动和禁用访问安全检查的开关**
- 参数值为true则指示反射的对象在使用时应该取消Java语言访问检查
  - **提高反射的效率，如果代码中必须用反射，而该句代码需要频繁的被调用，那么请设置为true**
  - **使得原本无法访问的私有成员也可以访问**
- **参数值为false则指示反射的对象应该实施Java语言访问检查**



```java
/**
 * @date 如何操作运行时类中的指定的方法 -- 需要掌握
 */
@Test
public void testMethod() throws Exception {

    Class clazz = Person.class;
    //创建运行时类的对象
    Person p = (Person) clazz.newInstance();
    //1.获取指定的某个方法 getDeclaredMethod():参数1 ：指明获取的方法的名称  参数2：指明获取的方法的形参列表
    Method show = clazz.getDeclaredMethod("show", String.class);
    //2.保证当前方法是可访问的
    show.setAccessible(true);
    //3. 调用方法的invoke():参数1：方法的调用者  参数2：给方法形参赋值的实参  invoke()的返回值即为对应类中调用的方法的返回值。
    System.out.println(show.invoke(p, "CHN"));

    System.out.println("*************如何调用静态方法*****************");

    Method showDesc = clazz.getDeclaredMethod("showDesc");
    showDesc.setAccessible(true);
    //如果调用的运行时类中的方法没有返回值，则此invoke()返回
    System.out.println(showDesc.invoke(Person.class));
}


/**
 * @date  如何调用运行时类中的指定的构造器
 */
@Test
public void testConstructor() throws Exception {
    Class clazz = Person.class;
    //1.获取指定的构造器 getDeclaredConstructor():参数：指明构造器的参数列表
    Constructor constructor = clazz.getDeclaredConstructor(String.class);
    //2.保证此构造器是可访问的
    constructor.setAccessible(true);
    //3.调用此构造器创建运行时类的对象
    Person per = (Person) constructor.newInstance("Tom");
    System.out.println(per);

}
```







## 6.2调用指定属

* 在反射机制中，可以直接通过Field类操作类中的属性，通过Field类提供的set()和get()方法就可以完成设置和取得属性内容的操作。

  * <font color='red'>**public Field getField(String name) **</font>返回此Class对象表示的类或接口的指定的public的Field。 

  * <font color='red'>**public Field getDeclaredField(String name)**</font>返回此Class对象表示的类或接口的指定的Field。 

* 在Field中：

  * <font color='red'>**public Object get(Object obj)**</font> 取得指定对象obj上此Field的属性内容

  * <font color='red'>**public void set(Object obj,Object value) **</font>设置指定对象obj上此Field的属性内容



```java
@Test
public void testField() throws Exception {
    Class clazz = Person.class;

    //创建运行时类的对象
    Person p = (Person) clazz.newInstance();
    //获取指定的属性：要求运行时类中属性声明为public
    Field id = clazz.getField("id");
    //设置当前属性的值 set():参数1：指明设置哪个对象的属性   参数2：将此属性值设置为多少
    id.set(p,1001);
    // 获取当前属性的值 get():参数1：获取哪个对象的当前属性值
    int pId = (int) id.get(p);
    System.out.println(pId);
}

/**
* @date  如何操作运行时类中的指定的属性 -- 需要掌握
*/
@Test
public void testField1() throws Exception {
    Class clazz = Person.class;

    //创建运行时类的对象
    Person p = (Person) clazz.newInstance();
    //1. getDeclaredField(String fieldName):获取运行时类中指定变量名的属性
    Field name = clazz.getDeclaredField("name");
    //2.保证当前属性是可访问的
    name.setAccessible(true);
    //3.获取、设置指定对象的此属性值
    name.set(p,"Tom");
    System.out.println(name.get(p));
}
```









# 7.反射操作泛型



- **Java采用泛型擦除的机制来引入泛型，Java中的泛型仅仅是给编译器javac使用的，确保数据的安全性和免去强制类型转换问题，但是，一旦编译完成，所有和泛型有关的类型全部擦除**
- **为了通过反射操作这些类型，Java新增了ParemeterizedType，GeneriArrayType，TypeVariable 和 WildcardType 几种类型来代表不能被归一到Class类中的类型但是又和原始类型齐名的类型**



- **ParameterizedType：表示一种参数化类型，比如Collection<String> **
- **GeneriArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型**
- **TypeVariable：是各种类型变量的公共父接口**
- **WildcardType：代表一种通配符类型表达式**

 



* 对应方法参数以及方法返回值是可以通过反射拿到泛形信息，但是无法拿到成员变量的泛型信息。
  * 获取方法参数以及方法返回值泛型信息

```java
public class Candy3 {
    public static void main(String[] args) throws Exception {
        Method test = Candy3.class.getMethod("way1", List.class, Map.class);
        Type[] types = test.getGenericParameterTypes();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                System.out.println("原始类型 - " + parameterizedType.getRawType());
                Type[] arguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < arguments.length; i++) {
                    System.out.printf("泛型参数[%d] - %s\n", i, arguments[i]);
                }
            }
        }
    }
    public Set<Integer> way1(List<String> list, Map<Integer, Object> map) {
        return null;
    }
}
```



```java
ParameterizedType parameterizedType= (ParameterizedType) test.getGenericReturnType();
System.out.println("原始返回值类型 - " + parameterizedType.getRawType());
Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
for (int i = 0; i < actualTypeArguments.length; i++) {
    System.out.printf("泛型参数[%d] - %s\n", i, actualTypeArguments[i]);
}
```













































