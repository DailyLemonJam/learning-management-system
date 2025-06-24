package com.leverx.learningmanagementsystem.multitenancy.subscription.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DependenciesResponseDto(

        @JsonProperty("xsappname") String xsAppName
) {
}
