package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingsResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstancesResponseDto;

public interface ServiceManager {

    // Instances

    InstanceResponseDto createInstance(CreateInstanceByPlanIdRequestDto request);

    InstanceResponseDto getInstanceByTenantId(String tenantId);

    InstancesResponseDto getAllInstances();

    void deleteInstance(String tenantId);

    // Bindings

    BindingResponseDto createBinding(CreateBindingRequestDto request);

    BindingResponseDto getBindingByTenantId(String tenantId);

    BindingsResponseDto getAllBindings();

    void deleteBinding(String tenantId);
}
