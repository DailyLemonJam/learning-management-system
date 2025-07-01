package com.leverx.learningmanagementsystem.application.dto;

import com.leverx.learningmanagementsystem.btp.xsuaa.model.XsuaaProperties;

public record ApplicationInfoResponseDto(

        XsuaaProperties xsuaaProperties
) {
}
