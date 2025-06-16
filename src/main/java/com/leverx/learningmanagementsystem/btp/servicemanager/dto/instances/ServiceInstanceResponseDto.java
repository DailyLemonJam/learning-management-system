package com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record ServiceInstanceResponseDto(

        @JsonProperty("id") String id,

        @JsonProperty("service_plan_id") String servicePlanId,

        @JsonProperty("platform_id") String platformId,

        @JsonProperty("name") String name,

        @JsonProperty("shared") Boolean shared,

        @JsonProperty("labels") Map<String, List<String>> labels
) {
}
