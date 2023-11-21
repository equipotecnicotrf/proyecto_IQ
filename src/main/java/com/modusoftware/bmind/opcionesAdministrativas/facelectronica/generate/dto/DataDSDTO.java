package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonRootName(value="DATA_DS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDSDTO {
	
	@JsonProperty("HEAD")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<HeadDTO> head;
	
	private CompaniaDTO compania;
	
	

}
