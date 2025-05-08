package com.leverx.learningmanagementsystem.email.service;

public interface EmailService {
    void send(String to, String subject, String body);
}
