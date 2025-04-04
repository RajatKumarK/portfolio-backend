package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PortfolioController {

  @GetMapping("/projects")
  public String getProjects() {
    return "List of portfolio projects (placeholder)";
  }
}
