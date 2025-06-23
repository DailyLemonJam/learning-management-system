package com.leverx.learningmanagementsystem.course.job.coursereminder.service;

import com.leverx.learningmanagementsystem.email.service.EmailService;
import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.leverx.learningmanagementsystem.core.async.config.AsyncConfiguration.ASYNC_EMAIL_SENDER_EXECUTOR;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CourseReminderSender {

    private final EmailService emailService;

    @Async(ASYNC_EMAIL_SENDER_EXECUTOR)
    public void sendAsync(String email, String subject, String body, SmtpServerProperties smtpServerProperties) {
        trySendAsync(email, subject, body, smtpServerProperties);
    }

    private void trySendAsync(String email, String subject, String body, SmtpServerProperties smtpServerProperties) {
        try {
            emailService.send(email, subject, body, smtpServerProperties);
            log.info("Sending email to {}\nSubject: {}\nBody: {}\nCurrent notifier thread: {}",
                    email, subject, body, Thread.currentThread());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
