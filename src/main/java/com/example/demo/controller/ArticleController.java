package com.example.demo.controller;


import com.example.demo.entity.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ArticleController {

    Map<Long, Article> maps = new HashMap<>();

    @GetMapping("/article/{index}")
    public ResponseEntity<?> getMethod(@PathVariable Long index){
        if (maps.get(index) == null){
            return ResponseEntity.notFound().build(); // notFound,notContent의 따라 에러코드도 달라짐
        }
        return ResponseEntity.ok(maps.get(index));
    }

    @PostMapping("/article")
    public ResponseEntity<?> postMethod(@RequestBody Article aricle){
        maps.put(aricle.getId(),aricle);
        return ResponseEntity.status(HttpStatus.CREATED).body(aricle);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<?> putMethod(@PathVariable Long id, @RequestBody Article aricle) {
        if (maps.get(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Article updates = maps.get(id);
        updates.setDescription(aricle.getDescription());
        maps.put(id, updates);
        return ResponseEntity.ok(updates);
    }


    @DeleteMapping("/article/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable Long id){
        if (maps.get(id) == null){
            return ResponseEntity.notFound().build();
        }
        maps.remove(id) ;
        return ResponseEntity.noContent().build();
    }
}
