# Python 迭代器与生成器

![image-20191220221637226](https://cdn.jsdelivr.net/gh/Colins-Ford/Image@master/20200424165648.jpg)

## 可迭代对象（iterable）

在Python世界里，一切皆对象。对象根据定义的维度，又可以分为各种不同的类型，比如：字符串对象，列表对象等。

什么样的对象才可以叫做可迭代对象呢？

可以用一句话来表述：“实现了 `__iter__` 方法的对象就叫做可迭代对象”

`__iter__` 方法的作用就是返回一个可迭代对象。直观理解就是能使用 `for` 循环进行迭代的对象就是可迭代对象，比如：字符串，列表，元组，字典，集合等等，都是可迭代对象。

`for` 循环与 `__iter__` 方法又有什么关系呢？

比如我们在对一个列表进行迭代时，如下代码：

```Python
x = [1, 2, 3]
for i in x:
    print(i)
```

实际执行情况如下图：

![img](https://cdn.jsdelivr.net/gh/Colins-Ford/Image@master/20200424165707.jpg)


## 迭代器（iterator）

那么什么叫迭代器呢？它是一个带状态的对象，能够在调用 `next()` 方法时返回容器中的下一个值，任何实现了 `__iter__` 和`__next__` 方法的对象都是迭代器，`__iter__` 返回迭代器自身，`__next__` 返回容器中的下一个值，如果容器中没有更多元素了，则抛出 StopIteration 异常。

字符串，列表或元组对象都可用于创建迭代器：

**实例**

```Python
#!/usr/bin/python
# -*- encoding:utf-8 -*-

list1 = [1, 2, 3, 4]
it = iter(list1)    # 创建迭代器对象
print(next(it))     # 输出迭代器的下一个元素
print(next(it))
print(next(it))
print(next(it))
```

迭代器对象可以使用常规for语句进行遍历：

**实例**

```Python
#!/usr/bin/python
# -*- encoding:utf-8 -*-

list1 = [1, 2, 3, 4]
it = iter(list1)    # 创建迭代器对象
for x in it:
    print(x)
```

执行以上程序，输出结果如下：

```Text
1
2
3
4
```

也可以使用 next() 函数：

**实例**

```Python
import sys          # 引入 sys 模块以使用 exit 方法

list1 = [1, 2, 3, 4]
it = iter(list1)    # 创建迭代器对象

while True:
    try:
        print(next(it))
    except StopIteration:
        sys.exit()

```

执行以上程序，输出结果如下：

```Text
1
2
3
4
```

可迭代对象与迭代器直接存在以下关系：

1）可迭代对象包含迭代器。
2）如果一个对象拥有 `__iter__` 方法，其是可迭代对象；如果一个对象拥有 `__next__` 方法，其是迭代器。
3）定义可迭代对象，必须实现 `__iter__` 方法；定义迭代器，必须实现 `__iter__` 和 `__next__` 方法。

以下是关于可迭代对象与迭代器对象自主实现的举例：

```python
class MyList(object):            # 定义可迭代对象类  
  
    def __init__(self, num):  
        self.data = num          # 上边界  
  
    def __iter__(self):  
        return MyListIterator(self.data)  # 返回该可迭代对象的迭代器类的实例  


class MyListIterator(object):    # 定义迭代器类，其是MyList可迭代对象的迭代器类  
  
    def __init__(self, data):  
        self.data = data         # 上边界  
        self.now = 0             # 当前迭代值，初始为0  
  
    def __iter__(self):  
        return self              # 返回该对象的迭代器类的实例；因为自己就是迭代器，所以返回self  
  
    def __next__(self):          # 迭代器类必须实现的方法  
        while self.now < self.data:  
            self.now += 1  
            return self.now - 1  # 返回当前迭代值  
        raise StopIteration      # 超出上边界，抛出异常  


my_list = MyList(5)              # 得到一个可迭代对象  
print(type(my_list))             # 返回该对象的类型  
  
my_list_iter = iter(my_list)     # 得到该对象的迭代器实例，iter函数在下面会详细解释  
print(type(my_list_iter))
  
  
for i in my_list:                # 迭代  
    print(i)
```

------

## 生成器

在 Python 中，使用了 yield 的函数被称为生成器（generator）。

跟普通函数不同的是，生成器是一个返回迭代器的函数，只能用于迭代操作，更简单点理解生成器就是一个迭代器。

在调用生成器运行的过程中，每次遇到 yield 时函数会暂停并保存当前所有的运行信息，返回 yield 的值, 并在下一次执行 next() 方法时从当前位置继续运行。

调用一个生成器函数，返回的是一个迭代器对象。

以下实例使用 yield 实现斐波那契数列：

**实例**

```Python
import sys

def fibonacci(n):  # 生成器函数 - 斐波那契
    a, b, counter = 0, 1, 0
    while True:
        if (counter > n):
            return # 越界则返回迭代器对象
        yield a
        a, b = b, a + b
        counter += 1

f = fibonacci(10)  # f 指向由生成器返回生成的迭代器对象

while True:
    try:
        print(next(f))
    except StopIteration:
        sys.exit()
```

执行以上程序，输出结果如下：

```Text
0
1
1
2
3
5
8
13
21
34
55
```

