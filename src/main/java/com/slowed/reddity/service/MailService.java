package com.slowed.reddity.service;

import com.slowed.reddity.exceptions.SpringRedditException;
import com.slowed.reddity.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender mailSender;

  @Async
  public void sendMail(NotificationEmail notificationEmail) {

    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom("reddity@email.com");
      messageHelper.setTo(notificationEmail.getRecipient());
      messageHelper.setSubject(notificationEmail.getSubject());
      messageHelper.setText(notificationEmail.getBody());
    };

    try {
      mailSender.send(messagePreparator);
      log.info("activation email sent!");
    } catch (MailException e) {
      log.error("exception occurred when sending mail", e);
      throw new SpringRedditException("exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
    }

  }

}
