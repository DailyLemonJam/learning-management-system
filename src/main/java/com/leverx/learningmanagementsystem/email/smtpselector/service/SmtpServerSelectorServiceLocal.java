package com.leverx.learningmanagementsystem.email.smtpselector.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class SmtpServerSelectorServiceLocal implements SmtpServerSelectorService {

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return new SmtpServerProperties();
    }

}
