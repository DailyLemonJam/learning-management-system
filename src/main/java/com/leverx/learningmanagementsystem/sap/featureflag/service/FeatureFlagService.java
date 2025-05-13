package com.leverx.learningmanagementsystem.sap.featureflag.service;

import com.leverx.learningmanagementsystem.sap.featureflag.dto.FeatureFlagResponseDto;

public interface FeatureFlagService {
    FeatureFlagResponseDto getFeatureFlag(String featureFlagName);
}
