package com.leverx.learningmanagementsystem.btp.featureflagservice.service;

import com.leverx.learningmanagementsystem.btp.featureflagservice.dto.FeatureFlagResponseDto;

public interface FeatureFlagService {

    FeatureFlagResponseDto getByName(String name);
}
