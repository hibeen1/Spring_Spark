package com.example.spring_spark.service;

import com.example.spring_spark.request.SparkReq;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.spark.*;

@Service
public class WordCountService {
    @Autowired
    JavaSparkContext sc;

//    public Map<String, Long> getCount(SparkReq req) {
//
//        JavaRDD<ArrayList<String>> words = sc.textFile("src/main/resources/static/40.csv").;
//
//        System.out.println(words.count());
////        Map<String, Long> wordCounts = words.countByValue();
//        System.out.println(words);
//        return wordCounts;
//    }

    public Map<String, Long> getCount(List<String> wordList) {
        JavaRDD<String> words = sc.parallelize(wordList);
        Map<String, Long> wordCounts = words.countByValue();
        return wordCounts;
    }
}
