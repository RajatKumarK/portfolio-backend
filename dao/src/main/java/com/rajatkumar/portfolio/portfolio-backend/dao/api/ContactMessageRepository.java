package com.rajatkumar.portfolio.portfolio_backend.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

  List<ContactMessage> findByReadFalse();
}

