# springboot
SpringBoot整合（SSM+Junit+Redis）
　　本文主要详细介绍一下SpringBoot整合，使用SpringBoot进行开发更加迅速，利用SpringBoot对SSM+Junit+Redis进行整合。整合代码已经上传github,并且会持续更新其它整合<a href="https://github.com/Java-XuJincheng/springboot" style="color:#0072E3;text-decoration:none;border-bottom:1px solid #0072E3
" target="_blank">点击此处下载代码</a>  
　　开发环境：  
　　IntelliJ IDEA 2018.3  
　　JDK1.9  
　　MySql5.0.22  
　　SpringBoot2.0.1
# 1.使用idea快速创建SpringBoot项目(Spring+SpringMVC)
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/8c2fa787cb741e220782f26762430cd.png "快速创建SpringBoot")  
  
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/7388a8f07091caccff5796a5059cebd.png "快速创建SpringBoot")  
  
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/da1e256ee08fd09b6c3cb6ab2fc7328.png "快速创建SpringBoot")  
  
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/5b66be722b8d2aa4ae38f0047e0d04c.png "快速创建SpringBoot")  
  
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/6836b6ac01404f21dcee7259c08b754.png "快速创建SpringBoot")  
  
项目的目录结构为  
![快速创建SpringBoot](http://image.jinchengcom.cn/blog/eae7d4431342ac8909206d73bc1388d.png "快速创建SpringBoot")  
  
将spring-boot-starter-parent版本改为2.0.1.RELEASE
```java
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.0.1.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>
```
# 2.SpringBoot整合Mybatis
## 2.1添加Mybatis的起步依赖
```java
<!--mybatis起步依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```
## 2.2添加数据库驱动坐标
```java
<!-- MySQL连接驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```
## 2.3添加数据库连接信息
　　在application.properties中添加数据库的连接信息
```java
#DB Configuration:
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springboot?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
```
## 2.4创建user表
　　在test数据库中创建user表
```java
-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhangsan', '123', '张三');
INSERT INTO `user` VALUES ('2', 'lisi', '123', '李四');
```
## 2.5创建实体Bean
```java
public class User {
    // 主键
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 姓名
    private String name;

    public User() {
    }

    public User(Long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
```
## 2.6编写Mapper
```java
@Mapper
public interface UserMapper {
    public List<User> queryUserList();

}
```
　　注意：@Mapper标记该类是一个mybatis的mapper接口，可以被spring boot自动扫描到spring上下文中
## 2.7配置Mapper映射文件
　　在src\main\resources\mapper路径下加入UserMapper.xml配置文件
```java
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.itheima.mapper.UserMapper">
    <select id="queryUserList" resultType="user">
        select * from user
    </select>
</mapper>
```
## 2.8在application.properties中添加mybatis的信息
```java
#spring集成Mybatis环境
#pojo别名扫描包
mybatis.type-aliases-package=com.itheima.domain
#加载Mybatis映射文件
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```
测试在Junit集成之后进行
# 3.SpringBoot整合Junit
## 3.1添加Mybatis的起步依赖
```java
<!--测试的起步依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
## 3.2编写Mybatis的测试类
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }
}
```
其中，SpringRunner继承自SpringJUnit4ClassRunner，使用哪一个Spring提供的测试测试引擎都可以
```java
public final class SpringRunner extends SpringJUnit4ClassRunner
```
@SpringBootTest的属性指定的是引导类的字节码对象
# 4.SpringBoot整合Redis
## 4.1添加redis的起步依赖
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
## 4.2配置redis的连接信息
```java
#Redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
```
## 4.3注入RedisTemplate测试redis操作
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class RedisTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() throws JsonProcessingException {
        //从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("user.findAll").get();
        //如果redis中没有数据的话
        if (null == userListData) {
            //查询数据库获得数据
            List<User> all = userMapper.queryUserList();
            //转换成json格式字符串
            ObjectMapper om = new ObjectMapper();
            userListData = om.writeValueAsString(all);
            //将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
            redisTemplate.boundValueOps("user.findAll").set(userListData);
            System.out.println("===============从数据库获得数据===============");
        } else {
            System.out.println("===============从redis缓存中获得数据===============");
        }
        System.out.println(userListData);
    }
}
```
# 5.SpringBoot开发使用thymeleaf模板开发html页面
## 5.1添加thymeleaf模板开发html页面的起步依赖
```java
<!-- thymeleaf模板开发html页面 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
## 5.2在application.yml配置thymeleaf
```java
spring:
  thymeleaf:
    mode: HTML5
```
## 5.3编写Controller
controller层，我写了三个方法，首页，跳转，和视图的三种
```java
@Controller
public class MapperController {

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/welcome")
    public String page() {
        return "system/index";
    }

    /**
     * 跳转
     *
     * @return
     */
    @RequestMapping("/redirect")
    public String page2() {
        return "redirect/redirect";
    }

    /**
     * 视图
     *
     * @param model
     * @return
     */
    @RequestMapping("/model")
    public String page3(Model model) {
        model.addAttribute("name", "xiaoming");
        return "hello";
    }

}
```
## 5.4编写页面
页面结构如下图所示  
![页面](http://image.jinchengcom.cn/blog/8c2fa787cb741e220782f26762430cd.png "页面")  
  
redirect.html页面代码
```java
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>redirectPage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>congratulation redirect success !</h1>
</body>
</html>
```
  
index.html页面代码
```java
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>thymeleaf</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Welcome page of thymeleaf.</h1>
<a href="/springboot/redirect">redirect</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/springboot/model">model</a>
</body>
</html>
```
  
hello.html页面代码
```java
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>model</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p>welcome : [[${name}]].</p>
<h1>hi , [[${name}]] !</h1>
</body>
</html>
```




