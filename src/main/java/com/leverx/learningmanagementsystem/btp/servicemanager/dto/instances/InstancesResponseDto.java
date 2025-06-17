package com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InstancesResponseDto(

        @JsonProperty("token") String token,

        @JsonProperty("num_items") Integer numItems,

        @JsonProperty("items") List<InstanceResponseDto> instances
) {
}
