package com.leverx.learningmanagementsystem.btp.saasprovisionningservice.controller;

import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SaasProvisioningServiceController {

    // TODO: implement service layer; use TENANT_HOST_PATTERN from mta.yaml?

    // TODO: replace hard-coded values with env variables in whole application

    @PutMapping("/subscribe/{tenantId}")
    @ResponseStatus(OK)
    public String subscribe(@PathVariable String tenantId, @RequestBody SubscribeRequestDto request) {
        return "https://%s-dev-approuter.cfapps.us10-001.hana.ondemand.com".formatted(request.subscribedSubdomain());
    }

    @DeleteMapping("/subscribe/{tenantId}")
    @ResponseStatus(OK)
    public UnsubscribeResponseDto subscribe(@PathVariable String tenantId, @RequestBody UnsubscribeRequestDto request) {
        return new UnsubscribeResponseDto("Tenant successfully subscribed");
    }
}
