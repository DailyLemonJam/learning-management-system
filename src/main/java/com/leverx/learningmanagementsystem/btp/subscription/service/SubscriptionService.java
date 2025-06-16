package com.leverx.learningmanagementsystem.btp.subscription.service;

import com.leverx.learningmanagementsystem.btp.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeResponseDto;

public interface SubscriptionService {

    String subscribe(SubscribeRequestDto request);

    UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request);
}
