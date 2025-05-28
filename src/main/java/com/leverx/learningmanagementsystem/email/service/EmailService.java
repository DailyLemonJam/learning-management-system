package com.leverx.learningmanagementsystem.email.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;

public interface EmailService {
    void send(String to, String subject, String body, SmtpServerProperties smtpServerProperties);
}
