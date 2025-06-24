package com.leverx.learningmanagementsystem.email.smtpprovider.service;

import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalSmtpServerCredentialsProvider implements SmtpServerCredentialsProvider {

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return null;
    }
}
