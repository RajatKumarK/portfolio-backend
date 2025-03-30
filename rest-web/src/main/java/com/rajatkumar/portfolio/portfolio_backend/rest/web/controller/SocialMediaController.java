package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.SocialMediaService;
import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/social")
public class SocialMediaController {
  @Autowired
  private SocialMediaService socialMediaService;

  @GetMapping("/linkedin")
  public ResponseEntity<List<LinkedInPost>> getLinkedInPosts() {
    try {
      return ResponseEntity.ok(socialMediaService.getLinkedInPosts());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .body(Collections.emptyList());
    }
  }

  @GetMapping("/twitter")
  public ResponseEntity<List<Tweet>> getTweets() {
    try {
      return ResponseEntity.ok(socialMediaService.getTweets());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .body(Collections.emptyList());
    }
  }

  @GetMapping("/youtube")
  public ResponseEntity<List<YouTubeVideo>> getYouTubeVideos() {
    try {
      return ResponseEntity.ok(socialMediaService.getYouTubeVideos());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .body(Collections.emptyList());
    }
  }

  @PostMapping("/refresh")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> refreshSocialMedia() {
    try {
      socialMediaService.refreshAllSocialMedia();
      return ResponseEntity.ok("Social media content refreshed successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to refresh social media content: " + e.getMessage());
    }
  }
}
