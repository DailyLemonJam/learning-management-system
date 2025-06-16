package com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record CreateBindingRequestDto(

        @JsonProperty("name") String bindingName,

        @JsonProperty("service_instance_id") String serviceInstanceId,

        @JsonProperty("parameters") Map<String, String> parameters,

        @JsonProperty("bind_resource") Map<String, String> bindResource,

        @JsonProperty("labels") Map<String, List<String>> labels
) {
}
