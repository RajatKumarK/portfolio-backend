package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.ContactMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Value("${app.admin.email}")
  private String adminEmail;

  public void sendContactNotification(ContactMessage contactMessage) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(fromEmail);
      helper.setTo(adminEmail);
      helper.setSubject("New Contact Form Submission: " + contactMessage.getSubject());

      String emailContent = String.format(
          "New message received from your portfolio website:\n\n" +
              "From: %s (%s)\n\n" +
              "Message:\n%s",
          contactMessage.getName(),
          contactMessage.getEmail(),
          contactMessage.getMessage()
      );

      helper.setText(emailContent);

      mailSender.send(message);
    } catch (MessagingException e) {
      // Log the error but don't throw exception to prevent API failure
      // You might want to use a proper logging framework like SLF4J
      System.err.println("Failed to send email notification: " + e.getMessage());
    }
  }
}
