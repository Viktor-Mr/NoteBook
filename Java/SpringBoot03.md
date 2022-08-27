# 单元测试



# 1、JUnit5 的变化

**Spring Boot 2.2.0 版本开始引入 JUnit 5 作为单元测试默认库**

作为最新版本的JUnit框架，JUnit5与之前版本的Junit框架有很大的不同。由三个不同子项目的几个不同模块组成。

> **JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage**

**JUnit Platform**: Junit Platform是在JVM上启动测试框架的基础，不仅支持Junit自制的测试引擎，其他测试引擎也都可以接入。

**JUnit Jupiter**: JUnit Jupiter提供了JUnit5的新的编程模型，是JUnit5新特性的核心。内部 包含了一个**测试引擎**，用于在Junit Platform上运行。

**JUnit Vintage**: 由于JUint已经发展多年，为了照顾老的项目，JUnit Vintage提供了兼容JUnit4.x,Junit3.x的测试引擎。

![image-20220218210843090](D:\笔记\Java\SpringBoot03.assets\image-20220218210843090.png)



注意：

**SpringBoot 2.4 以上版本移除了默认对** **Vintage 的依赖。如果需要兼容junit4需要自行引入（不能使用junit4的功能 @Test）**

**JUnit 5’s Vintage Engine Removed from** `spring-boot-starter-test,` **如果需要继续兼容junit4需要自行引入vintage**

```xml
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

![image-20220218211105878](D:\笔记\Java\SpringBoot03.assets\image-20220218211105878.png)

```java
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
```

以前：

@SpringBootTest + @RunWith(SpringRunner.class)



现在版本：

```java
@SpringBootTest
class Boot05WebAdminApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

SpringBoot整合Junit以后。

- 编写测试方法：@Test标注（注意需要使用junit5版本的注解）
- Junit类具有Spring的功能，@Autowired、比如 **@Transactional** 标注测试方法，测试完成后自动回滚





# 2、JUnit5常用注解

JUnit5的注解与JUnit4的注解有所变化

Junit4中的@Test是**import org.junit.Test;**

Jupiter中的@Test是**import org.junit.jupiter.api.Test;**

https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations





| Junit5             | Junit4       | 说明                                                         |
| ------------------ | ------------ | ------------------------------------------------------------ |
| @Test              | @Test        | 被注解的方法是一个测试方法。与JUnit4的@Test不同，他的职责非常单一不能声明任何属性，拓展的测试将会由Jupiter提供额外测试 |
| @BeforeAll         | @BeforeClass | 被注解的（静态）方法将在当前类中的所有 @Test 方法前执行一次。 |
| @BeforeEach        | @Before      | 被注解的方法将在当前类中的每个 @Test 方法前执行。            |
| @AfterEach         | @After       | 被注解的方法将在当前类中的每个 @Test 方法后执行。            |
| @AfterAll          | @AfterClass  | 被注解的（静态）方法将在当前类中的所有 @Test 方法后执行一次。 |
| @Disabled          | @Ignore      | 被注解的方法不会执行（将被跳过），但会报告为已执行           |
| @Tag               | @Categories  | 表示单元测试类别                                             |
| @RepeatedTest      |              | 表示方法可重复执行 ,不需要再加**@Test**                      |
| @Timeout           |              | 表示测试方法运行如果超过了指定时间将会返回错误   @Timeout(value = 500, unit = TimeUnit.MILLISECONDS) |
| @DisplayName       |              | 表示为测试类或者测试方法设置展示名称                         |
| @ExtendWith        |              | 为测试类或测试方法提供扩展类引用                             |
| @ParameterizedTest |              | 表示方法是参数化测试                                         |





# 3、断言（assertions）

断言（assertions）是测试方法中的核心部分，用来对测试需要满足的条件进行验证。**这些断言方法都是 org.junit.jupiter.api.Assertions 的静态方法**。JUnit 5 内置的断言可以分成如下几个类别：

**检查业务逻辑返回的数据是否合理。**

**所有的测试运行结束以后，会有一个详细的测试报告；**

## 1、简单断言

用来对单个值进行简单的验证。如：

| 方法            | 说明                                 |
| --------------- | ------------------------------------ |
| assertEquals    | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals | 判断两个对象或两个原始类型是否不相等 |
| assertSame      | 判断两个对象引用是否指向同一个对象   |
| assertNotSame   | 判断两个对象引用是否指向不同的对象   |
| assertTrue      | 判断给定的布尔值是否为 true          |
| assertFalse     | 判断给定的布尔值是否为 false         |
| assertNull      | 判断给定的对象引用是否为 null        |
| assertNotNull   | 判断给定的对象引用是否不为 null      |

```java
/**
* 断言：前面断言失败，后面的代码都不会执行
*/
@Test
@DisplayName("简单的断言测试")
void testSimpleAssertions(){
    int cal = cal(1,2);
    //判断相等
    assertEquals(3,cal,"业务逻辑相等");
    Object obj1 = new Object();
    Object obj2 = new Object();
    assertNotSame(obj1, obj2,"对象相同");

}
```





## 2、数组断言

通过 assertArrayEquals 方法来判断两个对象或原始类型的数组是否相等

```java
@Test
@DisplayName("数组断言测试")
void testArrayAssertions(){
    assertArrayEquals(new int[]{2, 2}, new int[]{1, 2}, "数组内容不相等");
}
```





## 3、组合断言

assertAll 方法接受多个 org.junit.jupiter.api.Executable 函数式接口的实例作为要验证的断言，可以通过 lambda 表达式很容易的提供这些断言

```java
/*
* @date  所有断言全部需要成功
*/
@Test
@DisplayName("组合断言测试")
void  testCombinationAssertions(){
    assertAll("test",
              () -> assertTrue(true && true, "结果不为true"),
              () -> assertEquals(2, 2, "结果不是1"));
    System.out.println("=====");
}
```







## 4、异常断言

在JUnit4时期，想要测试方法的异常情况时，需要用**@Rule**注解的ExpectedException变量还是比较麻烦的。而JUnit5提供了一种新的断言方式**Assertions.assertThrows()** ,配合函数式编程就可以进行使用。



```java
@DisplayName("异常断言测试")
@Test
void testExceptionAssertions(){
    //断定业务逻辑一定出现问题,不出现问题才是问题
    assertThrows(ArithmeticException.class, () -> {
        int i = 10 / 2;
    }, "业务逻辑居然正常运行？");
}
```





## 5、超时断言

Junit5还提供了**Assertions.assertTimeout()** 为测试方法设置了超时时间

```java
@Test
@DisplayName("超时测试")
public void timeoutTest() {
    //如果测试方法时间超过1s将会异常
    Assertions.assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500));
}
```



## 6、快速失败

通过 fail 方法直接使得测试失败

```java
/*
* @date  在没有失败消息的情况下使测试失败。
*/
@Test
@DisplayName("快速失败测试")
void testFailAssertions(){
    if ( 2 == 2){
        fail("测试失败");
    }
    System.out.println("测试");
}
```







# 4、前置条件（assumptions）

JUnit 5 中的前置条件（**assumptions【假设】**）类似于断言，不同之处在于<font color='red'>**不满足的断言会使得测试方法失败**</font>，而不满足的**前置条件只会使得测试方法的执行终止,不会抛出失败**。前置条件可以看成是测试方法执行的前提，当该前提不满足时，就没有继续执行的必要.

```java
@DisplayName("assumptions前置条件测试类")
public class assumptionsTest {
    private final String environment = "DEV";

    @Test
    @DisplayName("simple")
    public void simpleAssume() {
        assumeTrue(Objects.equals(this.environment, "DEdV"));
        assumeFalse(() -> Objects.equals(this.environment, "PROD"));
    }

    @Test
    @DisplayName("测试disPlayName注解 03")
    @Disabled
    void testDisplayName03(){
        System.out.println(3);
    }

    @Test
    @DisplayName("assume then do")
    public void assumeThenDo() {
        assumingThat(
                Objects.equals(this.environment, "DEV"),
                () -> System.out.println("In DEV")
        );
    }
}
```





![image-20220219182030378](D:\笔记\Java\SpringBoot03.assets\image-20220219182030378.png)

assumeTrue 和 assumFalse 确保给定的条件为 true 或 false，不满足条件会使得测试执行终止。assumingThat 的参数是表示条件的布尔值和对应的 Executable 接口的实现对象。只有条件满足时，Executable 对象才会被执行；当条件不满足时，测试执行并不会终止。



![image-20220219182043142](D:\笔记\Java\SpringBoot03.assets\image-20220219182043142.png)

assumptions失败后与 @disabled一样在测试报告中表现为skiped

注意和上面的包区分，这些方法都是assumptions下面的，上面是assertions。



# 5、嵌套测试

JUnit 5 可以通过 Java 中的内部类和@Nested 注解实现嵌套测试，从而可以更好的把相关的测试方法组织在一起。在内部类中可以使用@BeforeEach 和@AfterEach 注解，而且嵌套的层次没有限制。



嵌套测试情况下:

* 外层的Test不能驱动内层的Before(After)Each/All之类的方法提前/之后运行
* 内层的Test可以驱动外层的Before(After)Each/All之类的方法提前/之后运行

```java
package com.company.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fu-xiao-liu
 * @Date 2022/2/19 18:22
 */
@DisplayName("嵌套测试")
class TestingAStackDemo {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
        //嵌套测试情况下，外层的Test不能驱动内层的Before(After)Each/All之类的方法提前/之后运行
        assertNull(stack);
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }
            /**
             * 内层的Test可以驱动外层的Before(After)Each/All之类的方法提前/之后运行
             */
            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}
```





# 6、参数化测试

参数化测试是JUnit5很重要的一个新特性，它使得用不同的参数多次运行测试成为了可能，也为我们的单元测试带来许多便利。



利用**@ValueSource**等注解，指定入参，我们将可以使用不同的参数进行多次单元测试，而不需要每新增一个参数就新增一个单元测试，省去了很多冗余代码。



**@ValueSource**: 为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型

**@NullSource**: 表示为参数化测试提供一个null的入参

**@EnumSource**: 表示为参数化测试提供一个枚举入参

**@CsvFileSource**：表示读取指定CSV文件内容作为参数化测试入参

**@MethodSource**：表示读取指定方法的返回值作为参数化测试入参(**注意方法返回需要是一个流,且必须是静态)**



> 当然如果参数化测试仅仅只能做到指定普通的入参还达不到让我觉得惊艳的地步。让我真正感到他的强大之处的地方在于他可以支持外部的各类入参。如:CSV,YML,JSON 文件甚至方法的返回值也可以作为入参。只需要去实现**ArgumentsProvider**接口，任何外部文件都可以作为它的入参。



```java
@ParameterizedTest
@ValueSource(strings = {"one", "two", "three"})
@DisplayName("参数化测试1")
public void parameterizedTest1(String string) {
    System.out.println(string);
    Assertions.assertTrue(StringUtils.isNotBlank(string));
}


@ParameterizedTest
@DisplayName("方法来源参数")
@MethodSource("method")    //指定方法名
public void testWithExplicitLocalMethodSource(String name) {
    System.out.println(name);
    Assertions.assertNotNull(name);
}

static Stream<String> method() {
    return Stream.of("apple", "banana");
}
```







# 指标监控	



# 1、SpringBo	ot Actuator

## 1、简介

未来每一个微服务在云上部署以后，我们都需要对其进行监控、追踪、审计、控制等。SpringBoot就抽取了Actuator场景，使得我们每个微服务快速引用即可获得生产级别的应用监控、审计等功能

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

![image-20220220125919850](D:\笔记\Java\SpringBoot03.assets\image-20220220125919850.png)



## 2、1.x与2.x的不同

![image-20220220125658842](D:\笔记\Java\SpringBoot03.assets\image-20220220125658842.png)



## 3、如何使用

- 引入场景
- 访问 http://localhost:8080/actuator/**

- 暴露所有监控信息为HTTP

```yml
management:
  endpoints:
    enabled-by-default: true #暴露所有端点信息
    web:
      exposure:
        include: '*'  #以web方式暴露
```





## 4、可视化



**<font color='red'>web方式访问</font>**

http:// localhost:8080/ **actuator**/

http:// localhost:8080/ **actuator**/ {endpointName} /  {detailPath}

http://localhost:8080/actuator/beans

http://localhost:8080/actuator/configprops

http://localhost:8080/actuator/metrics

http://localhost:8080/actuator/metrics/jvm.gc.pause

。。。。。。

<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220132258831.png" alt="image-20220220132258831" style="zoom: 67%;" />





<font color='red'>**JMX 方式访问**</font>（Java Management Extensions，即Java管理扩展）

<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220130909818.png" alt="image-20220220130909818" style="zoom:67%;" />







<font color='red'>**第三方界面**</font>

https://github.com/codecentric/spring-boot-admin





# 2、Actuator Endpoint



## 1、最常使用的端点

| ID                 | 描述                                                         |
| ------------------ | ------------------------------------------------------------ |
| `auditevents`      | 暴露当前应用程序的审核事件信息。需要一个`AuditEventRepository组件`。 |
| `beans`            | 显示应用程序中所有Spring Bean的完整列表。                    |
| `caches`           | 暴露可用的缓存。                                             |
| `conditions`       | 显示自动配置的所有条件信息，包括匹配或不匹配的原因。         |
| `configprops`      | 显示所有`@ConfigurationProperties`。                         |
| `env`              | 暴露Spring的属性`ConfigurableEnvironment`                    |
| `flyway`           | 显示已应用的所有Flyway数据库迁移。 需要一个或多个`Flyway`组件。 |
| `health`           | 显示应用程序运行状况信息。                                   |
| `httptrace`        | 显示HTTP跟踪信息（默认情况下，最近100个HTTP请求-响应）。需要一个`HttpTraceRepository`组件。 |
| `info`             | 显示应用程序信息。                                           |
| `integrationgraph` | 显示Spring `integrationgraph` 。需要依赖`spring-integration-core`。 |
| `loggers`          | 显示和修改应用程序中日志的配置。                             |
| `liquibase`        | 显示已应用的所有Liquibase数据库迁移。需要一个或多个`Liquibase`组件。 |
| `metrics`          | 显示当前应用程序的“指标”信息。                               |
| `mappings`         | 显示所有`@RequestMapping`路径列表。                          |
| `scheduledtasks`   | 显示应用程序中的计划任务。                                   |
| `sessions`         | 允许从Spring Session支持的会话存储中检索和删除用户会话。需要使用Spring Session的基于Servlet的Web应用程序。 |
| `shutdown`         | 使应用程序正常关闭。默认禁用。                               |
| `startup`          | 显示由`ApplicationStartup`收集的启动步骤数据。需要使用`SpringApplication`进行配置`BufferingApplicationStartup`。 |
| `threaddump`       | 执行线程转储。                                               |



如果您的应用程序是Web应用程序（Spring MVC，Spring WebFlux或Jersey），则可以使用以下附加端点：

| ID           | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| `heapdump`   | 返回`hprof`堆转储文件。                                      |
| `jolokia`    | 通过HTTP暴露JMX bean（需要引入Jolokia，不适用于WebFlux）。需要引入依赖`jolokia-core`。 |
| `logfile`    | 返回日志文件的内容（如果已设置`logging.file.name`或`logging.file.path`属性）。支持使用HTTP`Range`标头来检索部分日志文件的内容。 |
| `prometheus` | 以Prometheus服务器可以抓取的格式公开指标。需要依赖`micrometer-registry-prometheus`。 |



最常用的Endpoint

- **Health：监控状况**
- **Metrics：运行时指标**

- **Loggers：日志记录**



## 2、Health Endpoint

健康检查端点，我们一般用于在云平台，平台会定时的检查应用的健康状况，我们就需要Health Endpoint可以为平台返回当前应用的一系列组件健康状况的集合。

重要的几点：

- health endpoint返回的结果，应该是一系列健康检查后的一个汇总报告
- 很多的健康检查默认已经自动配置好了，比如：数据库、redis等

- 可以很容易的添加自定义的健康检查机制

```yml
management:
  endpoint:  #对某个端点具体的配置
    health:
      show-details: always #显示详细信息。可显示每个模块的状态信息
```



<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220133205052.png" alt="image-20220220133205052" style="zoom: 80%;" />



## 3、Metrics Endpoint

提供详细的、层级的、空间指标信息，这些信息可以被pull（主动推送）或者push（被动获取）方式得到；

- 通过Metrics对接多种监控系统
- 简化核心Metrics开发

- 添加自定义Metrics或者扩展已有Metrics

<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220133822742.png" alt="image-20220220133822742" style="zoom:80%;" />







## 4、管理Endpoints

### 1、开启与禁用Endpoints

- 默认所有的Endpoint除过shutdown都是开启的。
- 需要开启或者禁用某个Endpoint:
  * 配置模式为  **management.endpoint.{endpointName}.enabled = true**

```yml
management:
  endpoint:
    beans:
      enabled: true
```

- 或者禁用所有的Endpoint然后手动开启指定的Endpoint

```yml
management:
  endpoints:
    enabled-by-default: false
  endpoint:
    beans:
      enabled: true
    health:
      enabled: true
```



### 2、暴露Endpoints

支持的暴露方式

- HTTP：默认只暴露**health** Endpoint
- **JMX**：默认暴露所有Endpoint

- 除过health，剩下的Endpoint都应该进行保护访问。如果引入SpringSecurity，则会默认配置安全访问规则

| ID                 | JMX  | Web  |
| :----------------- | :--- | :--- |
| `auditevents`      | Yes  | No   |
| `beans`            | Yes  | No   |
| `caches`           | Yes  | No   |
| `conditions`       | Yes  | No   |
| `configprops`      | Yes  | No   |
| `env`              | Yes  | No   |
| `flyway`           | Yes  | No   |
| `health`           | Yes  | Yes  |
| `heapdump`         | N/A  | No   |
| `httptrace`        | Yes  | No   |
| `info`             | Yes  | Yes  |
| `integrationgraph` | Yes  | No   |
| `jolokia`          | N/A  | No   |
| `logfile`          | N/A  | No   |
| `loggers`          | Yes  | No   |
| `liquibase`        | Yes  | No   |
| `metrics`          | Yes  | No   |
| `mappings`         | Yes  | No   |
| `prometheus`       | N/A  | No   |
| `scheduledtasks`   | Yes  | No   |
| `sessions`         | Yes  | No   |
| `shutdown`         | Yes  | No   |
| `startup`          | Yes  | No   |
| `threaddump`       | Yes  | No   |



# 3、定制 Endpoint

## 1、定制 Health 信息

```java
构建Health
Health build = Health.down()
                .withDetail("msg", "error service")
                .withDetail("code", "500")
                .withException(new RuntimeException())
                .build();
```





方式一: 通过继承 **AbstractHealthIndicator**

```java
@Component
public class MyConfigHealthIndicator extends AbstractHealthIndicator {
    /**
     * 真实的检查方法
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String,Object> map = new HashMap<>();
        //模拟检查完成
        if(1 == 1){
            builder.up(); //健康
            //builder.status(Status.UP);
            map.put("count",1);
            map.put("ms",200);
        }else {
            builder.down(); //不健康
            //builder.status(Status.OUT_OF_SERVICE);
            map.put("err","连接超时");
            map.put("ms",504);
        }

        builder.withDetail("code",101).withDetails(map);
    }
}
```

方式二:实现接口HealthIndicator

```java
@Component
public class MyConfig02HealthIndicator implements HealthIndicator {
    @Override
    public Health health() {

        //模拟检查后得到 错误码为400
        int errorCode = 400; // perform some specific health check
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }
}
```



<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220135555877.png" alt="image-20220220135555877" style="zoom:80%;" />





## 2、定制info信息

常用两种方式

#### 1、编写配置文件

```yml
info:
  appName: boot-admin
  version: 2.0.1
  mavenProjectName: @project.artifactId@  #使用@@可以获取maven的pom文件值
  mavenProjectVersion: @project.version@
```

可选 

```xml
<resources>
	<resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
    </resource>resource>   
</resource>resources>
```



#### 2、编写InfoContributor

```java
@Component
public class APPInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {

        builder.withDetail("msg","你好")
                .withDetail("hello","admin")
                .withDetails(Collections.singletonMap("world","666"));

    }
}
```

<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220140459890.png" alt="image-20220220140459890" style="zoom:80%;" />



## 3、定制Metrics信息

#### 1、SpringBoot支持自动适配的Metrics

- JVM metrics, report utilization of:

- - Various memory and buffer pools
  - Statistics related to garbage collection

- - Threads utilization
  - Number of classes loaded/unloaded

- CPU metrics
- File descriptor metrics

- Kafka consumer and producer metrics
- Log4j2 metrics: record the number of events logged to Log4j2 at each level

- Logback metrics: record the number of events logged to Logback at each level
- Uptime metrics: report a gauge for uptime and a fixed gauge representing the application’s absolute start time

- Tomcat metrics (`server.tomcat.mbeanregistry.enabled` must be set to `true` for all Tomcat metrics to be registered)
- [Spring Integration](https://docs.spring.io/spring-integration/docs/5.4.1/reference/html/system-management.html#micrometer-integration) metrics



#### 2、增加定制Metrics

统计某个方法被调用的次数

```java
Counter counter;
public CityServiceImpl(MeterRegistry meterRegistry){
    counter = meterRegistry.counter("cityService.getCity.count");
}

@Override
public City getCityById(Long id) {
    counter.increment();
    return  cityMapper.getCityById(id);
}

```



```java
//也可以使用下面的方式
@Bean
MeterBinder queueSize(Queue queue) {
    return (registry) -> Gauge.builder("queueSize", queue::size).register(registry);
}
```





![image-20220220142056104](D:\笔记\Java\SpringBoot03.assets\image-20220220142056104.png)



## 4、定制Endpoint

```java
@Component
@Endpoint(id = "docker")
public class DockerEndpoint {
    @ReadOperation
    public Map getDockerInfo(){
        //端点的读操作  http://localhost:8080/actuator/docker
        return Collections.singletonMap("dockerInfo","docker started.....");
    }

    @WriteOperation
    public void stopDocker(){
        System.out.println("docker stopped.....");
    }
}
```



<img src="D:\笔记\Java\SpringBoot03.assets\image-20220220141319770.png" alt="image-20220220141319770" style="zoom:80%;" />

![image-20220220141444384](D:\笔记\Java\SpringBoot03.assets\image-20220220141444384.png)





# 原理解析

# 1、Profile功能

为了方便多环境适配，springboot简化了profile功能。



## 1、application-profile功能

- 默认配置文件  application.yaml；任何时候都会加载
- 指定环境配置文件  **application-{env}.yaml**

- 激活指定环境

- - 配置文件激活

    ```yml
    spring:
      profiles:
        active: production
    ```

  - 命令行激活：java -jar xxx.jar --**spring.profiles.active=prod  --person.name=xxx**

- - - **修改配置文件的任意值，命令行优先**

- 默认配置与环境配置同时生效
- 同名配置项，profile配置优先





## 2、@Profile条件装配功能

```java
@Configuration(proxyBeanMethods = false)
@Profile("production")
public class ProductionConfiguration {
    // ...
}
```



## 3、profile分组

```xml
spring.profiles.group.production[0]=proddb
spring.profiles.group.production[1]=prodmq

使用：--spring.profiles.active=production  激活
```



# 2、外部化配置

https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config



Spring Boot 使用一种非常特殊的`PropertySource`顺序，旨在允许明智地覆盖值。属性按以下顺序考虑（<font color='red'>**较低项目的值覆盖较早的项目**</font>）：

1. Default properties (specified by setting `SpringApplication.setDefaultProperties`).
2. [`@PropertySource`](https://docs.spring.io/spring-framework/docs/5.3.15/javadoc-api/org/springframework/context/annotation/PropertySource.html) annotations on your `@Configuration` classes. Please note that such property sources are not added to the `Environment` until the application context is being refreshed. This is too late to configure certain properties such as `logging.*` and `spring.main.*` which are read before refresh begins.
3. Config data (such as `application.properties` files).
4. A `RandomValuePropertySource` that has properties only in `random.*`.
5. OS environment variables.
6. Java System properties (`System.getProperties()`).
7. JNDI attributes from `java:comp/env`.
8. `ServletContext` init parameters.
9. `ServletConfig` init parameters.
10. Properties from `SPRING_APPLICATION_JSON` (inline JSON embedded in an environment variable or system property).
11. Command line arguments.
12. `properties` attribute on your tests. Available on [`@SpringBootTest`](https://docs.spring.io/spring-boot/docs/2.6.3/api/org/springframework/boot/test/context/SpringBootTest.html) and the [test annotations for testing a particular slice of your application](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.autoconfigured-tests).
13. [`@TestPropertySource`](https://docs.spring.io/spring-framework/docs/5.3.15/javadoc-api/org/springframework/test/context/TestPropertySource.html) annotations on your tests.
14. [Devtools global settings properties](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools.globalsettings) in the `$HOME/.config/spring-boot` directory when devtools is active.





## 1、外部配置源

常用：**Java属性文件**、**YAML文件**、**环境变量**、**命令行参数**；





## 2、配置文件查找位置

(1) classpath 根路径

(2) classpath 根路径下config目录

(3) jar包当前目录  

(4) jar包当前目录的config目录

(5) /config子目录的直接子目录

## 3、配置文件加载顺序：

1. 　当前jar包内部的application.properties和application.yml
2. 　当前jar包内部的application-{profile}.properties 和 application-{profile}.yml

1. 　引用的外部jar包的application.properties和application.yml
2. 　引用的外部jar包的application-{profile}.properties 和 application-{profile}.yml





总结 **: <font color='red'>指定环境优先，外部优先，后面的可以覆盖前面的同名配置项</font>**









# 3、自定义starter

## 1、starter启动原理

- xxx-start项目中的**starter-pom**文件引入 xxx-start-autoConfigurer（包括了源码以及配置信息）项目

<img src="D:\笔记\Java\SpringBoot03.assets\image-20220221120714388.png" alt="image-20220221120714388" style="zoom:90%;" />



- autoconfigure包中配置使用 <font color='red'>**META-INF/spring.factories** </font>中 **EnableAutoConfiguration 的值，使得项目启动加载指定的自动配置类**
- **编写自动配置类 xxxAutoConfiguration -> xxxxProperties**

- - **@Configuration**
  - **@Conditional**

- - **@EnableConfigurationProperties**
  - **@Bean**

- - ......

**引入starter** **--- xxxAutoConfiguration --- 容器中放入组件 ---- 绑定xxxProperties ----** **配置项**

## 2、自定义starter

**company-hello-spring-boot-starter（启动器）**

**company-hello-spring-boot-starter-autoconfigure（自动配置包）**

![image-20220221122032880](D:\笔记\Java\SpringBoot03.assets\image-20220221122032880.png)





![image-20220221122259721](D:\笔记\Java\SpringBoot03.assets\image-20220221122259721.png)



# 4、SpringBoot原理 

Spring原理【[Spring注解](https://www.bilibili.com/video/BV1gW411W7wy?p=1)】、SpringMVC原理、自动配置原理、SpringBoot原理



## 1、SpringBoot启动过程 



* 创建  **SpringApplication**

  * 保存一些信息。

  * 判定当前应用的类型 : 通过一些工具类: ClassUtils 得出是 **Servlet**类型

    * ![image-20220221125659649](D:\笔记\Java\SpringBoot03.assets\image-20220221125659649.png)

  * bootstrapRegistryInitializers：引导注册表初始化程序（List < BootstrapRegistryInitializer.class>）：去spring.factories文件中找 org.springframework.boot.BootstrapperRegistryInitializer,

    * ![image-20220221130244927](D:\笔记\Java\SpringBoot03.assets\image-20220221130244927.png)

    * ![image-20220221125945787](D:\笔记\Java\SpringBoot03.assets\image-20220221125945787.png)

  * 找 **ApplicationContextInitializer**上下文初始化器去spring.factories找 ApplicationContextInitializer

    * List<ApplicationContextInitializer<?>> initializers


  * 找 **ApplicationListener**  ；应用监听器去spring.factories找 ApplicationListener
    * List<ApplicationListener<?>> listeners

  * <img src="D:\笔记\Java\SpringBoot03.assets\image-20220221130423886.png" alt="image-20220221130423886" style="zoom:120%;" />





* **运行 <font color='red'>SpringApplication  </font>** :   **return new SpringApplication(primarySources).run(args);**
    * **StopWatch.start()**  用来记录应用的启动时间
    * **创建引导上下文**（**DefaultBootstrapContext**环境）**create**BootstrapContext()
      * 获取到所有之前的 **bootstrapRegistryInitializers**挨个执行 **intitialize()** 来完成对引导启动器上下文环境设置

```java
@FunctionalInterface
public interface BootstrapRegistryInitializer {
	/**
	 * Initialize the given {@link BootstrapRegistry} with any required registrations.
	 * @param registry the registry to initialize
	 */
	void initialize(BootstrapRegistry registry);

}
```

* 

  * <font color='red'>**configureHeadlessProperty**</font>(); 让当前应用进入**headless**(自力更生)模式。**java.awt.headless **

  * **<font color='red'>getRunListeners</font>**(args); 获取所有 <font color='red'>RunListener</font>（运行监听器）【为了方便所有Listener进行事件感知】

    * **getSpringFactoriesInstances** 去spring.factories找 SpringApplicationRunListener. 

    * ![image-20220221151904496](D:\笔记\Java\SpringBoot03.assets\image-20220221151904496.png)

    * <img src="D:\笔记\Java\SpringBoot03.assets\image-20220221151615893.png" alt="image-20220221151615893" style="zoom:90%;" />

      

  * <font color='red'>**listeners.starting**</font>(xxx, xxx);遍历 SpringApplicationRunListener 调用 starting 方法；
    
* 相当于通知所有感兴趣系统正在启动过程的人，项目正在 starting。
    
  * **<font color='red'>new DefaultApplicationArguments(args)</font>**;保存命令行参数；ApplicationArguments
  
  * java -jar xx.jar --**server.port=8888**  server.prot=8888就会存入**ApplicationArguments**中
  
  * <font color='red'> **prepareEnvironment**</font>() 准备环境

    * <font color='red'>**getOrCreateEnvironment();** </font>返回或者创建基础环境信息对象。ApplicationServletEnvironment

    * <font color='red'>**configureEnvironment();**</font>配置环境信息对象。
  
      * addConversionService(); 类型转换器. 例如:需要yml中的**string**转换为**integer**
    * **configurePropertySources**(env, args); 加载外部配置源 通过**<font color='red'>@propertySources</font>**注解,**下图**
      * ![configurePropertySources](D:\笔记\Java\SpringBoot03.assets\image-20220221154152320.png)

      * 读取所有的配置源的配置属性值。

    * **ConfigurationPropertySources.attach**(environment);  绑定环境信息 . 保存工作

    * 监听器调用 **environmentPrepared()；**

      *  调用<font color='red'>**listener.environmentPrepared()**</font>: 通知所有的监听器当前环境准备完成.

      * ![image-20220221154521032](D:\笔记\Java\SpringBoot03.assets\image-20220221154521032.png)

  
  
  * **<font color='red'>创建IOC容器</font>**   createApplicationContext
  * 根据项目类型（Servlet）创建容器，
    * 当前会创建 AnnotationConfigServletWebServerApplicationContext();

  * <font color='red'>  **prepareContext()**  </font>准备ApplicationContext IOC容器的基本信息

    * 保存环境信息 **context.setEnvironment(environment);**

    * IOC容器的后置处理流程 **postProcessApplicationContext(context);**

    * 应用初始化器；**<font color='red'>applyInitializers(context);</font>**
  
    * 遍历所有的 **ApplicationContextInitializer** :   调用 **initialize** 来对ioc容器进行初始化扩展功能
      * 遍历所有的 **listener** 调用 **contextPrepared(context)**。EventPublishRunListenr；通知所有的监听器contextPrepared

      * 所有的监听器 调用 contextLoaded。通知所有的监听器 contextLoaded； 就是告诉所有监听器：IOC已经加载了

        

  * <font color='red'>**refreshContext(context);**</font> 刷新IOC容器

    * 创建容器中的所有组件（Spring注解）
  
    * ```java
      //实例化所以组件
      // Instantiate all remaining (non-lazy-init) singletons.
    finishBeanFactoryInitialization(beanFactory);
      ```

      
  
  
  * afterRefresh() 容器刷新完成后工作？
  * <font color='red'>**listeners.started(context);** </font>所有监听器调用通知所有的监听器 started
  * **callRunners(context, applicationArguments);** 调用所有runners；
    * 获取容器中的 **<font color='red'>ApplicationRunner</font>** 
    * 获取容器中的  **<font color='red'>CommandLineRunner</font>**
    * 合并所有runner并且按照@Order进行排序
    * 遍历所有的runner。调用 run 方法
  
  * 如果以上有异常
    * 调用Listener的failed
  
  *  listeners.running(context);调用所有监听器的 running 方法,通知所有的监听器 running 
  * running如果有问题。继续通知 failed 。调用所有 Listener 的 failed；通知所有的监听器 failed

## 2、Application Events and Listeners

https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-application-events-and-listeners

**ApplicationContextInitializer**

**ApplicationListener**

**SpringApplicationRunListener**

## 3、ApplicationRunner 与 CommandLineRunner