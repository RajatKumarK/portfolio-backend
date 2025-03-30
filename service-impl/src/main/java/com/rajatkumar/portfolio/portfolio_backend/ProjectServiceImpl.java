package com.rajatkumar.portfolio.portfolio_backend;


import com.rajatkumar.portfolio.portfolio_backend.api.GitHubClient;
import com.rajatkumar.portfolio.portfolio_backend.api.ProjectRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private GitHubClient gitHubClient;

  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  public Project getProjectById(Long id) {
    return projectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
  }

  public Project createProject(Project project) {
    project.setCreatedAt(LocalDateTime.now());
    project.setUpdatedAt(LocalDateTime.now());
    return projectRepository.save(project);
  }

  public Project updateProject(Long id, Project projectDetails) {
    Project project = getProjectById(id);
    // Update fields
    project.setUpdatedAt(LocalDateTime.now());
    return projectRepository.save(project);
  }

  public void deleteProject(Long id) {
    Project project = getProjectById(id);
    projectRepository.delete(project);
  }

  public List<Project> synchronizeGitHubProjects() {
    // Fetch projects from GitHub and update local database
  }
}
