package com.modusoftware.aldeamo.compensar.reporteDataCruda.generate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
class FacturaElectronicaServiceApplicationTests {

	@Value("${main.url.email}")
	private String emailMainUrl;
	
	@Value("${main.url.sms}")
	private String smsMainUrl;
	
	@Value("${application.generate.defaultDateFormat}")
	private String dateFormat;

	DateTimeFormatter dtf;
	
	@PostConstruct
	public void init() {
		dtf = DateTimeFormatter.ofPattern(this.dateFormat);
	}
	
	@Test
	void executeEmailReportTest() {
		
		RestAssured
		.given()
		.log()
		.all()
		.queryParam("idJob", 1)
		.queryParam("fechaProceso", dtf.format(LocalDate.now()))
		.post(this.emailMainUrl)
		.then()
		.log()
		.all()
		.statusCode(HttpStatus.OK.value());
		
	}

	@Test
	void executeSmsReportTest() {
		
		RestAssured
		.given()
		.log()
		.all()
		.queryParam("idJob", 1)
		.queryParam("fechaProceso", dtf.format(LocalDate.now()))
		.post(this.smsMainUrl)
		.then()
		.log()
		.all()
		.statusCode(HttpStatus.OK.value());
		
	}
	
}
