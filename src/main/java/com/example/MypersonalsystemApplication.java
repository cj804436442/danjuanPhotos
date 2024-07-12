package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.dao")
public class MypersonalsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypersonalsystemApplication.class, args);
    }

}
