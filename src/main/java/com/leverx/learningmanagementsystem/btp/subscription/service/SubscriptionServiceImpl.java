package com.leverx.learningmanagementsystem.btp.subscription.service;

import com.leverx.learningmanagementsystem.btp.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.TenantSchemaProvisioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "https://%s-dev-approuter.cfapps.us10-001.hana.ondemand.com";

    private final TenantSchemaProvisioner tenantSchemaProvisioner;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        tenantSchemaProvisioner.createSchema(tenantId);

        return TENANT_SPECIFIC_URL_TEMPLATE.formatted(request.subscribedSubdomain());
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        tenantSchemaProvisioner.deleteSchema(tenantId);

        return new UnsubscribeResponseDto("Tenant successfully subscribed");
    }
}
