package com.leverx.learningmanagementsystem.btp.destinationservice.service;

import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;

public interface DestinationService {

    DestinationResponseDto getByName(String name);

    String getXsAppName();
}
