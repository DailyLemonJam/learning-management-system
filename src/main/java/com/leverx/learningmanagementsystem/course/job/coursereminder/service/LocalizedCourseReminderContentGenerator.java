package com.leverx.learningmanagementsystem.course.job.coursereminder.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.leverx.learningmanagementsystem.student.model.Language;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Component
public class LocalizedCourseReminderContentGenerator {
    private static final String GREETINGS = "greetings";
    private static final String MAIN_CONTENT_MESSAGE = "mainContentMessage";
    private static final String DO_NOT_FORGET = "doNotForget";

    public String generateSubject(Language language, String courseTitle) {
        return switch (language) {
            case ENGLISH ->  String.format("Course starts: %s!", courseTitle);
            case FRENCH -> String.format("Le cours commence: %s!", courseTitle);
        };
    }

    public String generateBody(Language language, Map<String, String> data) {
        return switch (language) {
            case ENGLISH -> generateEnglishBody(data);
            case FRENCH -> generateFrenchBody(data);
        };
    }

    private String generateEnglishBody(Map<String, String> data) {
        var extendedData = extendWithEnglishLocalization(data);
        return generate(extendedData);
    }

    private String generateFrenchBody(Map<String, String> data) {
        var extendedData = extendWithFrenchLocalization(data);
        return generate(extendedData);
    }

    // TODO: mb combine extendWith...Localization methods into one with switch case to each Key in Map?
    private Map<String, String> extendWithEnglishLocalization(Map<String, String> data) {
        data.put(GREETINGS, "Hello");
        data.put(MAIN_CONTENT_MESSAGE, "There is a course about to start tomorrow:");
        data.put(DO_NOT_FORGET, "Don't forget about it!");
        return data;
    }

    private Map<String, String> extendWithFrenchLocalization(Map<String, String> data) {
        data.put(GREETINGS, "Bonjour");
        data.put(MAIN_CONTENT_MESSAGE, "Il y a un cours qui va commencer demain:");
        data.put(DO_NOT_FORGET, "N'oublie pas!");
        return data;
    }

    private String generate(Map<String, String> extendedData) {
        var mustacheFactory = new DefaultMustacheFactory();
        var mustache = mustacheFactory.compile("templates/course-notification-template.mustache");
        var stringWriter = new StringWriter();
        mustache.execute(stringWriter, extendedData);
        stringWriter.flush();
        return stringWriter.toString();
    }

}
