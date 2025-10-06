package com.cs.BatchAutoGenerateParameter.configulations;



import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"com.cs.BatchAutoGenerateParameter.entities"})  // scan JPA entities manually
@EnableJpaRepositories(
        basePackages = "com.cs.BatchAutoGenerateParameter.repositories",
        entityManagerFactoryRef = "batchAutoGenEntityManagerFactory",//1
        transactionManagerRef = "batchAutoGenTransactionManager"//2
)
public class BatchAutoGenerateParameterDataSourceConfig {
	@Primary
	@Bean
	@ConfigurationProperties("spring.datasource.batchautogen")
	public DataSourceProperties edcSyncHubDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	public DataSource edcSyncHubDataSource() {
		return edcSyncHubDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Primary
	@Bean
	//1
	public LocalContainerEntityManagerFactoryBean batchAutoGenEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = new HashMap<String, Object>();
//	    properties.put("hibernate.hbm2ddl.auto", "update");
		return builder.dataSource(edcSyncHubDataSource()).packages("com.cs.BatchAutoGenerateParameter.entities") // Specify the  package  for entities
				.properties(properties)
				.persistenceUnit("dbbatchautogen").build();
	}

	@Primary
	@Bean
	//2
	public PlatformTransactionManager batchAutoGenTransactionManager(
			@Qualifier("batchAutoGenEntityManagerFactory") EntityManagerFactory edclogserverEntityManagerFactory) {
		return new JpaTransactionManager(edclogserverEntityManagerFactory);
	}

}
