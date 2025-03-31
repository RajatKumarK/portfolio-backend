package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.Configuration.exception.ResourceNotFoundException;
import com.rajatkumar.portfolio.portfolio_backend.api.ContactMessageRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
  @Autowired
  private ContactMessageRepository contactMessageRepository;

  @Autowired
  private EmailService emailService;

  public ContactMessage saveContactMessage(ContactMessage contactMessage) {
    contactMessage.setReceivedAt(LocalDateTime.now());
    contactMessage.setRead(false);

    // Notify via email
    emailService.sendContactNotification(contactMessage);

    return contactMessageRepository.save(contactMessage);
  }

  public List<ContactMessage> getUnreadMessages() {
    return contactMessageRepository.findByReadFalse();
  }

  public ContactMessage markAsRead(Long id) {
    ContactMessage message = contactMessageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    message.setRead(true);
    return contactMessageRepository.save(message);
  }

  public List<ContactMessage> getAllMessages() {
    return contactMessageRepository.findAll();
  }

  public void deleteMessage(Long id) {
    ContactMessage message = contactMessageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    contactMessageRepository.delete(message);
  }
}
