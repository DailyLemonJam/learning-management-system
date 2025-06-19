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
    private static final String SCHEMA_SERVICE_PLAN_ID = "9196d940-4ba6-452c-941a-094b13934083";

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final ServiceManager serviceManager;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        log.info("Subscribe request: %s".formatted(request.toString()));

        var tenantId = request.subscribedTenantId();
        var subdomain = request.subscribedSubdomain();

        var createInstanceRequest = buildCreateInstanceByPlanIdRequestDto(tenantId, subdomain);
        var instanceResponse = serviceManager.createInstance(createInstanceRequest);
        log.info("--- Created Instance ---");
        log.info("Instance ID: %s".formatted(instanceResponse.id()));
        log.info("Instance Name: %s".formatted(instanceResponse.name()));
        log.info("Instance Labels: %s".formatted(instanceResponse.labels().toString()));

        var createBindingRequest = buildCreateBindingRequest(instanceResponse, tenantId);
        var bindingResponse = serviceManager.createBinding(createBindingRequest);
        log.info("--- Created Binding ---");
        log.info("Binding ID: %s".formatted(bindingResponse.id()));
        log.info("Instance ID: %s".formatted(bindingResponse.serviceInstanceId()));
        log.info("Binding Credentials: %s".formatted(bindingResponse.credentials().toString()));
        log.info("Binding Name: %s".formatted(bindingResponse.name()));
        log.info("Binding Labels: %s".formatted(bindingResponse.labels().toString()));

        connectionProvider.createTenantDataSource(bindingResponse, tenantId);
        log.info("--- Created TenantDataSource for new schema ---");

        schemaMigrationService.applyLiquibaseChangelog(tenantId);
        log.info("--- Applied Liquibase migration on new schema ---");

        return TENANT_SPECIFIC_URL_TEMPLATE.formatted(request.subscribedSubdomain());
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        log.info("Unsubscribe request: %s".formatted(request.toString()));

        var tenantId = request.subscribedTenantId();

        serviceManager.deleteBinding(tenantId);
        log.info("--- Deleted Binding ---");

        serviceManager.deleteInstance(tenantId);
        log.info("--- Deleted Instance ---");

        connectionProvider.deleteTenantDataSource(tenantId);
        log.info("--- Deleted DataSource ---");

        return new UnsubscribeResponseDto("Tenant successfully unsubscribed");
    }

    private CreateInstanceByPlanIdRequestDto buildCreateInstanceByPlanIdRequestDto(String tenantId, String schemaName) {
        var parameters = new HashMap<String, String>();
        parameters.put("databaseName", "hana-db");

        var labels = new HashMap<String, List<String>>();
        labels.put("tenantId", List.of(tenantId));

        return new CreateInstanceByPlanIdRequestDto("schema_%s".formatted(schemaName), SCHEMA_SERVICE_PLAN_ID, parameters, labels);
    }

    private CreateBindingRequestDto buildCreateBindingRequest(InstanceResponseDto instance, String tenantId) {
        var instanceId = instance.id();

        var labels = new HashMap<String, List<String>>();
        labels.put("tenantId", List.of(tenantId));

        return new CreateBindingRequestDto("binding_%s".formatted(instanceId), instanceId,
                null, null, labels);
    }
}
