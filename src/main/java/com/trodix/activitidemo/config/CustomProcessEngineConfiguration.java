package com.trodix.activitidemo.config;

import java.io.IOException;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

//@Configuration
public class CustomProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    public CustomProcessEngineConfiguration() throws IOException {
        final ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        final Resource[] mappingLocations = patternResolver.getResources("classpath*:/processes/*.bpmn2");
        this.setDeploymentResources(mappingLocations);
    }

}
