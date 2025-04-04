package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.BlogService;
import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage.BlogPost;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
  @Autowired
  private BlogService blogService;

  @GetMapping
  public List<BlogPost> getAllBlogPosts() {
    return blogService.getAllBlogPosts();
  }

  @GetMapping("/{id}")
  public BlogPost getBlogPostById(@PathVariable Long id) {
    return blogService.getBlogPostById(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
    return blogService.createBlogPost(blogPost);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public BlogPost updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost) {
    return blogService.updateBlogPost(id, blogPost);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteBlogPost(@PathVariable Long id) {
    blogService.deleteBlogPost(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/tags/{tag}")
  public List<BlogPost> getBlogPostsByTag(@PathVariable String tag) {
    return blogService.getBlogPostsByTag(tag);
  }
}
