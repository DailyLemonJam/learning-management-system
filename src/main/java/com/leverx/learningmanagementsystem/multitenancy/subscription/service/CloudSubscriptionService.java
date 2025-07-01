package com.leverx.learningmanagementsystem.multitenancy.subscription.service;

import com.leverx.learningmanagementsystem.application.config.ApplicationProperties;
import com.leverx.learningmanagementsystem.btp.approuter.model.ApprouterProperties;
import com.leverx.learningmanagementsystem.btp.destinationservice.model.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.multitenancy.database.connection.manager.CloudDataSourceManager;
import com.leverx.learningmanagementsystem.multitenancy.database.connection.provider.CloudDataSourceBasedMultiTenantConnectionProviderImpl;
import com.leverx.learningmanagementsystem.multitenancy.database.migration.LiquibaseSchemaMigrationService;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.service.ServiceManager;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.DependenciesResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;
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

    private static final String LABEL_TENANT_ID_NAME = "tenantId";
    private static final String SCHEMA_NAME_TEMPLATE = "schema_%s";
    private static final String BINDING_NAME_TEMPLATE = "binding_%s";
    private static final String HTTPS_PROTOCOL = "https";

    private final CloudDataSourceManager cloudDataSourceManager;
    private final LiquibaseSchemaMigrationService liquibaseSchemaMigrationService;
    private final ServiceManager serviceManager;
    private final ApplicationProperties applicationProperties;
    private final ApprouterProperties approuterProperties;
    private final DestinationServiceProperties destinationServiceProperties;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();
        var subdomain = request.subscribedSubdomain();

        var instanceResponse = createInstance(tenantId, subdomain);

        var bindingResponse = createBinding(instanceResponse, tenantId);

        cloudDataSourceManager.createTenantDataSource(bindingResponse, tenantId);

        liquibaseSchemaMigrationService.applyChangelog(tenantId);

        return buildTenantSpecificUrl(subdomain);
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        serviceManager.deleteBinding(tenantId);

        serviceManager.deleteInstance(tenantId);

        cloudDataSourceManager.deleteTenantDataSource(tenantId);

        return new UnsubscribeResponseDto("Tenant successfully unsubscribed");
    }

    @Override
    public List<DependenciesResponseDto> getDependencies() {
        String xsAppName = destinationServiceProperties.getXsAppName();

        var response = new DependenciesResponseDto(xsAppName);

        return List.of(response);
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
        String approuterUri = applicationProperties.getApplicationUri().replace(
                applicationProperties.getApplicationName(), approuterProperties.getApprouterName());

        String tenantSpecificUri = approuterUri.replace(
                applicationProperties.getProviderSubdomain(), tenantSubdomain);

        return "%s://%s".formatted(HTTPS_PROTOCOL, tenantSpecificUri);
    }
}
