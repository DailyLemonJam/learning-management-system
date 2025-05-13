package com.leverx.learningmanagementsystem.email.smtpselector.service;

import com.leverx.learningmanagementsystem.email.smtpselector.config.SmtpServerProperties;
import com.leverx.learningmanagementsystem.email.smtpselector.exception.FeatureFlagServiceBadResponseException;
import com.leverx.learningmanagementsystem.sap.destination.service.DestinationService;
import com.leverx.learningmanagementsystem.sap.featureflag.dto.FeatureFlagResponseDto;
import com.leverx.learningmanagementsystem.sap.featureflag.service.FeatureFlagService;
import com.leverx.learningmanagementsystem.sap.featureflag.service.FeatureFlagServiceImpl;
import com.leverx.learningmanagementsystem.sap.userprovided.service.UserProvidedService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Profile("sap-cloud")
@RequiredArgsConstructor
public class SmtpServerSelectorServiceImpl implements SmtpServerSelectorService {
    private final FeatureFlagService featureFlagService;
    private final DestinationService destinationService;
    private final UserProvidedService userProvidedService;

    private static final String DESTINATION_SERVICE_ENABLED = "destination-service-enabled";

    @Override
    public SmtpServerProperties getSmtpServerProperties() {
        var featureFlagResponseDto = featureFlagService.getFeatureFlag(DESTINATION_SERVICE_ENABLED);
        boolean isEnabled = convertToBooleanResponse(featureFlagResponseDto);
        return isEnabled ? destinationService.getSmtpServerProperties()
                : userProvidedService.getSmtpServerProperties();
    }

    private Boolean convertToBooleanResponse(FeatureFlagResponseDto response) {
        if (response == null || response.httpStatus() != HttpStatus.OK.value()) {
            throw new FeatureFlagServiceBadResponseException("Invalid feature flag response");
        }
        return Boolean.parseBoolean(response.variation());
    }

}
