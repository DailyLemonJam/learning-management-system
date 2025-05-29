package com.leverx.learningmanagementsystem.course.job.coursereminder;

import com.leverx.learningmanagementsystem.course.job.coursereminder.service.LocalizedCourseReminderContentGenerator;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.email.smtpselector.service.SmtpServerSelectorService;
import com.leverx.learningmanagementsystem.email.service.EmailService;
import com.leverx.learningmanagementsystem.student.model.Language;
import com.leverx.learningmanagementsystem.student.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("cloud")
public class DailyCourseReminderJob {
    private final CourseRepository courseRepository;
    private final SmtpServerSelectorService smtpServerSelectorService;
    private final EmailService emailService;
    private final LocalizedCourseReminderContentGenerator localizedCourseReminderContentGenerator;

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

        var studentLanguage = student.getLanguage();
        var subject = generateSubject(studentLanguage, course.getTitle());
        var body = generateBody(studentLanguage, student.getFirstName(), course.getTitle());

        tryToSendEmail(email, subject, body, smtpServerProperties);
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

    private String generateSubject(Language language, String courseTitle) {
        return localizedCourseReminderContentGenerator.generateSubject(language, courseTitle);
    }

    private String generateBody(Language language, String studentName, String courseTitle) {
        var data = generateData(studentName, courseTitle);
        return localizedCourseReminderContentGenerator.generateBody(language, data);
    }

    private Map<String, String> generateData(String studentName, String courseTitle) {
        var data = new HashMap<String, String>();
        data.put("studentName", studentName);
        data.put("courseTitle", courseTitle);
        return data;
    }

}
