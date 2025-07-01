package com.leverx.learningmanagementsystem.course.job.service;

import com.leverx.learningmanagementsystem.core.template.builder.MustacheTemplateBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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

    private final MustacheTemplateBuilder mustacheTemplateBuilder;
    private final MessageSource messageSource;

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
        return mustacheTemplateBuilder.build(TEMPLATE_NAME, data);
    }
}
