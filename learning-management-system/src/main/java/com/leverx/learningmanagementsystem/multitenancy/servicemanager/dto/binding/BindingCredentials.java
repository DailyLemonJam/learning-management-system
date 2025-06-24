package com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BindingCredentials(

        @JsonProperty("url") String url,

        @JsonProperty("user") String user,

        @JsonProperty("password") String password,

        @JsonProperty("driver") String driver
) {
}
