package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.api.LinkedInClient;
import com.rajatkumar.portfolio.portfolio_backend.api.TwitterClient;
import com.rajatkumar.portfolio.portfolio_backend.api.YouTubeClient;
import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {
  @Autowired
  private LinkedInClient linkedInClient;

  @Autowired
  private TwitterClient twitterClient;

  @Autowired
  private YouTubeClient youtubeClient;

  @Override
  public List<LinkedInPost> getLinkedInPosts() {
    return linkedInClient.getPosts();
  }

  @Override
  public List<Tweet> getTweets() {
    return twitterClient.getTweets();
  }

  @Override
  public List<YouTubeVideo> getYouTubeVideos() {
    return youtubeClient.getVideos();
  }

  public void refreshAllSocialMedia() {
    // Forcefully refresh all social media content
    CompletableFuture.allOf(
        CompletableFuture.runAsync(this::refreshLinkedInPosts),
        CompletableFuture.runAsync(this::refreshTweets),
        CompletableFuture.runAsync(this::refreshYouTubeVideos)
    ).join();
  }

  private void refreshLinkedInPosts() {
    linkedInClient.refreshPosts();
  }

  private void refreshTweets() {
    twitterClient.refreshTweets();
  }

  private void refreshYouTubeVideos() {
    youtubeClient.refreshVideos();
  }
}
