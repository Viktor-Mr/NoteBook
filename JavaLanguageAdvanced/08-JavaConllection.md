# Java Conllection



https://pdai.tech/md/java/collection/java-collection-all.html

## Java 集合概览

Java 集合， 也叫容器: 主要包括 Collection<font color='red'>**单列集合**</font>和 Map<font color='red'>**双列集合**</font> 两种 。Collection 存储着对象的集合，而 Map 存储着键值对(两个对象)的映射表。

- <font color='red'>**集合框架体系（Collection、Map等）位于java.util包下**</font>

## 知识完整的体系结构

<img src="http://mk-images.tagao.top/img/202204141604378.png?imageslim" style="zoom:120%;" />



## 简化的体系结构

<img src="http://mk-images.tagao.top/img/202204141611557.png?imageslim" style="zoom:120%;" />



## collection

![](http://mk-images.tagao.top/img/202204141741858.png?imageslim)





## Map

### [¶](#treemap) TreeMap

基于红黑树实现。

### [¶](#hashmap) HashMap

基于哈希表实现。

### [¶](#hashtable) HashTable

和 HashMap 类似，但它是线程安全的，这意味着同一时刻多个线程可以同时写入 HashTable 并且不会导致数据不一致。它是遗留类，不应该去使用它。现在可以使用 ConcurrentHashMap 来支持线程安全，并且 ConcurrentHashMap 的效率会更高，因为 ConcurrentHashMap 引入了分段锁。

### [¶](#LinkedHashMap)LinkedHashMap

使用双向链表来维护元素的顺序，顺序为插入顺序或者最近最少使用(LRU)顺序。





## HashCode( ) 与 equals()

默认实现：

 hashCode( ) 默认是对象的内存地址

 equals( ) 默认实现比较两个对象的内存地址



重写机制：

 hashCode( ) 实现比较计算对象属性的hashCode

 equals( ) 实现比较是两个对象的属性是否相同

![](http://mk-images.tagao.top/img/202204171338197.png?imageslim)





<img src="http://mk-images.tagao.top/img/202204171333488.png?imageslim" style="zoom:120%;" />

![](http://mk-images.tagao.top/img/202204171333219.png?imageslim)

![](http://mk-images.tagao.top/img/202204171336274.png?imageslim)







# 集合的比较

![](http://mk-images.tagao.top/img/202204172018698.png?imageslim)

| 比较          | 初始容量              | 扩容机制 | 加载因子           | 说明                   |
| ------------- | --------------------- | -------- | ------------------ | ---------------------- |
| ArrayList     | 0 （添加第一个时 10） | 1.5 倍   | 1 (全部用完才扩容) |                        |
| LinkedList    | -                     | -        | -                  | 底层双向链表，无需扩容 |
| Vector        | 10                    | 2 倍     | 1 (全部用完才扩容) |                        |
| HashSet       | 0 （添加第一个时 16） | 2 倍     | 0.75               | 底层为 HashMap         |
| LinkedHashSet | 0 （添加第一个时 16） | 2 倍     | 0.75               | 继承自 HashSet         |
| TreeSet       | -                     | -        | -                  | 底层 TreeMap           |
| HashMap       | 0 （添加第一个时 16） | 2 倍     | 0.75               | 树化(链表>8、数组>64)  |
| LinkedHashMap | 0 （添加第一个时 16） | 2 倍     | 0.75               | 继承自 HashMap         |
| TreeMap       | -                     | -        | -                  | 底层红黑树，无需扩容   |
| Hashtable     | 11                    | 2 倍 + 1 | 0.75               | 11 - 23 - 47           |

![](http://mk-images.tagao.top/img/202204172019985.png?imageslim)







# 工具类



## 1.Collections

- Collections 是一个操作 Set、List、Map 等集合的工具类
- Collections 中提供了一系列静态的方法对集合元素进行排序、查询、修改等操作

### （1）排序

> 操作对象为List集合，是有序的
>
> 静态方法，返回void



| 方法                              | 返回 | 属性   | 说明                           |
| :-------------------------------- | ---- | ------ | ------------------------------ |
| **reverse**(List list)            | void | static | 反转List集合中元素的顺序       |
| **shuffle**(List list)            | void | static | 随机排序（可用于抽奖）         |
| **sort**(List list)               | void | static | 升序排序（自然顺序）           |
| **sort**(List list, Comparator c) | void | static | 按比较器c的规则排序            |
| **swap**(List list, int i, int j) | void | static | 把List中i、j位置的元素交换顺序 |



### （2）查找、替换

| 方法                                          | 返回    | 属性   | 说明                                    |
| --------------------------------------------- | ------- | ------ | --------------------------------------- |
| **max**(Collection coll)                      | Object  | static | 最大元素（按自然顺序）                  |
| **max**(Collection coll, Comparator comp)     | Object  | static | 最大元素（按比较器comp指定规则的顺序）  |
| **min**(Collection coll)                      | Object  | static | 最小元素（按自然顺序）                  |
| **min**(Collection coll, Comparator comp)     | Object  | static | 最小元素（按比较器comp指定规则的顺序）  |
| **frequency**(Collection c, Object o)         | int     | static | 元素o在集合c中出现的次数                |
| **copy**(List dest, List src)                 | void    | static | 将List集合src中的内容复制到dest         |
| **replaceAll**(List list, T oldVal, T newVal) | boolean | static | 用新值newVal替换list中的所有旧值 oldVal |

### （3）同步控制

- Collections 类中提供了多个 synchronizedXxx() 方法，可把指定的集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全
- 都是 static 方法

![](http://mk-images.tagao.top/img/202204172022853.png?imageslim)





## 2.Arrays



## （1）概述

![](http://mk-images.tagao.top/img/202204172020811.png?imageslim)

## （2）常用方法（静态）

- void Arrays.sort(Object[] array)

  > 对数组按照升序排序

- List Arrays.asList(数组)

  > 返回由指定数组支持的一个固定大小的列表







## 二叉查找树（BST）





```markdown
# 定义
- 1.左子树上所有结点的值均小于或等于它的根结点的值。

- 2.右子树上所有结点的值均大于或等于它的根结点的值。

- 3.左、右子树也分别为二叉排序树。
```

![](http://mk-images.tagao.top/img/202204192201349.png?imageslim)





## 平衡二叉树



平衡二叉树用平衡因子差值来判断是否平衡，并旋转二叉树。平衡因子：左右子树高度差。平衡二叉树里平衡因子不能超过1，否则旋转。

 为了二叉树的稳定，牺牲了一些更重要的东西——时间。
 当新增删除数据导致的旋转二叉树时，很耗时间的！
 所有的操作的目的都是为了节省时间，提高效率。这样操作舍本逐末了。但也不是丝毫没有可取之处。
 使用场景：新增删除数据少，查找数据多的应用场景可以。







## 红黑树

减少平衡二叉树的旋转 - 红黑树

[漫画算法]: https://blog.csdn.net/P5dEyT322JACS/article/details/78433942
[详细讲解]: https://www.cnblogs.com/CarpenterLee/p/5503882.html

**红黑树是一种近似平衡的二叉查找树，它能够确保任何一个节点的左右子树的高度差不会超过二者中较低那个的一陪**。具体来说，红黑树是满足如下条件的二叉查找树（binary search tree）：

```markdown
# 定义
- 1.节点是红色或黑色。
- 2.根节点是黑色。
- 3.每个叶子节点都是黑色的空节点（NIL节点）。
- 4 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)
- 5.从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。
```

![](http://mk-images.tagao.top/img/202204192202682.png?imageslim)







### 1.变色：



为了重新符合红黑树的规则，尝试把红色节点变为黑色，或者把黑色节点变为红色。



### 2.旋转

#### 左旋转：



逆时针旋转红黑树的两个节点，使得父节点被自己的右孩子取代，而自己成为自己的左孩子。说起来很怪异，大家看下图：

![](http://mk-images.tagao.top/img/202204192203955.png?imageslim)

图中，身为右孩子的Y取代了X的位置，而X变成了自己的左孩子。此为左旋转。





#### 右旋转：

顺时针旋转红黑树的两个节点，使得父节点被自己的左孩子取代，而自己成为自己的右孩子。大家看下图：

![](http://mk-images.tagao.top/img/202204192204995.png?imageslim)



图中，身为左孩子的Y取代了X的位置，而X变成了自己的右孩子。此为右旋转。



