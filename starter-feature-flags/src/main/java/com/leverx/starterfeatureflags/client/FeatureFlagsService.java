package com.leverx.starterfeatureflags.client;

import com.leverx.starterfeatureflags.dto.FeatureFlagsResponseDto;

public interface FeatureFlagsService {

    FeatureFlagsResponseDto getFeatureFlag(String name);
}
