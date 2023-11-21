package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.HeadDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.reader.FacturaElectronicaReader;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.writer.FacturaElectronicaWriter;

@Configuration
public class GenerateFacturaElectronicaConfig {
	
	public static final String GENERATE_JOB = "generateJob";
	
	@Autowired
	private JobBuilderFactory jbf;
	
	@Autowired
	private StepBuilderFactory sbf;
	
	@Autowired
	private FacturaElectronicaWriter writer;
	
	@Autowired
	private FacturaElectronicaReader reader;
	
	@Bean(GENERATE_JOB)
	public Job generateFactura(){
		return this.jbf
				.get("GenerateFacturaElectronica")
				.preventRestart()
				.start(generateStep())
				.build();
	}
	
	@Bean
	public Step generateStep() {
		return this.sbf
				.get("Step1.GenerarArchivo")
				.<HeadDTO, HeadDTO>chunk(100)
				.reader(reader)
				.writer(writer)
				.build();
	}

}
