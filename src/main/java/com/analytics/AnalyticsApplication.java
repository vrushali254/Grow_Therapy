package com.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AnalyticsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {

        System.out.println("Starting the analytics application. Enjoy!");
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}