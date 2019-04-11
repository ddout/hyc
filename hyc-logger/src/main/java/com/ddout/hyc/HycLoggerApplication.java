package com.ddout.hyc;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringCloudApplication
@EnableEurekaClient
public class HycLoggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HycLoggerApplication.class, args);

    }
}
