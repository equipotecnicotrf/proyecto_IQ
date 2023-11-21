package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DetaImpDTO {
	
	@NotNull
	@JsonProperty("TAX_RATE")
	public Double TaxRate;
	
	@NotNull
	@JsonProperty("TAX")
	public String tax;
	
	@NotNull
	@JsonProperty("TAX_AMT")
	public Double taxAmt;
	
	@NotNull
	@JsonProperty("UNROUNDED_TAX_AMT")
	public Double unroundedTaxAmt;

}
