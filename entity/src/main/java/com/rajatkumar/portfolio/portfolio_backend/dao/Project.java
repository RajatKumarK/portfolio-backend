package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private String imageUrl;
  private String githubUrl;
  private String demoUrl;
  private boolean featured;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ElementCollection
  private List<String> technologies;

  // Getters, setters, constructors
}
