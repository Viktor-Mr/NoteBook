

# 数据类型

## Set底层存储

​	redis的集合对象set的底层存储结构特别神奇，我估计一般人想象不到，底层使用了intset和hashtable两种数据结构存储的，intset我们可以理解为数组，hashtable就是普通的哈希表（key为set的值，value为null）。是不是觉得用hashtable存储set是一件很神奇的事情。

 set的底层存储intset和hashtable是存在编码转换的，使用**intset**存储必须满足下面两个条件，否则使用hashtable，条件如下：

- 结合对象保存的所有元素都是整数值
- 集合对象保存的元素数量不超过512个

 hashtable的数据结构应该在前面的hash的章节已经介绍过了，所以这里着重讲一下**intset**这个新的数据结构好了。



![](http://mk-images.tagao.top/img/202204202205152.png?imageslim)