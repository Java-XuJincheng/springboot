package cn.jinchengcom.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
@ConfigurationProperties(prefix = "person")
public class QuickStartController {

    /*@Value("${person.name}")
    private String name;

    @Value("${person.age}")
    private Integer age;*/

    private String name;
    private Integer age;
    private String aaa;


    @RequestMapping("/quick")
    @ResponseBody
    public String getMesssage(){
        return "springboot 访问成功! name="+name+",age="+age+aaa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getAaa() {
        return aaa;
    }
}
