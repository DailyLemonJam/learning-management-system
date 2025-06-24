package com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BindingsResponseDto(

        @JsonProperty("token") String token,

        @JsonProperty("num_items") Integer numItems,

        @JsonProperty("items") List<BindingResponseDto> items
) {
}
