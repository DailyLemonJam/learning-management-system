package com.leverx.learningmanagementsystem.btp.featureflag.service;

import com.leverx.learningmanagementsystem.btp.featureflag.dto.FeatureFlagResponseDto;

public interface FeatureFlagService {
    FeatureFlagResponseDto getFeatureFlag(String featureFlagName);
}
