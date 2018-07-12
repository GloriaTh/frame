package com.sboot.springboot;

import com.sboot.springboot.common.MyJedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@MapperScan("com.sboot.springboot.dao")
public class SpringbootApplication {
    @Autowired
    private static MyJedisConfig config;

    @RequestMapping("/test")
    public void test(){
        System.out.println(config.getHost());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
