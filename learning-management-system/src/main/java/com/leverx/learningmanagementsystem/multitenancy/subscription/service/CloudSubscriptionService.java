package com.leverx.learningmanagementsystem.multitenancy.subscription.service;

import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.service.ServiceManager;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider.CloudDataSourceBasedMultiTenantConnectionProviderImpl;
import com.leverx.learningmanagementsystem.multitenancy.database.migration.LiquibaseSchemaMigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudSubscriptionService implements SubscriptionService {

    @Value("${application-variables.applicationUri}")
    private String applicationUri;

    @Value("${application-variables.applicationName}")
    private String applicationName;

    @Value("${application-variables.providerSubdomain}")
    private String providerSubdomain;

    @Value("${user-approuter-settings.approuterName}")
    private String approuterName;

    private static final String LABEL_TENANT_ID_NAME = "tenantId";
    private static final String SCHEMA_NAME_TEMPLATE = "schema_%s";
    private static final String BINDING_NAME_TEMPLATE = "binding_%s";
    private static final String HTTPS_PROTOCOL = "https";

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final ServiceManager serviceManager;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();
        var subdomain = request.subscribedSubdomain();

        log.info("Subscribe TenantId: {}", tenantId);
        log.info("Subscribe subdomain: {}", subdomain);

        var instanceResponse = createInstance(tenantId, subdomain);

        var bindingResponse = createBinding(instanceResponse, tenantId);

        connectionProvider.createTenantDataSource(bindingResponse, tenantId);

        schemaMigrationService.applyLiquibaseChangelog(tenantId);

        return buildTenantSpecificUrl(subdomain);
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        serviceManager.deleteBinding(tenantId);

        serviceManager.deleteInstance(tenantId);

        connectionProvider.deleteTenantDataSource(tenantId);

        return new UnsubscribeResponseDto("Tenant successfully unsubscribed");
    }

    private CreateInstanceByPlanIdRequestDto buildCreateInstanceByPlanIdRequestDto(String tenantId, String schemaName) {
        var schemaServicePlanId = getSchemaServicePlanId();

        var labels = new HashMap<String, List<String>>();
        labels.put(LABEL_TENANT_ID_NAME, List.of(tenantId));

        return new CreateInstanceByPlanIdRequestDto(SCHEMA_NAME_TEMPLATE.formatted(schemaName), schemaServicePlanId, null, labels);
    }

    private CreateBindingRequestDto buildCreateBindingRequest(InstanceResponseDto instance, String tenantId) {
        var instanceId = instance.id();

        var labels = new HashMap<String, List<String>>();
        labels.put(LABEL_TENANT_ID_NAME, List.of(tenantId));

        return new CreateBindingRequestDto(BINDING_NAME_TEMPLATE.formatted(instanceId), instanceId,
                null, null, labels);
    }

    private InstanceResponseDto createInstance(String tenantId, String subdomain) {
        var createInstanceRequest = buildCreateInstanceByPlanIdRequestDto(tenantId, subdomain);
        return serviceManager.createInstance(createInstanceRequest);
    }

    private BindingResponseDto createBinding(InstanceResponseDto instanceResponse, String tenantId) {
        var createBindingRequest = buildCreateBindingRequest(instanceResponse, tenantId);
        return serviceManager.createBinding(createBindingRequest);
    }

    private String getSchemaServicePlanId() {
        return serviceManager.getSchemaServicePlanId();
    }

    private String buildTenantSpecificUrl(String tenantSubdomain) {
        String approuterUri = applicationUri.replace(applicationName, approuterName);
        String tenantSpecificUri = approuterUri.replace(providerSubdomain, tenantSubdomain);
        return "%s://%s".formatted(HTTPS_PROTOCOL, tenantSpecificUri);
    }
}
