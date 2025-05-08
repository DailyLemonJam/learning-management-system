package com.leverx.learningmanagementsystem.email.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
