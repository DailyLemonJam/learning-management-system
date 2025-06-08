package com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto;

public record UnsubscribeRequestDto(

        String subscriptionAppId,

        String subscriptionAppName,

        String subscribedTenantId,

        String subscribedSubdomain,

        String globalAccountGUID,

        String subscribedLicenseType
) {
}
