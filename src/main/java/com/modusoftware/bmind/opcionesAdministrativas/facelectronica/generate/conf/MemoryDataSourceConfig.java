package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(
//		  entityManagerFactoryRef = MemoryDataSourceConfig.MEM_EM,
//		  transactionManagerRef = MemoryDataSourceConfig.MEM_TM)
public class MemoryDataSourceConfig {
	
	public static final String MEM_DS = "memoryDatasource";
	public static final String MEM_EM = "memoryEntityManager";
	public static final String MEM_TM = "memTransactionManager";
	
	@Bean(MEM_DS)
	@Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
    }
	
//    @Bean(name = MEM_EM)
//    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(
//    		EntityManagerFactoryBuilder builder,
//    		@Qualifier(MEM_DS) DataSource dataSource) {
//        return builder
//        		.dataSource(dataSource)
//        		.packages("com.modusoftware.aldeamo.hs.sms.batch.persistent.memory.entity")
//        		.persistenceUnit("persistenceUnitMemory")
//        		.build();
//    }

    @Bean(name = MEM_TM)
    @Primary
    public PlatformTransactionManager getTransactionManager() {
        return new JdbcTransactionManager(userDataSource());
    }


}
