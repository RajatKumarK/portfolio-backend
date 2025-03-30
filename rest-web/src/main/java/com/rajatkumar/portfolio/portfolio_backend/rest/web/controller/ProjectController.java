package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.ProjectService;
import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
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
@RequestMapping("/api/projects")
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @GetMapping
  public List<Project> getAllProjects() {
    return projectService.getAllProjects();
  }

  @GetMapping("/{id}")
  public Project getProjectById(@PathVariable Long id) {
    return projectService.getProjectById(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Project createProject(@RequestBody Project project) {
    return projectService.createProject(project);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
    return projectService.updateProject(id, project);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/github")
  public List<Project> syncGitHubProjects() {
    return projectService.synchronizeGitHubProjects();
  }
}
