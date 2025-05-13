package com.leverx.learningmanagementsystem.sap.destination.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;

public interface DestinationService {
    SmtpServerProperties getSmtpServerProperties();
}
