package com.leverx.learningmanagementsystem.multitenancy.subscription.controller;

import com.leverx.learningmanagementsystem.btp.destinationservice.service.DestinationService;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.DependenciesResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final DestinationService destinationService;

    @PutMapping("/tenants/{tenantId}")
    @ResponseStatus(OK)
    public String subscribe(@PathVariable String tenantId, @RequestBody SubscribeRequestDto request) {
        return subscriptionService.subscribe(request);
    }

    @DeleteMapping("/tenants/{tenantId}")
    @ResponseStatus(OK)
    public UnsubscribeResponseDto unsubscribe(@PathVariable String tenantId, @RequestBody UnsubscribeRequestDto request) {
        return subscriptionService.unsubscribe(request);
    }

    /**
     * @see <a href="https://help.sap.com/docs/btp/sap-business-technology-platform/develop-multitenant-application">getDependencies Response Structure</a>
     * */
    @GetMapping(value = "/tenants/dependencies")
    @ResponseStatus(OK)
    public List<DependenciesResponseDto> getDependencies() {
        String xsAppName = destinationService.getXsAppName();

        log.info("Returning xsAppName: {}", xsAppName);

        var response = new DependenciesResponseDto(xsAppName);

        return List.of(response);
    }
}
