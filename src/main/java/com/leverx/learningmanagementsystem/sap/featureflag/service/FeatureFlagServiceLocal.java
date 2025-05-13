package com.leverx.learningmanagementsystem.sap.featureflag.service;

import com.leverx.learningmanagementsystem.sap.featureflag.dto.FeatureFlagResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class FeatureFlagServiceLocal implements FeatureFlagService {
    @Override
    public FeatureFlagResponseDto getFeatureFlag(String featureFlagName) {
        return null;
    }
}
