package com.leverx.learningmanagementsystem.btp.subscription.controller;

import com.leverx.learningmanagementsystem.btp.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeResponseDto;
import com.leverx.learningmanagementsystem.btp.subscription.service.SubscriptionService;
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
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping("/subscribe/{tenantId}")
    @ResponseStatus(OK)
    public String subscribe(@PathVariable String tenantId, @RequestBody SubscribeRequestDto request) {
        return subscriptionService.subscribe(request);
    }

    @DeleteMapping("/subscribe/{tenantId}")
    @ResponseStatus(OK)
    public UnsubscribeResponseDto unsubscribe(@PathVariable String tenantId, @RequestBody UnsubscribeRequestDto request) {
        return subscriptionService.unsubscribe(request);
    }
}
