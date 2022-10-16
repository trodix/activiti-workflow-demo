package com.trodix.activitidemo.config;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;

//@Configuration
public class CustomProcessEngine extends ProcessEngineFactoryBean {

    public CustomProcessEngine(final ProcessEngineConfigurationImpl processEngineConfiguration) {
        this.setProcessEngineConfiguration(processEngineConfiguration);
    }

}
