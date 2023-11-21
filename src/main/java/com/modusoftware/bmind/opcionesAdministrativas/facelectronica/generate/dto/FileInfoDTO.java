package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileInfoDTO {
	
	@NotNull
	@ApiModelProperty(notes = "file")
	private byte[] file;
	
	@NotNull
	@ApiModelProperty(notes = "encoding")
	private String encoding;
	
	@NotNull
	@ApiModelProperty(notes = "filename")
	private String filename;
	
	@NotNull
	@ApiModelProperty(notes = "contentLength")
	private Long contentLength;
	
	@NotNull
	@ApiModelProperty(notes = "contentType")
	private String contentType;

}
