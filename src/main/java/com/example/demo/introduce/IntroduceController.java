package com.example.demo.introduce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IntroduceController {

    

    @GetMapping("/introduce")
    @ResponseBody
    public String getNameIntroduce(@RequestParam(required = false) String name) {
        return name;
    }

}

