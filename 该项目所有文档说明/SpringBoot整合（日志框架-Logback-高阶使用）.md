
# SpringBoot整合（日志框架-Logback-高阶使用）
　　本文主要详细介绍一下Logback日志框架的高阶使用，在阅读完<a href="https://www.jinchengcom.cn/posts/5d387491.html" style="color:#0072E3;text-decoration:none;border-bottom:1px solid #0072E3
" target="_blank">SpringBoot整合（日志框架-Logback）</a>之后发现在使用的时候如果level设置为debug时会产生特别的多的日志，无法从中寻找查看jdbc等关键日志，此文解决一下这个问题。整合代码已经上传github,并且会持续更新其它整合<a href="https://github.com/Java-XuJincheng/springboot" style="color:#0072E3;text-decoration:none;border-bottom:1px solid #0072E3
" target="_blank">点击此处下载代码</a>  
　　<a href="http://logback.qos.ch" style="color:#0072E3;text-decoration:none;border-bottom:1px solid #0072E3
" target="_blank">logback日志框架官网</a>
<!-- more -->
# 1.Logback的用法
应用中不可直接使用日志系统（Log4j、Logback）中的 API，而应依赖使用日志框架SLF4J 中的 API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一。
```java
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
private static final Logger logger = LoggerFactory.getLogger(Abc.class);
```
  
一般建议日志对象logger声明为private static final。声明为private可防止logger对象被其他类非法使用。声明为static可防止重复new出logger对象，造成资源浪费；还可以防止logger被序列化，造成安全风险。声明为final是因为在类的生命周期内无需变更logger。
```java
private static final Logger logger = LoggerFactory.getLogger(LogMsgController.class);
```
  
建议使用”{}”占位符风格，而不是”+”拼接符。
```java
logger.error("Country: {}, Province: {}, City: {}", ctry, prov, city); //占位符
```
  
没有输出全部错误信息 看以下代码，这样不会记录详细的堆栈异常信息，只会记录错误基本描述信息，不利于排查问题。
```java
try {
    // ...
} catch (Exception e) {
    // 错误
    LOG.error('XX 发生异常', e.getMessage());
    // 正确
    LOG.error('XX 发生异常', e);
}  
```
# 2.Logback常用日志打印
开启mybatis的sql语句日志打印:
```java
<!-- 用于打印 Spring 在启动的时候初始化各个 Bean 的信息 -->
<logger name="org.springframework.web" level="DEBUG"/>
<!-- mybatis 日志打印如果在 ssm 中，可能就需要下边的7行了。-->
<!--<logger name="com.ibatis" level="DEBUG" />-->
<!--<logger name="com.ibatis.common.jdbc.SimpleDataSource" level="DEBUG" />-->
<!--<logger name="com.ibatis.common.jdbc.ScriptRunner" level="DEBUG" />-->
<!--<logger name="com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate" level="DEBUG" />-->
<!--<logger name="java.sql.Connection" level="DEBUG" />-->
<!--<logger name="java.sql.Statement" level="DEBUG" />-->
<!--<logger name="java.sql.PreparedStatement" level="DEBUG" />-->

<!-- 以下这一句至关重要如果没有，就无法输出 sql 语句 -->
<!--注意：在 spring boot 中，想在控制台打印 mybatis 的 sql 语句，只需要配置下边这一句就好了。-->
<!--如果想要记录更详细的 SQL 日志，只需要把下面的日志级别改成 TRACE 就可以了-->
<!--即将 mapper 接口打入 logger 就行。-->
<logger name="cn.jinchengcom.mapper" level="DEBUG">
</logger>
```
  
开启其它常用日志打印(未亲测):
```java
<!-- rest template logger-->
<!--<logger name="org.springframework.web.client.RestTemplate" level="DEBUG" />-->
<!--<logger name="org.springframework" level="DEBUG" />-->

<!-- jdbc-->
<!--<logger name="jdbc.sqltiming" level="DEBUG" />-->
<logger name="org.mybatis" level="DEBUG" />

<!-- zookeeper-->
<logger name="org.apache.zookeeper"    level="ERROR"  />

<!-- dubbo -->
<logger name="com.alibaba.dubbo.monitor" level="ERROR"/>
<logger name="com.alibaba.dubbo.remoting" level="ERROR" />
```