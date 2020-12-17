package com.crudsavior.wechatPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.crudsavior.wechatPlatform")
@EnableScheduling
public class WechatPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatPlatformApplication.class, args);
    }

}
