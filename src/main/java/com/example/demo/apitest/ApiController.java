package com.example.demo.apitest;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApiController {


    @GetMapping("/api/{index}")
    public ResponseEntity<Long> getMethod(@PathVariable Long index){
        return ResponseEntity.ok(index);
    }
}
