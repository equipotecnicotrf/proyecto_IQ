package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoBillToDTO {
	
	@ApiModelProperty(notes = "attribute2")
	@JsonProperty("ATTRIBUTE2")
	private String attribute2;
	
	@ApiModelProperty(notes = "attribute8")
	@JsonProperty("ATTRIBUTE8")
	private String attribute8;

}
