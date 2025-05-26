package com.leverx.learningmanagementsystem.email.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void send(String to, String subject, String text, SmtpServerProperties smtpServerProperties) {
        var sender = getSender(smtpServerProperties);
        var message = createSimpleMailMessage(to, smtpServerProperties.getFrom(), subject, text);
        sender.send(message);
        log.info("Sending email to {}", to);
        log.info("Subject: {}", subject);
        log.info("Text: {}", text);
    }

    private JavaMailSender getSender(SmtpServerProperties smtpServerProperties) {
        var sender = new JavaMailSenderImpl();
        sender.setUsername(smtpServerProperties.getUser());
        sender.setPassword(smtpServerProperties.getPassword());
        sender.setHost(smtpServerProperties.getHost());
        sender.setPort(Integer.parseInt(smtpServerProperties.getPort()));
        sender.setProtocol(smtpServerProperties.getProtocol());
        sender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        sender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        return sender;
    }

    // TODO: change to MIME
    private SimpleMailMessage createSimpleMailMessage(String to, String from, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

}
