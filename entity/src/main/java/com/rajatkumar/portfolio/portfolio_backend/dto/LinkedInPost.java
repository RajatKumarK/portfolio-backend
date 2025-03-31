package com.rajatkumar.portfolio.portfolio_backend.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LinkedInPost {
  private String id;
  private String content;
  private String url;
  private LocalDateTime publishedAt;
  private int likes;
  private int comments;

  // Getters, setters, constructors
}
