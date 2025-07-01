package com.leverx.learningmanagementsystem.application.service;

import com.leverx.learningmanagementsystem.application.dto.ApplicationInfoResponseDto;
import com.leverx.learningmanagementsystem.btp.xsuaa.model.XsuaaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    private final XsuaaProperties xsuaaProperties;

    @Override
    public ApplicationInfoResponseDto getCredentials() {
        return new ApplicationInfoResponseDto(xsuaaProperties);
    }
}
