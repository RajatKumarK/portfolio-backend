package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.util.List;

public interface SocialMediaService {

  public List<LinkedInPost> getLinkedInPosts() ;

  public List<Tweet> getTweets() ;

  public List<YouTubeVideo> getYouTubeVideos() ;

  public void refreshAllSocialMedia() ;

}
