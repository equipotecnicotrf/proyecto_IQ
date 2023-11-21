package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AplicaDTO {
	
	@JsonProperty("TRX_NUMBER_RELATED")
	private String trxNumberRelated;

	@JsonProperty("CUFE_RELATED")
	private String cufeRelated;

	@JsonProperty("TRX_DATE_RELATED")
	private String trxDateRelated;	
	
}
