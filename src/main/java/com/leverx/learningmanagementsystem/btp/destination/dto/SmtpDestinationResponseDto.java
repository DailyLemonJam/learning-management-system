package com.leverx.learningmanagementsystem.btp.destination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SmtpDestinationResponseDto(
        @JsonProperty("mail.user") String user,
        @JsonProperty("mail.password") String password,
        @JsonProperty("mail.smtp.from") String from,
        @JsonProperty("mail.smtp.host") String host,
        @JsonProperty("mail.smtp.port") String port,
        @JsonProperty("mail.transport.protocol") String protocol
) {
}
