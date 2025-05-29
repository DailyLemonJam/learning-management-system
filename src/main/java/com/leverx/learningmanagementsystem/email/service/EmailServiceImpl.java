package com.leverx.learningmanagementsystem.email.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void send(String to, String subject, String text, SmtpServerProperties smtpServerProperties) throws MessagingException {
        var sender = getSender(smtpServerProperties);
        var mimeMessage = sender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        sender.send(mimeMessage);

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

}
