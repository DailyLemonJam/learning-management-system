package com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BindingCredentials(

        @JsonProperty("url") String url,

        @JsonProperty("user") String user,

        @JsonProperty("password") String password,

        @JsonProperty("driver") String driver
) {
}
