package com.rajatkumar.portfolio.portfolio_backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class YouTubeClientImpl {
  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String apiKey;
  private final String channelId;

  // Cache to store videos and reduce API calls
  private List<YouTubeVideo> cachedVideos = new ArrayList<>();
  private LocalDateTime lastUpdated;

  @Autowired
  public YouTubeClient(RestTemplate restTemplate,
      @Value("${social.youtube.api-url}") String apiUrl,
      @Value("${social.youtube.api-key}") String apiKey,
      @Value("${social.youtube.channel-id}") String channelId) {
    this.restTemplate = restTemplate;
    this.apiUrl = apiUrl;
    this.apiKey = apiKey;
    this.channelId = channelId;
  }

  /**
   * Fetches YouTube videos, either from cache if recent or from API
   */
  public List<YouTubeVideo> getVideos() {
    // If cache is not expired, return cached data
    if (lastUpdated != null &&
        Duration.between(lastUpdated, LocalDateTime.now()).toHours() < 3 &&
        !cachedVideos.isEmpty()) {
      return cachedVideos;
    }

    return refreshVideos();
  }

  /**
   * Forces a refresh of YouTube videos from the API
   */
  public List<YouTubeVideo> refreshVideos() {
    try {
      // YouTube Data API v3 endpoint for channel videos
      String url = apiUrl + "/youtube/v3/search?part=snippet&channelId=" + channelId +
          "&maxResults=10&order=date&type=video&key=" + apiKey;

      ResponseEntity<YouTubeSearchResponse> searchResponse = restTemplate.getForEntity(
          url,
          YouTubeSearchResponse.class
      );

      if (!searchResponse.getStatusCode().is2xxSuccessful() || searchResponse.getBody() == null) {
        return Collections.emptyList();
      }

      // Get video IDs from search response
      List<String> videoIds = searchResponse.getBody().getItems().stream()
          .map(item -> item.getId().getVideoId())
          .collect(Collectors.toList());

      if (videoIds.isEmpty()) {
        return Collections.emptyList();
      }

      // Get additional video details including statistics
      String videoDetailsUrl = apiUrl + "/youtube/v3/videos?part=snippet,statistics,contentDetails&id=" +
          String.join(",", videoIds) + "&key=" + apiKey;

      ResponseEntity<YouTubeVideoResponse> videoResponse = restTemplate.getForEntity(
          videoDetailsUrl,
          YouTubeVideoResponse.class
      );

      if (videoResponse.getStatusCode().is2xxSuccessful() && videoResponse.getBody() != null) {
        cachedVideos = convertToVideos(videoResponse.getBody());
        lastUpdated = LocalDateTime.now();
        return cachedVideos;
      }

      return Collections.emptyList();
    } catch (Exception e) {
      // Log the error
      System.err.println("Failed to fetch YouTube videos: " + e.getMessage());
      // Return cached data if available, otherwise empty list
      return !cachedVideos.isEmpty() ? cachedVideos : Collections.emptyList();
    }
  }

  /**
   * Converts YouTube API response to our domain model
   */
  private List<YouTubeVideo> convertToVideos(YouTubeVideoResponse response) {
    if (response.getItems() == null) {
      return Collections.emptyList();
    }

    return response.getItems().stream()
        .map(item -> {
          YouTubeVideo video = new YouTubeVideo();
          video.setId(item.getId());

          if (item.getSnippet() != null) {
            video.setTitle(item.getSnippet().getTitle());
            video.setDescription(item.getSnippet().getDescription());
            video.setPublishedAt(LocalDateTime.parse(item.getSnippet().getPublishedAt(),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            if (item.getSnippet().getThumbnails() != null &&
                item.getSnippet().getThumbnails().getHigh() != null) {
              video.setThumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl());
            }
          }

          video.setVideoUrl("https://www.youtube.com/watch?v=" + item.getId());

          if (item.getStatistics() != null) {
            video.setViewCount(item.getStatistics().getViewCount());
          }

          return video;
        })
        .collect(Collectors.toList());
  }

  // Inner classes for JSON deserialization
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeSearchResponse {
    private List<YouTubeSearchItem> items;

    public List<YouTubeSearchItem> getItems() {
      return items;
    }

    public void setItems(List<YouTubeSearchItem> items) {
      this.items = items;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeSearchItem {
    private YouTubeSearchId id;

    public YouTubeSearchId getId() {
      return id;
    }

    public void setId(YouTubeSearchId id) {
      this.id = id;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeSearchId {
    private String videoId;

    public String getVideoId() {
      return videoId;
    }

    public void setVideoId(String videoId) {
      this.videoId = videoId;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeVideoResponse {
    private List<YouTubeVideoItem> items;

    public List<YouTubeVideoItem> getItems() {
      return items;
    }

    public void setItems(List<YouTubeVideoItem> items) {
      this.items = items;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeVideoItem {
    private String id;
    private YouTubeSnippet snippet;
    private YouTubeStatistics statistics;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public YouTubeSnippet getSnippet() { return snippet; }
    public void setSnippet(YouTubeSnippet snippet) { this.snippet = snippet; }
    public YouTubeStatistics getStatistics() { return statistics; }
    public void setStatistics(YouTubeStatistics statistics) { this.statistics = statistics; }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeSnippet {
    private String title;
    private String description;
    private String publishedAt;
    private YouTubeThumbnails thumbnails;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
    public YouTubeThumbnails getThumbnails() { return thumbnails; }
    public void setThumbnails(YouTubeThumbnails thumbnails) { this.thumbnails = thumbnails; }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeThumbnails {
    private YouTubeThumbnail high;

    public YouTubeThumbnail getHigh() {
      return high;
    }

    public void setHigh(YouTubeThumbnail high) {
      this.high = high;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeThumbnail {
    private String url;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class YouTubeStatistics {
    private int viewCount;

    public int getViewCount() {
      return viewCount;
    }

    public void setViewCount(int viewCount) {
      this.viewCount = viewCount;
    }
  }
}
