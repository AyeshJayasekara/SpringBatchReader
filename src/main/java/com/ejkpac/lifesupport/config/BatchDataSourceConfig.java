package com.ejkpac.lifesupport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@Order(1)
public class BatchDataSourceConfig {

    private final Environment env;

    @Autowired
    public BatchDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource getBillingDataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.billing.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.billing.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.billing.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.billing.datasource.password"));

        return  dataSource;
    }

    @Bean("batchDataSource")
    @BatchDataSource
    public DataSource getBatchDataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.batch.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.batch.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.batch.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.batch.datasource.password"));

        return  dataSource;
    }
}
