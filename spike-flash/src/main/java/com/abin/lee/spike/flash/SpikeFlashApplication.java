package com.abin.lee.spike.flash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by abin on 2018/6/13.
 */
@SpringBootApplication
public class SpikeFlashApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(SpikeFlashApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpikeFlashApplication.class, args);
    }


}