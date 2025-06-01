package com.leverx.learningmanagementsystem.course.job.coursereminder.service;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LocalizedCourseReminderContentGenerator {

    private final String GREETINGS = "greeting";
    private final String MAIN_CONTENT = "mainContent";
    private final String CLOSING = "closing";
    private final String SUBJECT = "subject";
    private final String TEMPLATE_NAME = "course-notification-template";

    private final MessageSource messageSource;
    private final MustacheResourceTemplateLoader mustacheResourceTemplateLoader;

    public String generateSubject(Locale locale, String courseTitle) {
        return "%s: %s".formatted(messageSource.getMessage(SUBJECT, null, locale), courseTitle);
    }

    public String generateBody(Locale locale, Map<String, String> data) {
        var greetings = messageSource.getMessage(GREETINGS, null, locale);
        var mainContent = messageSource.getMessage(MAIN_CONTENT, null, locale);
        var closing = messageSource.getMessage(CLOSING, null, locale);
        data.put(GREETINGS, greetings);
        data.put(MAIN_CONTENT, mainContent);
        data.put(CLOSING, closing);
        return generate(data);
    }

    private String generate(Map<String, String> extendedData) {
        Reader reader;
        try {
            reader = mustacheResourceTemplateLoader.getTemplate(TEMPLATE_NAME);
        } catch (Exception ex) {
            throw new RuntimeException("Can't find template with name %s".formatted(TEMPLATE_NAME));
        }
        var compiler = Mustache.compiler();
        var mustache = compiler.compile(reader);
        return mustache.execute(extendedData);
    }

}
