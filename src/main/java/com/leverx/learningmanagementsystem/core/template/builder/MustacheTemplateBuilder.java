package com.leverx.learningmanagementsystem.core.template.builder;

import com.samskivert.mustache.Mustache.Compiler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MustacheTemplateBuilder {

    private final MustacheResourceTemplateLoader mustacheResourceTemplateLoader;
    private final Compiler mustacheCompiler;

    public String build(String templateName, Map<String, String> context) {
        try {
            var templateReader = mustacheResourceTemplateLoader.getTemplate(templateName);
            var template = mustacheCompiler.compile(templateReader);
            return template.execute(context);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while reading template.", e);
        }
    }
}
