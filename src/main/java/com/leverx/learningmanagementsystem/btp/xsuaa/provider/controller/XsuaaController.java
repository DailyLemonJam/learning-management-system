package com.leverx.learningmanagementsystem.btp.xsuaa.provider.controller;

import com.leverx.learningmanagementsystem.btp.xsuaa.provider.dto.XsuaaCredentialsResponseDto;
import com.leverx.learningmanagementsystem.btp.xsuaa.provider.service.XsuaaCredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class XsuaaController {

    private final XsuaaCredentialsService xsuaaCredentialsService;

    // TODO: check for Admin role here as well?

    @GetMapping("/application-info")
    @ResponseStatus(OK)
    public XsuaaCredentialsResponseDto getCredentials() {
        return xsuaaCredentialsService.getCredentials();
    }
}
