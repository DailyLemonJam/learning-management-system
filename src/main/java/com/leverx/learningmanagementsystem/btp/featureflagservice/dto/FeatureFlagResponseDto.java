package com.leverx.learningmanagementsystem.btp.featureflagservice.dto;

public record FeatureFlagResponseDto(

        Integer httpStatus,

        String featureName,

        String type,

        String variation
) {
}
