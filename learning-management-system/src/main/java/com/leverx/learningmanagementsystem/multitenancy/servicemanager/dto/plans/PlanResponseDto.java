package com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.plans;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlanResponseDto(

        @JsonProperty("id") String id
) {
}
