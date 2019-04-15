package cn.jinchengcom.controller;

import cn.jinchengcom.domain.User;
import cn.jinchengcom.mapper.UserMapper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LogMsgController {

    private static final Logger logger = LoggerFactory.getLogger(LogMsgController.class);

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/logMsg")
    @ResponseBody
    public String logMsg(){
        List<User> users = userMapper.queryUserList();
        logger.info(JSON.toJSONString(users));
        int a=1;
        String b="Spring Boot 学习";
        logger.info("占位符{}练习:{}",a,b);

        List<User> lisi = userMapper.queryUserMsg("lisi");
        for (User user : lisi) {
            System.out.println(user);
        }
        return "SUCESS";
    }
}
