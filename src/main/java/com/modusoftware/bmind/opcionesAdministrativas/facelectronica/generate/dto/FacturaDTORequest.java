package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTORequest {
    
    @NotNull
	@ApiModelProperty(notes = "encodeXML")
    private String encodeXML;

    @NotNull
	@ApiModelProperty(notes = "CompaniaDTO")
    private CompaniaDTO compania;

}
