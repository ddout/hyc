package com.ddout.hyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Hello world!
 */
@SpringCloudApplication
@MapperScan("com.ddout.hyc.dao")
public class HYCWebAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(HYCWebAdminApplication.class, args);
    }
}
