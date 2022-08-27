



[SpringMVC概念解读](https://www.cnblogs.com/xxkj/p/14250588.html)



# SpringMVC依赖

![](http://mk-images.tagao.top/img/202205211349442.png?imageslim)





```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

```xml
<!--避免资源文件过滤-->
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```





## 1.依赖解析



### 1.spring-web 

1.spring-web 提供了核心 HTTP 集成，包括一些便捷的 servlet 过滤器， Spring HTTP 调用，用于集成其它 web 框架的基础结构以及技术（Hessian，Burlap）。

###  2.spring-webmvc 

2.spring-webmvc 是 Spring MVC 的一个实现。spring-webmvc 依赖于 spring-web，这样包含它就会间接地添加 spring-web，不必显示添加 spring-web。

![](http://mk-images.tagao.top/img/202205211522965.png?imageslim)



### 3.servlet-api 

servlet-api 通俗来讲servlet是一个java的接口，实现servlet接口的类可以由服务器调用，运行在服务器端。是支持Serlvet的jar包，我们在使用到HttpServletRequest和HttpServletResponse等对象，需要使用到这个jar包。



​	在Spring-WEB-MVC中的请求处理中心DispatcherServlet继承到了HttpServlet ，HttpServlet的父类用到了Servlet接口。 <font color='red'>**DispatcherServlet本质上是实现了Servlet接口，所以才需要导入servlet-api 的依赖 。 **</font>

![](http://mk-images.tagao.top/img/202205211428737.png?imageslim)









### 4.JSP

JSP, 全称是JavaServer Pages, java 服务器页面，可以理解成Html。

### 5.JSTL

JSTL（Java server pages standarded tag library，即 JSP标准标签库，可以简化JSP开发。



## 2.依赖注意

1）jstl的groupId是javax.servlet，另一个groupId为javax.servlet.jsp.jstl包会出现ClassNotFound的异常
2）参考Tomcat版本选择servlet和jsp依赖的版本，网址为http://tomcat.apache.org/whichversion.html，

​	`Tomcat9.0.x`，故选择<font color='red'>**4.0.1的javax.servlet-api**</font>和<font color='red'>**2.3.3的javax.servlet.jsp-api**</font>，这里的两个依赖scope建议设置为provided，表示不会被打包，因为Tomcat自身会提供；这里版本选择不对可能造成错误？验证不通过。
3）lombok的scope建议设置为provided(表明该包只在编译和测试的时)，但是不设置也不会报错



## 3.名次解析



### 3.1Servlet

Servlet （Server applet)是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。



Servlet就是一个Java接口，只有5个方法的接口，Servlet接口中的5大方法：

```markdown
- 1、void init(ServletConfig)：初始化servlet，它是servlet的生命周期方法，由web容器调用一次。

- 2、void service(ServletRequest, ServletResponse)：为传入的请求提供响应，它由容器的每个请求调用。

- 3、void destroy()：仅被调用一次，表明servlet正在被销毁。

- 4、ServletConfig getServletConfig()：返回ServletConfig对象。

- 5、String getServletInfo()：返回有关servlet的信息，如作者、版权、版本等。
```

​    Servlet接口定义的是一套处理网络请求的规范，所有实现servlet的类，都要实现它的5个方法。其中最主要的是两个生命周期方法init()和destroy()，还有一个处理请求service()。也就是说所有实现servlet接口的类，或者说，所有想要处理网络请求的类，都要回答三个问题：初始化要做什么，销毁时要做什么，接受到请求时要做什么。

   <font color='red'>**Spring主要通过DispatcherServlet实现了Servlet。**</font>init()接口在其父类HttpServletBean中实现。service()接口在其父类FrameworkServlet中实现（回调了DispatcherServlet的doService()方法）。destroy()接口在父类FrameworkServlet中实现。

​    实现了servlet的类还不能处理请求。请求是通过servlet容器来到servlet，比如我们最常用的tomcat。所以需要将servlet部署到一个容器中，否则servlet根本不会起作用。容器（如tomcat）才是与客户端直接打交道的，它监听了端口，请求过来后，根据url等信息，确定要将请求交给哪个servlet去处理，然后调用那个servlet的service方法，service方法返回一个response对象，容器再把这个response返回给客户端。





### 3.2ServletContext

<font color='red'>**ServletContext**</font>：官网叫做servlet上下文。<font color='red'>**服务器会为每个工程创建一个对象**</font>，这个对象就是对象。这个对象是全局唯一，而且工程内部所有servlet都共享这个对象。所以叫全局应用程序共享对象。

![](http://mk-images.tagao.top/img/202205211413830.png?imageslim)



1. 是一个域对象 ,是服务器在内存上创建的存储空间，用于在不同动态资源（servlet）之间传递与共享数据。

2. 可以读取全局配置参数

3. 可以搜索当前工程目录下面的资源文件

4. 可以获取当前工程名字（了解）











### 3.3WebApplicationContext

<font color='red'>**WebApplicationContext**</font>是实现<font color='red'>**ApplicationContext**</font>接口的子类，它提供了web应用经常需要用到的特性。

作用： 

1. 它允许从相对于<font color='red'>**Web根目录的路径中加载配置文件完成初始化工作。**</font>从WebApplicationContext中可以获取ServletContext引用，整个<font color='red'>**WebApplicationContext对象将作为属性放置在ServletContext**</font>中，以便Web应用环境可以访问Spring上下文。 如果需要获取它，你可以通过`RequestContextUtils`工具类中的静态方法来拿到这个web应用的上下文`WebApplicationContext`。
2. WebApplicationContext还为Bean提供了三个新的作用域，request、session和globalsession。 其中两个参数HttpServletRequest：服务器从客户端拿去数据 HttpServletResponse：服务器向前台传送数据





<font color='red'>**WebApplicationContext定义了DispatcherServlet需要的Bean组件**</font>，并在容器启动的时候生成Bean。Bean包括**Controllers**、**ViewResolver**、**HandlerMapping**等等。





### 3.4 DispatcherServlet

 	 在整个 Spring MVC 框架中，DispatcherServlet（前置控制器） <font color='red'>**处于核心位置**</font>，<font color='red'>**负责协调和组织不同组件完成请求处理并返回响应工作（本身不进行逻辑上处理，交由WebApplicationContext中的Bean来处理）**</font>。配置在web.xml文件中的，拦截匹配的请求，将拦截下来的请求，依据相应的规则分发到目标Controller来处理，主要用于进行调度，控制流程。功能主要有：

​    1、文件上传解析，如果请求类型是multipart将通过MultipartResolver进行文件上传解析；

​    2、通过HandlerMapping，将请求映射到处理器（返回一个HandlerExecutionChain，它包括一个处理器、多个HandlerInterceptor拦截器）；

​    3、通过HandlerAdapter支持多种类型的处理器(HandlerExecutionChain中的处理器)；

​    4、通过ViewResolver解析逻辑视图名到具体视图实现；

​    5、本地化解析；

​    6、渲染具体的视图等；

​    7、如果执行过程中遇到异常将交给HandlerExceptionResolver来解析。





 <font color='red'>**DispatcherServlet对进行处理时请求需要使用WebApplicationContext中Bean**</font>

```markdown
# 1、Controller：处理器/页面控制器，实现的是MVC中C 那个组成部分，但控制逻辑转移到前端控制器了，用于对请求进行处理；

# 2、HandlerMapping：请求到处理器的映射，如果映射成功返回一个HandlerExecutionChain对象（包含一个Handler处理器（页面控制器）对象、多个HandlerInterceptor拦截器）对象，如BeanNameUrlHandlerMapping将URL与Bean名字映射，映射成功的Bean就是此处的处理器；

# 3、HandlerAdapter：HandlerAdapter将会把处理器包装为适配器，从而支持多种类型的处理器，即适配器设计模式的应用，从而很容易支持很多类型的处理器；如SimpleControllerHandlerAdapter将对实现了Controller接口的Bean进行适配，并且掉处理器的handleRequest方法进行功能处理；

# 4、ViewResolver：ViewResolver将把逻辑视图名解析为对应的视图，通过这种策略模式，很容易更换其他视图技术；如InternalResourceViewResolver将逻辑视图名映射为jsp视图；

# 5、LocalResover：本地化解析，因为Spring支持国际化，因此LocalResover解析客户端的Locale信息从而方便进行国际化；

# 6、ThemeResovler：主题解析，解析你的web应用所使用的主题，以提供个性化的布局。通过它来实现一个页面多套风格，即常见的类似于软件皮肤效果；

# 7、MultipartResolver：文件上传解析，用于支持文件上传；

# 8、HandlerExceptionResolver：处理器异常解析，可以将异常映射到相应的统一错误界面，从而显示用户友好的界面（而不是给用户看到具体的错误信息）；

# 9、RequestToViewNameTranslator：当处理器没有返回逻辑视图名等相关信息时，自动将请求URL映射为逻辑视图名；

# 10、FlashMapManager：用于管理FlashMap的策略接口，FlashMap用于存储一个请求的输出，当进入另一个请求时作为该请求的输入，通常用于重定向场景。
```



![](http://mk-images.tagao.top/img/202205212033899.png?imageslim)



  DispatcherServlet 处理请求的流程：

​    1、Tomcat 启动，对<font color='red'>**DispatcherServlet 进行实例化**</font>，然后调用它的 <font color='red'>**init() 方法进行初始化**</font>，在这个初始化过程中完成了：对 web.xml 中初始化参数的加载、建立 WebApplicationContext (SpringMVC的IOC容器)、进行组件的初始化；

​    2、客户端发出请求，由 Tomcat 接收到这个请求，如果匹配 DispatcherServlet 在 web.xml 中配置的映射路径，Tomcat 就将请求转交给 DispatcherServlet 处理；

​    3、DispatcherServlet 从容器中<font color='red'>**取出所有 HandlerMapping **</font>实例（每个实例对应一个 HandlerMapping 接口的实现类）并遍历， <font color='red'>**HandlerMapping 会根据请求信息，寻找匹配的Handler**</font>，并且将这个 <font color='red'>**Handler 与一堆 HandlerInterceptor**</font>（拦截器）封装成一个 HandlerExecutionChain 对象，一旦有一个 HandlerMapping 可以找到 Handler 则退出循环；

​    4、DispatcherServlet 取出 HandlerAdapter 组件，根据已经找到的 Handler，再从所有 HandlerAdapter 中找到可以处理该 Handler 的 HandlerAdapter 对象；

​    5、执行 HandlerExecutionChain 中所有拦截器的 preHandler() 方法，然后再利用 HandlerAdapter 执行 Handler ，执行完成得到 ModelAndView，再依次调用拦截器的 postHandler() 方法；

​    6、利用 ViewResolver 将 ModelAndView 或是 Exception（可解析成 ModelAndView）解析成 View，然后 View 会调用 render() 方法再根据 ModelAndView 中的数据渲染出页面；

​    7、最后再依次调用拦截器的 afterCompletion() 方法，这一次请求就结束了。



### 3.5关系





ServletContext、WebApplicationContext之间的关系

WebApplicationContext(Ioc容器)需要依赖ServletContext这个宿主环境,既<font color='red'>**WebApplicationContext对象将作为属性放置在ServletContext**</font>。 Web应用上下文作为`ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE`属性放在`ServletContext`中，以便Web应用可以访问Spring应用上下文。



WebApplicationContext、DispatcherServlet之间的关系

<font color='red'>**DispatcherServlet进行请求处理需要用到WebApplicationContext上下文中的Bean。**</font>





# ContextLoaderListener

问题：

下面是对Web.xml的配置(包括了spring 以及springmvc)

```xml
 <!-- Spring配置文件开始  -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:applicationContext.xml</param-value>
    </context-param>

    <!--启动Tomcat时，通过监听器加载spring运行环境 -->
    <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- Spring配置文件结束 -->


  <!--对springMVC配置servlet-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:spring-mvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <!--对springMVC配置 结束 servlet-->
```



疑问：ContextLoaderListener 是作用是什么?

当通过`<import>` 标签 spring-mvc.xml 于 applicationContext.xml  合并。 在 DispatcherServlet中把

` <param-value>classpath*:spring-mvc.xml</param-value>` 改成

 `<param-value>classpath*:applicationContext.xml</param-value>`  就不需要 web.xml 前面的

```xml
 <!-- Spring配置文件开始  -->
	内容 --- 内容  
<!-- Spring配置文件结束 -->
```

这部分就不需要配置了。



<font color='red'>问题</font>： ContextLoaderListener 是什么，作用是啥 ？ 

为什么 <!-- 对springMVC配置servlet --> 配置可以省去对`ContextLoaderListener` 



`ContextLoaderListener` 监听器的作用就是启动Web容器时，自动装配`ApplicationContext`的配置信息。

因为它实现了ServletContextListener这个接口，在web.xml配置这个监听器，启动容器时，就会默认执行它实现的方法。至于ApplicationContext.xml这个配置文件部署在哪，如何配置多个xml文件，书上都没怎么详细说明。现在的方法就是查看它的API文档。在ContextLoaderListener中关联了ContextLoader这个类，所以整个加载配置过程由ContextLoader来完成。



<font color='red'>**源码解读**</font>

第一段说明ContextLoader可以由 ContextLoaderListener和ContextLoaderServlet生成。如果查看ContextLoaderServlet的API，可以看到它也关联了ContextLoader这个类而且它实现了HttpServlet这个接口。



第二段，ContextLoader创建的是 XmlWebApplicationContext这样一个类，它实现的接口是WebApplicationContext->ConfigurableWebApplicationContext->ApplicationContext->BeanFactory这样一来spring中的所有bean都由这个类来创建。



第三段,讲如何部署applicationContext的xml文件。

如果在web.xml中不写任何参数配置信息，默认的路径是/WEB-INF/applicationContext.xml，在WEB-INF目录下创建的xml文件的名称必须是applicationContext.xml；





如果在web.xml中不写任何参数配置信息，默认的路径是/WEB-INF/applicationContext.xml，在WEB-INF目录下创建的xml文件的名称必须是applicationContext.xml；

如果是要自定义文件名可以在web.xml里加入contextConfigLocation这个context参数：

```xml
<context-param>

	<param-name>contextConfigLocation</param-name>

	<param-value>/WEB-INF/classes/applicationContext-*.xml</param-value>

</context-param>
```

在`<param-value> </param-value>`里指定相应的xml文件名，如果有多个xml文件，可以写在一起并一“,”号分隔。

由此可见applicationContext.xml的文件位置就可以有两种默认实现：

第一种：直接将之放到/WEB-INF下，之在web.xml中声明一个listener；

第二种：将之放到classpath下，但是此时要在web.xml中加入`<context-param>`，用它来指明你的applicationContext.xml的位置以供web容器来加载。按照Struts2 整合spring的官方给出的档案，写成：





> **<font color='red'>为什么不需要ContextLoaderListener也行</font>**

ApplicationContext是Spring的容器，将所以spring所有配置文件放到了DispatcherServlet中，而DispatcherServlet在初始化的时候拥有自己独立的IoC容器，所以不用加载Spring的ApplicationContext容器





[资料](https://zhuanlan.zhihu.com/p/128064108)

