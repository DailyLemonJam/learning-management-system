package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.auth.ServiceManagerAccessTokenProvider;
import com.leverx.learningmanagementsystem.btp.servicemanager.configuration.ServiceManagerProperties;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingsResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstancesResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

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

    private final ServiceManagerProperties serviceManagerProperties;
    private final ServiceManagerAccessTokenProvider serviceManagerAccessTokenProvider;
    private final RestClient restClient;

    // Instance methods

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public InstanceResponseDto createInstance(CreateInstanceByPlanIdRequestDto request) {
        log.info("Started trying to create Instance");
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

    // Binding methods

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

    // Try Instance methods

    private InstanceResponseDto tryCreateInstance(CreateInstanceByPlanIdRequestDto request) {
        try {
            log.info("tryCreateInstance method was called here");

            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getSmUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .toUriString();

            log.info("Create Instance uri: %s".formatted(uri));
            var headers = buildHeaders();
            log.info("Headers: %s".formatted(headers.asSingleValueMap().toString()));

            return restClient.post()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .body(request)
                    .retrieve()
                    .body(InstanceResponseDto.class);
        } catch (Unauthorized ex) {
            log.info("Unauthorized when creating Instance");
            log.info(ex.getMessage());
            refreshAccessToken();
            throw ex;
        }
    }

    private InstanceResponseDto tryGetInstanceByTenantId(String tenantId) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getSmUrl())
                    .pathSegment(INSTANCES_ENDPOINT_VERSION, INSTANCES_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .queryParam("labelQuery", "tenantId eq '%s'".formatted(tenantId))
                    .toUriString();
            var headers = buildHeaders();

            log.info("tryGetInstance: Uri with labelQuery: %s".formatted(uri));

            var instances = restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(InstancesResponseDto.class);

            return instances.instances().getFirst();

        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private InstancesResponseDto tryGetAllInstances() {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getSmUrl())
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
                    .fromUriString(serviceManagerProperties.getSmUrl())
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

    // Try Binding methods

    private BindingResponseDto tryCreateBinding(CreateBindingRequestDto request) {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getSmUrl())
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
                    .fromUriString(serviceManagerProperties.getSmUrl())
                    .pathSegment(BINDINGS_ENDPOINT_VERSION, BINDINGS_ENDPOINT_NAME)
                    .queryParam("async", false)
                    .queryParam("labelQuery", "tenantId eq '%s'".formatted(tenantId))
                    .toUriString();
            var headers = buildHeaders();

            log.info("tryGetBinding: Uri with labelQuery: %s".formatted(uri));

            var bindings = restClient.get()
                    .uri(uri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(BindingsResponseDto.class);

            return bindings.instances().getFirst();

        } catch (Unauthorized ex) {
            refreshAccessToken();
            throw ex;
        }
    }

    private BindingsResponseDto tryGetAllBindings() {
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(serviceManagerProperties.getSmUrl())
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
                    .fromUriString(serviceManagerProperties.getSmUrl())
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

    // Util methods

    private HttpHeaders buildHeaders() {
        log.info("Building headers");
        var accessToken = getAccessToken();
        var headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private String getAccessToken() {
        log.info("Getting Access Token");
        return serviceManagerAccessTokenProvider.getAccessToken(
                serviceManagerProperties.getClientId(),
                serviceManagerProperties.getClientSecret(),
                serviceManagerProperties.getUrl()
        );
    }

    private void refreshAccessToken() {
        log.info("Refreshing Access Token");
        serviceManagerAccessTokenProvider.refreshAccessToken(
                serviceManagerProperties.getClientId(),
                serviceManagerProperties.getClientSecret(),
                serviceManagerProperties.getUrl());
    }
}
