package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dto.YouTubeVideo;
import java.util.List;

public interface YouTubeClient {

  public List<YouTubeVideo> getVideos();

  public List<YouTubeVideo> refreshVideos();
}
