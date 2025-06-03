package com.leverx.learningmanagementsystem.email.smtpprovider.service;

import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;

public interface SmtpServerCredentialsProvider {

    SmtpServerProperties getSmtpServerProperties();

}
