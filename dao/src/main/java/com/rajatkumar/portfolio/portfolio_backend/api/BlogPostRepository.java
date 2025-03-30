package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.BlogPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

  List<BlogPost> findAllByOrderByPublishedAtDesc();

  List<BlogPost> findByTagsContaining(String tag);
}


