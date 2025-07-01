package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.auth.ServiceManagerAccessTokenProvider;
import com.leverx.learningmanagementsystem.btp.servicemanager.configuration.ServiceManagerProperties;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingsResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstancesResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.plans.PlansResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.isNull;
import static org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class ServiceManagerImpl implements ServiceManager {

    private static final String INSTANCES_ENDPOINT_VERSION = "v1";
    private static final String INSTANCES_ENDPOINT_NAME = "service_instances";
    private static final String BINDINGS_ENDPOINT_VERSION = "v1";
    private static final String BINDINGS_ENDPOINT_NAME = "service_bindings";
    private static final String PLANS_ENDPOINT_VERSION = "v1";
    private static final String PLANS_ENDPOINT_NAME = "service_plans";
    private static final String SCHEMA_INSTANCE_NAME = "schema";

    private final ServiceManagerProperties serviceManagerProperties;
    private final ServiceManagerAccessTokenProvider serviceManagerAccessTokenProvider;
    private final RestClient restClient;

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public InstanceResponseDto createInstance(CreateInstanceByPlanIdRequestDto request) {
        return tryCreateInstance(request);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public InstanceResponseDto getInstanceByTenantId(String tenantId) {
        return tryGetInstanceByTenantId(tenantId);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public InstancesResponseDto getAllInstances() {
        return tryGetAllInstances();
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public void deleteInstance(String tenantId) {
        tryDeleteInstance(tenantId);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public BindingResponseDto createBinding(CreateBindingRequestDto request) {
        return tryCreateBinding(request);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public BindingResponseDto getBindingByTenantId(String tenantId) {
        return tryGetBindingByTenantId(tenantId);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public BindingsResponseDto getAllBindings() {
        return tryGetAllBindings();
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public void deleteBinding(String tenantId) {
        tryDeleteBinding(tenantId);
    }

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public String getSchemaServicePlanId() {
        return tryGetSchemaServicePlanId();
    }

    private String tryGetSchemaServicePlanId() {
        var uri = UriComponentsBuilder
                .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                .pathSegment(PLANS_ENDPOINT_VERSION, PLANS_ENDPOINT_NAME)
                .queryParam("async", false)
                .queryParam("fieldQuery", "name+eq+'%s'".formatted(SCHEMA_INSTANCE_NAME))
                .toUriString();

        var headers = buildHeaders();

        var plansResponse = restClient.get()
                .uri(uri)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .body(PlansResponseDto.class);

        if (isNull(plansResponse) || isNull(plansResponse.items())) {
            throw new RuntimeException("Failed to retrieve plans");
        }

        if (plansResponse.items().size() != 1) {
            throw new RuntimeException("Amount of available plans is not equal to 1");
        }

        return plansResponse.items().getFirst().id();
    }

    private InstanceResponseDto tryCreateInstance(CreateInstanceByPlanIdRequestDto request) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .toUriString();

            var headers = buildHeaders();

            return restClient.post()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .body(request)
                    .retrieve()
                    .body(InstanceResponseDto.class);
        } catch (Unauthorized ex) {
            log.info("Unauthorized when creating Instance\n{}", ex.getMessage());
            refreshAccessToken();
            throw ex;
        }
    }

    private InstanceResponseDto tryGetInstanceByTenantId(String tenantId) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .queryParam("labelQuery", "tenantId+eq+'%s'".formatted(tenantId))
                    .toUriString();
            var headers = buildHeaders();

            var instances = restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(InstancesResponseDto.class);

            return instances.items().getFirst();

        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private InstancesResponseDto tryGetAllInstances() {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .toUriString();
            var headers = buildHeaders();

            return restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(InstancesResponseDto.class);
        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private void tryDeleteInstance(String tenantId) {
        try {
            var instance = tryGetInstanceByTenantId(tenantId);

            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME, instance.id())
                    .queryParam("async", false)
                    .toUriString();
            var headers = buildHeaders();

            restClient.delete()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private BindingResponseDto tryCreateBinding(CreateBindingRequestDto request) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(BINDINGS_ENDPOINT_VERSION, BINDINGS_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .toUriString();
            var headers = buildHeaders();

            return restClient.post()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .body(request)
                    .retrieve()
                    .body(BindingResponseDto.class);
        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private BindingResponseDto tryGetBindingByTenantId(String tenantId) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(BINDINGS_ENDPOINT_VERSION, BINDINGS_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .queryParam("labelQuery", "tenantId+eq+'%s'".formatted(tenantId))
                    .toUriString();
            var headers = buildHeaders();

            var bindings = restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(BindingsResponseDto.class);

            return bindings.items().getFirst();

        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private BindingsResponseDto tryGetAllBindings() {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(BINDINGS_ENDPOINT_VERSION, BINDINGS_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .toUriString();
            var headers = buildHeaders();

            return restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(BindingsResponseDto.class);
        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private void tryDeleteBinding(String tenantId) {
        try {
            var binding = tryGetBindingByTenantId(tenantId);

            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getServiceManagerUrl())
                    .pathSegment(BINDINGS_ENDPOINT_VERSION, BINDINGS_ENDPOINT_NAME, binding.id())
                    .queryParam("async", false)
                    .toUriString();
            var headers = buildHeaders();

            restClient.delete()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private HttpHeaders buildHeaders() {
        var accessToken = getAccessToken();
        var headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private String getAccessToken() {
        return serviceManagerAccessTokenProvider.getAccessToken(
                serviceManagerProperties.getClientId(),
                serviceManagerProperties.getClientSecret(),
                serviceManagerProperties.getAuthUrl()
        );
    }

    private void refreshAccessToken() {
        serviceManagerAccessTokenProvider.refreshAccessToken(
                serviceManagerProperties.getClientId(),
                serviceManagerProperties.getClientSecret(),
                serviceManagerProperties.getAuthUrl());
    }
}
