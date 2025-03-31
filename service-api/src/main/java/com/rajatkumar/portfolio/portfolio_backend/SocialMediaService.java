package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.util.List;

public interface SocialMediaService {

   List<LinkedInPost> getLinkedInPosts() ;

   List<Tweet> getTweets() ;

   List<YouTubeVideo> getYouTubeVideos() ;

   void refreshAllSocialMedia() ;

}
