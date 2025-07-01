package com.leverx.learningmanagementsystem.email.mapper;

import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.email.model.SmtpServerProperties;
import org.springframework.stereotype.Component;

@Component
public class DestinationToSmtpServerPropertiesMapper {

    private static final String MAIL_USER = "mail.user";
    private static final String MAIL_PASSWORD = "mail.password";
    private static final String MAIL_SMTP_FROM = "mail.smtp.from";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

    public SmtpServerProperties map(DestinationResponseDto destination) {
        return SmtpServerProperties.builder()
                .user(destination.destinationConfiguration().get(MAIL_USER))
                .password(destination.destinationConfiguration().get(MAIL_PASSWORD))
                .from(destination.destinationConfiguration().get(MAIL_SMTP_FROM))
                .host(destination.destinationConfiguration().get(MAIL_SMTP_HOST))
                .port(destination.destinationConfiguration().get(MAIL_SMTP_PORT))
                .protocol(destination.destinationConfiguration().get(MAIL_TRANSPORT_PROTOCOL))
                .build();
    }
}
