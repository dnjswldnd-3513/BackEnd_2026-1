package com.example.demo.apitest;


import com.example.demo.json.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ApiController {

    Map<Long,Aricle> maps = new HashMap<>();

    @GetMapping("/api/{index}")
    @ResponseBody
    public ResponseEntity<?> getMethod(@PathVariable Long index){
        return ResponseEntity.ok(maps.get(index));
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<?> postMethod(@RequestBody Aricle aricle){
        maps.put(aricle.getId(),aricle);
        return ResponseEntity.status(HttpStatus.CREATED).body(aricle);
    }
}
