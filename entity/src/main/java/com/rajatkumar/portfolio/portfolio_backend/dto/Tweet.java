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
public class Tweet {

  private String id;
  private String text;
  private String url;
  private LocalDateTime createdAt;
  private int retweetCount;
  private int likeCount;

}
