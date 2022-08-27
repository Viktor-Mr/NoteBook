



我在这里说一下,老师这里说错了,你重定向了用的不是Model,重定向用的是默认Model,默认只能在请求域有效,所以得到的空model对象实际上是RedirectAttributes要在这里放东西

# web开发



## 5、视图解析与模板引擎

视图解析：**SpringBoot默认不支持 JSP(打包成jar包, 不支持压缩包编译 )，需要引入第三方模板引擎技术实现页面渲染。**



### 5.1、视图解析



![image-20220213181829354](D:\笔记\Java\SpringBoot02.assets\image-20220213181829354.png)



#### 5.1.1、视图解析原理流程



1、目标方法处理的过程中，所有数据都会被放在 **ModelAndViewContainer 里面。包括数据和视图地址**

**2、方法的参数是一个自定义类型对象（从请求参数中确定的），把他重新放在** **ModelAndViewContainer** 

**3、任何目标方法执行完成以后都会返回 ModelAndView**（数据和视图地址）。

**4、processDispatchResult  处理派发结果（页面改如何响应）**

- 1、**render**(**mv**, request, response); 进行页面渲染逻辑

- - 1、根据方法的String返回值得到 **View** 对象【定义了页面的渲染逻辑】

- - - 1、所有的视图解析器尝试是否能根据当前返回值得到**View**对象
    - 2、得到了  **redirect:/main.html** --> Thymeleaf new **RedirectView**()

- - - 3、ContentNegotiationViewResolver 里面包含了下面所有的视图解析器，内部还是利用下面所有视图解析器得到视图对象。
    - 4、view.render(mv.getModelInternal(), request, response);   视图对象调用自定义的render进行页面渲染工作

- - - - **RedirectView 如何渲染【重定向到一个页面】**
      - **1、获取目标url地址**

- - - - **2、response.sendRedirect(encodedURL);**

**视图解析：**

- - **返回值以 forward: 开始： new InternalResourceView(forwardUrl); -->  转发**request.getRequestDispatcher(path).forward(request, response);

  - **返回值以** **redirect: 开始：** **new RedirectView() --> 重定向** 

    response.sendRedirect(encodedURL);

- - **返回值是普通字符串： new ThymeleafView（）--->** 





![image-20220215105937217](D:\笔记\Java\SpringBoot02.assets\image-20220215105937217.png)



![image-20220215105549592](D:\笔记\Java\SpringBoot02.assets\image-20220215105549592.png)

![image-20220215105558146](D:\笔记\Java\SpringBoot02.assets\image-20220215105558146.png)



### 5.2、模板引擎-Thymeleaf

#### 5.2.1、thymeleaf简介

Thymeleaf is a modern server-side Java template engine for both web and standalone environments, capable of processing HTML, XML, JavaScript, CSS and even plain text.

**现代化、服务端Java模板引擎**



#### 5.2.2、基本语法



**<font color='red'>1、表达式</font>**

| 表达式名字 | 语法   | 用途                               |
| ---------- | ------ | ---------------------------------- |
| 变量取值   | ${...} | 获取请求域、session域、对象等值    |
| 选择变量   | *{...} | 获取上下文对象值                   |
| 消息       | #{...} | 获取国际化等值                     |
| 链接       | @{...} | 生成链接  字符串拼接成 url         |
| 片段表达式 | ~{...} | jsp:include 作用，引入公共页面片段 |

在a标签中使用th:href来表达 /orders/{orderNo}?param1=aaa&param2=bbb 这个链接的正确做法:

```html
<a th:href="@{'/orders/' + ${orderNo}(param1=aaa,param2=bbb)}">
```



<font color='red'>**2、字面量**</font>

文本值: **'one text'** **,** **'Another one!'** **,…**数字: **0** **,** **34** **,** **3.0** **,** **12.3** **,…**布尔值: **true** **,** **false**

空值: **null**

变量： one，two，.... 变量不能有空格

**<font color='red'>3、文本操作</font>**

字符串拼接: **+**

变量替换: **|The name is ${name}|** 



**<font color='red'>4、数学运算</font>**

运算符: + , - , * , / , %



**<font color='red'>5、布尔运算</font>**

运算符:  **and** **,** **or**

一元运算: **!** **,** **not** 



**<font color='red'>6、比较运算</font>**

比较: **>** **,** **<** **,** **>=** **,** **<=** **(** **gt** **,** **lt** **,** **ge** **,** **le** **)**等式: **==** **,** **!=** **(** **eq** **,** **ne** **)** 



**<font color='red'>7、条件运算</font>**

If-then: **(if) ? (then)**

If-then-else: **(if) ? (then) : (else)**

Default: (value) **?: (defaultvalue)** 



**<font color='red'>8、特殊操作</font>**

无操作： _



#### 5.2.3、设置属性值-th:attr

设置单个值

```html
<form action="subscribe.html" th:attr="action=@{/subscribe}">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
  </fieldset>
</form>
```



设置多个值

```html
<img src="../../images/gtvglogo.png"  th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```



以上两个的代替写法 th:xxxx

```html
<input type="submit" value="Subscribe!" th:value="#{subscribe.submit}"/>
<form action="subscribe.html" th:action="@{/subscribe}">
```



所有h5兼容的标签写法

https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-value-to-specific-attributes



#### 5.2.4、迭代



#### 5.2.5、条件运算



#### 5.2.6、属性优先级



#### 5.2.3、设置属性值-th:attr





### 5.3、thymeleaf使用

#### 1、引入Starter

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```



#### 2、自动配置好了thymeleaf

```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ThymeleafProperties.class)
@ConditionalOnClass({ TemplateMode.class, SpringTemplateEngine.class })
@AutoConfigureAfter({ WebMvcAutoConfiguration.class, WebFluxAutoConfiguration.class })
public class ThymeleafAutoConfiguration { }
```



自动配好的策略

- 1、所有thymeleaf的配置值都在 ThymeleafProperties
- 2、配置好了 **SpringTemplateEngine** 

- **3、配好了** **ThymeleafViewResolver** 
- 4、我们只需要直接开发页面

```java
public static final String DEFAULT_PREFIX = "classpath:/templates/";

public static final String DEFAULT_SUFFIX = ".html";  //xxx.html
```

#### 3、页面开发

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1 th:text="${msg}">哈哈</h1>
<h2>
    <a href="www.atguigu.com" th:href="${link}">去百度</a>  <br/>
    <a href="www.atguigu.com" th:href="@{link}">去百度2</a>
</h2>
</body>
</html>
```





### 5.4、构建后台管理系统

#### 1、项目创建

#### 2、静态资源处理

#### 3、路径构建

#### 4、模板抽取

#### 5、页面跳转

#### 6、数据渲染



## 6、拦截器

### 1、HandlerInterceptor 接口

```java
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * @date  方法执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        log.info("preHandle进行拦截的url是 {}" ,requestURL);

        //登录请求
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("loginUser");
        //放行
        if (user != null) {
            return true;
        }
        //拦截 重定向回到 /login
        session.setAttribute("msg","请重新登录");
        response.sendRedirect("/");
        return false;
    }
    
    /**
     * @date  方法执行后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        log.info("postHandle进行拦截的url是 {}" ,requestURL);
    }

    /**
     * @date  渲染页面以后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        log.info("afterCompletion进行拦截的url是 {}" ,requestURL);
    }
}

```



### 2、配置拦截器

```java
/**
 * 1、编写一个拦截器实现HandlerInterceptor接口
 * 2、拦截器注册到容器中（实现WebMvcConfigurer的addInterceptors）
 * 3、指定拦截规则【如果是拦截所有，静态资源也会被拦截】
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")  //所有请求都被拦截包括静态资源
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**","/js/**"); //放行的请求
    }
}
```





### 3、拦截器原理

1、根据当前请求，找到**HandlerExecutionChain【**可以处理请求的handler以及handler的所有 拦截器】

2、先来**顺序执行** 所有拦截器的 preHandle方法

- 1、如果当前拦截器prehandler返回为true。则执行下一个拦截器的preHandle
- 2、如果当前拦截器返回为false。直接    倒序执行所有已经执行了的拦截器的  afterCompletion；

**3、如果任何一个拦截器返回false。直接跳出不执行目标方法**

**4、所有拦截器都返回True。执行目标方法**

**5、倒序执行所有拦截器的postHandle方法。**

**6、前面的步骤有任何异常都会直接倒序触发** afterCompletion

7、页面成功渲染完成以后，也会倒序触发 afterCompletion



![image-20220215114758680](D:\笔记\Java\SpringBoot02.assets\image-20220215114758680.png)

![image-20220215114642180](D:\笔记\Java\SpringBoot02.assets\image-20220215114642180.png)







## 7、文件上传



### 1、页面表单

```html
<form role="form" th:action="@{/upload}" method="post"  enctype="multipart/form-data">
    <div class="form-group">
        <label for="exampleInputFile">头像</label>
        <input type="file" name="headerImg" id="exampleInputFile">
    </div>
    <div class="form-group">
        <label for="exampleInputFile">生活照</label>
        <input type="file" name="photos" multiple>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

文件上传指定文件为 : enctype="multipart/form-data"

多文件上传 :需添加 multiple 





### 2、文件上传代码

```java
@PostMapping("/upload")
public  String upload(@RequestPart("email") String email,
                      @RequestPart("username") String username,
                      @RequestPart("headerImg") MultipartFile headerImg,
                      @RequestPart("photos") MultipartFile[] photos) throws IOException {
    log.info("上传信息: email= {} username ={} headerImg= {} photos ={}",email,username,headerImg.getName(),photos.length);
    //判断文件是否为空
    if ( !headerImg.isEmpty() ) {
        String originalFilename = headerImg.getOriginalFilename();
        //保存到文件服务器，OSS服务器
        headerImg.transferTo(new File("I:\\tables\\" + originalFilename));
    }
    for (MultipartFile photo: photos ) {
        String originalFilename = photo.getOriginalFilename();
        //取出文件然后放到指定的oss 或者 本地文件中
        photo.transferTo(new File("I:\\tables\\" + originalFilename));
    }

    return "main";
}
```





### 3、自动配置原理

**文件上传自动配置类-MultipartAutoConfiguration-MultipartProperties**

- 自动配置好了 **StandardServletMultipartResolver   【文件上传解析器】**
- **原理步骤**

- - **1、请求进来使用文件上传解析器判断（**isMultipart**）并封装（**resolveMultipart，**返回**MultipartHttpServletRequest**）文件上传请求**
  - **2、参数解析器来解析请求中的文件内容封装成MultipartFile**

- - **3、将request中文件信息封装为一个Map；**MultiValueMap<String, MultipartFile>

**FileCopyUtils**。实现文件流的拷贝



```java
@PostMapping("/upload")
public String upload(@RequestParam("email") String email,
                     @RequestParam("username") String username,
                     @RequestPart("headerImg") MultipartFile headerImg,
                     @RequestPart("photos") MultipartFile[] photos)
```





![image-20220215183223802](D:\笔记\Java\SpringBoot02.assets\image-20220215183223802.png)





## 8、异常处理

### 1、错误处理



#### 1、默认规则

- 默认情况下，Spring Boot提供`/error`处理所有错误的映射
- 对于机器客户端，它将生成JSON响应，其中包含错误，HTTP状态和异常消息的详细信息。对于浏览器客户端，响应一个“ whitelabel”错误视图，以HTML格式呈现相同的数据



<font color='red'>浏览器</font>返回的默认错误信息

![image-20220216105333460](D:\笔记\Java\SpringBoot02.assets\image-20220216105333460.png)

<font color='red'>PostMan</font>返回的默认错误信息 JSON 格式

![image-20220216105303783](D:\笔记\Java\SpringBoot02.assets\image-20220216105303783.png)



![image-20220216110212653](D:\笔记\Java\SpringBoot02.assets\image-20220216110212653.png)



#### 2、定制错误处理逻辑

- 自定义错误页

- - error/404.html   error/5xx.html；有精确的错误状态码页面就匹配精确，没有就找 4xx.html；如果都没有就触发白页

    

- **全局异常** : **<font color='red'>@ControllerAdvice</font> +  <font color='red'>@ExceptionHandler</font>**处理全局异常；底层是 **ExceptionHandlerExceptionResolver 支持的**



- **自定义异常**: **<font color='red'>@ResponseStatus</font>**+自定义异常 ；底层是 **ResponseStatusExceptionResolver ，responsestatus注解的信息被底层调用** 

  * **response.sendError(statusCode, resolvedReason)；tomcat发送的/error**

  

- **Spring底层异常**": 如参数类型转换异常；**DefaultHandlerExceptionResolver 处理框架底层的异常。**
  
  * response.sendError(HttpServletResponse.**SC_BAD_REQUEST**, ex.getMessage());



- 自定义实现 **HandlerExceptionResolver** 处理异常；可以作为默认的全局异常处理规则

![image-20220216163256118](D:\笔记\Java\SpringBoot02.assets\image-20220216163256118.png)



- **ErrorViewResolver**  实现自定义处理异常；

- - response.sendError 。error请求就会转给controller
  - 异常没有任何人能处理。tomcat底层 response.sendError。error请求就会转给controller

- - **basicErrorController 要去的页面地址是** **ErrorViewResolver**  ；



![image-20220216160530020](D:\笔记\Java\SpringBoot02.assets\image-20220216160530020.png)





#### 3、异常处理自动配置原理

- **ErrorMvcAutoConfiguration  自动配置异常处理规则**

- - **容器中的组件：类型：<font color='red'>DefaultErrorAttributes </font>** **id：errorAttributes**

- - - **public class** **<font color='red'>DefaultErrorAttributes</font>** **implements** <font color='red'>**ErrorAttributes**, **HandlerExceptionResolver**</font>

      

    - **DefaultErrorAttributes**：定义错误页面中可以包含哪些数据。

![image-20220216114527184](D:\笔记\Java\SpringBoot02.assets\image-20220216114527184.png)



- - **容器中的组件：**类型：<font color='red'>**DefaultErrorViewResolver** </font> -> id：conventionErrorViewResolver

- - - 如果发生错误，会以HTTP的状态码 作为视图页地址（viewName），找到真正的页面
    - error/404、5xx.html
    - 容器中放组件 **BeanNameViewResolver（视图解析器）；**按照返回的视图名作为组件的id去容器中找View对象。

![image-20220216115746082](D:\笔记\Java\SpringBoot02.assets\image-20220216115746082.png)



- - **容器中的组件：类型：<font color='red'>BasicErrorController</font> --> id：basicErrorController（json+白页 适配响应）**

- - - **处理默认** **/error 路径的请求；页面响应** **new** ModelAndView(**"error"**, model)；
    - **容器中有组件 View**->**id是error**；（响应默认错误页）

- - 

- ![image-20220216120221755](D:\笔记\Java\SpringBoot02.assets\image-20220216120221755.png)



如果想要返回页面；就会找error视图【**StaticView**】。(默认是一个白页)

![image-20220216120514961](D:\笔记\Java\SpringBoot02.assets\image-20220216120514961.png)



#### 4、异常处理步骤流程

1、执行目标方法，目标方法运行期间有任何异常都会被catch、而且标志当前请求结束；并且用 **dispatchException** 

2、进入视图解析流程（页面渲染） 

processDispatchResult(processedRequest, response, mappedHandler, **mv**, **dispatchException**);

3、**mv** = **processHandlerException**；处理handler发生的异常，处理完成返回ModelAndView；

- 1、遍历所有的 **handlerExceptionResolvers，看谁能处理当前异常【HandlerExceptionResolver处理器异常解析器】**

![image-20220216123154256](D:\笔记\Java\SpringBoot02.assets\image-20220216123154256.png)



- **2、系统默认的  异常解析器；**

- - **1、DefaultErrorAttributes先来处理异常。把异常信息保存到request域，并且返回null；**
  - **2、默认没有任何人能处理异常，所以异常会被抛出**

- - - **1、如果没有任何人能处理最终底层就会发送 /error 请求。会被底层的BasicErrorController处理**
    - **2、解析错误视图；遍历所有的**  **ErrorViewResolver  看谁能解析。**

- - - **3、默认的** **DefaultErrorViewResolver ,作用是把响应状态码作为错误页的地址，error/500.html** 
    - **4、模板引擎最终响应这个页面** **error/500.html** 

![image-20220216130728799](D:\笔记\Java\SpringBoot02.assets\image-20220216130728799.png)





## 9、Web原生组件注入（Servlet、Filter、Listener）

### 1、使用Servlet API

**<font color='red'>@ServletComponentScan</font>**(basePackages = **"com.company.admin"**) :指定原生Servlet组件都放在那里

**@WebServlet(urlPatterns = "/my")：**效果：直接响应，**没有经过Spring的拦截器？**

* **<font color='red'>根据精确优先原则:</font>** DispatcherServlet处理"/"请求，MyServlet处理"/my"请求，更精确，所以由原生的servlet（Tomcat处理）,而只有由DispatcherServlet(Spring)处理的请求才会经过spring的拦截器.
* 拦截器是DispatcherServlet类中dodispatch()方法中的，这里DispatcherServlet的没走，走的是我们定义的Servlet,所以拦截器就不会生效

**@WebFilter(urlPatterns={"/css/\*","/images/\*"})**

**@WebListener**

```java
@Slf4j
@WebFilter(urlPatterns = {"/css/*","/images/*"})
public class MyFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter初始化完成");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("MyFilter工作");
        chain.doFilter(request,response);
    }
    @Override
    public void destroy() {
        log.info("MyFilter销毁");
    }
}
```



```java
@Configuration
public class MyRegistrationConfig {
    @Bean
    public ServletRegistrationBean myServlet(){
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean( myServlet,"/my","/my02");
    }
    @Bean
    public FilterRegistrationBean myFilter(){
        MyFilter myFilter = new MyFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my","/css/*"));
        return filterRegistrationBean;
    }
    @Bean
    public ServletListenerRegistrationBean myListenerRegistrationBean(){
        MyServletContextListener listener = new MyServletContextListener();
        return new ServletListenerRegistrationBean(listener);
    }
}
```



```java
@Slf4j
@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("MyServletContextListener监听到项目初始化完成");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("MyServletContextListener监听到项目销毁");
    }
}
```





扩展：DispatchServlet 如何注册进来

- 容器中自动配置了  DispatcherServlet  属性绑定到 WebMvcProperties；对应的配置文件配置项是 **spring.mvc。**
- **通过** ServletRegistrationBean<DispatcherServlet> 把 DispatcherServlet  配置进来。

- <font color='red'>**默认映射的是 / 路径。**</font >





![image-20220216203300487](D:\笔记\Java\SpringBoot02.assets\image-20220216203300487.png)



Tomcat-Servlet；

多个Servlet都能处理到同一层路径，精确优选原则

A： /my/

B： /my/1



### 2、使用RegistrationBean

`ServletRegistrationBean`, `FilterRegistrationBean`, and `ServletListenerRegistrationBean`



```java
@Configuration
public class MyRegistrationConfig {

    @Bean
    public ServletRegistrationBean myServlet(){
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean( myServlet,"/my","/my02");
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        MyFilter myFilter = new MyFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my","/css/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListenerRegistrationBean(){
        MyServletContextListener listener = new MyServletContextListener();
        return new ServletListenerRegistrationBean(listener);
    }
}
```



## 10、嵌入式Servlet容器

### 1、切换嵌入式Servlet容器

- 默认支持的webServer

- - `Tomcat`, `Jetty`, or `Undertow`
  - `ServletWebServerApplicationContext 容器启动寻找ServletWebServerFactory 并引导创建服务器`

![image-20220216205442344](D:\笔记\Java\SpringBoot02.assets\image-20220216205442344.png)

- 切换服务器

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```



- 原理

- - SpringBoot应用启动发现当前是Web应用。web场景包-导入tomcat
  - web应用会创建一个web版的ioc容器 `ServletWebServerApplicationContext` 

- - `ServletWebServerApplicationContext` 启动的时候寻找 **`ServletWebServerFactory`** `（Servlet 的web服务器工厂---> Servlet 的web服务器）` 
  - SpringBoot底层默认有很多的WebServer工厂；
    * `TomcatServletWebServerFactory`,
    * `JettyServletWebServerFactory`, 
    *  `UndertowServletWebServerFactory`

- - `底层直接会有一个自动配置类。ServletWebServerFactoryAutoConfiguration`
  - `ServletWebServerFactoryAutoConfiguration导入了ServletWebServerFactoryConfiguration（配置类）`

- - `ServletWebServerFactoryConfiguration 配置类 根据动态判断系统中到底导入了那个Web服务器的包。（默认是web-starter导入tomcat包），容器中就有 TomcatServletWebServerFactory`
  - `TomcatServletWebServerFactory 创建出Tomcat服务器并启动；TomcatWebServer 的构造器拥有初始化方法initialize---this.tomcat.start();`

- - `内嵌服务器，就是手动把启动服务器的代码调用（tomcat核心jar包存在）`



### 2、定制Servlet容器

- 实现  **WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> **

- - 把配置文件的值和**`ServletWebServerFactory `进行绑定**

- 修改配置文件 **server.xxx**
- 直接自定义 **ConfigurableServletWebServerFactory** 



**xxxxxCustomizer**：**定制化器，可以改变xxxx的默认规则**

```java
@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }
}
```

## 11、定制化原理



### 1、定制化的常见方式 

- 修改配置文件；
- **xxxxxCustomizer；**

- **编写自定义的配置类   xxxConfiguration；+** **@Bean替换、增加容器中默认组件；视图解析器** 
- <font color='red'>**Web应用 编写一个配置类实现** **WebMvcConfigurer 即可定制化web功能；+ @Bean给容器中再扩展一些组件**</font>

```java
@Configuration
public class AdminWebConfig implements WebMvcConfigurer
```



- <font color='red'>**@EnableWebMvc + WebMvcConfigurer —— @Bean**</font>  可以全面接管SpringMVC，所有规则全部自己重新配置； 实现定制和扩展功能

- - 原理
  - 1、WebMvcAutoConfiguration  默认的SpringMVC的自动配置功能类。静态资源、欢迎页.....

- - 2、一旦使用 @EnableWebMvc  就会导入 @Import(DelegatingWebMvcConfiguration.**class**)
  - 3、**DelegatingWebMvcConfiguration** 的 作用，只保证SpringMVC最基本的使用

- - - 把所有系统中的 WebMvcConfigurer 拿过来。所有功能的定制都是这些 WebMvcConfigurer  合起来一起生效
    - 自动配置了一些非常底层的组件。**RequestMappingHandlerMapping**、这些组件依赖的组件都是从容器中获取

- - - **public class** DelegatingWebMvcConfiguration **extends** **WebMvcConfigurationSupport**

- - 4、**WebMvcAutoConfiguration** 里面的配置要能生效 必须  @ConditionalOnMissingBean(**WebMvcConfigurationSupport**.**class**)
  - 5、@EnableWebMvc  导致了 **WebMvcAutoConfiguration  没有生效。**

- ... ...



### 2、原理分析套路

**场景starter** **- xxxxAutoConfiguration - 导入xxx组件 - 绑定xxxProperties --** **绑定配置文件项** 







# 数据访问

# 1、SQL

## 1、数据源的自动配置-**HikariDataSource**

### 0、前言

[]: https://www.cnblogs.com/luckyna/p/15503248.html



1、概念：<font color='red'>**JDBC是 Java 访问数据库的标准规范。**</font>是一种用于执行[SQL语句](https://so.csdn.net/so/search?q=SQL语句&spm=1001.2101.3001.7020)的Java API，可以为多种关系数据库提供统一访问，它由一组用Java语言编写的类和接口组成。是Java访问数据库的标准规范.

​    2、原理：<font color='red'>**JDBC是接口，驱动是接口的实现**</font>，没有驱动将无法完成数据库连接，从而不能操作数据库！<font color='red'>**每个数据库厂商都需 要提供自己的驱动，用来连接自己公司的数据库，也就是说驱动一般都由数据库生成厂商提供。**</font>





**![](http://mk-images.tagao.top/img/202204222024311.png?imageslim)**



<font color='red'>**MySQL厂商实现的JDBC驱动**</font>

![](http://mk-images.tagao.top/img/202204222029811.png?imageslim)



### 1、导入JDBC场景 

<font color='red'>**Java Database Connectivity** </font> Java数据库连接 (不能确定数据库,没有导入数据库驱动)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```



![image-20220217105739791](D:\笔记\Java\SpringBoot02.assets\image-20220217105739791.png)



**<font color='red'>修改版本</font>**  maven的三大原则

0. <font color='red'>依赖传递原则</font> 官方默认存在版本仲裁,无需指定版本也行
1. <font color='red'>依赖就近原则</font> 直接依赖引入具体版本（maven的就近依赖原则）
2. <font color='red'>声明优先原则</font> 重新声明版本（maven的属性的就近优先原则）

```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <!-- <version>8.0.27</version>-->
</dependency>
    
<properties>
    <mysql.version>8.0.27</mysql.version>
</properties>    
```



### 2、分析自动配置

 **<font color='red'>自动配置的类</font>**

- DataSourceAutoConfiguration ： 数据源的自动配置

- - 修改数据源相关的配置：**spring.datasource**
  - **数据库连接池的配置，是自己容器中没有DataSource才自动配置的**

- - 底层配置好的连接池是：DataSourceConfiguration 表明使用**HikariDataSource**数据库连接池



* DataSourceTransactionManagerAutoConfiguration : 事务管理器的自动配置

* JdbcTemplateAutoConfiguration : **JdbcTemplate的自动配置，可以来对数据库进行crud**

- - 可以修改这个配置项@ConfigurationProperties(prefix = **"spring.jdbc"**) 来修改JdbcTemplate
  -   JdbcTemplate；容器中有这个组件

* JndiDataSourceAutoConfiguration  jndi的自动配置

* XADataSourceAutoConfiguration 分布式事务相关的



### 3、修改配置项

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/book-manage?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 4、测试

```java
@Autowired
JdbcTemplate jdbcTemplate =  new JdbcTemplate() ;

@Test
void contextLoads() {
    Long aLong = jdbcTemplate.queryForObject("select count(*) from books", Long.class);
    log.info("记录总数：{}",aLong);
}
```



## 2、使用Druid数据源

### 0、前言

​	

- 1.<font color='red'>**传统的JDBC数据库连接使用 DriverManager**</font>来获取，每次向数据库建立连接的时候都要将Connection 加载到内存中，再验证IP地址，用户名和密码(0.05s~1s时间)。需要数据库连接的时候，就向数据库要求一个，频繁的进行数据库连接操作将占用很多的系统资源,<font color='red'>**容易造成服务器崩溃**</font>。

- 2.每一次数据库连接，使用完后都得断开,如果<font color='red'>**程序出现异常而未能关闭，将导致数据库内存泄漏**</font>，最终将导致重启数据库。

- 3.传统获取连接的方式,不能控制创建的连接数量，如连接过多，也可能导致内存泄漏，MySQL崩溃。

- 4.解决传统开发中的数据库连接问题，可以采用数据库连接池技术 (connection pool)。

​	<font color='cornflowerblue'>**对数据库连接的管理能显著影响到整个应用程序的伸缩性和健壮性,影响到程序的性能指标。**</font>数据库连接池正是针对这个问题提出来的,<font color='red'>**数据库连接池负责分配,管理和释放数据库连接,**</font>它允许应用程序重复使用一个现有的数据库连接,而不是重新建立一个。





------



1.JDBC的数据库连接池使用javax.sql.DataSource来表示，DataSource只是一个接口，该接口通常由<font color='red'>**第三方提供实现**</font>[提供.jar] （<font color='red'>**不同的第三方实现了不同的数据库连接池**</font>）

2.<font color='red'>**C3PO**</font>数据库连接池,速度相对较慢,稳定性不错(hibernate, spring)

3.DBCP数据库连接池，速度相对c3p0较快,但不稳定

4.Proxool数据库连接池，有监控连接池状态的功能，稳定性较c3p0差一点

5.BoneCP数据库连接池，速度快

6.Druid(德鲁伊)是阿里提供的数据库连接池，集DBCP、C3PO、Proxool优点于一身的数据库连接池






### 1、druid官方github地址

https://github.com/alibaba/druid



整合第三方技术的两种方式

- 自定义

- 找starter

  

### 2、自定义方式

#### 1、创建数据源

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.17</version>
</dependency>

<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
		destroy-method="close">
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<property name="maxActive" value="20" />
		<property name="initialSize" value="1" />
		<property name="maxWait" value="60000" />
		<property name="minIdle" value="1" />
		<property name="timeBetweenEvictionRunsMillis" value="60000" />
		<property name="minEvictableIdleTimeMillis" value="300000" />
		<property name="testWhileIdle" value="true" />
		<property name="testOnBorrow" value="false" />
		<property name="testOnReturn" value="false" />
		<property name="poolPreparedStatements" value="true" />
		<property name="maxOpenPreparedStatements" value="20" />
</bean>    
```

```xml
需要给数据源中配置如下属性；可以允许多个filter，多个用，分割；如：
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
  	<property name="filters" value="stat" />
  </bean>
```



**<font color='red'>1.修改默认数据源</font>**

```java
// 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
@ConfigurationProperties("spring.datasource")
@Bean
public DataSource dataSource() {
    DruidDataSource druidDataSource = new DruidDataSource();
    //加入监控功能,以及防火墙功能
 	druidDataSource.setFilters("stat,wall");
    return druidDataSource;
}
```



![image-20220217114907343](D:\笔记\Java\SpringBoot02.assets\image-20220217114907343.png)





#### 2、StatViewServlet

StatViewServlet的用途包括：

- 提供监控信息展示的html页面
- 提供监控信息的JSON API

```xml
<servlet>
    <servlet-name>DruidStatView</servlet-name>
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>DruidStatView</servlet-name>
    <url-pattern>/druid/*</url-pattern>
</servlet-mapping>
```



**<font color='red'>2 . Druid的内置监控页面</font>**

```java
/*
 * @date  配置 druid的监控页功能
 */
@Bean
public ServletRegistrationBean statViewServlet ( ){
    StatViewServlet statViewServlet = new StatViewServlet();
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(statViewServlet, "/druid/*");
    registrationBean.addInitParameter("loginUsername","root");
    registrationBean.addInitParameter("loginPassword","root");
    return registrationBean;
}
```



#### 3、StatFilter

> 用于统计监控信息；如SQL监控、URI监控



**<font color='red'>3.Druid的Web关联监控配置</font>**

```java
/**
     * WebStatFilter 用于采集web-jdbc关联监控的数据。
     */
@Bean
public FilterRegistrationBean  webStatFilter ( ){
    WebStatFilter webStatFilter = new WebStatFilter();

    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(webStatFilter);
    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
    //不监控静态资源
    filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
}
```





### 3、使用官方starter方式

#### 1、引入druid-starter

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
</dependency>
```



#### 2、分析自动配置

![image-20220217162559772](D:\笔记\Java\SpringBoot02.assets\image-20220217162559772.png)

- 扩展配置项 **spring.datasource.druid**
- DruidSpringAopConfiguration.**class**,   监控SpringBean的；配置项：**spring.datasource.druid.aop-patterns**

- DruidStatViewServletConfiguration.**class**, 监控页的配置：**spring.datasource.druid.stat-view-servlet；默认开启**
-  DruidWebStatFilterConfiguration.**class**, web监控配置；**spring.datasource.druid.web-stat-filter；默认开启**

- DruidFilterConfiguration.**class**}) 所有Druid自己filter的配置

![image-20220217161801841](D:\笔记\Java\SpringBoot02.assets\image-20220217161801841.png)



#### 3、配置示例

```yml
spring:
  datasource:
    druid:
      aop-patterns: com.company.admin.*   #监控SpringBean

      filters: stat,wal     # 底层开启功能，stat（sql监控），wall（防火墙）

      stat-view-servlet: # 配置监控页功能
        enabled: true
        login-username: root
        login-password: 123
        reset-enable: false

      web-stat-filter:  # 监控web
        enabled: true
        url-pattern: /*
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'

      filter:
        stat:       # 对上面filters里面的stat的详细配置
          enabled: true
          slow-sql-millis: 1000
          log-slow-sql: true
        wall:      # 对上面well里面的stat的详细配置
          enabled: true
          config:
            drop-table-allow: false
```

SpringBoot配置示例

https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter



配置项列表[https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8](https://github.com/alibaba/druid/wiki/DruidDataSource配置属性列表)



## 3、整合MyBatis操作

**<font color='red'>mybatis</font>** github地址 https://github.com/mybatis

**<font color='red'>mybatis</font>** 官方文档    https://mybatis.org/mybatis-3/zh/

starter

SpringBoot官方的Starter：spring-boot-starter-*

第三方的： *-spring-boot-starter

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.1</version>
</dependency>
```

![image-20220217164625156](D:\笔记\Java\SpringBoot02.assets\image-20220217164625156.png)





### 1、配置模式



- **MybatisAutoConfiguration** 全局配置文件

  * **1.**@Bean **SqlSessionFactory**: 自动配置好了

  * **2.**@Bean  **SqlSessionTemplate** 
    *  从容器中拿到<font color='red'> **sqlSessionFactory** </font>,**SqlSessionTemplate 使用了SqlSessionFactory**

  * **3.**MapperScannerRegistrarNotFoundConfiguration类
    * @Import(**AutoConfiguredMapperScannerRegistrar**.**class**）；
      * Mapper： 只要我们写的操作MyBatis的接口标注了 **@Mapper 就会被自动扫描进来**
      * ![image-20220217175905618](D:\笔记\Java\SpringBoot02.assets\image-20220217175905618.png)

```java
@EnableConfigurationProperties(MybatisProperties.class) ： MyBatis配置项绑定类。
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class })
public class MybatisAutoConfiguration{}

@ConfigurationProperties(prefix = "mybatis")
public class MybatisProperties {}
```





**<font color='red'>XML 配置文件</font>**

XML 配置文件中包含了对 MyBatis 系统的核心设置，包括获取数据库连接实例的数据源（DataSource）以及决定事务作用域和控制方式的事务管理器（TransactionManager）。后面会再探讨 XML 配置文件的详细内容，这里先给出一个简单的示例：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    
</configuration>
```



在**MybatisProperties** 类中定义了 **private Configuration configuration;** 

* yml文件中 **mybatis: configuration:  xx**下面的所有配置信息  <font color='red'> **===** </font> 修改mybatis全局配置文件中的值



#把mapper和接口放在同一目录下不需要指定 config-location

```yml
# 配置mybatis规则
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml  #全局配置文件位置
  mapper-locations: classpath:mybatis/mapper/*.xml  #sql映射文件位置
  configuration:
    map-underscore-to-camel-case: true
    
可以不写全局配置文件，所有全局配置文件的配置都放在configuration配置项中即可  
```



Mapper接口--->绑定Xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.company.admin.mapper.AccountMapper">
    <!--    public Account getAcct(Long id); -->
    <select id="getAccountById" resultType="com.company.admin.bean.Account">
        select * from  account_tbl where  id=#{id}
    </select>
</mapper>
```





- 导入mybatis官方starter
- 编写mapper接口。标准@Mapper注解
- 编写sql映射文件并绑定mapper接口
- 在application.yaml中指定Mapper配置文件的位置，以及指定全局配置文件的信息 （建议；**配置在mybatis.configuration**）







### 2、注解模式

```java
@Mapper
public interface CityMapper {
    @Select("select * from city where id = #{id}")
    City getCityById(Long id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into  city (`name`,`state`,`country`) values(#{name},#{state},#{country})")
    void saveCity(City city);
}
```





### 3、混合模式

```java
@Options(useGeneratedKeys = true,keyProperty = "id")
@Insert("insert into  city (`name`,`state`,`country`) values(#{name},#{state},#{country})")
void saveCity(City city);
```



```xml
<insert id="saveCity" useGeneratedKeys="true" keyProperty="id" >
    insert into  city (`name`,`state`,`country`) values(#{name},#{state},#{country})
</insert>
```





**最佳实战：**

- 1.引入mybatis-starter
- 2.**配置application.yaml中，指定mapper-location位置即可 (mapper 与接口处于同一目录下是可以忽略)**

- 3.编写Mapper接口并标注@Mapper注解
  
* @MapperScan("com.atguigu.admin.mapper") 简化，其他的接口就可以不用标注@Mapper注解
  
- 4.复杂方法编写mapper.xml进行绑定映射

  * 简单方法直接注解方式

  

查询出中文乱码的在@RequestMapping(value = "city",produces = "application/json;charset=utf-8")  进行转码





## 4、整合 MyBatis-Plus 完成CRUD

### 1、什么是MyBatis-Plus

[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis](http://www.mybatis.org/mybatis-3/) 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

**<font color='red'>mybatis plus 官网</font>** https://baomidou.com/

建议安装 **MybatisX** 插件 



### 2、整合MyBatis-Plus

导入这个就不用导入 JDBC 以及 Mybatis

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.1</version>
</dependency>
```



自动配置

- MybatisPlusAutoConfiguration 配置类:
  * MybatisPlusProperties 配置项绑定。**mybatis-plus：xxx 就是对mybatis-plus的定制**
  * **SqlSessionFactory 自动配置好。底层是容器中默认的数据源**
  * <font color='red'>**MybatisPlusProperties**</font> 中以及**mapperLocations 自动配置好的。有默认值。`classpath*:/mapper/ * */ * .xml"`  任意包的类路径下的所有mapper文件夹下任意路径下的所有xml都是sql映射文件。  建议以后sql映射文件，放在 mapper下**
  * **容器中也自动配置好了** **SqlSessionTemplate**
  * **@Mapper 标注的接口也会被自动扫描；建议直接** @MapperScan(**"com.company.admin.mapper"**) 批量扫描就行



**优点：**

-  只需要我们的Mapper继承 **BaseMapper** 就可以拥有crud能力



### 3、CRUD功能

​	![image-20220218164236526](D:\笔记\Java\SpringBoot02.assets\image-20220218164236526.png)



分页功能, 组件注入

```java
@Configuration
public class MyBatisConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        //分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInnerInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(100L);

        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        plusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return plusInterceptor;
    }
}
```



Controller 接口的编写

```java
@GetMapping("/dynamic_table" )
public String dynamic_table (@RequestParam(value = "pn", defaultValue = "1") Integer pn , Model model){
    //构造分页参数
    Page<User> page = new Page<>(pn,2);
    /**
         * @date  调用page进行分页
         * @param model    查询条件
         */
    Page<User> userPage = userService.page(page, null);
    model.addAttribute("userPage",userPage);
    System.out.println(userPage.getPages());
    System.out.println(userPage.getTotal());
    return "tables/dynamic_table";
}

@GetMapping("/user/delete/{id}")
public String deleteUser(@PathVariable("id") Long id,
                         @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                         RedirectAttributes ra){

    userService.removeById(id);

    ra.addAttribute("pn",pn);
    return "redirect:/dynamic_table";
}
```





**<font color='red'>前端</font>**

![image-20220218165203461](D:\笔记\Java\SpringBoot02.assets\image-20220218165203461.png)

**<font color='red'>分页内容</font>**

![image-20220218165344494](D:\笔记\Java\SpringBoot02.assets\image-20220218165344494.png)



# 2、NoSQL

Redis 是一个开源（BSD许可）的，**<font color='red'>内存</font>**中的数据结构存储系统，它可以用作数据库、**缓存**和消息中间件。 它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。





## 1、Redis自动配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```



![image-20220218165746382](D:\笔记\Java\SpringBoot02.assets\image-20220218165746382.png)



**<font color='red'>自动配置：</font>**

- RedisAutoConfiguration 自动配置类。

  * RedisProperties 属性类 --> **spring.redis.xxx是对redis的配置**

    * spring.redis.url  配置可以整合<font color='red'> 用户名 密码 端口号</font>

    * Example: redis://user:password@example.com:6379 

  * 连接工厂推荐:**Lettuce**ConnectionConfiguration(默认)、**Jedis**ConnectionConfiguration

  * **自动注入了RedisTemplate**<**Object**, **Object**> ： **k：v都是Object** 

  * **自动注入了StringRedisTemplate； k：v都是String**

    

- **底层只要我们使用** **StringRedisTemplate、RedisTemplate就可以操作redis**



```yml
spring:
  redis:
    password: 123
    port: 6379
    client-type: lettuce
    host: 192.168.17.130
#    url: redis://default:123@192.168.17.130:6379
```





## 2、RedisTemplate与Lettuce



### 1.StringRedisTemplate操做redis数据

**StringRedisTemplate与RedisTemplate区别点**

- 二者的关系是StringRedisTemplate<font color='red'>**继承**</font>RedisTemplate,  两者方法基本一致，。 

  不同之处主要体现在操作的数据类型不同，**RedisTemplate中的两个泛型都是Object，意味着存储的key和value都可以是一个对象，而StringRedisTemplate的两个泛型都是String，意味着StringRedisTemplate的key和value都只能是字符串。**

  

- 二者的数据是**不共通**的；

  * StringRedisTemplate只能管理StringRedisTemplate里面的数据
  * RedisTemplate只能管理RedisTemplate中的数据。

  

- **<font color='red'>区别主要在于他们使用的序列化类:</font>**

　　　　RedisTemplate使用的是JdkSerializationRedisSerializer  存入数据会将数据先序列化成**字节数组**而后在存入Redis数据库。 

　　 　 StringRedisTemplate使用的是<font color='red'>**StringRedisSerializer**</font>进行序列化

- 使用时注意事项：

　　　当Redis数据库里面原本存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate便可。

　　　可是若是你的数据是复杂的对象类型，而取出的时候又不想作任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择。

- RedisTemplate使用时常见问题：

　　　　RedisTemplate 中存取数据都是<font color='red'>字节数组</font>。当redis中存入的数据是可读形式而非字节数组时，使用redisTemplate取值的时候会没法获取导出数据，得到的值为null。能够使用 StringRedisTemplate 试试。



### 2.RedisTemplate中定义了5种数据结构操做

```java
redisTemplate.opsForValue();　　//操做字符串
redisTemplate.opsForHash();　　 //操做hash
redisTemplate.opsForList();　　 //操做list
redisTemplate.opsForSet();　　   //操做set
redisTemplate.opsForZSet();　 　//操做有序set
```



### 3.StringRedisTemplate经常使用操做

```java
//向redis里存入数据和设置缓存时间  
stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);

//val作-1操做
stringRedisTemplate.boundValueOps("test").increment(-1);  

//根据key获取缓存中的val
stringRedisTemplate.opsForValue().get("test");

//val +1  
stringRedisTemplate.boundValueOps("test").increment(1);

//根据key获取过时时间  
stringRedisTemplate.getExpire("test");

//根据key获取过时时间并换算成指定单位
stringRedisTemplate.getExpire("test",TimeUnit.SECONDS);

//根据key删除缓存      
stringRedisTemplate.delete("test");

//检查key是否存在，返回boolean值  
stringRedisTemplate.hasKey("546545");

//向指定key中存放set集合 
stringRedisTemplate.opsForSet().add("red_123", "1","2","3"); 

//设置过时时间  
stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);

//根据key查看集合中是否存在指定数据 
stringRedisTemplate.opsForSet().isMember("red_123", "1");
    
//根据key获取set集合    
stringRedisTemplate.opsForSet().members("red_123");
 
```



### 4 .StringRedisTemplate的使用

 springboot中使用注解@Autowired 便可缓存

```java
@Autowired
public StringRedisTemplate stringRedisTemplate;

@RequestMapping ( "/num" )
public String countNum() {
    //放入数据
    String userNum = stringRedisTemplate.opsForValue().get( "userNum" );
	//取出数据
    stringRedisTemplate.opsForValue().set( "userNum","addValue");
	return  userNum;
}

```





## 3、切换至jedis

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!--导入jedis-->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

