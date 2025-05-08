package com.leverx.learningmanagementsystem.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void send(String to, String subject, String text) {
        log.info("Sending email to {}", to);
        log.info("Subject: {}", subject);
        log.info("Text: {}", text);
    }

}
