package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage.BlogPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

  List<BlogPost> findAllByOrderByPublishedAtDesc();

  List<BlogPost> findByTagsContaining(String tag);
}


