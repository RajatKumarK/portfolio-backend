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
public class YouTubeVideo {

  private String id;
  private String title;
  private String description;
  private String thumbnailUrl;
  private String videoUrl;
  private LocalDateTime publishedAt;
  private int viewCount;

}
