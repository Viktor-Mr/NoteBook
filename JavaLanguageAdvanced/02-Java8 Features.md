# Java8新特性

![](http://mk-images.tagao.top/img/20220319143812.png)



## 简介

Java 8 (又称为 jdk 1.8) 是 Java 语言开发的一个主要版本。

Java 8 是oracle公司于**2014**年3月发布，可以看成是自Java 5 以来最具革命性的版本。Java 8为Java语言、编译器、类库、开发工具与JVM带来了大量新特性。

*  速度更快

* 代码更少(增加了新的语法：**Lambda** **表达式**) 

* 强大的 **Stream API**

* 便于并行

* 最大化减少空指针异常：Optional

* Nashorn引擎，允许在JVM上运行JS应用



1.Nashorn，发音"nass-horn",是德国二战时一个坦克的命名，同时也是java8新一代的javascript引擎。

2.javascript运行在jvm已经不是新鲜事了，Rhino早在jdk6的时候已经存在，但现在为何要替代Rhino，官方的解释是Rhino相比其他javascript 引擎(Google V8)实在太慢，要改造Rhino还不如重写。所以Nashorn的性能也是其一个亮点。



## 并行流与串行流

**并行流**就是把一个内容分成多个数据块，并用不同的线程分别处理每个数据块的流。相比较串行的流，并行的流可以很大程度上提高程序的执行效率。

Java 8 中将并行进行了优化，我们可以很容易的对数据进行并行操作。Stream API 可以声明性地通过 parallel() 与 sequential() 在并行流与顺序流之间进行切换。





# 1.Lambda表达式



​	Lambda 是一个**匿名函数**，我们可以把 Lambda 表达式理解为是**一段可以传递的代码**（将代码像数据一样进行传递）。使用它可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。



## 1.1语法



Lambda 表达式：在Java 8 语言中引入的一种新的语法元素和操作符。这个操作符为 “**->**” ， 该操作符被称为 **Lambda** **操作符**  或**箭头操作符**。它将 Lambda 分为两个部分：

**左侧：**指定了 Lambda 表达式需要的**参数列表** <font color='red'>（其实就是接口中的抽象方法的形参列表）</font>

**右侧：**指定了 **Lambda** **体**，是抽象方法的实现逻辑，<font color='red'>（其实就是重写的抽象方法的方法体）</font>




## 1.2使用

Lambda表达式的使用：（分为6种情况介绍）



```java
//语法格式一：无参，无返回值
@Test
public void test1(){
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("我爱北京天安门");
        }
    };
    r1.run();
    System.out.println("*******************");
    Runnable r2 = ()-> {
        System.out.println("我爱北京天安门");
    };
    r2.run();
}
```



```java
//语法格式二：Lambda 需要一个参数，但是没有返回值。
@Test
public void test2(){
    Consumer<String> con1 = new Consumer<String>() {
        @Override
        public void accept(String s) {
            System.out.println(s + 1);
        }
    };
    con1.accept("谎言和誓言的区别是什么？");
    System.out.println("*******************");

    Consumer<String> con2 = (String s)-> System.out.println(s + 2);
    con2.accept("谎言和誓言的区别是什么？");
}
```

```java
//语法格式三：数据类型可以省略，因为可由编译器推断得出，称为“类型推断”
@Test
public void test3(){
    Consumer<String> con1 = (String s) -> {
        System.out.println(s + 1);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");
    System.out.println("*******************");

    Consumer<String> con2 =  (s) -> {
        System.out.println(s + 2);
    };
    con2.accept("一个是听得人当真了，一个是说的人当真了");
}

```

```java
//语法格式四：Lambda 若只需要一个参数时，参数的小括号可以省略
@Test
public void test4(){
    Consumer<String> con1 = (s) -> {
        System.out.println(s);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");
    System.out.println("*******************");

    Consumer<String> con2 = s -> {
        System.out.println(s);
    };
    con2.accept("一个是听得人当真了，一个是说的人当真了");
}
```

```java

//语法格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值
@Test
public void test5(){
    Comparator<Integer> com1 = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
                System.out.println(o1 + o2);
            System.out.println(o1);
        }
    };
    System.out.println(com1.compare(12,21));
    System.out.println("*****************************");

    Comparator<Integer> com2 = (o1,o2) -> {
           System.out.println(o1 + o2);
        return o1.compareTo(o2);
    };
    System.out.println(com1.compare(111,222));
}
```



```java
//语法格式六：当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略
@Test
public void test6(){
    Comparator<Integer> com1 = (o1,o2) -> {
        return o1.compareTo(o2);
    };
    System.out.println(com1.compare(12,6));
    System.out.println("*****************************");

    Comparator<Integer> com2 =  (o1,o2) -> o1.compareTo(o2);
    System.out.println(com1.compare(111,222));
}
```









## 1.3总结

 *    ->左边：lambda形参列表的参数类型可以省略(类型推断)；如果lambda形参列表只有一个参数，其一对()也可以省略
 * ->右边：lambda体应该使用一对{}包裹；如果lambda体只有一条执行语句（可能是return语句），省略这一对{}和return关键字

 * <font color='red'>Lambda表达式的本质：作为函数式接口的实例</font>

 * 如果一个**接口中**，只声明了**一个抽象方法**，**则此接口就称为函数式接口**。我们可以在一个接口上使用 @FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口。
  
 * 以前用匿名实现类表示的现在都可以用Lambda表达式来写。










接口里只有一个抽象方法可以这么写，如果有多个就不行了

1. 函数式接口，不用知道方法名。2.通过泛型得知参数类型，不用知道具体的数据类型 3. 直接返回调用的结果

可以有多个方法 但只能有一个抽象方法才是函数式接口@FunctionalInterface







## 1.4类型推断

**类型推断**

​	在Lambda 表达式中的参数类型都是由编译器推断得出的。Lambda 表达式中无需指定类型，程序依然可以编译，这是因为 javac 根据程序的上下文，在后台推断出了参数的类型。Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。这就是所谓的“类型推断”。



![](http://mk-images.tagao.top/img/20220320112037.png)





# 2.函数式接口

什么是函数式(Functional)接口

* 只包含<font color='red'>一个抽象方法的接口</font>，称为**函数式接口**。 
* 你可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda 表达式抛出一个受检异常(即：非运行时异常)，那么该异常需要在目标接口的抽象方法上进行声明）。

* 我们可以在一个接口上使用 **<font color='red'>@FunctionalInterface</font>** 注解，这样做可以检查它是否是一个函数式接口。同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。

* 在java.util.function包下定义了Java 8 的丰富的函数式接口





##  2.1理解函数式接口



* Java从诞生日起就是一直倡导“一切皆对象”，在Java里面面向对象(OOP)编程是一切。但是随着python、scala等语言的兴起和新技术的挑战，Java不得不做出调整以便支持更加广泛的技术要求，也即**<font color='red'>java不但可以支持OOP还可以支持OOF（面向函数编程） </font>**

* 在函数式编程语言当中，函数被当做一等公民对待。在将函数作为一等公民的编程语言中，Lambda表达式的类型是函数。但是在Java8中，有所不同。在Java8中，Lambda表达式是对象，而不是函数，它们必须依附于一类特别的对象类型——**函数式接口。** 

* 简单的说，在Java8中，**Lambda表达式就是一个函数式接口的实例。**这就是Lambda表达式和函数式接口的关系。也就是说，只要一个对象是函数式接口的实例，那么该对象就可以用Lambda表达式来表示。

* **以前用匿名实现类表示的现在都可以用Lambda表达式来写。**



![](http://mk-images.tagao.top/img/20220320114643.png)



![](http://mk-images.tagao.top/img/20220320114656.png)

## 2.2接口Api

**Java** **内置四大核心函数式接口**

![](http://mk-images.tagao.top/img/20220320120313.png)





**其他接口**

![](http://mk-images.tagao.top/img/20220320120353.png)







# 3. 方法引用

* 当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！

* 方法引用可以看做是Lambda表达式深层次的表达。换句话说，方法引用就是Lambda表达式，也就是函数式接口的一个实例，通过方法的名字来指向一个方法，可以认为是Lambda表达式的一个语法糖。

* 要求：<font color='red'>**实现接口的抽象方法的参数列表和返回值类型，必须与方法引用的方法的参数列表和返回值类型保持一致！(（针对于情况1和情况2）)**</font> 

* 格式：使用操作符 “**<font color='red'>::</font>**” 将类(或对象) 与 方法名分隔开来。

* 如下三种主要使用情况： 

  <font color='red'>**对象::实例方法名**</font>

  <font color='red'>**类::静态方法名**</font>

  <font color='red'>**类::实例方法名**</font>





## 3.1使用



**情况一：对象 :: 实例方法**

```java
//Supplier中的T get()
//Employee中的String getName()
@Test
public void test2() {
    Employee emp = new Employee(1001,"Tom",23,5600);
    Supplier<String> sup1 = () -> emp.getName();
    System.out.println(sup1.get());
    System.out.println("*******************");

    Supplier<String> sup2 = emp::getName;
    System.out.println(sup2.get());
}
```

![](http://mk-images.tagao.top/img/20220320133203.png)





**情况二：类 :: 静态方法**

```java
//Function中的R apply(T t)
//Math中的Long round(Double d)
@Test
public void test4() {
    Function<Double,Long> func1 = d -> Math.round(d);
    System.out.println(func1.apply(12.3));
    System.out.println("*******************");

    Function<Double,Long> func2 = Math::round;
    System.out.println(func2.apply(12.6));
}
```



![](http://mk-images.tagao.top/img/20220320133325.png)



**情况三：类 :: 实例方法  (有难度)**

```java
// Comparator中的int comapre(T t1,T t2)
// String中的int t1.compareTo(t2)
@Test
public void test5() {
    Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
    System.out.println(com1.compare("abc","abd"));
    System.out.println("*******************");

    Comparator<String> com2 = String::compareTo;
    System.out.println(com2.compare("abc","abe"));

}

// Function中的R apply(T t)
// Employee中的String getName();
@Test
public void test7() {
    Employee employee1 = new Employee(1001, "Jerry", 23, 6000);
    Function<Employee,String> func1 = e -> e.getName();
    System.out.println(func1.apply(employee1));
    System.out.println("*******************");
    Employee employee2 = new Employee(1001, "tom", 23, 6000);
    Function<Employee,String> func2 = Employee ::getName;
    System.out.println(func2.apply(employee2));

}
```

![](http://mk-images.tagao.top/img/20220320133356.png)

<font color='red'>**注意：当函数式接口方法的第一个参数是需要引用方法的调用者，并且第二个参数是需要引用方法的参数(或无参数)时：ClassName::methodName**</font>





# 4.构造器与数组|引用

## 4.1构造器引用

**格式：** **ClassName::new** 

​	与函数式接口相结合，自动与函数式接口中方法兼容。

​	可以把构造器引用赋值给定义的方法，**要求构造器参数列表要与接口中抽象方法的参数列表一致！且方法的返回值即为构造器对应类的对象**

![](http://mk-images.tagao.top/img/20220320133915.png)



```java
//BiFunction中的R apply(T t,U u)
@Test
public void test3(){
    BiFunction<Integer,String,Employee> func1 = (id, name) -> new Employee(id,name);
    System.out.println(func1.apply(1001,"Tom"));
    System.out.println("*******************");

    BiFunction<Integer,String,Employee> func2 = Employee :: new;
    System.out.println(func2.apply(1002,"Tom"));
}
```





## 4.2数组引用

**格式：** **type[] :: new**



*     可以把数组看做是一个特殊的类，则写法与构造器引用一致。


![](http://mk-images.tagao.top/img/20220320134519.png)

```java
//数组引用
//Function中的R apply(T t)
@Test
public void test4(){
    Function<Integer,String[]> func1 = length -> new String[length];
    String[] arr1 = func1.apply(5);
    System.out.println(Arrays.toString(arr1));
    System.out.println("*******************");

    Function<Integer,String[]> func2 = String[] :: new;
    String[] arr2 = func2.apply(10);
    System.out.println(Arrays.toString(arr2));
}
```





# 5.强大的Stream API

**Stream API说明**

*  Java8中有两大最为重要的改变。第一个是 **Lambda** **表达式**；另外一个则是 **Stream API**。 

*  **Stream API ( java.util.stream) 把真正的函数式编程风格引入到Java中**。这是目前为止对Java类库最好的补充，因为Stream API可以极大提供Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。

* Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。 **使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。也可以使用 Stream API 来并行执行操作。**简言之，Stream API 提供了一种高效且易于使用的处理数据的方式。





## 5.1为什么要使用Stream API

* 实际开发中，项目中多数数据源都来自于Mysql，Oracle等。但现在数据源可以更多了，有MongDB，Radis等，而这些NoSQL的数据就需要 Java层面去处理。 

* Stream 和 Collection 集合的区别：<font color='red'>**Collection 是一种静态的内存数据结构，而 Stream 是有关计算的。前者是主要面向内存，存储在内存中，后者主要是面向 CPU，通过 CPU 实现计算。**</font>





**什么是 Stream**

**Stream到底是什么呢？是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。**

**“集合讲的是数据，Stream讲的是计算！”**

**注意：**

①Stream 自己不会存储元素。

②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。 

③**Stream 操作是延迟执行的**。这意味着他们会等到需要结果的时候才执行。





## 5.2Stream 的操作三个步骤

* **1- 创建 Stream**

**一个数据源（如：集合、数组），获取一个流**

* **2- 中间操作**

**一个中间操作链，对数据源的数据进行处理**

* **3- 终止操作(终端操作)** 

**一旦执行终止操作，就执行中间操作链，并产生结果。之后，不会再被使用**



![](http://mk-images.tagao.top/img/20220320135131.png)



## 5.3 Stream创建方式

**创建 Stream方式一：通过集合**

**Java8 中的 Collection 接口被扩展，提供了两个获取流**

**的方法：** 

* <font color='red'>**default Stream<E> stream() : 返回一个顺序流**</font>

* <font color='red'>**default Stream<E> parallelStream() : 返回一个并行流**</font>

```java
//创建 Stream方式一：通过集合
@Test
public void test1() {
    List<Employee> employees = EmployeeData.getEmployees();
    //default Stream<E> stream() : 返回一个顺序流
    Stream<Employee> stream = employees.stream();

    // default Stream<E> parallelStream() : 返回一个并行流
    Stream<Employee> parallelStream = employees.parallelStream();
}
```





**创建 Stream方式二：通过数组**

**Java8 中的 Arrays 的静态方法 stream() 可以获取数组流：**

<font color='red'>**static <T> Stream<T> stream(T[] array): 返回一个流**</font>

**重载形式，能够处理对应基本类型的数组：**

* **public static IntStream stream(int[] array)**

* **public static LongStream stream(long[] array)**

* **public static DoubleStream stream(double[] array)**

```java
@Test
public void test2() {
    int[] arr = new int[]{1, 2, 3, 4, 5, 6};
    //调用Arrays类的static <T> Stream<T> stream(T[] array): 返回一个流
    IntStream stream = Arrays.stream(arr);

    Employee e1 = new Employee(1001, "Tom");
    Employee e2 = new Employee(1002, "Jerry");
    Employee[] arr1 = new Employee[]{e1, e2};
    Stream<Employee> stream1 = Arrays.stream(arr1);
}
```







**创建 Stream方式三：通过Stream的of()**

**可以调用Stream类静态方法 of(), 通过显示值创建一个流。它可以接收任意数量的参数。**

* <font color='red'> **public static<T> Stream<T> of(T... values) :** **返回一个流**</font>



```java
//创建 Stream方式三：通过Stream的of()
@Test
public void test3() {
    Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
}
```



**创建 Stream方式四：创建无限流**

**可以使用静态方法 Stream.iterate() 和 Stream.generate(),创建无限流。**

**迭代**

<font color='red'>**public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)** </font>

 **生成**

<font color='red'>**public static<T> Stream<T> generate(Supplier<T> s)** </font>



```java
//创建 Stream方式四：创建无限流
@Test
public void test4() {
    //      迭代
    //      public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
    //遍历前10个偶数
    Stream.iterate(0,t -> t + 2).limit(10).forEach(System.out::println);

    //      生成
    //      public static<T> Stream<T> generate(Supplier<T> s)
    Stream.generate(Math::random).limit(10).forEach(System.out::println);
}
```





## 5.4Stream中间操作

**Stream** **的中间操作**

​		**多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。** 



### 1-筛选与切片

​		

![](http://mk-images.tagao.top/img/20220320140024.png)



```java
//1-筛选与切片
@Test
public void test1(){
    List<Employee> list = EmployeeData.getEmployees();
    //filter(Predicate p)——接收 Lambda ， 从流中排除某些元素。
    //练习：查询员工表中薪资大于7000的员工信息
    list.stream().filter( (employee -> employee.getSalary() >= 7000 ) ).forEach(System.out::println);
    System.out.println();


    //limit(n)——截断流，使其元素不超过给定数量。
    list.stream().limit(3).forEach(System.out::println);
    System.out.println();

    //skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
    list.stream().skip(3).forEach(System.out::println);
    System.out.println();

    //distinct()——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
    list.add(new Employee(1010,"刘强东",40,8000));
    list.add(new Employee(1010,"刘强东",40,8000));
    list.add(new Employee(1010,"刘强东",41,8000));
    list.add(new Employee(1010,"刘强东",41,8000));
    list.stream().distinct().forEach(System.out::println);
}
```



### 2-映射

![](http://mk-images.tagao.top/img/20220320135959.png)





```java
//映射
@Test
public void test2(){
    //map(Function f)——接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
    List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
    list.stream().map(String::toUpperCase).forEach(System.out::println);
    System.out.println();

    //练习1：获取员工姓名长度大于3的员工的姓名。
    List<Employee> employees = EmployeeData.getEmployees();
    employees.stream().map(Employee::getName).filter(name -> name.length() > 3).forEach(System.out::println);
    System.out.println();

    //练习2：
    Stream<Stream<Character>> streamStream = list.stream().map(SteamApi_02_use::fromStringToStream);
    streamStream.forEach( (s) -> System.out.println(s.collect(Collectors.toList())));
    System.out.println();


    //flatMap(Function f)——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
    Stream<Character> characterStream = list.stream().flatMap(SteamApi_02_use::fromStringToStream);
    characterStream.forEach(System.out::println);
}

//将字符串中的多个字符构成的集合转换为对应的Stream的实例
public static Stream<Character> fromStringToStream(String str){//aa
    ArrayList<Character> list = new ArrayList<>();
    for(Character c : str.toCharArray()){
        list.add(c);
    }
    return list.stream();
}
```









### 3-排序

![](http://mk-images.tagao.top/img/20220320140048.png)



```java
//排序
@Test
public void test4(){
    //sorted()——自然排序
    List<Integer> list = Arrays.asList(12, 43, 65, 34, 87, 0, -98, 7);
    list.stream().sorted().forEach(System.out::println);
    System.out.println();


    //sorted(Comparator com)——定制排序
    List<Employee> employees = EmployeeData.getEmployees();
    employees.stream().sorted( (e1,e2) -> e1.getAge() - e2.getAge() ).forEach(System.out::println);
}
```





## 5.5Stream终止操作

 

* **终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。** 

* **流进行了终止操作后，不能再次使用。**



### 1-匹配与查找

![](http://mk-images.tagao.top/img/20220320140403.png)

![](http://mk-images.tagao.top/img/20220320140425.png)



```java
//1-匹配与查找
@Test
public void test1() {
    List<Employee> employees = EmployeeData.getEmployees();

    //allMatch(Predicate p)——检查是否匹配所有元素。
    //练习：是否所有的员工的年龄都大于18
    boolean flag = employees.stream().allMatch(employee -> employee.getAge() > 18);
    System.out.println(flag);

    //anyMatch(Predicate p)——检查是否至少匹配一个元素。
    //练习：是否存在员工的工资大于 10000
    flag = employees.stream().anyMatch(employee -> employee.getSalary() > 10000);
    System.out.println(flag);

    //noneMatch(Predicate p)——检查是否没有匹配的元素。
    //练习：是否存在员工姓“雷”
    flag = employees.stream().noneMatch(employee -> employee.getName().startsWith("雷"));
    System.out.println(flag);

    //findFirst——返回第一个元素
    Optional<Employee> employee = employees.stream().findFirst();
    System.out.println(employee);

    //findAny——返回当前流中的任意元素
    Optional<Employee> any = employees.parallelStream().findAny();
    System.out.println(any);
}

@Test
public void test2() {
    List<Employee> employees = EmployeeData.getEmployees();
    // count——返回流中元素的总个数
    long count = employees.stream().filter(e -> e.getSalary() > 5000).count();
    System.out.println(count);
    System.out.println();

    //max(Comparator c)——返回流中最大值
    //练习：返回最高的工资：
    Optional<Double> max = employees.stream().map(e -> e.getSalary()).max((m1, m2) -> (int) (m1 - m2));
    System.out.println(max);
    System.out.println();

    //min(Comparator c)——返回流中最小值
    //练习：返回最低工资的员工
    Optional<Employee> min = employees.stream().min((e1, e2) -> (int) (e1.getSalary() -  e2.getSalary()));
    System.out.println(min);
    System.out.println();

    //forEach(Consumer c)——内部迭代
    employees.stream().forEach(System.out::println);
    System.out.println();


    //使用集合的遍历操作 --外部迭代
    employees.forEach(System.out::println);
}
```





### 2-归约

![](http://mk-images.tagao.top/img/20220320140519.png)



备注：map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名

```java
//2-归约
@Test
public void test3() {
    //reduce(T identity, BinaryOperator)——可以将流中元素反复结合起来，得到一个值。返回 T
    //练习1：计算1-10的自然数的和
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    Integer sum1 = list.stream().reduce(0, Integer::sum);
    Integer sum2 = list.stream().reduce(0, (s1,s2)-> s1 + s2);
    System.out.println(sum1);
    System.out.println(sum2);
    System.out.println();

    //reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
    //练习2：计算公司所有员工工资的总和
    List<Employee> employees = EmployeeData.getEmployees();
    Optional<Double> sumMoney1 = employees.stream().map(Employee::getSalary).reduce(Double::sum);
    Optional<Double> sumMoney2 = employees.stream().map(Employee::getSalary).reduce((s1,s2)->s1 + s2);
    System.out.println(sumMoney1);
    System.out.println(sumMoney2);
    //Optional<Double> sumMoney = salaryStream.reduce(Double::sum);


}
```



### 3-收集

![](http://mk-images.tagao.top/img/20220320140600.png)



Collector 接口中方法的实现决定了如何对流执行收集的操作(如收集到 List、Set、Map)。

另外， Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例，具体方法与实例如下表：



![](http://mk-images.tagao.top/img/20220320140631.png)

![](http://mk-images.tagao.top/img/202206021214084.png?imageslim)

```java
//3-收集
@Test
public void test4() {
    //collect(Collector c)——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    //练习1：查找工资大于6000的员工，结果返回为一个List或Set
    List<Employee> employees = EmployeeData.getEmployees();
    List<Employee> collect = employees.stream().filter(employee -> employee.getSalary() > 6000).collect(Collectors.toList());
    collect.forEach(System.out::println);

}
```





# 6.Optional类

* 到目前为止，臭名昭著的空指针异常是导致Java应用程序失败的最常见原因。以前，为了解决空指针异常，Google公司著名的Guava项目引入了Optional类，Guava通过使用检查空值的方式来防止代码污染，它鼓励程序员写更干净的代码。受到Google Guava的启发，Optional类已经成为Java 8类库的一部分。

*  **Optional<T> 类(java.util.Optional) 是一个容器类**，**它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。** 

*  Optional类的Javadoc描述如下：这是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。





## 6.1使用

*  Optional提供很多有用的方法，这样我们就不用显式进行空值检测。

  

* 创建Optional类对象的方法：

  * **<font color='red'>Optional.of(T t) : 创建一个 Optional 实例，t必须非空；</font>** 

  * **<font color='red'>Optional.empty() : 创建一个空的 Optional 实例</font>**

  * **<font color='red'>Optional.ofNullable(T t)：t可以为null</font>**



*  判断Optional容器中是否包含对象：

  * **boolean isPresent() : 判断是否包含对象**

  * **void ifPresent(Consumer<? super T> consumer) ：如果有值，就执行Consumer接口的实现代码，并且该值会作为参数传给它。**

    

* 获取Optional容器的对象：

  * **T get(): 如果调用对象包含值，返回该值，否则抛异常**

  * **T orElse(T other) ：如果有值则将其返回，否则返回指定的other对象。**

  * **T orElseGet(Supplier<? extends T> other) ：如果有值则将其返回，否则返回由Supplier接口实现提供的对象。**

  * **T orElseThrow(Supplier<? extends X> exceptionSupplier) ：如果有值则将其返回，否则抛出由Supplier接口实现提供的异常。**





```java
@Test
public void test3(){
    Boy boy = null;
    //boy = new Boy();
    //boy = new Boy(new Girl("苍老师"));
    String girlName = getGirlName(boy);
    System.out.println(girlName);
}

//使用Optional类的getGirlName():
public String getGirlName(Boy boy){
    Optional<Boy> boyOptional = Optional.ofNullable(boy);
    //此时的boy1一定非空
    Boy boy1 = boyOptional.orElse(new Boy(new Girl("迪丽热巴")));
    Girl girl = boy1.getGirl();

    Optional<Girl> girlOptional = Optional.ofNullable(girl);
    //girl1一定非空
    Girl girl1 = girlOptional.orElse(new Girl("古力娜扎"));

    return girl1.getName();
}
```





# 7.新时间日期和API



## 7.1使用LocalDate、LocalTime、LocalDateTime

**LocalDate、LocalTime、LocalDateTime 类的实例是不可变的对象，分别表示使用 ISO-8601日历系统的日期、时间、日期和时间。它们提供了简单的日期或时间，并不包含当前的时间信息。也不包含与时区相关的信息。**

注：ISO-8601日历系统是国际标准化组织制定的现代公民的日期和时间的表示法

```java
LocalDateTime dateTime1 = LocalDateTime.now();
System.out.println(dateTime1);

LocalDateTime dateTime2 = LocalDateTime.of(2016, 11, 21, 10, 10);
System.out.println(dateTime2);
//增加
LocalDateTime dateTime3 = dateTime2.plusDays(5);
System.out.println(dateTime3);
//减少
LocalDateTime dateTime4 = dateTime3.minusDays(2);
System.out.println(dateTime4);
```





![](http://mk-images.tagao.top/img/20220321101828.png)

## 7.2Instant 时间戳

⚫ 用于“时间戳”的运算。它是以Unix元年(传统的设定为UTC时区<font color='red'>1970年1月1日</font>午夜时分)开始所经历的描述进行运算

```java
Instant ins = Instant.now();  //默认使用 UTC 时区
System.out.println(ins);
System.out.println(ins.getEpochSecond());
System.out.println(ins.toEpochMilli());

Instant ins2 = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));  //东8区
System.out.println(ins2);
System.out.println(ins2.getEpochSecond());
System.out.println(ins2.toEpochMilli());
```







## 7.3Duration 和 Period

⚫ Duration:用于计算两个“时间”间隔

⚫ Period:用于计算两个“日期”间隔

```java
Instant ins1 = Instant.now();
Thread.sleep(1000);
Instant ins2 = Instant.now();
System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));

System.out.println("----------------------------------");

LocalDate ld1 = LocalDate.now();
LocalDate ld2 = LocalDate.of(2011, 1, 1);
System.out.println(Period.between(ld1,ld2));
```



## 7.4日期的操纵

⚫ TemporalAdjuster : 时间校正器。有时我们可能需要获取例如：将日期调整到“下个周日”等操作。 

⚫ TemporalAdjusters: 该类通过静态方法提供了大量的常用 TemporalAdjuster 的实现。



```java
LocalDateTime ldt1 = LocalDateTime.now();
System.out.println(ldt1);

LocalDateTime ldt2 = ldt1.withDayOfMonth(10);
System.out.println(ldt2);

//TemporalAdjusters 静态方法对TemporalAdjuster的实现 | 下个周日
LocalDateTime ldt3 = ldt1.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
System.out.println(ldt3);

//TemporalAdjuster 自定义：下个工作日
LocalDateTime ldt4 = ldt1.with(l->{
    LocalDateTime ldt5 = (LocalDateTime) l;

    DayOfWeek dayOfWeek = ldt5.getDayOfWeek();
    if(dayOfWeek.equals(DayOfWeek.FRIDAY)){
        return ldt5.plusDays(3);
    }else if(dayOfWeek.equals(DayOfWeek.SATURDAY)){
        return ldt5.plusDays(2);
    }else{
        return ldt5.plusDays(1);
    }
});
System.out.println(ldt4);

```



## 7.5解析与格式化

java.time.format.DateTimeFormatter类：该类提供了三种格式化方法： 

⚫ 预定义的标准格式

⚫ 语言环境相关的格式

⚫ 自定义的格式

```

LocalDateTime ldt = LocalDateTime.now();

//API的日期格式
DateTimeFormatter dtf1 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
String strDate1 = ldt.format(dtf1);
System.out.println(strDate1);

//自定义日期格式
DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
String strDate2 = ldt.format(dtf2);
System.out.println(strDate2);

//字符串时间转换为LocalDateTime
LocalDateTime dateTime =  LocalDateTime.parse(strDate2, dtf2);
System.out.println(dateTime);
```

## 7.6时区的处理

⚫ Java8 中加入了对时区的支持，带时区的时间为分别为：ZonedDate、ZonedTime、ZonedDateTime

其中每个时区都对应着 ID，地区ID都为 “{区域}/{城市}”的格式

例如 ：Asia/Shanghai 等

ZoneId：该类中包含了所有的时区信息

getAvailableZoneIds() : 可以获取所有时区时区信息

of(id) : 用指定的时区信息获取ZoneId 对象

```java
//获取所有的时区
Set<String> set = ZoneId.getAvailableZoneIds();
set.forEach(System.out::println);

LocalDateTime ldt = LocalDateTime.now(ZoneId.of("US/Pacific"));
System.out.println(ldt);

ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
System.out.println(zdt);
```





## 7.7与传统日期处理的转换





![](http://mk-images.tagao.top/img/20220321102011.png)



# 8.接口中的默认方法与静态方法



Java8之前的接口中只能定义全局常量，抽象方法，

Java8之后的接口中能定义<font color='red'>全局常量，抽象方法，默认方法以及静态方法</font>



## 8.1默认方法

Java 8中允许接口中包含具有具体实现的方法，该方法称为“默认方法”，默认方法使用 default 关键字修饰。



![](http://mk-images.tagao.top/img/20220320205610.png)

**接口中的默认方接口默认方法的<font color='red'>”类优先”</font>原则**

若一个接口中定义了一个默认方法，而另外一个父类或接口中又定义了一个同名的方法时

* **(情况一)**选择父类中的方法。如果一个**父类**提供了具体的实现，那么**接口**中具有相同名称和参数的默认方法会被**忽略**。 
* **(情况二)**接口冲突。如果一个父接口提供一个默认方法，而另一个接口也提供了一个具有相同名称和参数列表的方法（不管方法是否是默认方法），那么必须覆盖该方法来解决冲突



情况一：

​				![](http://mk-images.tagao.top/img/20220320210143.png)



情况二：

![](http://mk-images.tagao.top/img/20220320205920.png)



## 8.2接口中的静态方法

Java8 中，接口中允许添加静态方法。

![](http://mk-images.tagao.top/img/20220320210227.png)







# 9.重复注解与类型注解

Java 8对注解处理提供了两点改进：可重复的注解及可用于类型的注解。

![](http://mk-images.tagao.top/img/20220321111754.png)





# PS需要的实体类

```java
public class Employee {

	private int id;
	private String name;
	private int age;
	private double salary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Employee() {
		System.out.println("Employee().....");
	}

	public Employee(int id) {
		this.id = id;
		System.out.println("Employee(int id).....");
	}

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Employee(int id, String name, int age, double salary) {

		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", salary=" + salary + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Employee employee = (Employee) o;

		if (id != employee.id)
			return false;
		if (age != employee.age)
			return false;
		if (Double.compare(employee.salary, salary) != 0)
			return false;
		return name != null ? name.equals(employee.name) : employee.name == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + age;
		temp = Double.doubleToLongBits(salary);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
```



```java
public class EmployeeData {
	public static List<Employee> getEmployees(){
		List<Employee> list = new ArrayList<>();
		
		list.add(new Employee(1001, "马化腾", 34, 6000.38));
		list.add(new Employee(1002, "马云", 12, 9876.12));
		list.add(new Employee(1003, "刘强东", 33, 3000.82));
		list.add(new Employee(1004, "雷军", 26, 7657.37));
		list.add(new Employee(1005, "李彦宏", 65, 5555.32));
		list.add(new Employee(1006, "比尔盖茨", 42, 9500.43));
		list.add(new Employee(1007, "任正非", 26, 4333.32));
		list.add(new Employee(1008, "扎克伯格", 35, 2500.32));
		
		return list;
	}
}
```

