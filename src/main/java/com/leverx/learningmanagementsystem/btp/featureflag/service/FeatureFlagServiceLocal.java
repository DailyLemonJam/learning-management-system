package com.leverx.learningmanagementsystem.btp.featureflag.service;

import com.leverx.learningmanagementsystem.btp.featureflag.dto.FeatureFlagResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class FeatureFlagServiceLocal implements FeatureFlagService {
    @Override
    public FeatureFlagResponseDto getFeatureFlag(String name) {
        return null;
    }
}
