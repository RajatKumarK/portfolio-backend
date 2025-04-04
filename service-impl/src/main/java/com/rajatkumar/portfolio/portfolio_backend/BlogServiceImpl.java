package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.Configuration.exception.ResourceNotFoundException;
import com.rajatkumar.portfolio.portfolio_backend.dao.api.BlogPostRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage.BlogPost;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
  @Autowired
  private BlogPostRepository blogPostRepository;

  public List<BlogPost> getAllBlogPosts() {
    return blogPostRepository.findAllByOrderByPublishedAtDesc();
  }

  public BlogPost getBlogPostById(Long id) {
    return blogPostRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Blog post not found"));
  }

  public BlogPost createBlogPost(BlogPost blogPost) {
    blogPost.setPublishedAt(LocalDateTime.now());
    blogPost.setUpdatedAt(LocalDateTime.now());
    return blogPostRepository.save(blogPost);
  }

  public BlogPost updateBlogPost(Long id, BlogPost blogPostDetails) {
    BlogPost blogPost = getBlogPostById(id);
    // Update fields
    blogPost.setUpdatedAt(LocalDateTime.now());
    return blogPostRepository.save(blogPost);
  }

  public void deleteBlogPost(Long id) {
    BlogPost blogPost = getBlogPostById(id);
    blogPostRepository.delete(blogPost);
  }

  public List<BlogPost> getBlogPostsByTag(String tag) {
    return blogPostRepository.findByTagsContaining(tag);
  }
}
