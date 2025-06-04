package com.leverx.learningmanagementsystem.btp.userprovided.service;

import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;

public interface UserProvidedService {

    SmtpServerProperties getSmtpServerProperties();
}
