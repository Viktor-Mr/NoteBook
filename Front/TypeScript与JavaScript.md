TypeScript 是 JavaScript 的一个超集，理解 TypeScript 与 JavaScript 区别可选择合适的语言。



# 1.TypeScript

> TypeScript 是 Microsoft 开发和维护的一种面向对象的编程语言。它是 JavaScript 的超集，包含了 JavaScript 的所有元素，可以载入 JavaScript 代码运行，并扩展了 JavaScript 的语法。



TypeScript 具有以下特点：

* TypeScript 增加了静态类型、类、模块、接口和类型注解

* TypeScript 可用于开发大型的应用
  

JavaScript 与 TypeScript的主要差异



- TypeScript 从核心语言方面和类概念的模塑方面对 JavaScript 对象模型进行扩展。
- TypeScript 为函数提供了[**缺省参数值**](https://blog.csdn.net/weixin_41289858/article/details/81364954)。
- TypeScript 中的数据要求带有明确的类型，JavaScript不要求。
- TypeScript 引入了 JavaScript 中没有的“类”概念。
- TypeScript 中引入了模块的概念，可以把声明、数据、函数和类封装在模块中。





**TypeScript 比 JavaScript 更好吗**

> 根据我的描述，TypeScript似乎只是JS的一个更好的版本。所以你可能会认为TS会在不久的将来取代JavaScript。其实不然，我仍然相信JavaScript会有用武之地。





* 复杂性是一个需要考虑的关键因素。

​	JavaScript 非常适合更简单的应用程序，因为它可以在所有平台（跨平台）上运行并且非常轻量级。另外，与JS的最小开销相比，编译TS代码需要的时间和CPU资源对项目而言会更麻烦。

与JavaScript相比，TypeScript有很多好处。
	TS 使代码重构变得更加容易，并且更强调显式类型，使开发人员能够掌握各种组件的交互方式。由于它支持编译时调试，对于处理大型复杂应用程序的团队来说，有一定的好处。



为任何项目设置TypeScript都是容易的。一些框架，如Angular，默认使用TypeScript。因此，在我看来TypeScript更胜一筹。



# 2.语法



TypeScript 的基本数据类型 有boolean、number 、string 、 array 、 enum 、any 、void。



# 3.TS的类

​	类是面向对象编程的核心基础，是属性和方法的集合，类不能真接编写程序时引用，必须实例化后才能使用。

​	创建一个TypeScript类时，必须使用关键字class进行声明，该关键字后紧跟类的名称，之后用大括号将类体进行封装，类的基本声明格式如下。

```typescript
class 类名{
    //类体
}
```

```typescript
class 类名{
  name:string;  //定义类的属性
  fun(){ //定义类的方法
           //定义该方法所要实现的功能
  }
}
```

```typescript
class student{  //定义student类
  name:string;  //定义类的属性
  constructor(myname:string){ //定义构造函数
      this.name=myname;
  }
  study(){ //定义类的方法
           //定义该方法所要实现的功能
  }
}

```









[参考文章](https://blog.csdn.net/gao511147456/article/details/125809525?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-125809525-blog-121381122.pc_relevant_layerdownloadsortv1&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-125809525-blog-121381122.pc_relevant_layerdownloadsortv1&utm_relevant_index=1)