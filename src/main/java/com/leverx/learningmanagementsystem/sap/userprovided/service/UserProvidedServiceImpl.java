package com.leverx.learningmanagementsystem.sap.userprovided.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("sap-cloud")
@RequiredArgsConstructor
public class UserProvidedServiceImpl implements UserProvidedService {
    private final SmtpServerProperties smtpServerProperties;

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return smtpServerProperties;
    }

}
