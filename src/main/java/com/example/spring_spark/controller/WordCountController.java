package com.example.spring_spark.controller;

import com.example.spring_spark.request.SparkReq;
import com.example.spring_spark.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spark")
public class WordCountController {
    @Autowired
    WordCountService service;

    @GetMapping
    public Map<String, Long> count(@RequestBody(required = true) SparkReq req) {
        return service.getCount(req);
    }


//    @PostMapping("wordcount")
//    public Map<String, Long> count(@RequestParam(required = true) String words) {
//        List<String> wordList = Arrays.asList(words.split(","));
//        return service.getCount(wordList);
//    }
}
