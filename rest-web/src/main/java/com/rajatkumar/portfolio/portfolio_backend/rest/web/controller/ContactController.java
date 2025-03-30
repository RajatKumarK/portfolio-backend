package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.ContactService;
import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/contact")
public class ContactController {
  @Autowired
  private ContactService contactService;

  @PostMapping
  public ResponseEntity<ContactMessage> submitContactForm(@Valid @RequestBody ContactMessage contactMessage) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(contactService.saveContactMessage(contactMessage));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<ContactMessage> getAllMessages() {
    return contactService.getAllMessages();
  }

  @GetMapping("/unread")
  @PreAuthorize("hasRole('ADMIN')")
  public List<ContactMessage> getUnreadMessages() {
    return contactService.getUnreadMessages();
  }

  @PutMapping("/{id}/read")
  @PreAuthorize("hasRole('ADMIN')")
  public ContactMessage markAsRead(@PathVariable Long id) {
    return contactService.markAsRead(id);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
    contactService.deleteMessage(id);
    return ResponseEntity.ok().build();
  }
}
