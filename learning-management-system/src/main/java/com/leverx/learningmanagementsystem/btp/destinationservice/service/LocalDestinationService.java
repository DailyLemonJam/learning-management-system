package com.leverx.learningmanagementsystem.btp.destinationservice.service;

import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalDestinationService implements DestinationService {

    @Override
    public DestinationResponseDto getByName(String name) {
        return null;
    }

    @Override
    public String getXsAppName() {
        return "localTestXsAppname";
    }
}
