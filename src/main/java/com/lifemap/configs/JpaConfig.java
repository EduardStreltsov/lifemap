package com.lifemap.configs;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.lifemap"})
@EnableTransactionManagement
public class JpaConfig {
	
	@Value("${spring.jpa.database-platform}")
	private String vendorDatabase;
	@Value("${spring.jpa.hibernate.dialect}")
	private String platformDialect;
	@Value("${spring.jpa.show-sql}")
	private Boolean showSql;
	@Value("${spring.jpa.generate-ddl}")
	private Boolean generateDdl;
	@Value("${spring.datasource.url}")
	private String databaseUrl;
	@Value("${spring.datasource.username}")
	private String databaseUser;
	@Value("${spring.datasource.password}")
	private String databasePassword;
	@Value("${spring.datasource.driver-class-name}")
	private String databaseDriver;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setPersistenceUnitName("lifemap");
		emf.setPackagesToScan("com.lifemap.model");
		return emf;
	}
	
	@Bean
	public JpaVendorAdapter getJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.valueOf(vendorDatabase));
		vendorAdapter.setShowSql(showSql);
		vendorAdapter.setDatabasePlatform(platformDialect);
		vendorAdapter.setGenerateDdl(generateDdl);
		return vendorAdapter;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	@Profile("prod")
	public DataSource postgresDataSource() {
		PGSimpleDataSource postgresDataSource = new PGSimpleDataSource();
		postgresDataSource.setUrl(databaseUrl);
		postgresDataSource.setUser(databaseUser);
		postgresDataSource.setPassword(databasePassword);
		return postgresDataSource;
	}
	
}
