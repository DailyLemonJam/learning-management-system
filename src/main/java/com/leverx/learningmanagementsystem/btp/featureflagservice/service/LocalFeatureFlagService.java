package com.leverx.learningmanagementsystem.btp.featureflagservice.service;

import com.leverx.learningmanagementsystem.btp.featureflagservice.dto.FeatureFlagResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalFeatureFlagService implements FeatureFlagService {

    @Override
    public FeatureFlagResponseDto getByName(String name) {
        return null;
    }
}
