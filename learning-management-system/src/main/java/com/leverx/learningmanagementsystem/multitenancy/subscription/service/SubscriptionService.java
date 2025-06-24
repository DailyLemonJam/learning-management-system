package com.leverx.learningmanagementsystem.multitenancy.subscription.service;

import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;

public interface SubscriptionService {

    String subscribe(SubscribeRequestDto request);

    UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request);
}
