package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class BlogPost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String summary;

  @Lob
  private String content; // Markdown or HTML content

  private String coverImageUrl;
  private LocalDateTime publishedAt;
  private LocalDateTime updatedAt;

  @ElementCollection
  private List<String> tags;

  // Getters, setters, constructors
}
