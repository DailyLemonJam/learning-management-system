package com.leverx.learningmanagementsystem.sap.userprovided.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;

public interface UserProvidedService {
    SmtpServerProperties getSmtpServerProperties();
}
