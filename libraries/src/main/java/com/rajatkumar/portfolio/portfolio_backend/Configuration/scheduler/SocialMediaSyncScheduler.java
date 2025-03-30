package com.rajatkumar.portfolio.portfolio_backend.Configuration.scheduler;

import com.rajatkumar.portfolio.portfolio_backend.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaSyncScheduler {
  @Autowired
  private SocialMediaService socialMediaService;

  @Scheduled(fixedRate = 3600000) // Every hour
  public void syncSocialMedia() {
    // Refresh social media content
  }

}
