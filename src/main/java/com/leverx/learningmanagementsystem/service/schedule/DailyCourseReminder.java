package com.leverx.learningmanagementsystem.service.schedule;

import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyCourseReminder {
    private final CourseRepository courseRepository;
    //private final EmailService emailService;

    @Scheduled(cron = "0 0 20 * * ?")
    public void sendTomorrowCoursesReminder() {
        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var courses = courseRepository.findAllByCourseSettings_StartDate(tomorrow);
        for (var course : courses) {
            var enrolledStudents = course.getStudents();
            for (var student : enrolledStudents) {
                var email = student.getEmail();
                var title = "Course start: " + course.getTitle();
                var body = "Hello, " + student.getFirstName() +
                        ", there is a course about to start tomorrow! "
                        + course.getCourseSettings().getStartDate();
                try {
                    //emailService.sendEmail(email, title, body);
                    System.out.println("Sending email: " + email);
                    System.out.println("Title: " + title);
                    System.out.println("Body: " + body);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

}
