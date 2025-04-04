package com.rajatkumar.portfolio.portfolio_backend.dao.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  List<Project> findByFeaturedTrue();
  Optional<Project> findByGithubUrl(String githubUrl);
}


