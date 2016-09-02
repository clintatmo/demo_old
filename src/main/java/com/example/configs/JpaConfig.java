package com.example.configs;

import com.example.configs.audit.AuditorAwareImpl;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.repositories")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource configureDataSource() {


        try {
            DataSource ds = new DataSource();
            ds.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
            ds.setUrl(env.getRequiredProperty("jdbc.url"));
            ds.setUsername(env.getRequiredProperty("jdbc.username"));
            ds.setPassword(env.getRequiredProperty("jdbc.password"));
            ds.setMaxIdle(60);
            return ds;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /*JAVA CONFIG FLYWAY*/

    /*@Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.clean();
        //flyway.setInitOnMigrate(true);
        //flyway.setSchemas("SBA_DIALOG");
        flyway.setLocations("filesystem:src/main/resources/db/migrations");
        flyway.setDataSource(configureDataSource());
        flyway.migrate();
        return flyway;
    }*/

    /*JAVA CONFIG FLYWAY*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(configureDataSource());
        entityManagerFactory.setPackagesToScan("com.example.entities");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, env.getProperty("hibernate.dialect"));
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, env.getProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}