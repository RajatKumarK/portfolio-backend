package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import java.util.List;

public interface ContactService {

  public ContactMessage saveContactMessage(ContactMessage contactMessage);

  public List<ContactMessage> getUnreadMessages();

  public ContactMessage markAsRead(Long id);

  public List<ContactMessage> getAllMessages();

  public void deleteMessage(Long id);
}
