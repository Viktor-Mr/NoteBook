# Junit



**JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage**

JUnit Platform 是在JVM上启动测试框架的基础。

JUnit Jupiter则是JUnit 5提供的一个测试引擎，运行在JUnit Platform 上。

JUnit Vintage 的作用是为JUnit4.x，Junit3.x提供兼容性。







```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.rushuni</groupId>
    <artifactId>junit5-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>


    <build>
        <plugins>
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
                <configuration>
                    <excludes>
                        <exclude/>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
    

        
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-launcher</artifactId>
            <version>1.7.2</version>
            <scope>test</scope>
        </dependency>
      
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.7.2</version>
            <scope>test</scope>
        </dependency>
     <dependency> 
         <groupId>org.junit.vintage</groupId> 
         <artifactId>junit-vintage-engine</artifactId>
         <version>5.7.2</version> 
         <scope>test</scope> 
      </dependency>

    </dependencies>

</project>
```