package com.example.demo.introduce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroduceController {

    @GetMapping("/introduce")
    public String introduce(){
        return "introduce";
    }

}
