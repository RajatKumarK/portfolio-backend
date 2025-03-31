package com.rajatkumar.portfolio.portfolio_backend.Configuration.scheduler;

import com.rajatkumar.portfolio.portfolio_backend.ProjectService;
import com.rajatkumar.portfolio.portfolio_backend.dao.Project;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GitHubSyncScheduler {

  @Autowired
  private ProjectService projectService;

  @Value("${scheduler.github.cron:{0 0 */12 * * *}}")
  private String cronExpression;

  @Scheduled(cron = "#{ @environment.getProperty('scheduler.github.cron', '${scheduler.github.cron}') }")
  public void syncGitHubProjects() {
    log.info("Starting scheduled GitHub project synchronization");
    try {
      List<Project> updatedProjects = projectService.synchronizeGitHubProjects();
      log.info("Scheduled GitHub sync completed successfully. Updated {} projects",
          updatedProjects.size());
    } catch (Exception e) {
      log.error("Error during scheduled GitHub synchronization", e);
    }
  }
}
