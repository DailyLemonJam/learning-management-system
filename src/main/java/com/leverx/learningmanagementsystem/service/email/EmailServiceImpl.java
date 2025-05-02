package com.leverx.learningmanagementsystem.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class EmailServiceImpl implements EmailService {
    //private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        log.info("Sending email to {}", to);
        log.info("Subject: {}", subject);
        log.info("Text: {}", text);
//        var message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
    }

}
