package com.leverx.learningmanagementsystem.btp.destinationservice.service;

import com.leverx.learningmanagementsystem.application.config.ApplicationProperties;
import com.leverx.learningmanagementsystem.btp.destinationservice.client.DestinationServiceClient;
import com.leverx.learningmanagementsystem.btp.destinationservice.config.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.web.oauth2.dto.OAuth2ClientCredentials;
import com.leverx.learningmanagementsystem.btp.destinationservice.exception.DestinationNotFoundException;
import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDestinationService implements DestinationService {

    private final DestinationServiceClient destinationServiceClient;
    private final DestinationServiceProperties destinationServiceProperties;
    private final ApplicationProperties applicationProperties;

    @Override
    public DestinationResponseDto getByName(String name) {
        return findDestinationOnSubscriberLevel(name)
                .or(() -> findDestinationOnProviderLevel(name))
                .orElseThrow(() -> new DestinationNotFoundException("Destination is not found [name = %s].".formatted(name)));
    }

    private Optional<DestinationResponseDto> findDestinationOnSubscriberLevel(String name) {
        Supplier<OAuth2ClientCredentials> clientCredentialsSupplier = this::buildClientCredentialsForSubscriber;
        return tryToGetDestination(name, clientCredentialsSupplier);
    }

    private Optional<DestinationResponseDto> findDestinationOnProviderLevel(String name) {
        Supplier<OAuth2ClientCredentials> clientCredentialsSupplier = this::buildClientCredentialsForProvider;
        return tryToGetDestination(name, clientCredentialsSupplier);
    }

    private Optional<DestinationResponseDto> tryToGetDestination(String name, Supplier<OAuth2ClientCredentials> clientCredentialsSupplier) {
        try {
            var clientCredentials = clientCredentialsSupplier.get();
            return Optional.ofNullable(destinationServiceClient.getByName(name, clientCredentials));
        } catch (Exception e) {
            log.error("Failed to get destination [name = %s].".formatted(name), e);
            return Optional.empty();
        }
    }

    private OAuth2ClientCredentials buildClientCredentialsForSubscriber() {
        var subscriberXsuaaUrl = createSubscriberXsuaaUrl();

        return new OAuth2ClientCredentials(
                destinationServiceProperties.getClientId(),
                destinationServiceProperties.getClientSecret(),
                subscriberXsuaaUrl);
    }

    private OAuth2ClientCredentials buildClientCredentialsForProvider() {
        return new OAuth2ClientCredentials(
                destinationServiceProperties.getClientId(),
                destinationServiceProperties.getClientSecret(),
                destinationServiceProperties.getUrl());
    }

    private String createSubscriberXsuaaUrl() {
        var commonUrl = destinationServiceProperties.getUrl();
        return commonUrl.replace(applicationProperties.getProviderSubdomain(), RequestContext.getTenantSubdomain());
    }
}
