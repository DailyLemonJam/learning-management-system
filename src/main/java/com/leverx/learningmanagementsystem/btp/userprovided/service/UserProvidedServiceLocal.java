package com.leverx.learningmanagementsystem.btp.userprovided.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class UserProvidedServiceLocal implements UserProvidedService {

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return null;
    }

}
