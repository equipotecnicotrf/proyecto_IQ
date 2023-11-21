package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.business.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.business.FacturaElectronicaService;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.conf.GenerateFacturaElectronicaConfig;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DataDSDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuDianDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuSatDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao.DocuDIANDAO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao.DocuSatDAO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.BatchExecutionContextKeys;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.exception.NoDataFoundException;
import com.modusoftware.commons.util.exception.BatchProcessException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FacturaElectronicaServiceImpl implements FacturaElectronicaService {

	private static final Logger log = LogManager.getLogger(FacturaElectronicaServiceImpl.class);

	@Autowired
	@Qualifier(GenerateFacturaElectronicaConfig.GENERATE_JOB)
	private Job generateFacturaElectronicaFile;

	@Autowired
	//@Qualifier(FacturaElectronicaServiceApplication.JOB_LAUNCHER_FACTURA_ELECTRONICA)
	private JobLauncher launcher;

	@Value("${oa.facturaElectronica.file.temp.dir.path}")
	private String filePath;

	@Value("${oa.facturaElectronica.file.name}")
	private String fileName;

	@Value("${oa.facturaElectronica.file.encoding}")
	private String fileEncoding;
	
	@Autowired
	private DocuSatDAO dao;
	
	@Autowired
	private DocuDIANDAO docuDianDAO;

	@Override
	public File executeDocumentGeneration(DataDSDTO request) throws Exception, NoDataFoundException {
		try {

			Map<String, JobParameter> params = new HashMap<>();

			Gson gson = new Gson();

			params.put(BatchExecutionContextKeys.TIME_KEY, new JobParameter(gson.toJson(System.currentTimeMillis())));
			params.put(BatchExecutionContextKeys.REQUEST, new JobParameter(gson.toJson(request)));
			params.put(BatchExecutionContextKeys.TXT_FILE_PATH_KEY, new JobParameter(filePath));
			params.put(BatchExecutionContextKeys.TXT_FILE_NAME_KEY, new JobParameter(fileName));
			params.put(BatchExecutionContextKeys.FILE_ENCODING, new JobParameter(fileEncoding));

			JobParameters param = new JobParameters(params);

			JobExecution je = this.launcher.run(generateFacturaElectronicaFile, param);

			if (je.getStatus().equals(BatchStatus.COMPLETED)) {
				
				return new FileSystemResource(new StringBuilder().append(this.filePath).append("/").append(this.fileName).toString()).getFile();
				
			} else {
				if (je.getExitStatus().getExitDescription().contains("NoDataFoundException")) {
					throw new NoDataFoundException("je.getExitStatus().getExitDescription()");
				}
				throw new BatchProcessException("Error in executeDocumentGeneration");
				
			}
			
		}  catch (NoDataFoundException e) {
			log.error("Error in executeDocumentGeneration", e);
			throw e;

		} catch (Throwable e) {
			log.error("Error in executeDocumentGeneration", e);
			throw new BatchProcessException("Error in executeDocumentGeneration", e);
		}

	}

	@Override
	public void insertDocuSat(DocuSatDTO dto) throws Exception {
		dao.insertDocuSat(dto.getCompania(), dto.getDocumento(), dto.getFechaEvento(), dto.getCufe(), dto.getEmail(), dto.getTipoCfd(), dto.getTrxId()); 
	}

	@Override
	public void insertDocuDian(DocuDianDTO dto) throws Exception {
		docuDianDAO.insertDocuDian(dto.getCompania(), dto.getTipoDocumento(), dto.getNumeroDocumento(), dto.getCufeDocumento(), dto.getCufeDian(), dto.getCodigoEvento(), dto.getFechaEvento());
	}
}
