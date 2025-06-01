package com.leverx.learningmanagementsystem.btp.destination.service;

import com.leverx.learningmanagementsystem.btp.destination.dto.DestinationResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class DestinationServiceLocal implements DestinationService {

    @Override
    public DestinationResponseDto getByName(String name) {
        return null;
    }

}
