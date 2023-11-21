package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolucionDTO {
	
	private String serie;
	private String nroAprobacion;
	private String fechaResolucion;
	private String rangoResolucion;
	
	private String rangoInicial;
	private String rangoFinal;
	private String fechaInicial;
	private String mesesVigencia;
	private String claveTecnica;

}
