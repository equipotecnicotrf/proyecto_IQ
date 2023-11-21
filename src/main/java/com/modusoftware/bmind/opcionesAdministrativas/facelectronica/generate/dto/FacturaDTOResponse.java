package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTOResponse {
    
    @NotNull
	@ApiModelProperty(notes = "content file")
    private String content;
    
    private String customerTransactionId;
    
    private String tipoCfd;
    
    private String documento;

}
