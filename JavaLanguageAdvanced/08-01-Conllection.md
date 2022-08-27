# 	单列集合



# 1.Iterator(迭代器)



## 1.Iterator基本介绍

1. Iterator对象称为迭代器，主要用于<font color='red'>**遍历Collection集合中的元素**</font>。
2. **所有Collection接口的集合类实现了iterator()，返回一个实现了Iterator接口的对象，返回一个迭代器。**
3. **Iterator 的结构**
4. **Iterator 仅用于遍历集合，Iterator 本身并不存放对象。**
5. 集合对象每次调用iterator()方法都得到一个全新的迭代器对象，<font color='red'>**默认游标都在集合的第一个元素之前。**</font>

```java
//得到一个集合的迭代器
Iterator iterator = coll.iterator();
//hasNext():判断是否还有下一个元素
while(iterator.hasNext()){
    //next(): 返回迭代中的下一个元素
    System.out.printIn(iterator.next());
}
```



## 2.Iterable 与 Iterator

![](http://mk-images.tagao.top/img/202204141731401.png?imageslim)





<font color='red'>**为什么Collection一定要去实现Iterable这个接口呢？为什么不直接实现Iterator接口呢？**</font>

------

<font color='red'>**Iterator是做迭代的，而Iterable是提供迭代器的。**</font>

因为Iterator接口的核心方法next()或者hasNext() 是**依赖于迭代器的当前迭代位置**的，是**有状态的**。 

当集合在不同方法间被传递时，由于当前迭代位置不可预知，那么next()方法的结果会变成不可预知。 而Iterable则不然，每次调用都会返回一个从头开始计数的迭代器。 多个迭代器是互不干扰的。



## 3.迭代器的执行原理

![](http://mk-images.tagao.top/img/202204141729403.png?imageslim)





# 2.Collection父接口

```JAVA
public interface Collection<E> extends Iterable<E> {}
```



- **特点**：代表一组任意类型的对象，无序、无下标、不能重复。
- **方法**：
  - `boolean add(Object obj) //添加一个对象。`
  - `boolean addAll(Collection c) //讲一个集合中的所有对象添加到此集合中。`
  - `void clear() //清空此集合中的所有对象。`
  - `boolean contains(Object o) //检查此集合中是否包含o对象。`
  - `boolean equals(Object o) //比较此集合是否与指定对象相等。`
  - `boolean isEmpty() //判断此集合是否为空。`
  - `boolean remove(Object o) //在此集合中移除o对象。`
  - `int size() //返回此集合中的元素个数。`
  - `Object[] toArray() //姜此集合转换成数组。`



# 3.List

## 1.介绍

- List集合中元素有序、且可重复，集合中的每个元素都有其对应的顺序。存取顺序一致。
- List容器中的元素都对应一个整数型的序号(索引index)记录位置。从0开始。
- 常用的实现类有：ArrayList、LinkedList 和 Vector
- ![](http://mk-images.tagao.top/img/202204141748912.png?imageslim)

## 2.List新增方法

> List除了从Collection集合继承的方法外，List集合里添加了一些根据索引来操作元素的方法。如果操作的索引index不存在，将抛出异常。

- `void add(int index,Object)`

  > 在index位置插入ele元素。index后面的元素后移。

- `boolean addAll(int index,Collection eles)`

  > 从index位置把eles中的所有元素添加进来。返回boolean

- `Object get(int index)`

  > 获取index位置的元素

- `int indexOf(Object obj)`

  > 返回obj在集合中首次出现的位置

- `int lastIndexOf(Object obj)`

  > 返回obj在集合中末次出现的位置

- `Object remove(int index)`

  > 删除index位置的元素，并返回该元素

- `Object set(int index,Object ele)`

  > 把index位置的元素设为ele

- `List subList(int fromIndex, int toIndex)`

  > 返回从fromIndex 到 toIndex位置的子集合，不包括toIndex





## 3.ArraryList

### 1.特性

- ArrayList是List接口的典型实现类，也是主要实现类

- 线程不安全，效率高

- 可以存null值，并且可以存多个

- ArrayList本质上是一个可<font color='red'>**变长数组**</font>，数组元素为Object。
  * <font color='red'>**transient Object[] elementData**</font>

  > transient：表示瞬间的、短暂的。该属性不会被序列化。

```java
ArrayList的序列化机制：
	1)ArrayList实现了java.io.Serializable接口。
	2)ArrayList中存放集合元素的Object数组被关键字transient修饰。代码：transient Object[] elementData;
	3)ArrayList序列化集合元素的机制：
		ArrayList在序列化时会调用writeObject(ObjectOutputStream s)方法，将size和element写入ObjectOutputStream中；
		ArrayList在反序列化时调用readObject(java.io.ObjectInputStream s)方法，从ObjectInputStream中获取到size和element，再恢复到存储集合元素的Object数组中。

	4)与直接序列化Object数组相比，这样的机制有什么优点呢？
	
		elementData是一个Object数组，它通常会预留一些容量，等容量不足时再进行扩容；有些空间(elementData[size] ~ elementData[elementData.length-1])实际上并没有存储元素(存的是null)；
		ArrayList的序列化机制：只序列化实际存储的集合元素，而不是去实例化整个Object数组，从而节省空间和时间的消耗。

```



- <font color='red'>**ArrayList在JDK1.8前后是有区别的**</font>

  - JDK1.7：像<font color='red'>**饿汉式**</font>，直接创建一个<font color='red'>**初始容量为10的数组**</font>
  - JDK1.8：像<font color='red'>**懒汉式**</font>，一开始<font color='red'>**创建一个长度为0的数组**</font>；当添加<font color='red'>**第一个元素时再创建一个初始容量为10**</font>的数组。运行效率更高。

- Arrays.asList(…)方法返回的是List集合，既不是ArrayList实例，也不是Vector实例。是一个固定长度的List集合。

  

### 2.扩容

- 初始值：

  - 用无参构造方法 ArrayList()

    > JDK1.7初始值为：10
    >
    > JDK1.8初始值为：0。当添加第一个元素时再创建一个容量为10的数组

  - 用有参构造方法 ArrayList(int)

    > 初始值就是 int

- 扩容

  - 每次都扩大至现有容量的<font color='red'>**1.5**</font>倍

    > 容量全部用完后才开始扩容
    >
    > 如：初始为10，扩容后为15；15再扩容为15+15/2=22… new  = old +（old >> 1）
    >
    > 底层用Arrays.copyOf()



### 3.源码



![](http://mk-images.tagao.top/img/202204151556504.png?imageslim)



![](http://mk-images.tagao.top/img/202204151556676.png?imageslim)

## 4.vector

### 1.特性

- Vector是一个古老集合，JDK1.0就有了。

- 与ArrayList基本相同，区别是Vector是线程安全的，但效率比较低。

- Vector扩容机制：

  - 默认构造方法，长度为10，默认扩容至现有容量的2倍

    > 饿汉式：只要声明了vector，就马上创建容量为10的数组

  - 指定大小时，每次扩容至现有容量的2倍

  - 全部用完后才扩容



### 2.源码



1. <font color='red'>**new Vector() 底层**</font>

```java
public Vector() {
    this(10);
}

#补充：如果是  Vector vector = new Vector(8);
public Vector(int initialCapacity) {
    this(initialCapacity, 0);
}
```



2. <font color='red'>**vector.add(i)**</font>

```java
2.1  //下面这个方法就添加数据到vector集合
public synchronized boolean add(E e) {
      modCount++;
      ensureCapacityHelper(elementCount + 1);
      elementData[elementCount++] = e;
      return true;
  }
```

```java
2.2  //确定是否需要扩容 条件 ： minCapacity - elementData.length>0
private void ensureCapacityHelper(int minCapacity) {
	// overflow-conscious code
      if (minCapacity - elementData.length > 0)
          grow(minCapacity);
}
```

```java
2.3 //如果 需要的数组大小不够用，扩容两倍
//newCapacity=oldCapacity+((capacityIncrement > 0)?                       capacityIncrement:oldCapacity);
   
 private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                     capacityIncrement : oldCapacity);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```



## 5.LinkedList

### 1.特性

- 当插入、删除频繁时选择LinkedList。添加、删除效率高。

- 线程不安全，效率较高

- <font color='red'>**双向链表**</font>

  * 内部没有声明数组，而是定义了Node类型的first和last，用于记录首末元素。同时，定义<font color='red'>**内部类Node**</font>，作为LinkedList中保存数据的基本结构，其中还定义了两个变量：

  - prev：记录前一个元素的位置
  - next：记录后一个元素的位置

  ![](http://mk-images.tagao.top/img/202204150740043.png?imageslim)

  ![](http://mk-images.tagao.top/img/202204150740038.png?imageslim)

  ![](http://mk-images.tagao.top/img/202204150741705.png?imageslim)



### 2.源码



1. <font color='red'>**new LinkedList() 源码解读**</font> linkeList 的初始化节点 first = null  last = null

```java
 public LinkedList() {}
```



2. <font color='red'>**add(E e）**</font>

final 修饰变量，引用地址不可改变。 

```java
public boolean add(E e) {
    linkLast(e);
    return true;
}
# 将新的结点，加入到双向链表的最后
void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}   
```



3. <font color='red'>**linkedList.remove(); **</font>默认删除的是第一个结点

```java
public E remove() {
    return removeFirst();
}

public E removeFirst() {
    final Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return unlinkFirst(f);
}

# 执行 unlinkFirst, 将 f 指向的双向链表的第一个结点拿掉
 private E unlinkFirst(Node<E> f) {
    // assert f == first && f != null;
    final E element = f.item;
    final Node<E> next = f.next;
    f.item = null;
    f.next = null; // help GC
    first = next;
    if (next == null)
        last = null;
    else
        next.prev = null;
    size--;
    modCount++;
    return element;
}
```



## 6.比较

- 如何选择？

  > 把ArrayList作为默认选择
  >
  > 当插 入、删除频繁时选择LinkedList
  >
  > 当多个线程需要同时操作某个集合时，选择Vector，线程安全
  >
  > 由于Vector是线程安全的，很多方法有synchronized限制，效率低，尽量避免使用



- <font color='red'>**相同点**</font>

| 比较             | ArrayList、Vector、LinkedList相同点 |
| ---------------- | ----------------------------------- |
| 包               | java.util                           |
| 实现接口         | list                                |
| 元素是否有序     | 有序，存取一致                      |
| 元素是否可以重复 | 可以重复，可以存多个null            |



- <font color='red'>**不同点**</font>

| 比较     | ArrayList                                | Vector                                | LinkedList                            |
| -------- | ---------------------------------------- | ------------------------------------- | ------------------------------------- |
| 底层实现 | 可变数组                                 | 可变数组                              | <font color='red'>**双向链表**</font> |
| 版本     | JDK1.2                                   | JDK1.0                                | -                                     |
| 初始容量 | 0，第一次添加时创建10个                  | 10                                    | -                                     |
| 加载因子 | 1 (用完才扩容)                           | 1 (用完才扩容)                        | -                                     |
| 长度     | 可变长，1.5倍扩容                        | 可变长，2倍扩容                       | -                                     |
| 线程安全 | 线程不安全                               | <font color='red'>**线程安全**</font> | 线程不安全                            |
| 执行效率 | <font color='red'>**高 (查询快)**</font> | 低                                    | 中（添加、删除快）                    |
| 适用场景 | 查询 (单线程操作)                        | 多线程操作                            | 添加、删除频繁时 (单线程操作)         |







# 4.Set

## 1.介绍

- Set接口是Collection接口的子接口
- Set有些是无序的，有些是有序的(存取顺序一致，内部数组中仍是散列分布，无序的)

![](http://mk-images.tagao.top/img/202204151616316.png?imageslim)

> - HashSet是无序的，存入和取出的顺序不保证一致，不能通过索引index来获取元素；但内部也是有计算逻辑的，所以取出顺序是固定的，多次取出顺序一致
> - LinkedHashSet 有双向链表，存取顺序一致，内部数组中仍是散列分布，无序的
> - TreeSet 也是有序的



- <font color='red'>**Set集合不允许包含相同的元素**</font>，可以存null，但只能有一个。存入相同元素会失败，不报错。
- Set集合判断两个对象是否相同，<font color='red'>**用equals()方法**</font>，不用运算符
- Set接口的主要实现类有：HashSet，TreeSet。



- Set集合遍历

  > 具体实现见迭代器小节
  >
  > 普通for循环不适用。因为Set是无序的，没有索引index

  - <font color='red'>**增强for循环**</font>
  - <font color='red'>**迭代器**</font>

![](http://mk-images.tagao.top/img/202204171104959.png?imageslim)







## 2.HashSet

### 1.介绍

- HashSet底层是HashMap

  ```java
  public HashSet() {
      map = new HashMap<>();
  }
  ```

- HashMap底层是：<font color='red'>**数组 + 链表（jdk8后是尾插法） + 红黑树 **</font>(JDK1.8)；

  > JDK1.7及之前：<font color='red'>**数组 + 链表（在jdk7，这个链表是头插法）**</font>   

![](http://mk-images.tagao.top/img/202204171108553.png?imageslim)



> 由于HashSet底层是HashMap，HashMap底层有数组，所以HashSet也是有索引的，但外部不能通过索引来获取元素，这个索引值是由元素的haspCode值计算来的，是散列分布在数组中的，无序的



### 2.扩容机制



1. HashSet底层是HashMap,<font color='red'>**初始化为0**</font>， 第一次添加时,table数组扩容到16<font color='red'>**(newCap)**</font>，临界值<font color='red'>**(threshold)**</font>是12。加载因子(loadFactor)  0.75 * 16  = 12。
2. 如果HashSet的<font color='red'>**size**</font>到了临界值12,就会扩容<font color='red'>**(threshold) (16<<1) = 32**</font>，新的临界值就是32 * 0.75 = 24,依次类推
3. 在Java8中,如果一条链表的元素个数到达<font color='red'>**TREEIFY_THRESHOLD**</font>(默认是8),<font color='red'>**并且table数组的大小>=MIN_ TREEIFY_ CAPACITY(默认64)**</font>,就会进行<font color='red'>**树化(红黑树)**</font>，否则仍然采用数组扩容机制



* 只要<font color='red'>**size达到临界值**</font>就扩容 **||**  <font color='red'>**链表的长度大于8 & 数组长度小于64**</font>

> <font color='red'>**size是数组、链表、红黑树中元素的总和**</font>。即便数组中的元素数量没有达到临界值，也一样扩容。
>
> 只要向HashSet里面加一个元素，size就增加1。不管这个元素最后落在哪里（数组、链表 或者 红黑树）
>
> 如：数组为16，临界值为12，size为13，但13个元素全部在一个链表中，只占用一个数组位，数组中还有15个空位，此时仍然扩容

<img src="http://mk-images.tagao.top/img/202204171303179.png?imageslim" style="zoom:150%;" />



### 3.添加原理



**实现步骤：**

- 先计算元素的哈希值，进而得到索引

![](http://mk-images.tagao.top/img/202204171324069.png?imageslim)



- 通过索引，找到数组中的位置 (即数据表table的位置) <font color='red'>**（n-1) & hash**</font>

![](http://mk-images.tagao.top/img/202204171326628.png?imageslim)

- 索引位置没有元素时，直接加入
- 索引位置有元素，调用equals()比较。<font color='red'>**如果相同，放弃添加**</font>,进行覆盖；<font color='red'>**如果不同，以链表的形式添加到后面**</font>。

![](http://mk-images.tagao.top/img/202204171318321.png?imageslim)



深入理解

```java
public class study_01_HashSet {
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();

        Dog x1 = new Dog(1001,"AA");
        Dog x2 = new Dog(1002,"BB");
        hashSet.add(x1);
        hashSet.add(x2);
        System.out.println(x1.hashCode());
        x1.name = "cc";

        //删除失败。因为(1001,"CC")的哈希值对应位置为空。虽然此时p1为(1001,"CC")，删除时会计算(1001,"CC")哈希值,此时哈希表中这个位置没有值，所以删除失败。
        hashSet.remove(x1);

        //添加成功。因为(1001,"CC")的哈希值对应位置为空。虽然集合中已有(1001,"CC")，但集合中的(1001,"CC")哈希值对应位置还是(1001,"AA")计算得来的，"CC"只是由"AA"修改得来的
        hashSet.add(new Dog(1001,"CC"));;

        //添加成功。因为此时集合中没有元素equal=(1001,"AA")。虽然集合中第一个(1001,"CC")的哈希值对应位置和(1001,"AA")一致，但equals()不同。(1001,"AA")会挂在(1001,"CC")链表的的后面
        hashSet.add(new Dog(1001,"AA"));

        System.out.println(hashSet);

    }
}

class Dog{
    int id;
    String name ;

    public Dog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dog dog = (Dog) o;

        if (id != dog.id) return false;
        return name != null ? name.equals(dog.name) : dog.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```







## 3.LinkedHashSet

### 1.介绍

- LinkedHashSet 是 HashSet的子类
- <font color='red'>**LinkedHashSet 底层是 LinkedHashMap (HashMap的子类)**</font>
- LinkedHashSet 有双向链表，存入和取出顺序是一致的，所以表面上看是有序的，但在内部数组中仍然是散列分布，无序的
- LinkedHashSet 插入性能略低于 HashSet，但有很好的查询性能
- LinkedHashSet 不能插入重复元素（Set接口的特性）

![](http://mk-images.tagao.top/img/202204170009956.png?imageslim)

### 2.原理

![](http://mk-images.tagao.top/img/202204171422098.png?imageslim)

- 扩容机制和 HashSet 一样

* 在LinkedHashMap$Entry中实现了三个 Node,自身的before(Node)，after(Node) 以及父类的node

![](http://mk-images.tagao.top/img/202204171428508.png?imageslim)

![](http://mk-images.tagao.top/img/202204171425683.png?imageslim)



## 4.TreeSet

### 1.介绍

- <font color='red'>**TreeSet 实现了 SortedSet 接口，SortedSet 是 Set 的子接口。**</font>TreeSet 可以确保集合元素处于排序状态

- TreeSet 底层是 TreeMap。使用<font color='red'>**红黑树结构存储数据**</font>。

- 有两种排序方法：

  - **自然排序 （默认）**
- TreeMap 的所有 key 必须实现<font color='red'>**Comparator 接口**</font>，而且所有的 key 应该是同一个类的对象，否则将会抛出 ClassCastException

```java
TreeSet  set = new TreeSet();//未传入Comparator对象，按默认自然排序
set.add(new Person("absent",12));//Person 需实现 Comparator 接口，否则抛异常
set.add("hello");//String 对象实现了 Comparator 接口

class Person implements Comparator{// key类需要实现 Comparator 接口
    private String name;
    private int age;
}
```

​      

  - 定制排序

    - 创建 TreeSet 时，传入一个 Comparator 对象，该对象实现了 compare() 方法，用该方法对 key 进行排序。<font color='red'>**此时不需要 key 对象实现 Comparator 接口**</font>

      

```java
Comparator comparator = new Comparator() {
    @Override
    public int compare(Object o1, Object o2) {

        if (o1 instanceof  Person && o2 instanceof Person){
            Person p1 = (Person)o1 ;
            Person p2 = (Person)o2 ;
            return  Integer.compare(p1.getAge(),p2.getAge());
        }else {
            throw  new RuntimeException("输入的数据类型不匹配");
        }
    }
};

TreeSet  set = new TreeSet(comparator);//传入一个 Comparator 对象
set.add(new Person("absent",12));

class Person{// 有 Comparator 对象时，key类不需要实现 Comparator 接口
    private String name;
    private int age;
}
```

- 有序，查询速度比 List 快



![](http://mk-images.tagao.top/img/202204171440563.png?imageslim)

![](http://mk-images.tagao.top/img/202204171440655.png?imageslim)



<font color='red'>**会抛出ClassCastException**</font>

TreeSet 在创建时未传入 Comparator 对象，默认自然排序。此时 key 对象类需实现 Comparator 接口



## 5.比较



| 比较        | HashSet        | LinkedHashSet            | TreeSet        |
| ----------- | -------------- | ------------------------ | -------------- |
| 包          | java.util      | java.util                | java.util      |
| 父接口/父类 | Set            | Set / 父类HashSet        | Set            |
| 底层实现    | HashMap        | LinkedHashMap (双向链接) | TreeMap        |
| 元素惟一性  | 不可重复       | 不可重复                 | 不可重复       |
| 元素顺序    | 存取不一致     | 存取一致                 | 可排序         |
| 效率        | 增删快、查询慢 | 增删稍慢、查询快         | 增删慢、查询快 |



**HashSet 和 TreeSet 的去重机制**

> 适用于 HashMap 和 TreeMap
>
> HashSet 底层为 HashMap
>
> TreeSet 底层为 TreeMap

- <font color='red'>**HashSet去重机制**</font>
  - <font color='cornflowerblue'>**hashCode() + equals() 。**</font>底层先通过存入对象，进行运算得到一个hash值，通过hash值得到对应的索引，如果发现哈希表table索引所在的位置没有数据，就直接存放；如果有数据，就进行equals()比较（遍历）；比较后，不相同，就加入（挂在链表后面或红黑树上），相同就不加入。
- <font color='red'>**TreeSet去重机制**</font>
  - <font color='cornflowerblue'>**comparator。 **</font>如果传入了一个Comparator匿名对象，就通过<font color='red'>**实现的 compare()去重，方法返回0**</font>，就认为是相同的元素(或对象)，就不添加；如是没有传入一个Comparator匿名对象，那么添加的对象必须实现Comparator接口，该接口有compareTo()方法，就利用该方法去重。



