package com.example.demo.json;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {


    @GetMapping("/json")
    public Entity get(){
        return new Entity(26L,"허준기");
    }
}
