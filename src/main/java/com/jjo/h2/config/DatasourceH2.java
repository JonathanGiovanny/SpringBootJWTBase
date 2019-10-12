package com.jjo.h2.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = DatasourceH2.BASE_PACKAGES, //
    entityManagerFactoryRef = DatasourceH2.ENTITY_MANAGER, //
    transactionManagerRef = DatasourceH2.TRANSACTION_MANAGER)
public class DatasourceH2 {

  public static final String ENTITY_MANAGER = "entityManager";
  static final String TRANSACTION_MANAGER = "transactionManagerDataH2";

  static final String BASE_PACKAGES = "com.jjo.h2.repositories";

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }

  @Primary
  @Bean(name = "dataSource")
  @ConfigurationProperties("spring.datasource")
  public HikariDataSource dataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @PersistenceContext(unitName = "h2")
  @Primary
  @Bean(name = ENTITY_MANAGER)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource()).packages("com.jjo.h2.model").persistenceUnit("h2").build();
  }

  @Primary
  @Bean(name = TRANSACTION_MANAGER)
  public PlatformTransactionManager transactionManager(
      @Qualifier(ENTITY_MANAGER) EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
