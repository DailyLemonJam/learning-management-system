package com.leverx.learningmanagementsystem.btp.saasprovisionningservice.service;

import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaasProvisioningServiceImpl implements SaasProvisioningService {

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        return "https://%s-dev-approuter.cfapps.us10-001.hana.ondemand.com".formatted(request.subscribedSubdomain());
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        return new UnsubscribeResponseDto("Tenant successfully subscribed");
    }
}
