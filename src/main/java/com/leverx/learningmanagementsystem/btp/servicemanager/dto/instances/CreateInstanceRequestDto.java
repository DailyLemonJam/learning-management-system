package com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record CreateInstanceRequestDto(

        @JsonProperty("name") String name,

        @JsonProperty("service_plan_id") String servicePlanId,

        @JsonProperty("parameters") Map<String, String> parameters,

        @JsonProperty("labels") Map<String, List<String>> labels
) {
}
