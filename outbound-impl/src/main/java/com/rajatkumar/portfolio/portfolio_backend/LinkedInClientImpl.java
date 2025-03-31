package com.rajatkumar.portfolio.portfolio_backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rajatkumar.portfolio.portfolio_backend.api.LinkedInClient;
import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LinkedInClientImpl implements LinkedInClient {
  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String accessToken;

  // Cache to store posts and reduce API calls
  private List<LinkedInPost> cachedPosts = new ArrayList<>();
  private LocalDateTime lastUpdated;

  @Autowired
  public LinkedInClientImpl(RestTemplate restTemplate,
      @Value("${social.linkedin.api-url}") String apiUrl,
      @Value("${social.linkedin.access-token}") String accessToken) {
    this.restTemplate = restTemplate;
    this.apiUrl = apiUrl;
    this.accessToken = accessToken;
  }

  /**
   * Fetches LinkedIn posts, either from cache if recent or from API
   */
  public List<LinkedInPost> getPosts() {
    // If cache is not expired, return cached data
    if (lastUpdated != null &&
        Duration.between(lastUpdated, LocalDateTime.now()).toHours() < 1 &&
        !cachedPosts.isEmpty()) {
      return cachedPosts;
    }

    return refreshPosts();
  }

  /**
   * Forces a refresh of LinkedIn posts from the API
   */
  public List<LinkedInPost> refreshPosts() {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      // LinkedIn API v2 endpoint for user posts
      String url = apiUrl + "/v2/me/posts?fields=id,text,created-time,likes,comments";

      ResponseEntity<LinkedInResponse> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          LinkedInResponse.class
      );

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        cachedPosts = convertToPosts(response.getBody());
        lastUpdated = LocalDateTime.now();
        return cachedPosts;
      }

      return Collections.emptyList();
    } catch (Exception e) {
      // Log the error
      System.err.println("Failed to fetch LinkedIn posts: " + e.getMessage());
      // Return cached data if available, otherwise empty list
      return !cachedPosts.isEmpty() ? cachedPosts : Collections.emptyList();
    }
  }

  /**
   * Converts LinkedIn API response to our domain model
   */
  private List<LinkedInPost> convertToPosts(LinkedInResponse response) {
    if (response.getElements() == null) {
      return Collections.emptyList();
    }

    return response.getElements().stream()
        .map(element -> {
          LinkedInPost post = new LinkedInPost();
          post.setId(element.getId());
          post.setContent(element.getText());
          post.setUrl("https://www.linkedin.com/feed/update/" + element.getId());
          post.setPublishedAt(LocalDateTime.parse(element.getCreatedTime(),
              DateTimeFormatter.ISO_OFFSET_DATE_TIME));
          post.setLikes(element.getLikes().getTotal());
          post.setComments(element.getComments().getTotal());
          return post;
        })
        .collect(Collectors.toList());
  }

  // Inner classes for JSON deserialization
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class LinkedInResponse {
    private List<LinkedInElement> elements;

    public List<LinkedInElement> getElements() {
      return elements;
    }

    public void setElements(List<LinkedInElement> elements) {
      this.elements = elements;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class LinkedInElement {
    private String id;
    private String text;
    private String createdTime;
    private LinkedInSocialCount likes;
    private LinkedInSocialCount comments;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getCreatedTime() { return createdTime; }
    public void setCreatedTime(String createdTime) { this.createdTime = createdTime; }
    public LinkedInSocialCount getLikes() { return likes; }
    public void setLikes(LinkedInSocialCount likes) { this.likes = likes; }
    public LinkedInSocialCount getComments() { return comments; }
    public void setComments(LinkedInSocialCount comments) { this.comments = comments; }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class LinkedInSocialCount {
    private int total;

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
  }
}
