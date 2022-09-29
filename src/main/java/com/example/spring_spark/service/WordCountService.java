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

    public Map<String, Long> getCount(SparkReq req) {

        JavaRDD<String> words = sc.textFile("src/main/resources/static/40.csv");
        words.take(10).forEach(System.out::println);
        System.out.println(words.take(1));
        System.out.println(words.take(1).getClass().getName());
        String [] temp = words.take(2).get(1).toString().split(",");
        for (String item : temp) {
            System.out.println(item);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++");
//        for (String word: words.collect()) {
//            System.out.println(word);
//        }
//        System.out.println("++++++++++++++++++++++++++++++++++++");
//        System.out.println(words.count());
        Map<String, Long> wordCounts = words.countByValue();
//        System.out.println(words);
        return wordCounts;
    }

    public Map<String, Long> getCount(List<String> wordList) {
        JavaRDD<String> words = sc.parallelize(wordList);
        Map<String, Long> wordCounts = words.countByValue();
        return wordCounts;
    }
}
