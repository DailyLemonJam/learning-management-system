package com.leverx.learningmanagementsystem.multitenancy.servicemanager.service;

import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.BindingsResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.instances.InstancesResponseDto;

public interface ServiceManager {

    InstanceResponseDto createInstance(CreateInstanceByPlanIdRequestDto request);

    InstanceResponseDto getInstanceByTenantId(String tenantId);

    InstancesResponseDto getAllInstances();

    void deleteInstance(String tenantId);

    BindingResponseDto createBinding(CreateBindingRequestDto request);

    BindingResponseDto getBindingByTenantId(String tenantId);

    BindingsResponseDto getAllBindings();

    void deleteBinding(String tenantId);
}
