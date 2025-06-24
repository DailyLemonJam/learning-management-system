package com.leverx.starterfeatureflags.dto;

public record FeatureFlagsResponseDto(

        Integer httpStatus,

        String featureName,

        String type,

        String variation
) {
}
