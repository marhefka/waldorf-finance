package hu.waldorf.finance.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/import")
    public String importPage(){
        return "import";
    }
}
