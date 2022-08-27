# day01

## Dos的基本操作

```dos
//进入Dos：win+R+cmd
切换盘符：                          删除目录：
     D：                                rd 目录名
进入目录：                          删除文件：
     cd 目录名                          del 目录名
回退到上一级目录：                   查看ip：
     cd..                               ipconfig
回到上一级根目录：                   查看系统信息：
     cd/                                systeminfo
查看当前目录所有的文件夹：            清除屏幕： 
     dir                                cls
创建文件夹：                      
     md 目录名                        
查看网络是否连通:	
	ping 127.0.0.1
```


##人机交互

​	`命令行界面`         	`图形化界面`





## JVM/JDK/JRE的区别和关联

	1.作用
		Jvm:保证了跨平台性
	    Jre:保证运行环境
	    Jdk:保证开发环境
	
	2.关系
		JDK  =  JRE  + 开发工具集 （例如Javac编译工具等）
		JRE   =  JVM + JavaSE标准类库
	       	       bin      lib

 


JDK目录分析:
	 bin:提供了JDK的开发工具和一些需要执行的文件

​	 lib:就是为我们的jar包提供归档

​	 include:保留了C语言的头部文件，主要用于链接java程序和调试程序使用

​	jre：主要负责java程序运行的一些工作

	JDK/bin/javac.exe
		该文件负责编译java文件
	JDK/jre/java.exe
		该文件负责执行.class文件

11.JAVA环境变量的配置:
		我的电脑-->右键-->属性-->高级系统设置-->环境变量-->系统变量
		1.新建JAVA_HOME
			JAVA_HOME = C:\Program Files\Java\jdk1.7.0_80
			
		2.添加path路径
			%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
			
		保证javac命令可以到任意目录运行




JDK的配置:
	第一步:JAVA_HOME
	值:C:\Program Files\Java\jdk1.7.0_80      jdk的根目录
	
	我们需要的文件的具体路径1:C:\Program Files\Java\jdk1.7.0_80 jdk\bin\javac.exe -- 将JAVA程序编译成字节码文件(.class)
	我们需要的文件的具体路径2:C:\Program Files\Java\jdk1.7.0_80 jdk\jre\bin\java.exe -- 负责运行我们的.class文件
	
	1.新建JAVA_HOME
		JAVA_HOME = C:\Program Files\Java\jdk1.7.0_80
				
	2.添加path路径
		%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
	PATH:
		%JAVA_HOME%\bin
		%JAVA_HOME%\jre\bin



##JAVA程序的开发流程:

​	1.进行JAVA程序的编码
​	2.通过我们的javac.exe，将JAVA的源文件编译成.class文件(字节码文件)
​		格式:javac HelloWorld.java(必须要写上后缀)
​	3.通过java.exe来运行我们的.class文件
​		格式:java HelloWorld(千万不要写上后缀)






​        

​	