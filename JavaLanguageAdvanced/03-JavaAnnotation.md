# **注解（Annotation）**



## 1.注解(Annotation)概述

- Annotation 是从 JDK 1.5 开始引入的新技术
- Annotation 的作用
  - 不是程序本身，可以对程序作出解释（这一点和注释没什么区别）
  - 可以被其它程序（比如：编译器等）读取
- Annotation 的格式
  - 注解是以 “@注释名” 在代码中存在的，还可以添加一些参数值，例如： @SuppressWarnings(value=“unchecked”)
- Annotation 在哪里使用？
  - 可以附加在 package，class，method，field 等上面，相当于给他们添加了额外的辅助信息，我们可以通过反射机制编程实现对这些元数据的访问





## 2.常见的Annotation示例

- @Override：定义在 java.lang.Override 中，此注解只适用于修辞方法，表示一个方法声明打算重写超类中的另一个方法声明  **（重写）**



- @Deprecated：定义在 java.lang.Deprecated 中，此注释可以用于修辞方法，属性，类，表示不鼓励程序员使用这样的元素，通常是因为它很危险或者存在更好的选择 **（废弃）**





* @SuppressWarnings：定义在 java.lang.SuppressWarings 中，用来抑制编译时的警告信息，与前两个注释有所不同，你需要添加一个参数才能正确使用，这些参数都是已经定义好了的，我们选择性的使用就好了**（警告）**

* * @SuppressWarnings(“all”)
  * @SuppressWarnings(“unchecked”)
  * @SuppressWarnings(value={“unchecked”,“deprecation”})

- 等等…





## 3. JDK中的元注解

- 元注解的作用就是负责注解其它注解，Java 定义了 4 个标准的 meta-annotation 类型，它们被用来提供对其它注解类型做说明



- 这些类型和它们所支持的类在 java.lang.annotation 包中可以找到（@Target，@Retention，@Documented，@Inherited）



### @Target

用于描述注解的使用范围（即：被描述的注解可以用在什么地方）

**用于描述注解的使用范围（即：被描述的注解可以用在什么地方）**



### @Retention

- *@Retention*：表示需要在什么级别保存该注释信息，用于描述注解的生命周期
  - **（SOURCE < CLASS < RUNTIME）**



### @Documented

- **@Document：说明该注解将被包含在javadoc中**

### @Inherited

- **@Inherited：说明子类可以继承父类中的注解**

  

```java
/**
 * @author fu-xiao-liu
 * @Date 2022/3/23 20:54  定义一个注解
 * Target 表示我们的注解可以用在哪些地方
 *  Retention 表示我们的注解站在什么地方还有效 runtime > class > sources
 *  Documented 表示是否将我们的注解生成在Javadoc中
 *  Inherited 子类可以继承父类的注解
 */
@Target(value = {ElementType.METHOD,ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public  @interface MyAnnotation {

}
```



##  4.自定义Annotation

- 使用 @interface 自定义注解时，自动继承了 java.lang.annotation.Annotation 接口
- 分析：
  - @interface 用来声明一个注解，格式：public @interface 注解名 {定义内容}
  - 其中的每一个方法实际上是声明了一个配置参数
  - 方法的名称就是参数的名称
  - 返回值类型就是参数的类型（返回值只能是基本类型，Class，String，enum）
  - 可以通过 default 来声明参数的默认值
  - 如果只有一个参数成员，一般参数名为 value
  - 注解元素必须要有值，我们定义注解元素时，经常使用空字符串，0 作为默认值

```java
public class CustomUse {
    /**
     * @date    注解可以显示赋值， 如果没有默认值 ，我们必须给注解赋值
     */
    @MyAnnotation01(name = "小孩")
    public void way01(){};

    @MyAnnotation02("ink")
    public void test02(){}
}


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation01{
    // 注解的参数： 参数类型 + 参数名() (default 默认值);
    String name() ;
    int age() default 0;
    int id() default -1;

    String[] schools() default "幼儿园";
}


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation02{
    String value();
}
```









## 5.利用反射获取注解信息

## （在反射部分涉及）







##  6.JDK 8中注解的新特性

