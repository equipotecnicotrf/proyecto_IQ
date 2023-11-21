package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;

import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf.MemoryDataSourceConfig;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf.PsqlDataSourceConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@PropertySource(value={"classpath:ws.properties"})
@EnableBatchProcessing
@EnableSwagger2
@EnableAsync
public class FacturaElectronicaServiceApplication {

	public static final String JOB_LAUNCHER_FACTURA_ELECTRONICA = "jobLauncherFacturaElectronica";
	
	@Value("${oa.facturaElectronica.batch.jobRepository.isolationLevelForCreate}")
	private String isolationLevelForCreate;
	
	@Autowired
	@Qualifier(MemoryDataSourceConfig.MEM_TM)
	public PlatformTransactionManager tm;
	
	@Autowired
	@Qualifier(MemoryDataSourceConfig.MEM_DS)
	public DataSource dataSource;
	
	@Autowired
	@Qualifier(PsqlDataSourceConfig.MAIN_DS)
	public DataSource psqlDataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(FacturaElectronicaServiceApplication.class, args);
	}
	

	@Bean
	public CommandLineRunner schedulingRunner() {
	    return new CommandLineRunner() {
	        public void run(String... args) throws Exception {
	        	Resource initSchema = new ClassPathResource("schema.sql");
	            DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
	            DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	        }
	    };
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(this.psqlDataSource);
	}

	
}
