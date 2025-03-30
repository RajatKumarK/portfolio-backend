package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.BlogPost;
import java.util.List;

public interface BlogService {

  public List<BlogPost> getAllBlogPosts();

  public BlogPost getBlogPostById(Long id);

  public BlogPost createBlogPost(BlogPost blogPost);

  public BlogPost updateBlogPost(Long id, BlogPost blogPostDetails);

  public void deleteBlogPost(Long id);

  public List<BlogPost> getBlogPostsByTag(String tag);

}
