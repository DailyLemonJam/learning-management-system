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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudSubscriptionService implements SubscriptionService {

    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "https://%s-dev-approuter.cfapps.us10-001.hana.ondemand.com";
    private static final String SCHEMA_SERVICE_PLAN_ID = "9196d940-4ba6-452c-941a-094b13934083";

    private static final String LABEL_TENANT_ID_NAME = "tenantId";
    private static final String SCHEMA_NAME_TEMPLATE = "schema_%s";
    private static final String BINDING_NAME_TEMPLATE = "binding_%s";

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final ServiceManager serviceManager;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();
        var subdomain = request.subscribedSubdomain();

        var instanceResponse = createInstance(tenantId, subdomain);

        var bindingResponse = createBinding(instanceResponse, tenantId);

        connectionProvider.createTenantDataSource(bindingResponse, tenantId);

        schemaMigrationService.applyLiquibaseChangelog(tenantId);

        return TENANT_SPECIFIC_URL_TEMPLATE.formatted(request.subscribedSubdomain());
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
//        var parameters = new HashMap<String, String>();
//        parameters.put("databaseName", "hana-db");

        var labels = new HashMap<String, List<String>>();
        labels.put(LABEL_TENANT_ID_NAME, List.of(tenantId));

        return new CreateInstanceByPlanIdRequestDto(SCHEMA_NAME_TEMPLATE.formatted(schemaName), SCHEMA_SERVICE_PLAN_ID, null, labels);
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
}
