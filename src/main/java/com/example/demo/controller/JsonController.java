package com.example.demo.controller;

import com.example.demo.entity.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {


    @GetMapping("/json")
    public Json get(){
        return new Json(26L,"허준기");
    }
}
