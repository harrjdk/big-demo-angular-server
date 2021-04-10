package dev.hornetshell.bigdemoangular.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Jpa configuration */
@Profile("jpa")
@Configuration
@EnableJpaRepositories(basePackages = "dev.hornetshell.bigdemoangular.repositories")
@EntityScan(basePackages = "dev.hornetshell.bigdemoangular.repositories.entities")
@EnableTransactionManagement
public class JpaConfig {}
