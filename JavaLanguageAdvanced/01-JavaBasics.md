​	



# 类的初始化顺序

![](http://mk-images.tagao.top/img/202204141120132.png?imageslim)



![](http://mk-images.tagao.top/img/202204141120350.png?imageslim)





![](http://mk-images.tagao.top/img/202204141126314.png?imageslim)



```markdown
//5f
//2s
//4f
//6s
//1s
//3s
```

​	

![](http://mk-images.tagao.top/img/202204141224400.png?imageslim)



疑问是在“ static Son son = new Son();” 虚拟机规范并没有规定类初始化阶段  不能执行创建对象的逻辑。类初始化就是JVM执行代码的过程，没什么特别的。

```markdown
//4
//6
//3
//1
//---------------
//5f
//2s
//4f
//6s
//1s
//3s
```

## 总结



![](http://mk-images.tagao.top/img/202204141226607.png?imageslim)

<img src="http://mk-images.tagao.top/img/202204141226913.png?imageslim"  />

<font color='red'>**1、javac编译实现：**</font>

​    javac编译器生成一个`<clinit>`方法，把“静态变量的初始化语句”和“static代码块语句”按照代码中的顺序编译到`<clinit>`方法中；

  javac编译器把“实例变量的初始化语句”和“实例代码块的语句”按照代码中的顺序编译到了构造方法中；



<font color='red'>**2、类初始化：**</font>

 类初始化前，其直接超类已经被初始化，直接超类的直接超类也需如此。即javac生成的` <clinit>`方法由JVM在“类初始化阶段”调用，调用类的`<clinit>`方法前，必须先调用父类的`<clinit>`方法。

<font color='red'>**3、创建对象、对象初始化 **</font>

  执行构造方法在堆中创建一个对象。在没有显示调用父类构造方法情况下，所有构造方法的首行都会隐含调用父类的无参构造方法。

