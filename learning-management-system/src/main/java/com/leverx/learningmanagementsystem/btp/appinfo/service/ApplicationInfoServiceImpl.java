package com.leverx.learningmanagementsystem.btp.appinfo.service;

import com.leverx.learningmanagementsystem.btp.xsuaa.model.XsuaaProperties;
import com.leverx.learningmanagementsystem.btp.appinfo.dto.ApplicationInfoResponseDto;
import com.leverx.learningmanagementsystem.btp.appinfo.dto.XsuaaCredentialsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    private final XsuaaProperties xsuaaProperties;

    @Override
    public ApplicationInfoResponseDto getCredentials() {
        var xsuaaCredentials = new XsuaaCredentialsResponseDto(xsuaaProperties.getTokenUrl(),
                xsuaaProperties.getClientId(), xsuaaProperties.getClientSecret());

        return new ApplicationInfoResponseDto(xsuaaCredentials);
    }
}
