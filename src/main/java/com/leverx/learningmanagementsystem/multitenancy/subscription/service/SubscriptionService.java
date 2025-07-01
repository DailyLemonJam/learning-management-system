package com.leverx.learningmanagementsystem.multitenancy.subscription.service;

import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.DependenciesResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;

import java.util.List;

public interface SubscriptionService {

    String subscribe(SubscribeRequestDto request);

    UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request);

    List<DependenciesResponseDto> getDependencies();
}
