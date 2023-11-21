package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DetalleDTO {
	
	@NotNull
	@ApiModelProperty(notes = "Linea_Descripcion")
	@JsonProperty("DESCRIPCION")
	private String descripcion;
	
	@NotNull
	@ApiModelProperty(notes = "Linea_Cantidad")
	@JsonProperty("CANTIDAD")
	private Double cantidad;
	
	@NotNull
	@ApiModelProperty(notes = "Linea_Unidad")
	@JsonProperty("UNIDAD_DIAN")
	private String unidadDian;
	
	@NotNull
	@ApiModelProperty(notes = "Linea_PrecioUnitario")
	@JsonProperty("PRECIO")
	private Double precio;
	
	@NotNull
	@ApiModelProperty(notes = "Linea_Importe")
	@JsonProperty("SUBTOTAL_LINEA")
	private Double subtotalLinea;

	@NotNull
	@ApiModelProperty(notes = "Linea_Cod_Articulo")
	@JsonProperty("LINEA_COD_ARTICULO")
	private String lineaCodArticulo;
	
	@NotNull
	@ApiModelProperty(notes = "DetaImpDTO")
	@JsonProperty("DETA_IMP")
	private DetaImpDTO detaImp;

}
