package com.leverx.learningmanagementsystem.email.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String to, String subject, String body, SmtpServerProperties smtpServerProperties) throws MessagingException;
}
