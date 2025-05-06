package com.leverx.learningmanagementsystem.service.schedule;

import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyCourseReminderJob {
    private final CourseRepository courseRepository;

    private static final String SUBJECT = "Course starts: %s!";
    private static final String BODY = "Hello, %s, there is a course about to start tomorrow!";

    @Scheduled(cron = "0 0 20 * * ?")
    public void sendTomorrowCoursesReminder() {
        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var afterTomorrow = LocalDate.now().plusDays(2).atStartOfDay();
        var courses = courseRepository.findAllByCourseSettings_StartDateBetween(tomorrow, afterTomorrow);
        courses.forEach(this::sendEmailNotifications);
    }

    public void sendEmailNotifications(Course course) {
        var students = course.getStudents();
        students.forEach(student -> {
            var email = student.getEmail();
            tryToSendEmail(email,
                    String.format(SUBJECT, course.getTitle()),
                    String.format(BODY, student.getFirstName()));
        });
    }

    private void tryToSendEmail(String email, String subject, String body) {
        try {
            log.info("Sending email to {}", email);
            log.info("Subject: {}", subject);
            log.info("Body: {}", body);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
