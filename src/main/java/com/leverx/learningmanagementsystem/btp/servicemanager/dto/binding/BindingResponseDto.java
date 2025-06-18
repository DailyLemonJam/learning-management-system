package com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record BindingResponseDto(

        @JsonProperty("id") String id,

        @JsonProperty("service_instance_id") String serviceInstanceId,

        @JsonProperty("credentials") Map<String, String> credentials,

        @JsonProperty("name") String name,

        @JsonProperty("labels") Map<String, List<String>> labels
) {
}
