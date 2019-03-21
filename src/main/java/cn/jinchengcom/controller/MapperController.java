package cn.jinchengcom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
