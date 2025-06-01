package com.leverx.learningmanagementsystem.btp.destination.service;

import com.leverx.learningmanagementsystem.btp.destination.dto.DestinationResponseDto;

public interface DestinationService {

    DestinationResponseDto getByName(String name);

}
