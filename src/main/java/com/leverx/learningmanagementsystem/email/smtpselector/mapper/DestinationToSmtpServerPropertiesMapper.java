package com.leverx.learningmanagementsystem.email.smtpselector.mapper;

import com.leverx.learningmanagementsystem.btp.destination.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import org.springframework.stereotype.Component;

@Component
public class DestinationToSmtpServerPropertiesMapper {

    public SmtpServerProperties map(DestinationResponseDto destination) {
        return SmtpServerProperties.builder()
                .user(destination.destinationConfiguration().get("mail.user"))
                .password(destination.destinationConfiguration().get("mail.password"))
                .from(destination.destinationConfiguration().get("mail.smtp.from"))
                .host(destination.destinationConfiguration().get("mail.smtp.host"))
                .port(destination.destinationConfiguration().get("mail.smtp.port"))
                .protocol(destination.destinationConfiguration().get("mail.transport.protocol"))
                .build();
    }

}
