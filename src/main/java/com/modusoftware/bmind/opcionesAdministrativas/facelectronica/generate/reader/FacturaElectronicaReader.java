package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.reader;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DataDSDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.HeadDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.BatchExecutionContextKeys;

@Service
@StepScope
public class FacturaElectronicaReader implements ItemReader<HeadDTO>{

	private AtomicInteger counter = new AtomicInteger(0);
	
	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	
	@Override
	public HeadDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		String request = stepExecution.getJobExecution().getJobParameters().getString(BatchExecutionContextKeys.REQUEST);
		
		Gson gson = new Gson();
		
		DataDSDTO dto = gson.fromJson(request, DataDSDTO.class);
		
		int index = this.counter.getAndIncrement();
	
		if(index >= dto.getHead().size()) {
			return null;
		}else {
			dto.getHead().get(index).setCompania(dto.getCompania());
			return dto.getHead().get(index);
		}

	}
}
