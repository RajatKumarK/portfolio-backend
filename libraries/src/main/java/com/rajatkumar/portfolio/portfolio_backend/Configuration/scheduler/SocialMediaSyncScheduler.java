package com.rajatkumar.portfolio.portfolio_backend.Configuration.scheduler;

import com.rajatkumar.portfolio.portfolio_backend.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaSyncScheduler {

  @Autowired
  private SocialMediaService socialMediaService;

  @Value("${scheduler.social-media.fixed-rate:3600000}") // Default 1 hour
  private long fixedRate;

  @Scheduled(fixedRateString = "${scheduler.social-media.fixed-rate}")
  public void syncSocialMedia() {
    // Refresh social media content
    socialMediaService.refreshAllSocialMedia();
  }
}