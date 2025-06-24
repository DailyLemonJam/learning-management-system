package com.leverx.learningmanagementsystem.multitenancy.tenant.config;

import com.leverx.learningmanagementsystem.multitenancy.tenant.filter.CloudRequestContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class CloudFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CloudRequestContextFilter> filterCloudRequestContextFilter() {
        var filter = new FilterRegistrationBean<CloudRequestContextFilter>();
        filter.setFilter(new CloudRequestContextFilter());
        filter.addUrlPatterns("/*");
        filter.setOrder(3);
        return filter;
    }
}
