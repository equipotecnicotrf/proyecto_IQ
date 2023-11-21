package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillToDTO {
	
	@ApiModelProperty(notes = "Nombre_Receptor")
	@JsonProperty("PARTY_NAME")
	private String partyName;
	
	@ApiModelProperty(notes = "Dom_Receptor_localidad")
	@JsonProperty("CITY")
	private String city;
	
	@ApiModelProperty(notes = "Dom_Receptor_calle")
	@JsonProperty("ADDRESS1")
	private String address1;
	
	@ApiModelProperty(notes = "State")
	@JsonProperty("STATE")
	private String state;
	
	@ApiModelProperty(notes = "Dom_Receptor_pais")
	@JsonProperty("COUNTRY")
	private String country;
	
	@ApiModelProperty(notes = "Dom_Receptor_codigoPostal")
	@JsonProperty("POSTAL_CODE")
	private String postalCode;
	
	@ApiModelProperty(notes = "InfoBillToDTO")
	@JsonProperty("INFO_BILL_TO")
	private InfoBillToDTO infoBillTo;
	
	@ApiModelProperty(notes = "COD_DPTO_RECEPTOR")
	@JsonProperty("COD_DPTO_RECEPTOR")
	private String codDptoReceptor;

	@ApiModelProperty(notes = "COD_CIUDAD_RECEPTOR")
	@JsonProperty("COD_CIUDAD_RECEPTOR")
	private String codCiudadReceptor;

	@JsonProperty("MONEDAIMP")
	private String monedaImp;
	
}
