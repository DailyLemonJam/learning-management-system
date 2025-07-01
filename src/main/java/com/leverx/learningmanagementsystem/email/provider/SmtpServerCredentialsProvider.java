package com.leverx.learningmanagementsystem.email.provider;

import com.leverx.learningmanagementsystem.email.model.SmtpServerProperties;

public interface SmtpServerCredentialsProvider {

    SmtpServerProperties getSmtpServerProperties();
}
