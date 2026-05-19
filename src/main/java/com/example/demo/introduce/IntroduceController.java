package com.example.demo.introduce;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IntroduceController {

    @GetMapping("/introduce")
    public String introduce() {
        return "introduce";
    }

    @ResponseBody
    @GetMapping(value = "/introduce", params = "name")
    public String getNameIntroduce(@RequestParam String name) {
        return "안녕하세요 제 이름은 "+name+"입니다!";
    }

}

