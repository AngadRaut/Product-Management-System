package com.pms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "postgresqlLocalFactoryBean"
                         ,basePackages = "com.pms.repository",
                          transactionManagerRef = "postgresqlTransactionManager")
public class PostgresqlDbConfig {

    @Autowired
    private Environment environment;

    // datasource
    @Bean
    public DataSource postgresqlDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    // entityManagerFactoryBean
    @Bean
    public LocalContainerEntityManagerFactoryBean postgresqlLocalFactoryBean(){
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setPackagesToScan("com.pms.entities");

        Map<String, String> hibernateProperties = new HashMap<>();

        // Setting the Hibernate Dialect for PostgreSQL
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        // Enabling SQL query logging
        hibernateProperties.put("hibernate.show_sql", "true");

        // Auto schema creation or update
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");

        // Optional: Format the SQL output for better readability
        hibernateProperties.put("hibernate.format_sql", "true");
        hibernateProperties.put("spring.jpa.properties.hibernate.jdbc.time_zone","UTC");

        lc.setJpaPropertyMap(hibernateProperties);
        lc.setDataSource(postgresqlDataSource());
        lc.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Setting a unique persistence unit name for PostgreSQL
        lc.setPersistenceUnitName("postgresqlPersistenceUnit");
        return lc;
    }

    //transaction
    @Bean
    public PlatformTransactionManager postgresqlTransactionManager( @Autowired LocalContainerEntityManagerFactoryBean postgresqlLocalFactoryBean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(Objects.requireNonNull(postgresqlLocalFactoryBean.getObject()));
        return transactionManager;
    }
}
