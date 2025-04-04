package com.rajatkumar.portfolio.portfolio_backend.dao.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}