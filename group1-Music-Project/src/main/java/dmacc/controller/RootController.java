package dmacc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RootController {


//    @GetMapping("/")
//    public String viewIndex() {
//        return "index";
//    }

    @GetMapping("/")
    public String viewHome() {
        return "home";
    }


}
