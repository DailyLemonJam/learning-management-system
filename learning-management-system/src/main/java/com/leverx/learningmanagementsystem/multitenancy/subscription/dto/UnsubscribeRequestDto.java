package com.leverx.learningmanagementsystem.multitenancy.subscription.dto;

public record UnsubscribeRequestDto(

        String subscriptionAppId,

        String subscriptionAppName,

        String subscribedTenantId,

        String subscribedSubdomain,

        String globalAccountGUID,

        String subscribedLicenseType
) {
}
