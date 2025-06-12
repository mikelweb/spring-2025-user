package edu.uoc.epcsd.user;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "edu.uoc.epcsd.user.infrastructure.repository.jpa")

public class DigitalItemRepositoryIntegrationTestConfig { }