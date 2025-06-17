package com.leverx.learningmanagementsystem.btp.subscription.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.service.ServiceManager;
import com.leverx.learningmanagementsystem.btp.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.btp.subscription.dto.UnsubscribeResponseDto;
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
    private static final String SCHEMA_PLAN_ID = "9196d940-4ba6-452c-941a-094b13934083";

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final ServiceManager serviceManager;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = createValidSQLTenantId(request.subscribedTenantId());

        var createInstanceRequest = buildCreateInstanceByPlanIdRequestDto(tenantId);
        var instanceResponse = serviceManager.createInstance(createInstanceRequest);

        var createBindingRequest = buildCreateBindingRequest(instanceResponse, tenantId);
        var bindingResponse = serviceManager.createBinding(createBindingRequest);

        connectionProvider.createTenantDataSource(bindingResponse, tenantId);

        schemaMigrationService.applyLiquibaseChangelog(tenantId);

        return TENANT_SPECIFIC_URL_TEMPLATE.formatted(request.subscribedTenantId());
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = createValidSQLTenantId(request.subscribedTenantId());

        serviceManager.deleteBinding(tenantId);

        serviceManager.deleteInstance(tenantId);

        connectionProvider.deleteTenantDataSource(tenantId);

        return new UnsubscribeResponseDto("Tenant successfully unsubscribed");
    }

    private CreateInstanceByPlanIdRequestDto buildCreateInstanceByPlanIdRequestDto(String tenantId) {
        var parameters = new HashMap<String, String>();
        parameters.put("databaseName", "hana-db");

        var labels = new HashMap<String, List<String>>();
        labels.put("tenantId", List.of(tenantId));

        return new CreateInstanceByPlanIdRequestDto("schema_%s".formatted(tenantId), SCHEMA_PLAN_ID, parameters, labels);
    }

    private CreateBindingRequestDto buildCreateBindingRequest(InstanceResponseDto instance, String tenantId) {
        var instanceId = instance.id();

        var labels = new HashMap<String, List<String>>();
        labels.put("tenantId", List.of(tenantId));

        return new CreateBindingRequestDto("binding_%s".formatted(instanceId), instanceId,
                null, null, labels);
    }
}
