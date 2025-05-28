package com.leverx.learningmanagementsystem.course.job;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.email.smtpselector.service.SmtpServerSelectorService;
import com.leverx.learningmanagementsystem.email.service.EmailService;
import com.leverx.learningmanagementsystem.student.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("cloud")
public class DailyCourseReminderJob {
    private final CourseRepository courseRepository;
    private final SmtpServerSelectorService smtpServerSelectorService;
    private final EmailService emailService;

    private static final String SUBJECT = "Course starts: %s!";
    private static final String BODY = "Hello, %s, there is a course about to start tomorrow!";

    @Scheduled(cron = "0 0 20 * * ?")
    public void sendTomorrowCoursesReminder() {
        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var afterTomorrow = LocalDate.now().plusDays(2).atStartOfDay();
        var courses = courseRepository.findAllByCourseSettings_StartDateBetween(tomorrow, afterTomorrow);

        var smtpServerProperties = smtpServerSelectorService.getSmtpServerProperties();

        courses.forEach(course -> sendEmailNotifications(course, smtpServerProperties));
    }

    public void sendEmailNotifications(Course course, SmtpServerProperties smtpServerProperties) {
        var students = course.getStudents();
        students.forEach(student -> sendEmailToStudent(student, course, smtpServerProperties));
    }

    private void sendEmailToStudent(Student student, Course course, SmtpServerProperties smtpServerProperties) {
        var email = student.getEmail();
        tryToSendEmail(email,
                String.format(SUBJECT, course.getTitle()),
                String.format(BODY, student.getFirstName()),
                smtpServerProperties);
    }

    private void tryToSendEmail(String email, String subject, String body, SmtpServerProperties smtpServerProperties) {
        try {
            emailService.send(email, subject, body, smtpServerProperties);
            log.info("Sending email to {}", email);
            log.info("Subject: {}", subject);
            log.info("Body: {}", body);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
