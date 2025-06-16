package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.ServiceBindingsResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.ServiceInstancesResponseDto;

public interface ServiceManager {

    ServiceInstancesResponseDto getServiceInstances();

    ServiceBindingsResponseDto getServiceBindings();

    void createServiceInstance(CreateInstanceRequestDto request);

    void bindServiceInstance(CreateBindingRequestDto request);

    void deleteServiceInstance(String serviceInstanceId);

    void unbindServiceInstance(String serviceBindingId);
}
