package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import lombok.Data;

@Data
public class DocuSatDTO {
	
	private String compania;
	private String documento;
	private String fechaEvento;
	private String cufe;
	private String email;
	private String tipoCfd;
	private String trxId;

}
