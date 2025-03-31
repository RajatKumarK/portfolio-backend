package com.rajatkumar.portfolio.portfolio_backend;

import java.util.Map;
import com.rajatkumar.portfolio.portfolio_backend.dao.Profile;

public interface ProfileService {

  Profile getProfile();

  Profile updateProfile(Profile profileDetails);

  Profile updateSkills(Map<String, String> skills);
}
