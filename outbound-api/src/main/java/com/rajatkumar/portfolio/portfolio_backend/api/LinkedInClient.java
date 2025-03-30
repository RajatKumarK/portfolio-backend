package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dto.LinkedInPost;
import java.util.List;

public interface LinkedInClient {

  public List<LinkedInPost> getPosts();

  public List<LinkedInPost> refreshPosts();

}
