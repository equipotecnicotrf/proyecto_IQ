package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompaniaDTO {
	
	@NotNull
	@ApiModelProperty(notes = "Nombre de la compañia")
	private String nombre;
	
	@NotNull
	@ApiModelProperty(notes = "Tipo de la compañia")
	private String tipo;
	
	@NotNull
	@ApiModelProperty(notes = "Codigo postal de la compañia")
	private String codigoPostal;
	
	@NotNull
	@ApiModelProperty(notes = "Codigo postal del municipio")
	private String codigoMunicipio;
	
	@NotNull
	@ApiModelProperty(notes = "Regimen")
	private String regimen;
	
	@NotNull
	@ApiModelProperty(notes = "Responsabilidad")
	private String responsabilidad;
	
	@NotNull
	@ApiModelProperty(notes = "Email de la compañia")
	private String email;
	
	@NotNull
	@ApiModelProperty(notes = "abreviacion de la compañia")
	private String abreviatura;
	
}
