package com.leverx.learningmanagementsystem.btp.appinfo.controller;

import com.leverx.learningmanagementsystem.btp.appinfo.dto.ApplicationInfoResponseDto;
import com.leverx.learningmanagementsystem.btp.appinfo.service.ApplicationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApplicationInfoController {

    private final ApplicationInfoService applicationInfoService;

    @GetMapping("/application-info")
    @ResponseStatus(OK)
    public ApplicationInfoResponseDto getCredentials() {
        return applicationInfoService.getCredentials();
    }
}
