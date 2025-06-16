package com.leverx.learningmanagementsystem.btp.servicemanager.service;

public interface ServiceManager {

    // TODO: check pc for local commits

    void createSchema();

    void bindSchema();

    void deleteSchema();

    void unbindSchema();
}
