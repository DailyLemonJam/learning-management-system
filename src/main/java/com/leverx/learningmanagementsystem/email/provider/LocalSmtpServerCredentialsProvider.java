package com.leverx.learningmanagementsystem.email.provider;

import com.leverx.learningmanagementsystem.email.model.SmtpServerProperties;
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
