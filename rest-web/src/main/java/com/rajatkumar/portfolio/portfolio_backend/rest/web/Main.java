package com.rajatkumar.portfolio.portfolio_backend.rest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = {"com.rajatkumar.portfolio.portfolio_backend.*"})
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}