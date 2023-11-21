package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.api;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.business.FacturaElectronicaService;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DataDSDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuDianDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DocuSatDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.FacturaDTORequest;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.FacturaDTOResponse;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.exception.NoDataFoundException;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/oa/ekomercio/facturaElectronica")
@Log4j2
public class FacturaElectronicaAPI/* extends MSGenericRestController */ {

	private static final Logger log = LogManager.getLogger(FacturaElectronicaAPI.class);

	@Value("${application.generate.defaultDateFormat}")
	private String defaultDateFormat;

	@Autowired
	private FacturaElectronicaService service;

	@PostMapping(consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE, value = "/generateDoc")
	public ResponseEntity<FacturaDTOResponse> generateDoc(HttpServletRequest req,
			@Valid @RequestBody(required = true) FacturaDTORequest dto) {
		try {

			// 200
			byte[] xmlBytes = Base64.getDecoder().decode(dto.getEncodeXML());
			String xmlStr = StringUtils.stripAccents(new String(xmlBytes));

			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			DataDSDTO dataDS = xmlMapper.readValue(xmlStr, DataDSDTO.class);

			if (dataDS.getHead() != null && !dataDS.getHead().isEmpty()) {
				dataDS.setCompania(dto.getCompania());

				File file = this.service.executeDocumentGeneration(dataDS);

				FacturaDTOResponse response = new FacturaDTOResponse();
				response.setContent(FileUtils.readFileToString(file));
				response.setCustomerTransactionId(dataDS.getHead().get(0).getCustomerTransactionId());
				response.setTipoCfd(dataDS.getHead().get(0).getTipoCfd());
				response.setDocumento(dataDS.getHead().get(0).getTrxNumber());

				return new ResponseEntity<FacturaDTOResponse>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<FacturaDTOResponse>(HttpStatus.NO_CONTENT);
			}

		} catch (DateTimeParseException ex) {
			// 400
			return new ResponseEntity<FacturaDTOResponse>(HttpStatus.BAD_REQUEST);
		} catch (NoDataFoundException e) {
			// 204
			log.error("Error in executeEmailReport", e);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			// 500
			log.error("Error in executeEmailReport", e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE, value = "/insertDocuSat")
	public ResponseEntity<Void> insertDocuSat(HttpServletRequest req,
			@Valid @RequestBody(required = true) DocuSatDTO dto) {
		try {

			this.service.insertDocuSat(dto);

			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (DateTimeParseException ex) {
			// 400

			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// 500
			log.error("Error in executeEmailReport", e);

			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE, value = "/insertDocuDian")
	public ResponseEntity<Void> insertDocuDian(HttpServletRequest req,
			@Valid @RequestBody(required = true) DocuDianDTO dto) {
		try {

			this.service.insertDocuDian(dto);

			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (DateTimeParseException ex) {
			// 400

			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// 500
			log.error("Error in executeEmailReport", e);

			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
