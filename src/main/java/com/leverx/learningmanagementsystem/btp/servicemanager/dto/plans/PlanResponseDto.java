package com.leverx.learningmanagementsystem.btp.servicemanager.dto.plans;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlanResponseDto(

        @JsonProperty("id") String id
) {
}
