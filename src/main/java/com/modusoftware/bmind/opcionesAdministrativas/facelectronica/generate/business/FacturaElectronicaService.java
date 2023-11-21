package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.business;

import java.io.File;

import org.springframework.stereotype.Service;

import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DataDSDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuDianDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuSatDTO;

@Service
public interface FacturaElectronicaService {
	
	public File executeDocumentGeneration(DataDSDTO request) throws Exception;
	
	public void insertDocuSat(DocuSatDTO dto) throws Exception;
	
	public void insertDocuDian(DocuDianDTO dto) throws Exception;
	
}
