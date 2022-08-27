官网介绍

![image-20220210142952200](D:\笔记\Java\SpringBoot.assets\image-20220210142952200.png)

![image-20220210143006030](D:\笔记\Java\SpringBoot.assets\image-20220210143006030.png)





# 基础入门



## 1.SpringBoot特点 



### 1.1 依赖管理

* <font color='red'>父项目做依赖管理</font>

```xml
依赖管理    
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
</parent>

他的父项目
 <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.3.4.RELEASE</version>
  </parent>

几乎声明了所有开发中常用的依赖的版本号,自动版本仲裁机制
```



* <font color='red'>无需关注版本号，自动版本仲裁</font>

```xml
1、引入依赖默认都可以不写版本
2、引入非版本仲裁的jar，要写版本号。
```

  

* <font color='red'>修改默认的版本号</font>

```xml
//修改默认
<properties>
        <lombok.version>1.18.22</lombok.version>
 </properties>

//默认 1.18.20
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
  </dependencies>

```

mvn - https://mvnrepository.com/



* <font color='red'>开发导入starter场景启动器 </font>

```xml
1、以 spring-boot-starter-* ： *就某种场景
2、引入starter，这个场景的所有常规需要的依赖我们都自动引入
3、SpringBoot所有支持的场景
https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
4、以 *-spring-boot-starter： 第三方为我们提供的简化开发的场景启动器。

** 所有场景启动器最底层的依赖 ** 
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
  <version>2.3.4.RELEASE</version>
  <scope>compile</scope>
</dependency>

```





### 1.2  自动配置

- 自动配好Tomcat ,   引入starter-web 自动引入Tomcat

- - 引入Tomcat依赖。
  - 配置Tomcat

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <version>2.3.4.RELEASE</version>
      <scope>compile</scope>
 </dependency>
```



- 自动配好SpringMVC  <font color='red'>引入starter-web 自动引入</font>

- - 引入SpringMVC全套组件
  - 自动配好SpringMVC常用组件（功能）

- 

- 自动配好Web常见功能，如：字符编码问题

- - SpringBoot帮我们配置好了所有web开发的常见场景

```java
// 1 返回IOC容器
ConfigurableApplicationContext run = SpringApplication.run(first.class, args);
// 2 查看容器内的所有组件
String[] names  = run.getBeanDefinitionNames();
for (String name : names) {
    System.out.println(name);
}
```



- 默认的包结构

- - 主程序所在包及其下面的所有子包里面的组件都会被默认扫描进来
  - 无需以前的包扫描配置

- - 想要改变扫描路径，
    1. @SpringBootApplication(scanBasePackages=**"com.company"**)

- - 2. @ComponentScan 指定扫描路径

```java
@SpringBootApplication
等同于
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.company.boot")
```



- 各种配置拥有默认值

- - 默认配置最终都是映射到某个类上，如：MultipartProperties

  - 配置文件的值最终会绑定每个类上，这个类会在容器中创建对象

    

- 按需加载所有自动配置项

- - 非常多的starter
  - 引入了哪些场景这个场景的自动配置才会开启

- - SpringBoot所有的自动配置功能都在 spring-boot-autoconfigure 包里面



## 2 容器功能



### 2.1@Configuration

- 基本使用
- **Full模式与Lite模式**

- - 示例
  - 最佳实战

- - - 配置 类组件之间无依赖关系用Lite模式加速容器启动过程，减少判断
    - 配置类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式







```java
###################Configuration使用示例#####################################
/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 *      Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 *      Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
 *
 *
 *
 */
@Configuration(proxyBeanMethods = false) //告诉SpringBoot这是一个配置类 == 配置文件
public class MyConfig {

    /**
     * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return
     */
    @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User user01(){
        User zhangsan = new User("zhangsan", 18);
        //user组件依赖了Pet组件
        zhangsan.setPet(tomcatPet());
        return zhangsan;
    }

    @Bean("tom")
    public Pet tomcatPet(){
        return new Pet("tomcat");
    }
}


####################@Configuration测试代码如下##############################
@SpringBootApplication
public class first {
    public static void main(String[] args) {
        // 1 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(first.class, args);

        // 2.1 查看容器内的所有组件
        String[] names  = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        //2.2 测试 configuration 测试

        //2.2.1 从容器中获取组件
        Pet tom01 = run.getBean( "tom", Pet.class);
        Pet tom02 = run.getBean("tom", Pet.class);
        System.out.println("2.1配置类里面使用@Bean在方法上给容器注册组件，默认单实例 " +( tom01 == tom02));

        // com.company.config.MyConfig$$EnhancerBySpringCGLIB$$283c68a9@4a29f290
        MyConfig bean = run.getBean(MyConfig.class);
        System.out.println(bean);

        //@Configuration(proxyBeanMethods=true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
        //保持组件单实例
        User user1 = bean.user01();
        User user2 = bean.user01();	
        System.out.println(user1 == user2);

        User user = bean.user01();
        Pet pet = bean.pet01();
        System.out.println("用户的宠物："+(user.getPet() == pet));
    }
}

```



### 2.2 组件注解

 @Bean         是在 @Configuration容器中的组件注解 

@Component 是普通组件注解

@Controller   是控制层组件注解

@Service         是业务层组件注解

@Repository   是底层组件注解

### 



### 2.3  容器添加组件

方式一: @ComponentScan("xxx.xxx")  指定包扫描路径 



方式二: @import( {xxx.class, xxx.class})  是一个数组 ,导入指定的类作为容器的组件

```java

 给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
 

@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = false) //告诉SpringBoot这是一个配置类 == 配置文件
public class MyConfig {
   
}



 // 3.1 测试import 容器中导入指定组件
        
 //查看不同 user的名字 
 String[] name = run.getBeanNamesForType(User.class);
System.out.println("======");
for (String s : name) {
      System.out.println(s);
}

//通过不同的名字获取 组件的内容
Map<String, User> beansOfType = run.getBeansOfType(User.class);
System.out.println(beansOfType.get("user01").toString());
System.out.println(beansOfType.get("com.company.bean.User").toString());

System.out.println(run.getBean(DBHelper.class));
```



![image-20220210170625208](D:\笔记\Java\SpringBoot.assets\image-20220210170625208.png)



结论 : <font color='red'>通过import 导入与bean 导入的组件是不同的bean实例</font>





### 2.4@Conditional

条件装配:满足Conditional指定的条件,则进行组件注入

此注解可以加在类上或者方法上,  是否需要进行注入

![image-20220210201719686](D:\笔记\Java\SpringBoot.assets\image-20220210201719686.png)



## 3 配置文件引入

### 3.1@ImportResource



@ImportResource  可以<font color='red'>引入 XML文件的配置</font>信息进入容器



```xml
======================beans.xml=========================
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <bean id="haha" class="com.atguigu.boot.bean.User">
        <property name="name" value="zhangsan"></property>
        <property name="age" value="18"></property>
    </bean>

    <bean id="hehe" class="com.atguigu.boot.bean.Pet">
        <property name="name" value="tomcat"></property>
    </bean>
</beans>
```



```java
@ImportResource("classpath:beans.xml")
public class MyConfig {}

======================测试=================
        boolean haha = run.containsBean("haha");
        boolean hehe = run.containsBean("hehe");
        System.out.println("haha："+haha);//true
        System.out.println("hehe："+hehe);//true
```



### 3.2@ConfigurationProperties

作用 : 将  yml 或者 properties 配置信息导入到容器中 

<font color='red'>只有在容器中的组件,ConfigurationProperties才会生效 </font >,所以便有两种使用方法

* 1 . @ConfigurationProperties (组件上) +   @Component  (组件上)

```java
// 只有在容器中的组件，才会拥有SpringBoot提供的强大功能
@Component
@ConfigurationProperties(prefix = "mycar")
public class Car {

    private String brand;
    private Integer price;
}
```



* 2 . @ConfigurationProperties (组件上)  + @EnableConfigurationProperties (配置类上)

```java
@EnableConfigurationProperties(Car.class)
//1、开启Car配置绑定功能
//2、把这个Car这个组件自动注册到容器中
public class MyConfig 	{
}
```

@EnableConfigurationProperties注解的作用： 让使用 @ConfigurationProperties 注解的类生效



# 自动配置原理入门





## 1.引导加载自动配置类

 主配置类的启动注解   @SpringBootApplication中包含三大注解

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```



### 1.1@SpringBootConfiguration

@Configuration   代表当前是一个配置类



### 1.2@ComponentScan

指定扫描包路径 ，Spring注解



### 1.3@EnableAutoConfiguration

```java
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
}
```



#### 1.3.1@AutoConfigurationPackage

@AutoConfigurationPackage  

```java
@Import(AutoConfigurationPackages.Registrar.class)  //给容器中导入一个组件
public @interface AutoConfigurationPackage {}

//利用Registrar给容器中导入一系列组件
//将指定的一个包下的所有组件导入进来？MainApplication(主配置类) 所在包下。
```

<font color='red'>Registrar</font> 批处理 



#### 1.3.2@Import(AutoConfigurationImportSelector.class)

```java
1、利用getAutoConfigurationEntry(annotationMetadata);给容器中批量导入一些组件
2、调用List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);获取到所有需要导入到容器中的配置类
3、利用工厂加载 Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader)；得到所有的组件
4、从META-INF/spring.factories位置来加载一个文件。
	默认扫描我们当前系统里面所有META-INF/spring.factories位置的文件
    spring-boot-autoconfigure-2.3.4.RELEASE.jar包里面也有META-INF/spring.factories
    
```

![image-20220210222037054](D:\笔记\Java\SpringBoot.assets\image-20220210222037054.png)

```java
文件里面写死了spring-boot一启动就要给容器中加载的所有配置类
spring-boot-autoconfigure-2.3.4.RELEASE.jar/META-INF/spring.factories
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
....
```



## 2.按需开启自动配置项

```java
虽然131个场景的所有自动配置启动的时候默认全部加载。xxxxAutoConfiguration

    按照条件装配规则（@Conditional），最终会按需配置。
```





## 3.修改默认配置

```java
@Bean
@ConditionalOnBean(MultipartResolver.class)  //容器中有这个类型组件
@ConditionalOnMissingBean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME) //容器中没有这个名字 multipartResolver 的组件
public MultipartResolver multipartResolver(MultipartResolver resolver) {
     //给@Bean标注的方法传入了对象参数，这个参数的值就会从容器中找。
     //SpringMVC multipartResolver。防止有些用户配置的文件上传解析器不符合规范
	// Detect if the user has created a MultipartResolver but named it incorrectly
	return resolver;
 }
给容器中加入了文件上传解析器；
```

即对IOC容器中的命名不规范的文件上传解析器起了个别名，这个别名是SpringMVC要求的规范化名字。所以容器中的这个Bean有两个名字。





SpringBoot默认会在底层配好所有的组件。但是如果用户自己配置了以用户的优先

```java
@Bean
@ConditionalOnMissingBean
public CharacterEncodingFilter characterEncodingFilter() {
}
```

总结：

- SpringBoot先加载所有的自动配置类  xxxxxAutoConfiguration
- 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。xxxxProperties里面拿。xxxProperties和配置文件进行了绑定

- 生效的配置类就会给容器中装配很多组件
- 只要容器中有这些组件，相当于这些功能就有了

- 定制化配置

- - 用户直接自己@Bean替换底层的组件
  - 用户去看这个组件是获取的配置文件什么值就去修改。

**xxxxxAutoConfiguration ---> 组件  --->** **xxxxProperties里面拿值  ----> application.properties**



xxxAutoConfiguration 中导入了很多的组件，然后这些组件从 xxxProperties 中拿到对应的默认值，然后 xxxProperties 是从 application.properties 配置文件中获取的值，所以一般情况下我们只要改配置文件，就可以修改所有的默认行为。



## 4.最佳实践

- 引入场景依赖

- - https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter

- 查看自动配置了哪些（选做）

- - 自己分析，引入场景对应的自动配置一般都生效了
  - 配置文件中debug=true开启自动配置报告。Negative（不生效）\Positive（生效）

- 是否需要修改

- - 参照文档修改配置项

- - - https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties
    - 自己分析。xxxxProperties绑定了配置文件的哪些。

- - 自定义加入或者替换组件

- - - @Bean、@Component。。。

- - 自定义器  **XXXXXCustomizer**；
  - ......

# 开发技巧

## @Slf4j

```java
================================简化日志开发===================================
@Slf4j
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String handle01(@RequestParam("name") String name){
        log.info("请求进来了....");
        return "Hello, Spring Boot 2!"+"你好："+name;
    }
}
```



# 核心内容

<font color='red'>先扫描yml后扫描properties ,yml里的内容会被properties的覆盖</font>





## .yml配置



YAML 是 "YAML Ain't Markup Language"（YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。



<font color='red'>基本语法</font>

- key: value；kv之间有空格
- 大小写敏感

- 使用缩进表示层级关系
- 缩进不允许使用tab，只允许空格

- 缩进的空格数不重要，只要相同层级的元素左对齐即可
- '#'表示注释

- 字符串无需加引号，如果要加，''与""表示字符串内容 会被 转义/不转义





<font color='red'>数据类型</font>



- 字面量：单个的、不可再分的值。date、boolean、string、number、null

```YAML
k: v
```



- 对象：键值对的集合。map、hash、object 

```YAML
行内写法：  k: {k1:v1,k2:v2,k3:v3}
#或
k: 
	k1: v1
  k2: v2
  k3: v3
```



- 数组：一组按次序排列的值。array、list、set 、queue

```yaml
行内写法：  k: [v1,v2,v3]
#或者
k:
 - v1
 - v2
 - v3
```



<font color='red'>**示例**</font>

```java
@Data
public class Person {
	
	private String userName;
	private Boolean boss;
	private Date birth;
	private Integer age;
	private Pet pet;
	private String[] interests;
	private List<String> animal;
	private Map<String, Object> score;
	private Set<Double> salarys;
	private Map<String, List<Pet>> allPets;
}

@Data
public class Pet {
	private String name;
	private Double weight;
}
```



```java
# yaml表示以上对象
person:
  userName: zhangsan
  boss: false
  birth: 2019/12/12 20:12:33
  age: 18
  pet: 
    name: tomcat
    weight: 23.4
  interests: [篮球,游泳]
  animal: 
    - jerry
    - mario
  score:
    english: 
      first: 30
      second: 40
      third: 50
    math: [131,140,148]
    chinese: {first: 128,second: 136}
  salarys: [3999,4999.98,5999.99]
  allPets:
    sick:
      - {name: tom}
      - {name: jerry,weight: 47}
    health: [{name: mario,weight: 47}]
```



<font color='red'>配置提示</font>

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>


 <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-configuration-processor</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
```



## web开发

![image-20220211142819842](D:\笔记\Java\SpringBoot.assets\image-20220211142819842.png)





### 1.SpringMVC自动配置概览

Spring Boot provides auto-configuration for Spring MVC that **works well with most applications.(大多场景我们都无需自定义配置)**

The auto-configuration adds the following features on top of Spring’s defaults:

- Inclusion of `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.

- - 内容协商视图解析器和BeanName视图解析器

- Support for serving static resources, including support for WebJars (covered [later in this document](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-static-content))).

- - 静态资源（包括webjars）

- Automatic registration of `Converter`, `GenericConverter`, and `Formatter` beans.

- - 自动注册 `Converter，GenericConverter，Formatter `

- Support for `HttpMessageConverters` (covered [later in this document](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-message-converters)).

- - 支持 `HttpMessageConverters` （后来我们配合内容协商理解原理）

- Automatic registration of `MessageCodesResolver` (covered [later in this document](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-message-codes)).

- - 自动注册 `MessageCodesResolver` （国际化用）

- Static `index.html` support.

- - 静态index.html 页支持

- Custom `Favicon` support (covered [later in this document](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-favicon)).

- - 自定义 `Favicon`  

- Automatic use of a `ConfigurableWebBindingInitializer` bean (covered [later in this document](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-web-binding-initializer)).

- - 自动使用 `ConfigurableWebBindingInitializer` ，（DataBinder负责将请求数据绑定到JavaBean上）

> If you want to keep those Spring Boot MVC customizations and make more [MVC customizations](https://docs.spring.io/spring/docs/5.2.9.RELEASE/spring-framework-reference/web.html#mvc) (interceptors, formatters, view controllers, and other features), you can add your own `@Configuration` class of type `WebMvcConfigurer` but **without** `@EnableWebMvc`.

**不用@EnableWebMvc注解。使用** **`@Configuration`  ** + `WebMvcConfigurer`   **自定义规则**



> If you want to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`, or `ExceptionHandlerExceptionResolver`, and still keep the Spring Boot MVC customizations, you can declare a bean of type `WebMvcRegistrations` and use it to provide custom instances of those components.

**声明** `WebMvcRegistrations` **改变默认底层组件**



> If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`, or alternatively add your own `@Configuration`-annotated `DelegatingWebMvcConfiguration` as described in the Javadoc of `@EnableWebMvc`.

**使用** `@EnableWebMvc`  + `@Configuration` +   `@DelegatingWebMvcConfiguration ` 全面接管SpringMVC



### 2.简单功能分析

#### 2.1 静态资源访问



**<font color='red'>静态资源目录</font>**

只要静态资源放在类路径下： called `/static` (or `/public` or `/resources` or `/META-INF/resources`

访问 ： 当前项目根路径/ + 静态资源名 



原理： 静态映射/**。

请求进来，先去找Controller看能不能处理。不能处理的所有请求又都交给静态资源处理器。静态资源也找不到则响应404页面



<font color='red'>静态资源路径</font>  ,资源**存放**地址

改变默认: (默认为: /**)

```yml
spring:
  web:
    resources:
      static-locations: [classpath:/haha/]
```



<font color='red'>静态资源访问前缀</font>  ,是访问路径

默认无前缀 

```yml
spring:
  mvc:
    static-path-pattern: /res/**
```

当前项目 + static-path-pattern + 静态资源名 = 静态资源文件夹下找

![image-20220211152701920](D:\笔记\Java\SpringBoot.assets\image-20220211152701920.png)





**<font color='red'>webjar</font>**

自动映射:  /[webjars](http://localhost:8080/webjars/jquery/3.5.1/jquery.js)/**

https://www.webjars.org/

```xml
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.5.1</version>
        </dependency>
```

访问地址：[http://localhost:8080/webjars/**jquery/3.5.1/jquery.js**](http://localhost:8080/webjars/jquery/3.5.1/jquery.js)   后面地址要按照依赖里面的包路径







#### 2.2 欢迎页支持

- 静态资源路径下  index.html

- - 可以配置静态资源路径
  - 但是不可以配置静态资源的访问前缀。否则导致 index.html不能被默认访问

```yml
spring:
#  mvc:
#    static-path-pattern: /res/**   这个会导致welcome page功能失效

  resources:
    static-locations: [classpath:/haha/]
```

* controller能处理 /index



#### 2.3 自定义 Favicon

favicon.ico 放在静态资源目录下即可。

```yml
spring:
#  mvc:
#    static-path-pattern: /res/**   这个会导致 Favicon 功能失效
```







#### 2.4、静态资源配置原理

- SpringBoot启动默认加载  xxxAutoConfiguration 类（自动配置类）
- SpringMVC功能的自动配置类 WebMvcAutoConfiguration，生效



```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {}
```



定义了两个内部类，其中一个反射到欢迎配置内部类中 这就是原理



- 给容器中配了什么。

```java
@Configuration(proxyBeanMethods = false)
@Import(EnableWebMvcConfiguration.class)
@EnableConfigurationProperties(
 			{ WebMvcProperties.class,
			org.springframework.boot.autoconfigure.web.ResourceProperties.class, 					WebProperties.class })
@Order(0)
public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer, ServletContextAware {}
```

- 配置文件的相关属性和xxx进行了绑定。WebMvcProperties==**spring.mvc**、ResourceProperties==**spring.resources**  、 WebProperties.class == **"spring.web"**

  

**ResourceProperties 2.4.0后移除，以支持WebProperties。资源**







**<font color='red'>1、配置类只有一个有参构造器</font>**   ,类似于通过@Autowire 注解,不过官方<font color='red'>推荐</font>使用构造器方式进行

```java
	//有参构造器所有参数的值都会从容器中确定
//ResourceProperties resourceProperties；获取和spring.resources绑定的所有的值的对象
//WebMvcProperties mvcProperties 获取和spring.mvc绑定的所有的值的对象
//ListableBeanFactory beanFactory Spring的beanFactory
//HttpMessageConverters 找到所有的HttpMessageConverters
//ResourceHandlerRegistrationCustomizer 找到 资源处理器的自定义器。=========
//DispatcherServletPath  
//ServletRegistrationBean   给应用注册Servlet、Filter....
	public WebMvcAutoConfigurationAdapter(ResourceProperties resourceProperties, WebMvcProperties mvcProperties,
				ListableBeanFactory beanFactory, ObjectProvider<HttpMessageConverters> messageConvertersProvider,
				ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider,
				ObjectProvider<DispatcherServletPath> dispatcherServletPath,
				ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
			this.resourceProperties = resourceProperties;
			this.mvcProperties = mvcProperties;
			this.beanFactory = beanFactory;
			this.messageConvertersProvider = messageConvertersProvider;
			this.resourceHandlerRegistrationCustomizer = resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
			this.dispatcherServletPath = dispatcherServletPath;
			this.servletRegistrations = servletRegistrations;
		}
```





<font color='red'>**2.资源处理的默认规则**</font>



在WebMvcAutoConfiguration 的内部类 WebMvcAutoConfigurationAdapter的方法 .

资源处理规则



```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
    addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
        registration.addResourceLocations(this.resourceProperties.getStaticLocations());
        if (this.servletContext != null) {
            ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
            registration.addResourceLocations(resource);
        }
    });
}
```


```java
private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, String... locations) {
    addResourceHandler(registry, pattern, (registration) -> registration.addResourceLocations(locations));
}
```


```java
private void addResourceHandler(ResourceHandlerRegistry registry, String pattern,
                                Consumer<ResourceHandlerRegistration> customizer) {
    if (registry.hasMappingForPattern(pattern)) {
        return;
    }
    ResourceHandlerRegistration registration = registry.addResourceHandler(pattern);
    customizer.accept(registration);
    registration.setCachePeriod(getSeconds(this.resourceProperties.getCache().getPeriod()));
    registration.setCacheControl(this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl());
    registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
    customizeResourceHandlerRegistration(registration);
}
```



```yml
spring:
#  mvc:
#    static-path-pattern: /res/**

  resources:
    add-mappings: false   禁用所有静态资源规则
```



```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	/**
	 * Locations of static resources. Defaults to classpath:[/META-INF/resources/,
	 * /resources/, /static/, /public/].
	 */
	private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
```





**<font color='red'>3.欢迎页的处理规则</font>**

```java
	HandlerMapping：处理器映射。保存了每一个Handler能处理哪些请求。	

        @Bean
        public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
                                                                   FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
        WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
            new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
            this.mvcProperties.getStaticPathPattern());
        welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
        welcomePageHandlerMapping.setCorsConfigurations(getCorsConfigurations());
        return welcomePageHandlerMapping;
    }
```

```java
WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders,
                          ApplicationContext applicationContext, Resource welcomePage, String staticPathPattern) {
    if (welcomePage != null && "/**".equals(staticPathPattern)) { //要用欢迎页功能，必须是/**
        logger.info("Adding welcome page: " + welcomePage);
        setRootViewName("forward:index.html");
    }
    else if (welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
        logger.info("Adding welcome page template: index");   // 调用Controller  /index
        setRootViewName("index");
    }
}
```







### 3.请求参数处理



#### 0、请求映射



##### 1、Rest使用与原理

- @xxxMapping；
- Rest风格支持（*使用**HTTP**请求方式动词来表示对资源的操作*）

- - 以前：*/getUser*  *获取用户*    */deleteUser* *删除用户*   */editUser*  *修改用户*      */saveUser* *保存用户*
  - *现在： /user*    *GET-获取用户*    *DELETE-删除用户*     *PUT-修改用户*      *POST-保存用户*

- - 核心Filter；HiddenHttpMethodFilter

- - - 用法： 表单method=post，隐藏域 _method=put
    - SpringBoot中手动开启

- - 扩展：如何把_method 这个名字换成我们自己喜欢的。





````java
@RequestMapping(value = "/user",method = RequestMethod.GET)
public String getUser(){
    return "GET-张三";
}

@RequestMapping(value = "/user",method = RequestMethod.POST)
public String saveUser(){
    return "POST-张三";
}

@RequestMapping(value = "/user",method = RequestMethod.PUT)
public String putUser(){
    return "PUT-张三";
}

@RequestMapping(value = "/user",method = RequestMethod.DELETE)
public String deleteUser(){
    return "DELETE-张三";
}
````



```java
@Bean
@ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false)
public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new OrderedHiddenHttpMethodFilter();
}
```

```java
//自定义filter
@Bean
public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
    HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
    methodFilter.setMethodParam("_m");
    return methodFilter;
}
```



Rest原理（表单提交要使用REST的时候）

- 表单提交会带上**_method=PUT**
- **请求过来被**HiddenHttpMethodFilter拦截

- - 请求是否正常，并且是POST

- - - 获取到**_method**的值。
    - 兼容以下请求；**PUT**.**DELETE**.**PATCH**

- - - **原生request（post），包装模式requesWrapper重写了getMethod方法，返回的是传入的值。**
    - **过滤器链放行的时候用wrapper。以后的方法调用getMethod是调用**requesWrapper的。



**Rest使用客户端工具，**

- 如PostMan直接发送Put、delete等方式请求，无需Filter。

因为 html 表单默认不支持 result风格，所以加个隐藏字段做判断实现的，<font color='red'>postman</font>和<font color='red'>Ajax</font>可以实现result 风格，可以不用配置

```yml
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true   #开启页面表单的Rest功能
```







##### 2、请求映射原理

SpringMVC功能分析都从 org.springframework.web.servlet.**DispatcherServlet** ->  doDispatch（）



**DispatcherServlet** 继承关系

![image-20220211215700864](D:\笔记\Java\SpringBoot.assets\image-20220211215700864.png)



doService <font color='red'>核心</font>是调用了doDispatch方法.

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;
    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        try {
            ModelAndView mv = null;
            Object dispatchException = null;

            try {
                processedRequest = this.checkMultipart(request);
                multipartRequestParsed = processedRequest != request;
                mappedHandler = this.getHandler(processedRequest);
                
                
               // 找到当前请求使用那个Handler（Controller的方法）处理
				mappedHandler = getHandler(processedRequest);
                
                //HandlerMapping：处理器映射。/xxx->>xxxx
```



mappedHandler = getHandler(processedRequest); 

通过getHandler,获取到五个handlerMappings . 

**RequestMappingHandlerMapping**：保存了所有@RequestMapping 和handler的映射规则

![image-20220211220506627](D:\笔记\Java\SpringBoot.assets\image-20220211220506627.png)

**RequestMappingHandlerMapping**：保存了所有@RequestMapping 和handler的映射规则

![image-20220211220811077](D:\笔记\Java\SpringBoot.assets\image-20220211220811077.png)

所有的请求映射都在HandlerMapping中。



- SpringBoot自动配置欢迎页的 WelcomePageHandlerMapping 。访问 /能访问到index.html；
- SpringBoot自动配置了默认 的 RequestMappingHandlerMapping

- 请求进来，挨个尝试所有的HandlerMapping看是否有请求信息。

- - 如果有就找到这个请求对应的handler
  - 如果没有就是下一个 HandlerMapping

- 我们需要一些自定义的映射处理，我们也可以自己给容器中放**HandlerMapping**。自定义 **HandlerMapping**



```java
@Nullable
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    if (this.handlerMappings != null) {
        Iterator var2 = this.handlerMappings.iterator();

        while(var2.hasNext()) {
            HandlerMapping mapping = (HandlerMapping)var2.next();
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
    }
```





#### 1、普通参数与基本注解

##### 1.1、注解：

@PathVariable（路径变量）、@RequestHeader（获取请求头）、@RequestParam（获取请求参数）、@CookieValue（获取cookie值））、@RequestBody（获取请求体[POST] 表单）

```java
@RestController
public class ParameterTestController {


    @RequestMapping("/car/{id}/owner/{name}")
    public Map<String,Object> getCar(@PathVariable("id") String id,
                                     @PathVariable("name") String name,
                                     @PathVariable Map<String,String> pv,
                                     @RequestHeader("User-Agent") String userAgent,
                                     @RequestHeader Map<String,String> header,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("inters") List<String> inters,
                                     @RequestParam Map<String,String> params,
                                     @CookieValue("_ga") String _ga,
                                     @CookieValue("_ga") Cookie cookie){


        HashMap<String, Object> map = new HashMap<>();
        //map.put("name",name);
        //map.put("id",id);
        //map.put("pv",pv);
        //map.put("userAgent",userAgent);
        //map.put("headers",header);
        map.put("age",age);
        map.put("inters",inters);
        map.put("params",params);
        map.put("_ga",_ga);
        System.out.println(cookie.getName()+"===>"+cookie.getValue());
        return map;
    }
    
    
    @PostMapping("/save")
    public Map postMap(@RequestBody String  content){
        Map<String,Object> map = new HashMap<>();
        map.put("content",content);
        return map;
    }
}

```

可以是表格的内容设置为正确的编码格式

System.out.println(URLDecoder.decode(content));



@RequestAttribute（获取request域属性）

```java
@Controller
public class RequestController {
    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request){

        request.setAttribute("msg","成功了...");
        request.setAttribute("code",200);
        return "forward:/success";  //转发到  /success请求
    }

    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg") String msg,
                       @RequestAttribute("code") Integer code,
                       HttpServletRequest request){
        Map map = new HashMap();
        map.put("reqMsg",request.getAttribute("msg"));
        map.put("annotationMsg",msg);
        return map;
    }
}
```



**<font color='red'>@MatrixVariable（矩阵变量）</font>**

矩阵变量是在URL中的PathVariable { } 中通过 ; 号来分割变量的一种传参方式. 

例如:   /cars/**sell;low=34;brand=byd,audi,yd**   的请求映射为 /cars/{path}

​			





配置类设置开启, 默认是不开启读取分号后面内容

![image-20220212104017717](D:\笔记\Java\SpringBoot.assets\image-20220212104017717.png)

方法一:  重写组件

```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {

    return new WebMvcConfigurer(){
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {

            UrlPathHelper urlPathHelper = new UrlPathHelper();
            urlPathHelper.setRemoveSemicolonContent(false);
            configurer.setUrlPathHelper(urlPathHelper);
        }
    };
}
```



方法二: 重写组件内部方法

```java
@Configuration(proxyBeanMethods = false)
public class MyConfig/* implements WebMvcConfigurer*/ {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
```





使用

```java
//http://localhost:8080/cars/sell;low=34;brand=byd,audi,yd
//http://localhost:8080/cars/sell;low=34;brand=byd;brand=audi;brand=yd
@ResponseBody
@GetMapping("/cars/{path}")
public Map carSell(@MatrixVariable("low") String low,
                   @MatrixVariable("brand") List<String> brand, @PathVariable String path){

    HashMap<String, Object> map = new HashMap<>();
    map.put("low",low);
    map.put("brand",brand);
    map.put("path",path);
    return map;
}

//http://localhost:8080/boss/1;age=20/2;age=10
@ResponseBody
@GetMapping("/boss/{bossId}/{kaId}")
public Map boss(@MatrixVariable(pathVar = "bossId" ,value = "age") Integer bossAge,
                @MatrixVariable(pathVar = "kaId" ,value = "age") Integer kaAge
               ){

    HashMap<String, Object> map = new HashMap<>();
    map.put("bossAge",bossAge);
    map.put("kaAge",kaAge);

    return map;
}
```





面试 : 页面开发，cookie禁用了，session里面的内容怎么使用；

**session.set(a,b)---> jsessionid ---> cookie ----> 每次发请求携带。**

在Session中设置了信息, 这个Session会生成唯一对应的 jsessionid , 每次发请求时携带cookie,cookie找到对应的jsessionid从而获得信息.

 session是通过cookie来建立联系的,一次会话的周期中都通过一个cookie来确认session 



**答** : url重写：/abc;jsesssionid=xxxx 把cookie的值使用矩阵变量的方式进行传递.



**矩阵变量的作用 :** cookie禁用下，矩阵变量一般用于携带cookie的参数。因为要把cookie的参数和请求参数分隔开，所以一般就不把cookie放在路径变量或者请求体参数中(未被禁用时，是放在请求头中)







##### 1.2、Servlet API：

WebRequest、ServletRequest、MultipartRequest、 HttpSession、javax.servlet.http.PushBuilder、Principal、InputStream、Reader、HttpMethod、Locale、TimeZone、ZoneId

**ServletRequestMethodArgumentResolver  以上的部分参数**



```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
    Class<?> paramType = parameter.getParameterType();
    return (WebRequest.class.isAssignableFrom(paramType) ||
            ServletRequest.class.isAssignableFrom(paramType) ||
            MultipartRequest.class.isAssignableFrom(paramType) ||
            HttpSession.class.isAssignableFrom(paramType) ||
            (pushBuilder != null && pushBuilder.isAssignableFrom(paramType)) ||
            Principal.class.isAssignableFrom(paramType) ||
            InputStream.class.isAssignableFrom(paramType) ||
            Reader.class.isAssignableFrom(paramType) ||
            HttpMethod.class == paramType ||
            Locale.class == paramType ||
            TimeZone.class == paramType ||
            ZoneId.class == paramType);
}
```



##### 1.3、复杂参数：

**Map**、**Model（map、model里面的数据会被放在request的请求域  request.setAttribute）、**Errors/BindingResult、**RedirectAttributes（ 重定向携带数据）**、**ServletResponse（response）**、SessionStatus、UriComponentsBuilder、ServletUriComponentsBuilder

```java
Map<String,Object> map, Model model, HttpServletRequest request 都是可以给request域中放数据，
request.getAttribute();
```

**Map、Model类型的参数**，会返回 mavContainer.getModel()--->  BindingAwareModelMap 是Model 也是Map

<font color='red'> **Map和Model的底层都是同一个对象**</font>

**mavContainer**.getModel(); 获取到值的

![image-20220212163755872](D:\笔记\Java\SpringBoot.assets\image-20220212163755872.png)



![image-20220212163844348](D:\笔记\Java\SpringBoot.assets\image-20220212163844348.png)

![image-20220212164036528](D:\笔记\Java\SpringBoot.assets\image-20220212164036528.png)



##### 1.4、自定义对象参数：

可以自动类型转换与格式化，可以级联封装。

```java
/**
 *     姓名： <input name="userName"/> <br/>
 *     年龄： <input name="age"/> <br/>
 *     生日： <input name="birth"/> <br/>
 *     宠物姓名：<input name="pet.name"/><br/>
 *     宠物年龄：<input name="pet.age"/>
 */
@Data
public class Person {
    
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
    
}

@Data
public class Pet {

    private String name;
    private String age;

}

result
```



#### 2、POJO封装过程

![image-20220224122413125](D:\笔记\Java\SpringBoot.assets\image-20220224122413125.png)

​	![image-20220224122431773](D:\笔记\Java\SpringBoot.assets\image-20220224122431773.png)



- **ServletModelAttributeMethodProcessor**



#### 3、参数处理原理

- HandlerMapping中找到能处理请求的Handler（ Controller.method() ）
- 为当前Handler 找一个适配器 HandlerAdapter； **RequestMappingHandlerAdapter**

- 适配器执行目标方法并确定方法参数的每一个值

##### 

##### 1、HandlerAdapter

![image-20220212141742294](D:\笔记\Java\SpringBoot.assets\image-20220212141742294.png)

0 = 支持方法上标注 @RequestMapping 

1 =  支持函数式编程  

xxxxxx

##### 2、执行目标方法

```java
// Actua	lly invoke the handler.
//DispatcherServlet -- doDispatch
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```

```java
mav = invokeHandlerMethod(request, response, handlerMethod); //执行目标方法


//ServletInvocableHandlerMethod
Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs);
//获取方法的参数值
Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);

```

##### 3、参数解析器-HandlerMethodArgumentResolver

确定将要执行的目标方法的每一个参数的值是什么;

SpringMVC目标方法能写多少种参数类型。取决于参数解析器。



![image-20220212145041481](D:\笔记\Java\SpringBoot.assets\image-20220212145041481.png)

![image-20220212151608110](D:\笔记\Java\SpringBoot.assets\image-20220212151608110.png)

- 当前解析器是否支持解析这种参数
- 支持就调用 resolveArgument



##### 4、返回值处理器

![image-20220212145200177](D:\笔记\Java\SpringBoot.assets\image-20220212145200177.png)



##### 5、如何确定目标方法每一个参数的值

```java
============InvocableHandlerMethod==========================
protected Object[] getMethodArgumentValues(NativeWebRequest request, @Nullable ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {

    MethodParameter[] parameters = getMethodParameters();
    if (ObjectUtils.isEmpty(parameters)) {
        return EMPTY_ARGS;
    }

    Object[] args = new Object[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
        MethodParameter parameter = parameters[i];
        parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
        args[i] = findProvidedArgument(parameter, providedArgs);
        if (args[i] != null) {
            continue;
        }
        if (!this.resolvers.supportsParameter(parameter)) {
            throw new IllegalStateException(formatArgumentError(parameter, "No suitable resolver"));
        }
        try {
            args[i] = this.resolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory);
        }
        catch (Exception ex) {
            // Leave stack trace for later, exception may actually be resolved and handled...
            if (logger.isDebugEnabled()) {
                String exMsg = ex.getMessage();
                if (exMsg != null && !exMsg.contains(parameter.getExecutable().toGenericString())) {
                    logger.debug(formatArgumentError(parameter, exMsg));
                }
            }
            throw ex;
        }
    }
    return args;
}
```



###### 5.1、挨个判断所有参数解析器那个支持解析这个参数

```java
@Nullable
private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
    HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
    if (result == null) {
        for (HandlerMethodArgumentResolver resolver : this.argumentResolvers) {
            if (resolver.supportsParameter(parameter)) {
                result = resolver;
                this.argumentResolverCache.put(parameter, result);
                break;
            }
        }
    }
    return result;
}
```

###### 

###### 5.2、解析这个参数的值

```java
调用各自 HandlerMethodArgumentResolver 的 resolveArgument 方法即可
```



###### 5.3、自定义类型参数 封装POJO

**ServletModelAttributeMethodProcessor  这个参数处理器支持是否为简单类型。**

```java
public static boolean isSimpleValueType(Class<?> type) {
		return (Void.class != type && void.class != type &&
				(ClassUtils.isPrimitiveOrWrapper(type) ||
				Enum.class.isAssignableFrom(type) ||
				CharSequence.class.isAssignableFrom(type) ||
				Number.class.isAssignableFrom(type) ||
				Date.class.isAssignableFrom(type) ||
				Temporal.class.isAssignableFrom(type) ||
				URI.class == type ||
				URL.class == type ||
				Locale.class == type ||
				Class.class == type));
	}
```





```java
@Override
@Nullable
public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

    Assert.state(mavContainer != null, "ModelAttributeMethodProcessor requires ModelAndViewContainer");
    Assert.state(binderFactory != null, "ModelAttributeMethodProcessor requires WebDataBinderFactory");

    String name = ModelFactory.getNameForParameter(parameter);
    ModelAttribute ann = parameter.getParameterAnnotation(ModelAttribute.class);
    if (ann != null) {
        mavContainer.setBinding(name, ann.binding());
    }

    Object attribute = null;
    BindingResult bindingResult = null;

    if (mavContainer.containsAttribute(name)) {
        attribute = mavContainer.getModel().get(name);
    }
    else {
        // Create attribute instance
        try {
            attribute = createAttribute(name, parameter, binderFactory, webRequest);
        }
        catch (BindException ex) {
            if (isBindExceptionRequired(parameter)) {
                // No BindingResult parameter -> fail with BindException
                throw ex;
            }
            // Otherwise, expose null/empty value and associated BindingResult
            if (parameter.getParameterType() == Optional.class) {
                attribute = Optional.empty();
            }
            bindingResult = ex.getBindingResult();
        }
    }

    if (bindingResult == null) {
        // Bean property binding and validation;
        // skipped in case of binding failure on construction.
        WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
        if (binder.getTarget() != null) {
            if (!mavContainer.isBindingDisabled(name)) {
                bindRequestParameters(binder, webRequest);
            }
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }
        // Value type adaptation, also covering java.util.Optional
        if (!parameter.getParameterType().isInstance(attribute)) {
            attribute = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
        }
        bindingResult = binder.getBindingResult();
    }

    // Add resolved attribute and BindingResult at the end of the model
    Map<String, Object> bindingResultModel = bindingResult.getModel();
    mavContainer.removeAttributes(bindingResultModel);
    mavContainer.addAllAttributes(bindingResultModel);

    return attribute;
}
```



**WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);**

**WebDataBinder :web数据绑定器，将请求参数的值绑定到指定的JavaBean里面**

**WebDataBinder 利用它里面的 Converters 将请求数据转成指定的数据类型。再次封装到JavaBean中**



**GenericConversionService：在设置每一个值的时候，找它里面的所有converter那个可以将这个数据类型（request带来参数的字符串）转换到指定的类型（JavaBean -- Integer）**

**byte -- > file**



@FunctionalInterface**public interface** Converter<S, T>

![image-20220212203705294](D:\笔记\Java\SpringBoot.assets\image-20220212203705294.png)



![image-20220212203720158](D:\笔记\Java\SpringBoot.assets\image-20220212203720158.png)



未来我们可以给WebDataBinder里面放自己的Converter；

**private static final class** StringToNumber<T **extends** Number> **implements** Converter<String, T>



自定义 Converter

```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {

    return new WebMvcConfigurer(){
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new Converter<String, Pet>() {
                @Override
                public Pet convert(String source) {
                    // 啊猫,3
                    if(!StringUtils.isEmpty(source)){
                        Pet pet = new Pet();
                        String[] split = source.split(",");
                        pet.setName(split[0]);
                        pet.setAge(Integer.parseInt(split[1]));
                        return pet;
                    }
                    return null;
                }
            });
        }
    };
```



##### 6、目标方法执行完成

将所有的数据都放在 **ModelAndViewContainer**；包含要去的页面地址View。还包含Model数据。

![image-20220212164448697](D:\笔记\Java\SpringBoot.assets\image-20220212164448697.png)

##### 7、处理派发结果

**processDispatchResult**(processedRequest, response, mappedHandler, mv, dispatchException);



renderMergedOutputModel(mergedModel, getRequestToExpose(request), response);

```java
InternalResourceView：
@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, request);

		// Expose helpers as request attributes, if any.
		exposeHelpers(request);

		// Determine the path for the request dispatcher.
		String dispatcherPath = prepareForRendering(request, response);

		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		RequestDispatcher rd = getRequestDispatcher(request, dispatcherPath);
		if (rd == null) {
			throw new ServletException("Could not get RequestDispatcher for [" + getUrl() +
					"]: Check that the corresponding file exists within your web application archive!");
		}

		// If already included or response already committed, perform include, else forward.
		if (useInclude(request, response)) {
			response.setContentType(getContentType());
			if (logger.isDebugEnabled()) {
				logger.debug("Including [" + getUrl() + "]");
			}
			rd.include(request, response);
		}

		else {
			// Note: The forwarded resource is supposed to determine the content type itself.
			if (logger.isDebugEnabled()) {
				logger.debug("Forwarding to [" + getUrl() + "]");
			}
			rd.forward(request, response);
		}
	}
```



```java
暴露模型作为请求域属性
// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, request);
```

**<font color='red'>  model中的所有数据遍历挨个放在请求域中</font>**

```java
protected void exposeModelAsRequestAttributes(Map<String, Object> model,
HttpServletRequest request) throws Exception {
    //model中的所有数据遍历挨个放在请求域中
    model.forEach((name, value) -> {
        if (value != null) {
            request.setAttribute(name, value);
        }
        else {
            request.removeAttribute(name);
        }
    });
}
```











#### 4、数据响应与内容协商





响应页面适用于单体项目, 响应数据适用于前后端分离项目

![image-20220212212120710](D:\笔记\Java\SpringBoot.assets\image-20220212212120710.png)

##### 1、响应JSON

###### 1.1、jackson.jar+@ResponseBody

```java
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
            
            
web场景自动引入了json场景
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-json</artifactId>
      <version>2.3.4.RELEASE</version>
      <scope>compile</scope>
    </dependency>

```

![image-20220213095926464](D:\笔记\Java\SpringBoot.assets\image-20220213095926464.png)

给前端自动返回json数据；





**<font color='red'>1、返回值解析器</font>**

![image-20220213101618651](D:\笔记\Java\SpringBoot.assets\image-20220213101618651.png)

```java
try {
    this.returnValueHandlers.handleReturnValue(
        returnValue, getReturnValueType(returnValue), mavContainer, webRequest);
}
```

```java
@Override
public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
    HandlerMethodReturnValueHandler handler = selectHandler(returnValue, returnType);
    if (handler == null) {
        throw new IllegalArgumentException("Unknown return value type: " + 				         returnType.getParameterType().getName());
    }
    handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
}
```

```java
				======RequestResponseBodyMethodProcessor=====
    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
    throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

    mavContainer.setRequestHandled(true);
    ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
    ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);

    // Try even with null return value. ResponseBodyAdvice could get involved.
    // 使用消息转换器进行写出操作
    writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
}
```





**<font color='red'>2、返回值解析器原理</font>**



![image-20220213102054682](D:\笔记\Java\SpringBoot.assets\image-20220213102054682.png)





- 1、返回值处理器判断是否支持这种类型返回值 supportsReturnType
- 2、返回值处理器调用 handleReturnValue 进行处理

- 3、RequestResponseBodyMethodProcessor 可以处理返回值标了@ResponseBody 注解的。

- - \1.  利用 MessageConverters 进行处理 将数据写为json

- - - 1、内容协商（浏览器默认会以请求头的方式告诉服务器他能接受什么样的内容类型）
    - 2、服务器最终根据自己自身的能力，决定服务器能生产出什么样内容类型的数据，

- - - 3、SpringMVC会挨个遍历所有容器底层的 HttpMessageConverter ，看谁能处理？

- - - - 1、得到MappingJackson2HttpMessageConverter可以将对象写为json

      - 2、利用MappingJackson2HttpMessageConverter将对象转为json再写出去。

        

![image-20220213102212709](D:\笔记\Java\SpringBoot.assets\image-20220213102212709.png)



###### 1.2、SpringMVC到底支持哪些返回值



```java
ModelAndView
Model
View
ResponseEntity 
ResponseBodyEmitter
StreamingResponseBody
HttpEntity
HttpHeaders
Callable
DeferredResult
ListenableFuture
CompletionStage
WebAsyncTask
有 @ModelAttribute 且为对象类型的
@ResponseBody 注解 ---> RequestResponseBodyMethodProcessor；
```



###### 1.3、HTTPMessageConverter原理



**<font color='red'>1、MessageConverter规范</font>**

![image-20220213102539676](D:\笔记\Java\SpringBoot.assets\image-20220213102539676.png)

HttpMessageConverter: 看是否支持将 此 Class类型的对象，转为MediaType类型的数据。

例子：Person对象转为JSON。或者 JSON转为Person





**<font color='red'>2、默认的MessageConverter</font>**

![image-20220213103102935](D:\笔记\Java\SpringBoot.assets\image-20220213103102935.png)

0 - 只支持Byte类型的

1 - String

2 - String

3 - Resource

4 - ResourceRegion

5 - DOMSource.**class \** SAXSource.**class**) \ StAXSource.**class \**StreamSource.**class \**Source.**class**

**6 -** MultiValueMap

7 - **true** 

**8 - true**

**9 - 支持注解方式xml处理的**



最终 MappingJackson2HttpMessageConverter把对象转为JSON（利用底层的jackson的objectMapper转换的）



![image-20220213103154149](D:\笔记\Java\SpringBoot.assets\image-20220213103154149.png)







##### 2、内容协商

根据客户端接收能力不同，返回不同媒体类型的数据。



###### 1、引入xml依赖

```xml
 <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

###### 2、postman分别测试返回json和xml

只需要改变请求头中Accept字段。Http协议中规定的，告诉服务器本客户端可以接收的数据类型。

![image-20220213104656324](D:\笔记\Java\SpringBoot.assets\image-20220213104656324.png)





###### 3、开启浏览器参数方式内容协商功能

为了方便内容协商，开启基于请求参数的内容协商功能

```yml
spring:
    contentnegotiation:
      favor-parameter: true  #开启请求参数内容协商模式
```

发请求： http://localhost:8080/test/person?format=json

​				 http://localhost:8080/test/person?format=xml



![image-20220213164944551](D:\笔记\Java\SpringBoot.assets\image-20220213164944551.png)



确定客户端接收什么样的内容类型；

1、Parameter策略优先确定是要返回json数据（获取请求头中的format的值）

2、最终进行内容协商返回给客户端json即可。





###### 4、内容协商原理

- 1、判断当前响应头中是否已经有确定的媒体类型。MediaType
- **2、获取客户端（PostMan、浏览器）支持接收的内容类型。（获取客户端Accept请求头字段）【application/xml】**

- - <font color='red'>接收的内容类型</font>
  
  ```java
    List<MediaType> acceptableTypes = getAcceptableMediaTypes(request);
  ```
  
  - **contentNegotiationManager 内容协商管理器 默认使用基于请求头的策略**
  
  - ![image-20220213120130966](D:\笔记\Java\SpringBoot.assets\image-20220213120130966.png)
  
  - **HeaderContentNegotiationStrategy  确定客户端可以接收的内容类型**
  
    ![image-20220213120547702](D:\笔记\Java\SpringBoot.assets\image-20220213120547702.png)
  



​		

- 3、遍历循环所有当前系统的 **MessageConverter**，看谁支持操作这个对象（Person）

- 4、找到支持操作Person的converter，把converter支持的媒体类型统计出来。

- 5、客户端需要【application/xml】。服务端能力【10种、json、xml】

  <font color='red'>可生产类型</font>

```java
List<MediaType>producibleTypes=getProducibleMediaTypes(request, valueType, targetType);
```

  ![image-20220213114530583](D:\笔记\Java\SpringBoot.assets\image-20220213114530583.png)

- 6、进行内容协商的最佳匹配媒体类型
- 7、用 支持 将对象转为 最佳匹配媒体类型 的converter。调用它进行转化 。

![image-20220213114227686](D:\笔记\Java\SpringBoot.assets\image-20220213114227686.png)



导入了jackson处理xml的包，xml的converter就会自动进来

```java
WebMvcConfigurationSupport
    jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);

if (jackson2XmlPresent) {
    Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
    if (this.applicationContext != null) {
        builder.applicationContext(this.applicationContext);
    }
    messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
}
```





###### 5、自定义 MessageConverter



**实现多协议数据兼容。json、xml、x-tagao**

0、@ResponseBody 响应数据出去 调用 **RequestResponseBodyMethodProcessor** 处理

1、Processor 处理方法返回值。通过 **MessageConverter** 处理

2、所有 **MessageConverter** 合起来可以支持各种媒体类型数据的操作（读、写）

3、内容协商找到最终的 **messageConverter**；



SpringMVC的什么功能。一个入口给容器中添加一个  WebMvcConfigurer

```java
@Configuration(proxyBeanMethods = false)
public class MyConfig {
    //1、WebMvcConfigurer定制化SpringMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
           //内容协商的Converter
           @Override
          public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new taGaoMessageConverters());
           }
}
```

自定义messageConverter

```java
public class taGaoMessageConverters implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Person.class);
    }
    /**
     * 服务器要统计所有MessageConverter都能写出哪些内容类型
     *
     * application/x-tagao
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes("application/x-tagao");
    }

    @Override
    public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //自定义协议数据的写出
        String data = person.getUserName()+";"+person.getAge()+";"+person.getBirth();
        OutputStream body = outputMessage.getBody();
        body.write(data.getBytes());
    }
}
```





**<font color='red'>自定义浏览器的参数协商 </font>**

 http://localhost:8080/test/person?format=tagao

![image-20220213175336710](D:\笔记\Java\SpringBoot.assets\image-20220213175336710.png)

```java
@Bean
public WebMvcConfigurer webMvcConfigurer() {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //Map<String, MediaType> mediaTypes
        Map<String, MediaType> mediaTypes = new HashMap<>();
        mediaTypes.put("json",MediaType.APPLICATION_JSON);
        mediaTypes.put("xml",MediaType.APPLICATION_XML);
        mediaTypes.put("tagao",MediaType.parseMediaType("application/x-tagao"));
        //指定支持解析哪些参数对应的哪些媒体类型
        ParameterContentNegotiationStrategy strategy = new ParameterContentNegotiationStrategy(mediaTypes);
        HeaderContentNegotiationStrategy headerContentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        configurer.strategies(Arrays.asList(strategy,headerContentNegotiationStrategy));
    }
}
```











