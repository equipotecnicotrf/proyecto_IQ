package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PsqlDataSourceConfig {
	
	public static final String MAIN_DS = "mainDatasource";
	public static final String MAIN_EM = "mainEntityManager";
	public static final String MAIN_TM = "mainTransactionManager";
	
	@Bean(MAIN_DS)
	@ConfigurationProperties(prefix="spring.psql-datasource")
    public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
    }
}
