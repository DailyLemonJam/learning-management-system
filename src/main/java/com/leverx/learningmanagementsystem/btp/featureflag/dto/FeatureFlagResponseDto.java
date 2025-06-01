package com.leverx.learningmanagementsystem.btp.featureflag.dto;

public record FeatureFlagResponseDto(

        Integer httpStatus,

        String featureName,

        String type,

        String variation

) {
}
