package com.leverx.learningmanagementsystem.btp.destinationservice.client;

import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.web.oauth2.dto.OAuth2ClientCredentials;

public interface DestinationServiceClient {

    DestinationResponseDto getByName(String name, OAuth2ClientCredentials clientCredentials);
}
