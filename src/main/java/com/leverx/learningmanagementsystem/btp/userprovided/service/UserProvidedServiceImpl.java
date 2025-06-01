package com.leverx.learningmanagementsystem.btp.userprovided.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class UserProvidedServiceImpl implements UserProvidedService {

    private final SmtpServerProperties smtpServerProperties;

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return smtpServerProperties;
    }

}
