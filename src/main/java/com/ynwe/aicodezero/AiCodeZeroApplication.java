package com.ynwe.aicodezero;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("com.ynwe.aicodezero.mapper")
public class AiCodeZeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiCodeZeroApplication.class, args);
    }

}
