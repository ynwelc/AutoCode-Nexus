package com.ynwe.aicodezero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ynwe.aicodezero.mapper")
public class AiCodeZeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiCodeZeroApplication.class, args);
    }

}
