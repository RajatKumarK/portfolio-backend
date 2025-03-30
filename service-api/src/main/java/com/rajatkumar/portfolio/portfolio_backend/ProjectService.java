package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.annotation.Profile;

public interface ProjectService {

  public List<Project> getAllProjects() ;

  public Project getProjectById(Long id) ;

  public Project createProject(Project project) ;

  public Project updateProject(Long id, Project projectDetails) ;

  public void deleteProject(Long id) ;

  public List<Project> synchronizeGitHubProjects() ;

}
