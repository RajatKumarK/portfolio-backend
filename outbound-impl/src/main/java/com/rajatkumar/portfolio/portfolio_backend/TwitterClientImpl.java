package com.rajatkumar.portfolio.portfolio_backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TwitterClientImpl {

  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String bearerToken;

  // Cache to store tweets and reduce API calls
  private List<Tweet> cachedTweets = new ArrayList<>();
  private LocalDateTime lastUpdated;

  @Autowired
  public TwitterClient(RestTemplate restTemplate,
      @Value("${social.twitter.api-url}") String apiUrl,
      @Value("${social.twitter.bearer-token}") String bearerToken) {
    this.restTemplate = restTemplate;
    this.apiUrl = apiUrl;
    this.bearerToken = bearerToken;
  }

  /**
   * Fetches tweets, either from cache if recent or from API
   */
  public List<Tweet> getTweets() {
    // If cache is not expired, return cached data
    if (lastUpdated != null &&
        Duration.between(lastUpdated, LocalDateTime.now()).toHours() < 1 &&
        !cachedTweets.isEmpty()) {
      return cachedTweets;
    }

    return refreshTweets();
  }

  /**
   * Forces a refresh of tweets from the API
   */
  public List<Tweet> refreshTweets() {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(bearerToken);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      // Twitter API v2 endpoint for user tweets
      String twitterHandle = "@your_twitter_handle"; // Replace with your Twitter handle
      String url = apiUrl + "/2/users/by/username/" + twitterHandle.substring(1) +
          "/tweets?tweet.fields=created_at,public_metrics&max_results=10";

      ResponseEntity<TwitterResponse> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          TwitterResponse.class
      );

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        cachedTweets = convertToTweets(response.getBody());
        lastUpdated = LocalDateTime.now();
        return cachedTweets;
      }

      return Collections.emptyList();
    } catch (Exception e) {
      // Log the error
      System.err.println("Failed to fetch tweets: " + e.getMessage());
      // Return cached data if available, otherwise empty list
      return !cachedTweets.isEmpty() ? cachedTweets : Collections.emptyList();
    }
  }

  /**
   * Converts Twitter API response to our domain model
   */
  private List<Tweet> convertToTweets(TwitterResponse response) {
    if (response.getData() == null) {
      return Collections.emptyList();
    }

    return response.getData().stream()
        .map(tweetData -> {
          Tweet tweet = new Tweet();
          tweet.setId(tweetData.getId());
          tweet.setText(tweetData.getText());
          tweet.setUrl("https://twitter.com/twitter/status/" + tweetData.getId());
          tweet.setCreatedAt(LocalDateTime.parse(tweetData.getCreatedAt(),
              DateTimeFormatter.ISO_OFFSET_DATE_TIME));

          if (tweetData.getPublicMetrics() != null) {
            tweet.setRetweetCount(tweetData.getPublicMetrics().getRetweetCount());
            tweet.setLikeCount(tweetData.getPublicMetrics().getLikeCount());
          }

          return tweet;
        })
        .collect(Collectors.toList());
  }

  // Inner classes for JSON deserialization
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class TwitterResponse {

    private List<TweetData> data;

    public List<TweetData> getData() {
      return data;
    }

    public void setData(List<TweetData> data) {
      this.data = data;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class TweetData {

    private String id;
    private String text;
    private String createdAt;
    private PublicMetrics publicMetrics;

    // Getters and setters
    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }

    public PublicMetrics getPublicMetrics() {
      return publicMetrics;
    }

    public void setPublicMetrics(PublicMetrics publicMetrics) {
      this.publicMetrics = publicMetrics;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class PublicMetrics {

    private int retweetCount;
    private int likeCount;

    // Getters and setters
    public int getRetweetCount() {
      return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
      this.retweetCount = retweetCount;
    }

    public int getLikeCount() {
      return likeCount;
    }

    public void setLikeCount(int likeCount) {
      this.likeCount = likeCount;
    }
  }
}
