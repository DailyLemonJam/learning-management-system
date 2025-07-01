package com.leverx.learningmanagementsystem.btp.servicemanager.dto.plans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlansResponseDto(

        @JsonProperty("items") List<PlanResponseDto> items
) {
}
