package com.progex.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
@EntityScan("com.progex.tracker")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
