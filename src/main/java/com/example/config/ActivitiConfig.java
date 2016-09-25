package com.example.config;

import com.example.activiti.identity.ActivitiGroupManagerFactory;
import com.example.activiti.identity.ActivitiUserManagerFactory;
import com.example.activiti.listener.ManagerAssignmentHandler;
import org.activiti.engine.*;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by catmosoeredjo on 1/25/2016.
 */

@Configuration
public class ActivitiConfig {

    @Autowired
    private DataSource configureDataSource;

    @Autowired
    private PlatformTransactionManager annotationDrivenTransactionManager;

    @Autowired
    private Environment env;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration speconfig = new SpringProcessEngineConfiguration();
        speconfig.setDatabaseType(env.getProperty("activiti.database.type"));
        speconfig.setDataSource(configureDataSource);
        speconfig.setTransactionManager(annotationDrivenTransactionManager);
        speconfig.setDatabaseSchemaUpdate("true");
        speconfig.setJobExecutorActivate(false);
        speconfig.setJpaEntityManagerFactory(entityManagerFactory);

        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(groupManagerFactory());
        customSessionFactories.add(userManagerFactory());
        speconfig.setCustomSessionFactories(customSessionFactories);

        return speconfig;
    }

    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean(SpringProcessEngineConfiguration spec) throws Exception {
        ProcessEngineFactoryBean pefbean = new ProcessEngineFactoryBean();
        pefbean.setProcessEngineConfiguration(spec);
        /*System.out.println(spec.getSessionFactories());
        System.out.println(springProcessEngineConfiguration().getSessionFactories());*/
        return pefbean;

    }

    @Bean
    public ActivitiUserManagerFactory userManagerFactory(){
        return new ActivitiUserManagerFactory();
    }

    @Bean
    public ActivitiGroupManagerFactory groupManagerFactory(){
        return new ActivitiGroupManagerFactory();
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngineFactoryBean pefb) throws Exception{
        return pefb.getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getRuntimeService();
    }

    @Bean
    public HistoryService historyService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getHistoryService();
    }

    @Bean
    public ManagementService managementService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getManagementService();
    }

    @Bean
    public IdentityService identityService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getIdentityService();
    }

    @Bean
    public FormService formService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getFormService();
    }

    @Bean
    public TaskService taskService(ProcessEngineFactoryBean pefb) throws Exception {
        return pefb.getObject().getTaskService();
    }

    @Bean
    public ManagerAssignmentHandler managerAssignmentHandler(){
        return new ManagerAssignmentHandler();
    }
}
