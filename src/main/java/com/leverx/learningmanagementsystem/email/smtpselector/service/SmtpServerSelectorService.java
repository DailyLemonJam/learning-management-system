package com.leverx.learningmanagementsystem.email.smtpselector.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;

public interface SmtpServerSelectorService {

    SmtpServerProperties getSmtpServerProperties();

}
