package com.leverx.learningmanagementsystem.btp.destination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DestinationResponseDto(
    @JsonProperty("destinationConfiguration.mail.user") String user,
    @JsonProperty("destinationConfiguration.mail.password") String password,
    @JsonProperty("destinationConfiguration.mail.smtp.from") String from,
    @JsonProperty("destinationConfiguration.mail.smtp.host") String host,
    @JsonProperty("destinationConfiguration.mail.smtp.port") String port,
    @JsonProperty("destinationConfiguration.mail.smtp.protocol") String protocol
) {
}
