package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage.BlogPost;
import java.util.List;

public interface BlogService {

  List<BlogPost> getAllBlogPosts();

  BlogPost getBlogPostById(Long id);

  BlogPost createBlogPost(BlogPost blogPost);

  BlogPost updateBlogPost(Long id, BlogPost blogPostDetails);

  void deleteBlogPost(Long id);

  List<BlogPost> getBlogPostsByTag(String tag);

}
