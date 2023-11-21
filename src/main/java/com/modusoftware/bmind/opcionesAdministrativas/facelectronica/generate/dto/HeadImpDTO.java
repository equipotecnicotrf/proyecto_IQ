package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadImpDTO {
	
	@NotNull
	@ApiModelProperty(notes = "Impuesto_monto_importe")
	@JsonProperty("TOT_IMP")
	private Double totImp;
	
	@NotNull
	@ApiModelProperty(notes = "impuesto_tasa")
	@JsonProperty("PORC_IMP")
	private Double porcImp;
	
	@NotNull
	@ApiModelProperty(notes = "Impuesto_TaxableAmount")
	@JsonProperty("TAXABLE_AMT")
	private Double taxableAmt;
	
	@JsonProperty("TAX")
	private String tax;

	@JsonProperty("UNROUNDED_TAX_AMT")
	private String unroundedTaxAmt;
	
	//PARA EL RESTAIVA campo 187
	//1. Si la moneda del billTO es COP, y la moneda del documento no es COP y el country es CO y no es exportacion, la factura es en pesos
	//2. Si el tax es Co Iva y si la factura es E
	
}
