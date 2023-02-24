package com.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 13:39
 */
@SpringBootApplication
@MapperScan("com.cc.mapper")
public class IwordUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(IwordUiApplication.class,args);
    }
}
