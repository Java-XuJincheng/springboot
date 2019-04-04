package cn.jinchengcom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Log1Controller {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/log1")
    @ResponseBody
    public String login(){
        //日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。
        logger.trace("log1日志输出 trace");
        logger.debug("log1日志输出 debug");
        logger.info("log1日志输出 info");
        logger.warn("log1日志输出 warn");
        logger.error("log1日志输出 error");

        return "SUCESS";
    }

}
