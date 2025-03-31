package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import com.rajatkumar.portfolio.portfolio_backend.dto.GitHubRepositoryDto;
import java.util.List;
import java.util.Map;

public interface GitHubClient {
  public List<GitHubRepositoryDto> getUserRepositories();
  public Map<String, Long> getRepositoryLanguages(String repoName);
}
