package com.leverx.learningmanagementsystem.sap.destination.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.sap.destination.config.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.sap.destination.dto.AccessTokenResponseDto;
import com.leverx.learningmanagementsystem.sap.destination.dto.SmtpDestinationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@Profile("sap-cloud")
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private final DestinationServiceProperties destinationServiceProperties;

    private static final String SMTP_DESTINATION = "SmtpDestination";
    private static final String SMTP_API = "/destination-configuration/v1/destinations/";
    private static final String OAUTH_API = "/oauth/token";

    private static final String EMPTY = "";
    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BEARER = "Bearer ";

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        var response = getSmtpDestination();

        // SmtpDestinationResponseDto -> SmtpServerProperties
        return new SmtpServerProperties(response.user(), response.password(),
                response.from(), response.host(), response.port(), response.protocol());
    }

    private SmtpDestinationResponseDto getSmtpDestination() {
        var route = createSmtpDestinationRoute();
        var accessToken = getAccessToken();
        var authHeader = BEARER + accessToken;

        return RestClient.create().get()
                .uri(route)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(SmtpDestinationResponseDto.class);
    }

    private String createSmtpDestinationRoute() {
        var builder = new StringBuilder();
        builder.append(destinationServiceProperties.getUri());
        builder.append(SMTP_API);
        builder.append(SMTP_DESTINATION);
        return builder.toString();
    }

    private String getAccessToken() {
        var tokenRoute = createAccessTokenRoute();
        var credentials = Map.of(
                "grant_type", "client_credentials",
                "client_id", destinationServiceProperties.getClientId(),
                "client_secret", destinationServiceProperties.getClientSecret()
        );
        var response = RestClient.create().post()
                .uri(tokenRoute)
                .body(credentials)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(AccessTokenResponseDto.class);
        return response != null ? response.accessToken() : EMPTY;
    }

    private String createAccessTokenRoute() {
        var builder = new StringBuilder();
        builder.append(destinationServiceProperties.getUrl());
        builder.append(OAUTH_API);
        return builder.toString();
    }

}
