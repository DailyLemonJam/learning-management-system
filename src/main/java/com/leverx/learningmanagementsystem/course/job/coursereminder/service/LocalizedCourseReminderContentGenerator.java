package com.leverx.learningmanagementsystem.course.job.coursereminder.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.leverx.learningmanagementsystem.student.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LocalizedCourseReminderContentGenerator {
    private final MessageSource messageSource;

    public String generateSubject(Language language, String courseTitle) {
        var locale = getLocaleFromLanguage(language);
        return messageSource.getMessage("email.subject", null, locale) +
                ": " + courseTitle;
    }

    public String generateBody(Language language, Map<String, String> data) {
        var locale = getLocaleFromLanguage(language);
        var greeting = messageSource.getMessage("email.greeting", null, locale);
        var mainContent = messageSource.getMessage("email.mainContent", null, locale);
        var closing = messageSource.getMessage("email.closing", null, locale);
        data.put("greetings", greeting);
        data.put("mainContent", mainContent);
        data.put("closing", closing);
        return generate(data);
    }

    private Locale getLocaleFromLanguage(Language language) {
        return switch (language) {
            case ENGLISH -> Locale.ENGLISH;
            case FRENCH -> Locale.FRENCH;
        };
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
