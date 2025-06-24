package com.leverx.starterfeatureflags.client;

import com.leverx.starterfeatureflags.config.FeatureFlagsServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

class FeatureFlagsServiceTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(
                            FeatureFlagsServiceConfiguration.class,
                            RestClientAutoConfiguration.class));

    @Test
    void shouldContainNamedBeans() {
        contextRunner.run(context -> {
           assertTrue(context.containsBean("restClient"));
           assertTrue(context.containsBean("featureFlagsServiceImpl"));
        });
    }
}
