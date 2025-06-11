package com.leverx.learningmanagementsystem.btp.xsuaa.provider.service;

import com.leverx.learningmanagementsystem.btp.xsuaa.provider.config.XsuaaProperties;
import com.leverx.learningmanagementsystem.btp.xsuaa.provider.dto.XsuaaCredentialsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XsuaaCredentialsServiceImpl implements XsuaaCredentialsService {

    private final XsuaaProperties xsuaaProperties;

    @Override
    public XsuaaCredentialsResponseDto getCredentials() {
        return new XsuaaCredentialsResponseDto(xsuaaProperties.getTokenUrl(),
                xsuaaProperties.getClientId(), xsuaaProperties.getClientSecret());
    }
}
