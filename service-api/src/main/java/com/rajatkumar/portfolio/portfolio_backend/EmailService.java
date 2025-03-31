package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;

public interface EmailService {

  void sendContactNotification(ContactMessage contactMessage);
}
