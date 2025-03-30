package com.rajatkumar.portfolio.portfolio_backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubClientImpl {
  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String username;
  private final String accessToken;

  @Autowired
  public GitHubClient(RestTemplate restTemplate,
      @Value("${social.github.api-url}") String apiUrl,
      @Value("${social.github.username}") String username,
      @Value("${social.github.access-token:#{null}}") String accessToken) {
    this.restTemplate = restTemplate;
    this.apiUrl = apiUrl;
    this.username = username;
    this.accessToken = accessToken;
  }

  /**
   * Fetches repositories from GitHub API
   */
  public List<Project> getRepositories() {
    try {
      HttpHeaders headers = new HttpHeaders();
      // Add auth token if available
      if (accessToken != null && !accessToken.isEmpty()) {
        headers.setBearerAuth(accessToken);
      }

      HttpEntity<String> entity = new HttpEntity<>(headers);

      String url = apiUrl + "/users/" + username + "/repos?sort=updated&per_page=10";

      ResponseEntity<GitHubRepository[]> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          GitHubRepository[].class
      );

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        GitHubRepository[] repos = response.getBody();

        // For each repository, get additional information like languages used
        return Arrays.stream(repos)
            .filter(repo -> !repo.isFork()) // Skip forked repositories
            .map(this::convertToProject)
            .collect(Collectors.toList());
      }

      return Collections.emptyList();
    } catch (Exception e) {
      // Log the error
      System.err.println("Failed to fetch GitHub repositories: " + e.getMessage());
      return Collections.emptyList();
    }
  }

  /**
   * Fetches languages used in a repository
   */
  private Map<String, Long> getRepositoryLanguages(String repoName) {
    try {
      HttpHeaders headers = new HttpHeaders();
      if (accessToken != null && !accessToken.isEmpty()) {
        headers.setBearerAuth(accessToken);
      }

      HttpEntity<String> entity = new HttpEntity<>(headers);

      String url = apiUrl + "/repos/" + username + "/" + repoName + "/languages";

      ResponseEntity<Map> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          Map.class
      );

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        Map<String, Long> languages = response.getBody();
        return languages;
      }

      return Collections.emptyMap();
    } catch (Exception e) {
      // Log the error
      System.err.println("Failed to fetch languages for " + repoName + ": " + e.getMessage());
      return Collections.emptyMap();
    }
  }

  /**
   * Converts GitHub repository to our domain Project model
   */
  private Project convertToProject(GitHubRepository repo) {
    Project project = new Project();
    project.setTitle(repo.getName());
    project.setDescription(repo.getDescription());
    project.setGithubUrl(repo.getHtmlUrl());

    if (repo.getHomepage() != null && !repo.getHomepage().isEmpty()) {
      project.setDemoUrl(repo.getHomepage());
    }

    project.setCreatedAt(LocalDateTime.parse(repo.getCreatedAt(),
        DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    project.setUpdatedAt(LocalDateTime.parse(repo.getUpdatedAt(),
        DateTimeFormatter.ISO_OFFSET_DATE_TIME));

    // Get languages used in the repository
    Map<String, Long> languages = getRepositoryLanguages(repo.getName());
    List<String> languageList = new ArrayList<>(languages.keySet());
    project.setTechnologies(languageList);

    return project;
  }

  // Inner class for JSON deserialization
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class GitHubRepository {
    private String name;
    private String description;
    private String htmlUrl;
    private String homepage;
    private String createdAt;
    private String updatedAt;
    private boolean fork;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getHtmlUrl() { return htmlUrl; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
    public String getHomepage() { return homepage; }
    public void setHomepage(String homepage) { this.homepage = homepage; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public boolean isFork() { return fork; }
    public void setFork(boolean fork) { this.fork = fork; }
  }
}
