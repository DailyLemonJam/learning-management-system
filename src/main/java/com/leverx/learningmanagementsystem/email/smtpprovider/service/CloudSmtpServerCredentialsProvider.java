package com.leverx.learningmanagementsystem.email.smtpprovider.service;

import com.leverx.learningmanagementsystem.btp.destination.service.DestinationService;
import com.leverx.learningmanagementsystem.btp.featureflag.dto.FeatureFlagResponseDto;
import com.leverx.learningmanagementsystem.btp.featureflag.service.FeatureFlagService;
import com.leverx.learningmanagementsystem.btp.userprovided.service.UserProvidedService;
import com.leverx.learningmanagementsystem.email.smtpprovider.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.email.smtpprovider.exception.FeatureFlagServiceBadResponseException;
import com.leverx.learningmanagementsystem.email.smtpprovider.mapper.DestinationToSmtpServerPropertiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudSmtpServerCredentialsProvider implements SmtpServerCredentialsProvider {

    private static final String DESTINATION_SERVICE_ENABLED = "destination-service-enabled";
    private static final String SMTP_SERVER_DESTINATION_NAME = "SmtpDestination";

    private final FeatureFlagService featureFlagService;
    private final DestinationService destinationService;
    private final UserProvidedService userProvidedService;
    private final DestinationToSmtpServerPropertiesMapper destinationToSmtpServerPropertiesMapper;

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        var featureFlagResponseDto = featureFlagService.getFeatureFlag(DESTINATION_SERVICE_ENABLED);
        boolean isEnabled = convertToBooleanResponse(featureFlagResponseDto);
        if (!isEnabled) {
            return userProvidedService.getSmtpServerProperties();
        }
        var destination = destinationService.getByName(SMTP_SERVER_DESTINATION_NAME);
        return destinationToSmtpServerPropertiesMapper.map(destination);
    }

    private Boolean convertToBooleanResponse(FeatureFlagResponseDto response) {
        if (response == null || response.httpStatus() != HttpStatus.OK.value()) {
            throw new FeatureFlagServiceBadResponseException("Invalid feature flag response");
        }
        return Boolean.parseBoolean(response.variation());
    }

}
