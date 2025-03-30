package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {
  @GetMapping("/api/projects")
  public String getProjects() {
    return "List of portfolio projects (placeholder)";
  }
}
