package com.leverx.learningmanagementsystem.btp.destination.dto;

import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://help.sap.com/docs/connectivity/sap-btp-connectivity-cf/find-destination-response-structure">Destination Response Structure</a>
 * */
public record DestinationResponseDto(
        Map<String, String> owner,
        Map<String, String> destinationConfiguration,
        List<Map<String, Object>> authTokens,
        List<Map<String, Object>> certificates
) {
}
