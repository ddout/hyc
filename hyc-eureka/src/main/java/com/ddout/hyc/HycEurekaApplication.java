package com.ddout.hyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HycEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(HycEurekaApplication.class, args);
    }
}
