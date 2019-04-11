package com.ddout.hyc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HycConfigureApplication {

    public static void main(String[] args) {
        SpringApplication.run(HycConfigureApplication.class, args);

    }
}
