package com.leverx.learningmanagementsystem.course.job.coursereminder.service;

import com.leverx.learningmanagementsystem.email.service.EmailService;
import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.leverx.learningmanagementsystem.core.async.config.AsyncConfiguration.ASYNC_EMAIL_SENDER_EXECUTOR;

@Slf4j
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CourseReminderSender {

    private final EmailService emailService;

    @Async(ASYNC_EMAIL_SENDER_EXECUTOR)
    public void sendAsync(String email, String subject, String body, SmtpServerProperties smtpServerProperties) {
        try {
            log.info("Sending email to {}\nSubject: {}\nBody: {}",
                    email, subject, body);
            emailService.send(email, subject, body, smtpServerProperties);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
