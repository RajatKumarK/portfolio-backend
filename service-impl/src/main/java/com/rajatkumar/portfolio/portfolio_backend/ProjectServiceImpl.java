package com.rajatkumar.portfolio.portfolio_backend;


import com.rajatkumar.portfolio.portfolio_backend.Configuration.exception.ResourceNotFoundException;
import com.rajatkumar.portfolio.portfolio_backend.api.GitHubClient;
import com.rajatkumar.portfolio.portfolio_backend.dao.api.ProjectRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import com.rajatkumar.portfolio.portfolio_backend.dto.GitHubRepositoryDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private GitHubClient gitHubClient;

  @Override
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

  @Transactional
  public List<Project> synchronizeGitHubProjects() {
    log.info("Starting GitHub project synchronization");
    List<GitHubRepositoryDto> repositories = gitHubClient.getUserRepositories();

    List<Project> updatedProjects = new ArrayList<>();

    for (GitHubRepositoryDto repo : repositories) {
      // Skip forks and archived repos if you want
      if (repo.isArchived() || repo.isFork()) {
        continue;
      }

      // Find existing project or create new one
      Project project = projectRepository.findByGithubUrl(repo.getHtml_url())
          .orElse(new Project());

      // Update project details
      project.setTitle(repo.getName());
      project.setDescription(repo.getDescription() != null ?
          repo.getDescription() : "");
      project.setGithubUrl(repo.getHtml_url());

      // Set demo URL if available
      if (repo.getHomepage() != null && !repo.getHomepage().isEmpty()) {
        project.setDemoUrl(repo.getHomepage());
      }

      // Get languages used
      Map<String, Long> languages = gitHubClient.getRepositoryLanguages(repo.getName());
      List<String> techs = new ArrayList<>(languages.keySet());

      // Add primary language if not in languages map
      if (repo.getLanguage() != null && !languages.containsKey(repo.getLanguage())) {
        techs.add(repo.getLanguage());
      }

      // Add topics as technologies
      if (repo.getTopics() != null && !repo.getTopics().isEmpty()) {
        techs.addAll(repo.getTopics());
      }

      project.setTechnologies(techs);

      // Set dates
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        if (project.getCreatedAt() == null) {
          LocalDateTime createdAt = LocalDateTime.parse(repo.getCreated_at(), formatter);
          project.setCreatedAt(createdAt);
        }

        LocalDateTime updatedAt = LocalDateTime.parse(repo.getUpdated_at(), formatter);
        project.setUpdatedAt(updatedAt);
      } catch (Exception e) {
        log.error("Error parsing GitHub dates", e);
        // Use current time as fallback
        if (project.getCreatedAt() == null) {
          project.setCreatedAt(LocalDateTime.now());
        }
        project.setUpdatedAt(LocalDateTime.now());
      }

      // Add metadata
      Map<String, String> metadata = new HashMap<>();
      metadata.put("stargazers_count", String.valueOf(repo.getStargazers_count()));
      metadata.put("forks_count", String.valueOf(repo.getForks_count()));
      metadata.put("watchers_count", String.valueOf(repo.getWatchers_count()));
      project.setMetadata(metadata);

      // Save the project
      Project savedProject = projectRepository.save(project);
      updatedProjects.add(savedProject);
    }

    log.info("GitHub synchronization completed. Updated {} projects", updatedProjects.size());
    return updatedProjects;
  }
}
