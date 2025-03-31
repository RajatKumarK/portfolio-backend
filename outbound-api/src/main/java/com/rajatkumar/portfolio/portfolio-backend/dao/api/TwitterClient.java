package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dto.Tweet;
import java.util.List;

public interface TwitterClient {

  public List<Tweet> getTweets();

  public List<Tweet> refreshTweets();

}
