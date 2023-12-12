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
	@ApiModelProperty(notes = "Columna_36")
	@JsonProperty("COLUMNA_36")
	private String Columna36;

	@NotNull
	@ApiModelProperty(notes = "Columna_37")
	@JsonProperty("COLUMNA_37")
	private String Columna37;

	@NotNull
	@ApiModelProperty(notes = "Columna_38")
	@JsonProperty("COLUMNA_38")
	private String Columna38;

	@NotNull
	@ApiModelProperty(notes = "IvaLinea")
	@JsonProperty("IVA_LINEA")
	private Double IvaLinea;//Campo que sustituye el taxAmt y el unroundedTaxAmt del DetaImpDTO

	@NotNull
	@ApiModelProperty(notes = "TaxRate")
	@JsonProperty("TAX_RATE")
	private Double TaxRate;//Campo que sustituye el taxRate del DetaImpDTO

	@NotNull
	@ApiModelProperty(notes = "Tax")
	@JsonProperty("TAX")
	private Double Tax;//Campo que sustituye el tax del DetaImpDTO
	
	/*@NotNull
	@ApiModelProperty(notes = "DetaImpDTO")//Este campo se elimina debido a que se elimino el data set de DETA_IMP
	@JsonProperty("DETA_IMP")
	private DetaImpDTO detaImp;*/

}
