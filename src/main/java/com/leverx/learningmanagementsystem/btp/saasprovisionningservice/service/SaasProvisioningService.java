package com.leverx.learningmanagementsystem.btp.saasprovisionningservice.service;

import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.saasprovisionningservice.dto.UnsubscribeResponseDto;

public interface SaasProvisioningService {

    String subscribe(SubscribeRequestDto request);

    UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request);
}
