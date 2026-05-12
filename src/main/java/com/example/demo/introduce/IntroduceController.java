package com.example.demo.introduce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class IntroduceController {

    @GetMapping("/introduce")
    public String getNameIntroduce(@RequestParam(required = false) String name) {
        if (name == null){
            return "안녕하세요. 저는 원지웅입니다.";
        }
        return name;
    }

}

