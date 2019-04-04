
# SpringBoot整合（日志框架-Logback）
　　本文主要详细介绍一下在SpringBoot项目集成日志功能，这个功能是项目初期必须集成好的功能，日志对于一个项目的排错及日常维护非常重要，本文使用的是日志框架-Logback。整合代码已经上传github,并且会持续更新其它整合<a href="https://github.com/Java-XuJincheng/springboot" style="color:#0072E3;text-decoration:none;border-bottom:1px solid #0072E3
" target="_blank">点击此处下载代码</a>
<!-- more -->
# 1.日志框架介绍
　　Logback是由log4j创始人设计的又一个开源日志组件。logback当前分成三个模块：logback-core,logback- classic和logback-access。logback-core是其它两个模块的基础模块。logback-classic是log4j的一个 改良版本。此外logback-classic完整实现SLF4J API使你可以很方便地更换成其它日志系统如log4j或JDK14 Logging。logback-access访问模块与Servlet容器集成提供通过Http来访问日志的功能。logback在功能与性能上完全可以取代log4j。
# 2.添加日志框架依赖
```java
<!--添加Logback日志框架-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```
　　但是呢，实际开发中我们不需要直接添加该依赖。  
　　你会发现spring-boot-starter其中包含了spring-boot-starter-logging，该依赖内容就是 Spring Boot 默认的日志框架 logback。工程中有用到了Thymeleaf，而Thymeleaf依赖包含了spring-boot-starter，最终我只要引入Thymeleaf即可。
```java
<!-- thymeleaf模板开发html页面 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
# 3.日志级别
　　日志级别从低到高分别为ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF  
　　常用日志级别从低到高分别为DEBUG < INFO < WARN < ERROR  
　　如果设置为WARN，则低于WARN的信息都不会输出，也就是说只会打印WARN和ERROR的日志  
　　ALL Level是最低等级的，用于打开所有日志记录。  
　　DEBUG Level指出细粒度信息事件对调试应用程序是非常有帮助的。  
　　INFO level表明 消息在粗粒度级别上突出强调应用程序的运行过程。  
　　WARN level表明会出现潜在错误的情形。  
　　ERROR level指出虽然发生错误事件，但仍然不影响系统的继续运行。  
　　FATAL level指出每个严重的错误事件将会导致应用程序的退出。  
　　OFF Level是最高等级的，用于关闭所有日志记录。
# 4.日志配置文件命名
　　根据不同的日志系统，你可以按如下规则组织配置文件名，就能被正确加载  
　　Logback:logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy  
　　Log4j:log4j-spring.properties, log4j-spring.xml, log4j.properties, log4j.xml  
　　Log4j2:log4j2-spring.xml, log4j2.xml  
　　JDK(Java Util Logging):logging.properties  
　　说明:  
　　Spring Boot官方推荐优先使用带有-spring的文件名作为你的日志配置（如使用logback-spring.xml，而不是logback.xml），命名为logback-spring.xml的日志配置文件，spring boot可以为它添加一些spring boot特有的配置项（下面会提到）。默认的命名规则，并且放在 src/main/resources 下面即可  
　　多环境日志配置文件命名:  
　　如果你即想完全掌控日志配置，但又不想用logback.xml作为Logback配置的名字，application.yml可以通过logging.config属性指定自定义的名字：
```java
logging.config=classpath:logging-config.xml
```
　　虽然一般并不需要改变配置文件的名字，但是如果你想针对不同运行时Profile使用不同的日志配置，这个功能会很有用。一般不需要这个属性，而是直接在logback-spring.xml中使用springProfile配置，不需要logging.config指定不同环境使用不同配置文件。springProfile配置在下面介绍。
# 5.logback-spring.xml配置文件详解
## 5.1根节点的属性configuration
　　scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。  
　　scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。  
　　debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
## 5.2子节点一<root>
　　root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性。  
　　level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，不能设置为INHERITED或者同义词NULL。默认是DEBUG。  
　　可以包含零个或多个元素，标识这个appender将会添加到这个logger。
```java
<root level="info">
	<appender-ref ref="STDOUT" />
	<appender-ref ref="file" />
</root>
```
## 5.3子节点二<contextName> 设置上下文名称
　　每个logger都关联到logger上下文，默认上下文名称为“default”。但可以使用设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改,可以通过%contextName来打印日志上下文名称，一般来说我们不用这个属性，可有可无。
```java
<contextName>springboot_api</contextName>
```
## 5.4子节点三<property> 设置变量
　　resource:引入其它配置文件
```java
<property resource="application.yml" />
```
　　name:变量的名称  
　　value:变量定义的值  
　　可以使“${变量的名称}”来获取变量的值
```java
<property name="logback.appname" value="api"/>
```
　　如果要读取application.yml文件中的参数应该按如下配置
```java
<springProperty scope="context" name="logback.logdir" source="logback.logdir"/>

<!--application.yml中的配置-->
logback:
  logdir: /var/springboot/pro/log
```
## 5.5子节点四<appender>
　　appender用来格式化日志输出节点，有俩个属性name和class，class用来指定哪种输出策略，常用就是控制台输出策略和文件输出策略。  
  
控制台输出-ConsoleAppender:  
　　如同它的名字一样，这个Appender将日志输出到console，更准确的说是System.out 或者System.err。
```java
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
	<layout class="ch.qos.logback.classic.PatternLayout">
		<Pattern>
			%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{80} %line - %msg%n
		</Pattern>
	</layout>
</appender>
```
  
文件输出-FileAppender  
　　将日志输出到文件当中，目标文件取决于file属性。是否追加输出，取决于append属性。
```java
<appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
	<!--如果只是想要 Info 级别的日志，只是过滤 info 还是会输出 Error 日志，因为 Error 的级别高，
    	所以我们使用下面的策略，可以避免输出 Error 的日志-->
	<filter class="ch.qos.logback.classic.filter.LevelFilter">
		<!--过滤 Error-->
		<level>ERROR</level>
		<!--匹配到就禁止-->
		<onMatch>DENY</onMatch>
		<!--没有匹配到就允许-->
		<onMismatch>ACCEPT</onMismatch>
	</filter>
	<!--日志名称，如果没有File 属性，那么只会使用FileNamePattern的文件路径规则 如果同时有<File>和<FileNamePattern>
		那么当天日志是<File>，明天会自动把今天的日志改名为今天的日期。即，<File> 的日志都是当天的。 -->
	<File>${logback.logdir}/${logback.appname}.log</File>
	<!--滚动策略，按照时间滚动 TimeBasedRollingPolicy -->
	<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
	<!--文件路径,定义了日志的切分方式——把每一天的日志归档到一个文件中,以防止日志填满整个磁盘空间 -->
	<FileNamePattern>
		${logback.logdir}/${logback.appname}.%d{yyyy-MM-dd}.log
	</FileNamePattern>
	<!--只保留最近30天的日志 -->
	<maxHistory>30</maxHistory>
	<!--用来指定日志文件的上限大小，那么到了这个值，就会删除旧的日志 -->
	<!--<totalSizeCap>1GB</totalSizeCap> -->
	</rollingPolicy>
	<!--日志输出编码格式化 -->
	<encoder>
		<charset>UTF-8</charset>
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss} %contextName [%thread] %-5level %logger{80} %line - %msg%n
            </pattern>
	</encoder>
</appender>
```  
  
　　可以看到layout和encoder，都可以将事件转换为格式化后的日志记录，但是控制台输出使用layout，文件输出使用encoder  
　　日志输出格式含义:  
　　%d{yyyy-MM-dd HH:mm:ss.SSS}——日志输出时间(SSS:是自第二个以线程形式开始以来的完整毫秒数)  
　　%contextName——输出项目名称  
　　%thread——输出日志的进程名字，这在Web应用以及异步任务处理中很有用  
　　%-5level——输出日志级别，并且使用5个字符靠左对齐  
　　%logger{36}——日志输出者的名字(包名路径如:cn.jinchengcom.controller.LogController)  
　　%line——输出打印的日志内容的代码在哪行  
　　%msg——输出日志消息内容  
　　%n——换行符 
## 5.6子节点五<logger>
　　<logger>用来设置某一个包或者具体的某一个类的日志打印级别、以及指定<appender>。<logger>仅有一个name属性，一个可选的level和一个可选的addtivity属性。  
　　name:用来指定受此logger约束的某一个包或者具体的某一个类。  
　　level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。如果未设置此属性，那么当前loger将会继承上级的级别。  
　　addtivity:是否向上级logger传递打印信息。默认是true。  
　　通常用法示例:
```java
<!--指定cn.jinchengcom.controller包下的接口打印日志级别为warn，不添加<appender>子节点否则会打印两遍信息，
	该logger向上传递打印日志信息，如果上一级（root）打印日志级别不包含该接口的日志级别，需添加<appender>子节点 -->
<logger name="cn.jinchengcom.controller" level="warn" additivity="true">
</logger>
```
　　范围有重叠的话，范围小的，有效。
## 5.7多环境配置
　　多环境logback-spring.xml配置文件部分代码
```java
<!-- 也可以将springProfile放到logger节点上 -->
<springProfile name="test,dev">
	<root level="debug">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="file" />
	</root>
</springProfile>

<springProfile name="prod">
	<root level="info">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="file" />
	</root>
</springProfile>

<logger name="cn.jinchengcom.controller.LogController" level="warn" additivity="true">
</logger>
```
多环境application.yml配置文件部分代码
```java
spring:
  profiles:
    active: prod
```
## 6.Logback在SpringBoot项目中的完整配置
application.yml配置文件
```java
logback:
  logdir: /var/springboot/pro/log
```
logback-spring.xml配置文件
```java
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <contextName>springboot_api</contextName>
    <property resource="application.yml" />
    <!--可变 -->
    <springProperty scope="context" name="logback.logdir" source="logback.logdir"/>
    <!--不可变 -->
    <property name="logback.appname" value="api"/>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{80} %line - %msg%n
            </Pattern>
        </layout>
    </appender>

    <appender name="file"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--如果只是想要 Info 级别的日志，只是过滤 info 还是会输出 Error 日志，因为 Error 的级别高，
    所以我们使用下面的策略，可以避免输出 Error 的日志-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!--过滤 Error-->
            <level>ERROR</level>
            <!--匹配到就禁止-->
            <onMatch>DENY</onMatch>
            <!--没有匹配到就允许-->
            <onMismatch>ACCEPT</onMismatch>
        </filter>
        <!--日志名称，如果没有File 属性，那么只会使用FileNamePattern的文件路径规则 如果同时有<File>和<FileNamePattern>，那么当天日志是<File>，明天会自动把今天
            的日志改名为今天的日期。即，<File> 的日志都是当天的。 -->
        <File>${logback.logdir}/${logback.appname}.log</File>
        <!--滚动策略，按照时间滚动 TimeBasedRollingPolicy -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--文件路径,定义了日志的切分方式——把每一天的日志归档到一个文件中,以防止日志填满整个磁盘空间 -->
            <FileNamePattern>
                ${logback.logdir}/${logback.appname}.%d{yyyy-MM-dd}.log
            </FileNamePattern>
            <!--只保留最近30天的日志 -->
            <maxHistory>30</maxHistory>
            <!--用来指定日志文件的上限大小，那么到了这个值，就会删除旧的日志 -->
            <!--<totalSizeCap>1GB</totalSizeCap> -->
        </rollingPolicy>
        <!--日志输出编码格式化 -->
        <encoder>
            <charset>UTF-8</charset>
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{80} %line - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- 也可以将springProfile放到logger节点上 -->
    <springProfile name="test,dev">
        <root level="debug">
            <appender-ref ref="STDOUT" />
            <appender-ref ref="file" />
        </root>
    </springProfile>

    <springProfile name="prod">
        <root level="info">
            <appender-ref ref="STDOUT" />
            <appender-ref ref="file" />
        </root>
    </springProfile>

    <logger name="cn.jinchengcom.controller.LogController" level="warn" additivity="true">
    </logger>
</configuration>
```






