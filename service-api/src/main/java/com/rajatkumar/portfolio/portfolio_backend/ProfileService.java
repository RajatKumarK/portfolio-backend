package com.rajatkumar.portfolio.portfolio_backend;

import java.util.Map;
import org.springframework.context.annotation.Profile;

public interface ProfileService {
  public Profile getProfile();

  public Profile updateProfile(Profile profileDetails) ;

  public Profile updateSkills(Map<String, String> skills) ;
}
