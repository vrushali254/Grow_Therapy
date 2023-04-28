package com.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsApplication {
    public static void main(String[] args) {

        System.out.println("Starting the analytics application. Enjoy!");
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}