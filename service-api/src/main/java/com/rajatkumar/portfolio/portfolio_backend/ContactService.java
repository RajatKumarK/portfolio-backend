package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import java.util.List;

public interface ContactService {

  ContactMessage saveContactMessage(ContactMessage contactMessage);

  List<ContactMessage> getUnreadMessages();

  ContactMessage markAsRead(Long id);

  List<ContactMessage> getAllMessages();

  void deleteMessage(Long id);
}
