package com.pms.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.pms.repository")
public class MongoDbConfiguration {

    @Autowired
    private Environment environment;

    // MongoClient Bean
    @Bean
    public MongoClient mongoClient() {
        String connectionString = environment.getProperty("spring.mongodb.connection-string");
        return MongoClients.create(connectionString);
    }

    // MongoDatabaseFactory Bean
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), environment.getProperty("spring.data.mongodb.database"));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}
