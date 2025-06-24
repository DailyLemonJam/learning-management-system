package com.leverx.learningmanagementsystem.course.job.coursereminder;

import com.leverx.learningmanagementsystem.course.job.coursereminder.service.CourseReminderSender;
import com.leverx.learningmanagementsystem.course.job.coursereminder.service.LocalizedCourseReminderContentGenerator;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.email.smtpprovider.service.SmtpServerCredentialsProvider;
import com.leverx.learningmanagementsystem.student.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("cloud")
public class DailyCourseReminderJob {

    private final CourseRepository courseRepository;
    private final SmtpServerCredentialsProvider smtpServerCredentialsProvider;
    private final LocalizedCourseReminderContentGenerator localizedCourseReminderContentGenerator;
    private final CourseReminderSender courseReminderSender;

    @Scheduled(cron = "0 0 20 * * ?")
    public void sendTomorrowCoursesReminder() {
        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var afterTomorrow = LocalDate.now().plusDays(2).atStartOfDay();
        var courses = courseRepository.findAllByCourseSettings_StartDateBetween(tomorrow, afterTomorrow);

        var smtpServerProperties = smtpServerCredentialsProvider.getSmtpServerProperties();

        courses.forEach(course -> sendEmailNotifications(course, smtpServerProperties));
    }

    public void sendEmailNotifications(Course course, SmtpServerProperties smtpServerProperties) {
        var students = course.getStudents();
        students.forEach(student -> sendEmailToStudent(student, course, smtpServerProperties));
    }

    private void sendEmailToStudent(Student student, Course course, SmtpServerProperties smtpServerProperties) {
        var email = student.getEmail();

        var studentLocale = student.getLocale();
        var subject = generateSubject(studentLocale, course.getTitle());
        var body = generateBody(studentLocale, student.getFirstName(), course.getTitle());

        courseReminderSender.sendAsync(email, subject, body, smtpServerProperties);
    }

    private String generateSubject(Locale locale, String courseTitle) {
        return localizedCourseReminderContentGenerator.generateSubject(locale, courseTitle);
    }

    private String generateBody(Locale locale, String studentName, String courseTitle) {
        var data = generateData(studentName, courseTitle);
        return localizedCourseReminderContentGenerator.generateBody(locale, data);
    }

    private Map<String, String> generateData(String studentName, String courseTitle) {
        var data = new HashMap<String, String>();
        data.put("studentName", studentName);
        data.put("courseTitle", courseTitle);
        return data;
    }
}
