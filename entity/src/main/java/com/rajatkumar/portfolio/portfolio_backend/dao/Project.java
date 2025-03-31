package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MapKeyColumn;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(length = 1000)
  private String description;

  private String imageUrl;
  private String githubUrl;
  private String demoUrl;
  private boolean featured;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ElementCollection
  private List<String> technologies = new ArrayList<>();

  @ElementCollection
  @MapKeyColumn(name = "key")
  @Column(name = "value")
  private Map<String, String> metadata = new HashMap<>();
}
