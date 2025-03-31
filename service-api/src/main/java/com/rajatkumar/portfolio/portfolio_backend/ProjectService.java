package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.util.List;

public interface ProjectService {

  List<Project> getAllProjects();

  Project getProjectById(Long id);

  Project createProject(Project project);

  Project updateProject(Long id, Project projectDetails);

  void deleteProject(Long id);

  List<Project> synchronizeGitHubProjects();

}
