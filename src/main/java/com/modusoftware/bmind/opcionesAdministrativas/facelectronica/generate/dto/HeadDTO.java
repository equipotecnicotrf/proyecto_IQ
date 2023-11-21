package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadDTO {
	
	@JsonProperty("TRX_NUMBER")
	private String trxNumber;
	
	@JsonProperty("NOMBRE_EMISOR")
	private String nombreEmisor;
	
	@JsonProperty("NIT_EMISOR")
	private Long nitEmisor;
	
	@JsonProperty("EMISOR_DIRECCION")
	private String emisorDireccion;
	
	@JsonProperty("EMISOR_DPTO")
	private String emisorDpto;
	
	@JsonProperty("FECHA_CREACION")
	private String fechaCreacion;
	
	@JsonProperty("HORA")
	private String hora;
	
	@JsonProperty("EMISOR_CIUDAD")
	private String emisorCiudad;
	
	@JsonProperty("COD_DPTO_EMISOR")
	private String codDptoEmisor;
	
	@JsonProperty("COD_CIUDAD_EMISOR")
	private String codCiudadEmisor;
	
	@JsonProperty("EMISOR_PAIS")
	private String emisorPais;

	@JsonProperty("DIAS_PAGO")
	private Long diasPago;
	
	@JsonProperty("FECHA_DOCU")
	private String fechaDocu;
	
	@JsonProperty("NIT_CLIENTE")
	private String nitCliente;
	
	@JsonProperty("CC_NATURAL")
	private String ccNatural;

	@JsonProperty("SUBTOTAL")
	private Double subtotal;
	
	@JsonProperty("TIPOCFD")
	private String tipoCfd;
	
	@JsonProperty("ORDEN_COMPRA")
	private String ordenCompra;
	
	@JsonProperty("MONEDA_DOCU")
	private String monedaDocu;
	
	@JsonProperty("FECHA_VENCIMIENTO")
	private String fechaVencimiento;
	
	@JsonProperty("COD_VENDEDOR")
	private Long codVendedor;
	
	@JsonProperty("NOMBRE_VENDEDOR")
	private String nombreVendedor;
	
	@JsonProperty("TOTALFACTURA")
	private Double totalFactura;
	
	@JsonProperty("PEDIDO")
	private String pedido;

	@JsonProperty("DOCU_EMISOR")
	private String docuEmisor;
	
	@JsonProperty("TRM")
	private Double trm;
	
	@JsonProperty("NRO_ENVIO")
	private Double nroEnvio;
	
	@JsonProperty("MAIL_ENVIO")
	private String mailEnvio;
	
	@JsonProperty("DIGITOVERIFICACION")
	private String digitoVerificacion;
	
	@JsonProperty("RAZONREFERENCIA")
	private String razonReferencia;
	
	@JsonProperty("CODREFERENCIA")
	private String codReferencia;
	
	@JsonProperty("REMISION")
	private Double remision;
	
	@JsonProperty("BILL_TO")
	private BillToDTO billTo;
	
	@JsonProperty("DETALLE")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<DetalleDTO> detalle;
	
	@JsonProperty("HEAD_IMP")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<HeadImpDTO> headImp;	

	@JsonProperty("APLICA")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<AplicaDTO> aplica;

	@JsonProperty("CUSTOMER_TRX_ID")
	private String customerTransactionId;

	@JsonProperty("OBSERVACIONES")
	private String observaciones;
	
	private CompaniaDTO compania;	
		
}