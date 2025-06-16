package com.leverx.learningmanagementsystem.btp.servicemanager.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("cloud")
@Service
public class ServiceManagerImpl implements ServiceManager {
}
