package com.leverx.learningmanagementsystem.sap.featureflag.dto;

public record FeatureFlagResponseDto(Integer httpStatus,
                                     String featureName,
                                     String type,
                                     String variation) {
}
