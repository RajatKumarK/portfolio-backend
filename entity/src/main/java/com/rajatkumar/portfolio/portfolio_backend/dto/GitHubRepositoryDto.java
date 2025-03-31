package com.rajatkumar.portfolio.portfolio_backend.dto;

import java.util.List;
import java.util.Map;
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
public class GitHubRepositoryDto {
  private Long id;
  private String name;
  private String full_name;
  private String html_url;
  private String description;
  private boolean fork;
  private String created_at;
  private String updated_at;
  private String pushed_at;
  private String homepage;
  private int stargazers_count;
  private int watchers_count;
  private int forks_count;
  private String language;
  private boolean archived;
  private String default_branch;
  private Map<String, Object> owner;
  private List<String> topics;
}
