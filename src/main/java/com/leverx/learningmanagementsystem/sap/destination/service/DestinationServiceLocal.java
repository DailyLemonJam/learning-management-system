package com.leverx.learningmanagementsystem.sap.destination.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class DestinationServiceLocal implements DestinationService {
    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        return null;
    }
}
