package com.example.demo.json;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController {


    @GetMapping("/json")
    @ResponseBody
    public Entity get(){
        return new Entity(26L,"허준기");
    }
}
