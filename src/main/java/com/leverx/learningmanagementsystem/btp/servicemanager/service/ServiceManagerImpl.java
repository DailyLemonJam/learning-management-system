package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingsResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.CreateBindingRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.CreateInstanceByPlanIdRequestDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstanceResponseDto;
import com.leverx.learningmanagementsystem.btp.servicemanager.dto.instances.InstancesResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("cloud")
@Service
public class ServiceManagerImpl implements ServiceManager {

    @Override
    public InstanceResponseDto createInstance(CreateInstanceByPlanIdRequestDto request) {
        return null;
    }

    @Override
    public InstanceResponseDto getInstanceByInstanceId(String instanceId) {
        return null;
    }

    @Override
    public InstancesResponseDto getAllInstances() {
        return null;
    }

    @Override
    public void deleteInstance(String instanceId) {

    }

    @Override
    public BindingResponseDto createBinding(CreateBindingRequestDto request) {
        return null;
    }

    @Override
    public BindingResponseDto getBindingByBindingId(String bindingId) {
        return null;
    }

    @Override
    public BindingsResponseDto getAllBindings() {
        return null;
    }

    @Override
    public void deleteBinding(String bindingId) {

    }
}
