package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.writer;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.AplicaDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.DetalleDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.HeadDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.HeadImpDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.ResolucionDTO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao.ResolucionDAO;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.BatchExecutionContextKeys;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.NumerosALetras;
import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util.exception.NoDataFoundException;

@Service
@StepScope
public class FacturaElectronicaWriter extends FlatFileItemWriter<HeadDTO> {

	@Value("${opcionesAdministrativas.facturaElectronica.generate.domEmisorNoExterior}")
	private String domEmisorNoExterior;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.domEmisorNoInterior}")
	private String domEmisorNoInterior;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.diasPago}")
	private String diasPago;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoDescuento}")
	private String montoDescuento;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoOtrosImp}")
	private String montoOtrosImp;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.misc09}")
	private String misc09;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.misc27}")
	private String misc27;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.misc28}")
	private String misc28;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.misc38}")
	private String misc38;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.misc39}")
	private String misc39;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalSerExen}")
	private String montoTotalSerExen;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalMerGrav}")
	private String montoTotalMerGrav;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalMerExen}")
	private String montoTotalMerExen;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.tipoDocIdEmisor}")
	private String tipoDocIdEmisor;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.tipoPersonaEmisor}")
	private String tipoPersonaEmisor;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalRecargos}")
	private String montoTotalRecargos;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.metodoPago}")
	private String metodoPago;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalGravado}")
	private String montoTotalGravado;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.montoTotalExento}")
	private String montoTotalExento;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.lineaMisc18}")
	private String lineaMisc18;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.lineaMisc19}")
	private String lineaMisc19;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.lineaMisc20}")
	private String lineaMisc20;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.impustoDescripcion.iva}")
	private String impuestoDescripcionIva;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.impustoDescripcion.ica}")
	private String impuestoDescripcionIca;

	@Value("#{jobParameters['" + BatchExecutionContextKeys.TXT_FILE_PATH_KEY + "']}")
	private String filePath;

	@Value("#{jobParameters['" + BatchExecutionContextKeys.TXT_FILE_NAME_KEY + "']}")
	private String fileName;

	@Value("#{jobParameters['" + BatchExecutionContextKeys.FILE_ENCODING + "']}")
	private String encoding;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.impuestos}")
	private String impuestos;

	@Value("${opcionesAdministrativas.facturaElectronica.generate.impuestos.rte.iva}")
	private String impuestosRteIva;

	@Autowired
	private ResolucionDAO resolucionDAO;

	@PostConstruct
	public void init() throws Exception {

		Resource file = new FileSystemResource(
				new StringBuilder().append(filePath).append("/").append(fileName).toString());

		super.setEncoding(encoding);
		super.setLineSeparator("");
		super.setResource(file);

		super.setLineAggregator(new LineAggregator<HeadDTO>() {

			public String aggregate(HeadDTO item) {

				try {
					ResolucionDTO resolucionDTO = new ResolucionDTO();

					String textoResolucion = "";
					String misc06 = "";
					String monedaDocumento;
					String TipoCfd="";

					if (item.getBillTo().getMonedaImp().equals("COP")) {
						monedaDocumento = "COP";
					} else {
						monedaDocumento = item.getMonedaDocu();
					}

					StringBuilder sbAuxiliar = new StringBuilder();

					if (item.getTipoCfd().equals("91")) {

						resolucionDTO = resolucionDAO.getDataResolucion("ALL",
							String.valueOf(item.getCompania().getTipo()), item.getTrxNumber(),
							item.getFechaDocu(), 1L);
						
						item.setHora("23:59:59");
						misc06 = "20";
						TipoCfd = item.getTipoCfd();
						
						if (item.getAplica() != null) {
							
							int i = 0;

							for (AplicaDTO aplica : item.getAplica()) {

								String datoAux3 = "";

								if (i == 0) {
									sbAuxiliar.append("[@@CRLF]¬*RFA").append("|");
								} else {
									sbAuxiliar.append("[@@CRLF]RFA").append("|");
								}

								if(item.getCompania().getAbreviatura().equals("EKC")) {
									datoAux3 = "ED." + aplica.getTrxNumberRelated();
								} else if (item.getCompania().getAbreviatura().equals("OAD") || item.getCompania().getAbreviatura().equals("PRI") || item.getCompania().getAbreviatura().equals("LIG")) {
									datoAux3 = "11." + aplica.getTrxNumberRelated();
								} else {
									datoAux3 = aplica.getTrxNumberRelated() ;
								}

								sbAuxiliar.append("IdRelacion").append("|")
								.append(datoAux3).append("|")
								.append(aplica.getCufeRelated()).append("|")
								.append(aplica.getTrxDateRelated()).append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|")
								.append("|");
		
							}
						}

						textoResolucion = "Autorizacion de numeracion DIAN FE No. " + resolucionDTO.getNroAprobacion()
								+ " rango del " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoInicial()
								+ " al " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoFinal() + " de "
								+ resolucionDTO.getFechaInicial() + ", vigencia de " + resolucionDTO.getMesesVigencia()
								+ " meses";


					} else if (item.getTipoCfd().equals("22")){
 

                        resolucionDTO = resolucionDAO.getDataResolucion("ALL",

                            String.valueOf(item.getCompania().getTipo()), item.getTrxNumber(),

                            item.getFechaDocu(), 1L);


                        misc06 = "22";
						TipoCfd ="91";

                        String hora = item.getFechaCreacion();

                        item.setHora(hora.substring(11, 19));

                        textoResolucion = "Autorizacion de numeracion DIAN FE No. " + resolucionDTO.getNroAprobacion()

                                + " rango del " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoInicial()

                                + " al " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoFinal() + " de "

                                + resolucionDTO.getFechaInicial() + ", vigencia de " + resolucionDTO.getMesesVigencia()

                                + " meses";

                    }else {

						resolucionDTO = resolucionDAO.getDataResolucion(item.getCompania().getAbreviatura(),
							String.valueOf(item.getCompania().getTipo()), item.getTrxNumber(),
							item.getFechaDocu(), 0L);


						misc06 = "10";
						TipoCfd = item.getTipoCfd();
						String hora = item.getFechaCreacion();
						item.setHora(hora.substring(11, 19));
						textoResolucion = "Autorizacion de numeracion DIAN FE No. " + resolucionDTO.getNroAprobacion()
								+ " rango del " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoInicial()
								+ " al " + resolucionDTO.getSerie() + " " + resolucionDTO.getRangoFinal() + " de "
								+ resolucionDTO.getFechaInicial() + ", vigencia de " + resolucionDTO.getMesesVigencia()
								+ " meses";
					}
					
					String docuEmisorSubString = item.getDocuEmisor();

					DecimalFormat df = new DecimalFormat("#0.00");

					
					Double subTotal = 0.0;
					Double totalImp = 0.0;
					Double totalImpUnround = 0.0;
					Double factura;
					
					if (item.getCompania().getTipo().equals("N") && !item.getMonedaDocu().equals("COP") && item.getBillTo().getMonedaImp().equals("COP")) {
						subTotal = Double.valueOf(Math.round(item.getSubtotal() * item.getTrm()));
						factura = Double.valueOf(String.valueOf(Math.round(item.getTotalFactura() * item.getTrm())));
					} else {
						subTotal = item.getSubtotal();
						factura = item.getTotalFactura();
					}
					
					
					int i = 0;
					
					Double ivaAcumulado = 0.0;
					
					StringBuilder sbDetalle = new StringBuilder();

					for (DetalleDTO detalle : item.getDetalle()) {

						String cantidadDecimal = df.format(detalle.getCantidad());
						
						String precio;
						String subtotalLinea;
						String taxAmt;
						String unroundedTax;
						
						if (item.getCompania().getTipo().equals("N") && !item.getMonedaDocu().equals("COP") && item.getBillTo().getMonedaImp().equals("COP")) {
							precio = df.format(detalle.getPrecio() * item.getTrm());
							subtotalLinea = df.format(Math.abs(Math.round(detalle.getSubtotalLinea() * item.getTrm())));
							//taxAmt = df.format(Math.abs(Math.round(detalle.getDetaImp().getTaxAmt() * item.getTrm())));
							//unroundedTax = df.format(Math.round(detalle.getDetaImp().getUnroundedTaxAmt() * item.getTrm()));
							ivaAcumulado += Math.abs(Math.round(detalle.getDetaImp().getTaxAmt() * item.getTrm()));
							taxAmt = df.format(Math.round((detalle.getSubtotalLinea() * (detalle.getDetaImp().getTaxRate()/100)) * item.getTrm()));
							unroundedTax = df.format(Math.round((detalle.getSubtotalLinea() * (detalle.getDetaImp().getTaxRate()/100)) * item.getTrm()));
							
						} else {
							precio = df.format(detalle.getPrecio());
							subtotalLinea = df.format(Math.abs(detalle.getSubtotalLinea()));
							taxAmt = df.format(Math.abs(detalle.getDetaImp().getTaxAmt()));
							unroundedTax = df.format(detalle.getDetaImp().getUnroundedTaxAmt());
							ivaAcumulado += Math.abs(detalle.getDetaImp().getTaxAmt());
						}
						
						
						String taxRateDecimal = df.format(detalle.getDetaImp().getTaxRate());
						

						

						sbDetalle.append("[@@CRLF]")
						/* 1 */.append(i == 0 ? "¬" : "").append((detalle.getDescripcion())).append("|")
						/* 2 */.append(cantidadDecimal == null ? "" : cantidadDecimal).append("|")
						/* 3 */.append(detalle.getUnidadDian() == null ? "" : detalle.getUnidadDian()).append("|")
						/* 4 */.append(precio).append("|")
						/* 5 */.append(subtotalLinea).append("|")
						/* 6 */.append("|")
						/* 7 */.append("|")
						/* 8 */.append("|")
						/* 9 */.append("|")
						/* 10 */.append("|")
						/* 11 */.append("|")
						/* 12 */.append("|")
						/* 13 */.append("|")
						/* 14 */.append("|")
						/* 15 */.append("|")
						/* 16 */.append(detalle.getLineaCodArticulo() == null ? "" : detalle.getLineaCodArticulo()).append("|")
						/* 17 */.append("|")
						/* 18 */.append("0").append("|")
						/* 19 */.append("0.00").append("|")
						/* 20 */.append("|")
						/* 21 */.append("|")
						/* 22 */.append("|")
						/* 23 */.append(taxRateDecimal == null ? "" : taxRateDecimal).append("|")
						/* 24 */.append(taxAmt).append("|")
						/* 25 */.append("|")
						/* 26 */.append("|")
						/* 27 */.append("|")
						/* 28 */.append("|")
						/* 29 */.append("|")
						/* 30 */.append("|")
						/* 31 */.append("|")
						/* 32 */.append(item.getCompania().getAbreviatura() == null ? "" : item.getCompania().getAbreviatura()).append("|")
						/* 33 */.append("|")
						/* 34 */.append("|")
						/* 35 */.append(detalle.getLineaCodArticulo() == null ? "" : detalle.getLineaCodArticulo()).append("|")
						/* 36 */.append("|")
						/* 37 */.append("|")
						/* 38 */.append("|")
						/* 39 */.append("|")
						/* 40 */.append("|")
						/* 41 */.append("|")
						/* 42 */.append("|")
						/* 43 */.append("|")
						/* 44 */.append("|")
						/* 45 */.append("|")
						/* 46 */.append("|")
						/* 47 */.append("|")
						/* 48 */.append("|")
						/* 49 */.append("").append("|")
						/* 50 */.append("").append("|")
						/* 51 */.append("").append("|")
						/* 52 */.append("|")
						/* 53 */.append("|")
						/* 54 */.append("|")
						/* 55 */.append("|")
						/* 56 */.append("|")
						/* 57 */.append("|")
						/* 58 */.append("|")
						/* 59 */.append("|")
						/* 60 */.append("|")
						/* 61 */.append("|")
						/* 62 */.append("|")/* 63 */.append("|")
						/* 64 */.append("|")/* 65 */.append("|")/* 66 */.append("|")/* 67 */.append("|")
						/* 68 */.append("|")/* 69 */.append("|")/* 70 */.append("|")/* 71 */.append("|")
						/* 72 */.append("|")/* 73 */.append("|")/* 74 */.append("|")/* 75 */.append("|")
						/* 76 */.append("|")/* 77 */.append("|")/* 78 */.append("|")/* 79 */.append("|")
						/* 80 */.append("|")/* 81 */.append("|")/* 82 */.append("|")/* 83 */.append("|")
						/* 84 */.append("ES").append("|")/* 85 */.append("|")/* 86 */.append("|")/* 87 */.append("|")
						/* 88 */.append("|")
						/* 89 */.append("|")/* 90 */.append("|")/* 91 */.append("|")
						/* 92 */.append("|")/* 93 */.append("|")/* 94 */.append("|")/* 95 */.append("|")
						/* 96 */.append("|")/* 97 */.append("|")/* 98 */.append("|")
						/* 99 */.append(detalle.getLineaCodArticulo() == null ? "" : detalle.getLineaCodArticulo()).append((char) 92).append("I").append("|")								
						/* 100 */.append("TR").append("|")
						/* 101 */.append(df.format(detalle.getDetaImp().getTaxRate())).append("|")
						/* 102 */.append(unroundedTax).append("|")
						/* 103 */.append(subtotalLinea).append("|")
						/* 104 */.append("").append("|")
						/* 105 */.append("").append("|")
						/* 106 */.append("01").append("|")
						/* 107 */.append("");

						i++;

					}

					//Impuestos
					Long reteiva = 0L;
					Double restaIva = 0.0;
					
					
					StringBuilder sbImpuestos = new StringBuilder();
					
					i = 0;

					Double diferenciaIva = 0.0;
					Double retenciones = 0.0;
					Double ivaCalculado = 0.0;
					
					for (HeadImpDTO impDto : item.getHeadImp()) {

						String taxableAmount = "";
						String montoImporte = "";

						if (impDto.getTax().equals("CO_IVA")) {

							/* 1 */sbImpuestos.append(i == 0 ? "¬TR" : "[@@CRLF]TR").append("|")
							/* 2 */.append(impuestoDescripcionIva).append("|");

							ivaCalculado = subTotal * (impDto.getPorcImp() / 100);
							diferenciaIva = ivaCalculado - Math.abs(ivaAcumulado);

							if (Math.abs(diferenciaIva) > 2 && Math.abs(diferenciaIva) < 1000) {
								ivaAcumulado =  Double.valueOf(Long.valueOf(Math.round(ivaAcumulado + diferenciaIva)).toString());
							}

							if (impDto.getPorcImp().equals(0.0)) {
								montoImporte = df.format(impDto.getTotImp());
							} else {
								montoImporte = df.format(ivaAcumulado);
							}
							

						} 

						if (item.getCompania().getTipo().equals("N") && !item.getMonedaDocu().equals("COP") && item.getBillTo().getMonedaImp().equals("COP")) {
							taxableAmount = df.format(Math.abs(Math.round(impDto.getTaxableAmt() * item.getTrm())));
						} else {
							taxableAmount = df.format(Math.abs(impDto.getTaxableAmt()));
						}

						if (impuestos.contains(impDto.getTax())) {

							/* 1 */sbImpuestos.append("[@@CRLF]RE").append("|");

							if (impDto.getTax().equals("LIG_AR_RENTA")) {
								/* 2 */sbImpuestos.append("06").append("|");
							} else if (impDto.getTax().equals("OAD_AR_ICA_DB")
									|| impDto.getTax().equals("FTX_AR_ICA_BGTA")
									|| impDto.getTax().equals("PTQ_AR_ICA_SABANETA")) {
								/* 2 */sbImpuestos.append(impuestoDescripcionIca).append("|");
								montoImporte = df.format(impDto.getTotImp());
							} else {
								/* 2 */sbImpuestos.append("05").append("|");
								if (impDto.getTax().equals("FTX_AR_RETEIVA")
										&& !String.valueOf(item.getCompania().getTipo()).equals("N")) {
									montoImporte = df.format(ivaAcumulado * 0.15);
								}
							}
						}

						String taxTotal = "0.0";

						if (impDto.getTax().equals("FTX_AR_RETEIVA") || impDto.getTax().equals("PRI_AR_RETEIVA")
								|| impDto.getTax().equals("AZE_AR_RETEIVA") || impDto.getTax().equals("OAD_AR_RETEIVA")
								|| impDto.getTax().equals("OAD_AR_ICA_DB") || impDto.getTax().equals("FTX_AR_ICA_BGTA")
								|| impDto.getTax().equals("LIG_AR_RENTA") || impDto.getTax().equals("PTQ_AR_RETEIVA")
								|| impDto.getTax().equals("PTQ_AR_ICA_SABANETA")) {
							

							if (item.getCompania().getTipo().equals("N")) {
								totalImp = impDto.getTotImp();
								taxTotal = montoImporte = df.format(Math.round(Double.valueOf(impDto.getUnroundedTaxAmt())));
							} else {
								totalImp = impDto.getTotImp() * item.getTrm(); 
								taxTotal = montoImporte = df.format(Double.valueOf(impDto.getUnroundedTaxAmt()));
							}

							reteiva = Math.abs(Math.round(totalImp));

						} else if (impDto.getTax().equals("CO_IVA")) {
							taxTotal = df.format(ivaAcumulado);
							
							if (item.getCompania().getTipo().equals("N") && !item.getMonedaDocu().equals("COP") && item.getBillTo().getMonedaImp().equals("COP")) {
								totalImp = impDto.getTotImp() * item.getTrm(); 
								totalImpUnround = Double.valueOf(impDto.getUnroundedTaxAmt()) * item.getTrm();
							} else {
								totalImp = impDto.getTotImp();
								totalImpUnround = Double.valueOf(impDto.getUnroundedTaxAmt());
							}

							if (Math.abs(Math.abs(totalImp) - Math.abs(totalImpUnround)) > 1) {
								restaIva = Math.abs(Math.abs(totalImp) - Math.abs(totalImpUnround));
							}


						} else if (impDto.getTax().equals("LIG_AR_RENTA")) {
							taxTotal = df.format(Math.abs(impDto.getTotImp()));
						}

						if (!impDto.getTax().equals("CO_IVA")) {
							retenciones += Double.valueOf(montoImporte);
						}

						/* 3 */sbImpuestos.append(montoImporte).append("|")
						/* 4 */.append(df.format(impDto.getPorcImp())).append("|")
						/* 5 */.append("").append("|")
						/* 6 */.append("").append("|")
						/* 7 */.append("").append("|")
						/* 8 */.append(taxableAmount).append("|")
						/* 9 */.append(taxTotal);
						
						i++;

					}

                    //Cabecera
					String attribute2 = "";
					String attribute8 = "";
					String partyName = "";
					String adress1 = "";
					String country = "";
					String city = "";
					
					String postalCode = "";

					String nitCliente = "";

					if (item.getBillTo() != null && item.getBillTo().getInfoBillTo() != null) {

						if (item.getBillTo().getInfoBillTo().getAttribute2() != null) {
							
							if (item.getBillTo().getInfoBillTo().getAttribute2().equals("31")) {
								nitCliente = item.getNitCliente().substring(0, 9);
								
							} else if (item.getBillTo().getInfoBillTo().getAttribute2().equals("13")) {
								nitCliente = item.getCcNatural();
							} else {
								nitCliente = item.getNitCliente();
							}
							
							attribute2 = item.getBillTo().getInfoBillTo().getAttribute2().toString();
						}

						if (item.getBillTo().getInfoBillTo().getAttribute8() != null) {
							attribute8 = item.getBillTo().getInfoBillTo().getAttribute8().toString();
						}

						if (item.getBillTo().getPartyName() != null) {
							partyName = item.getBillTo().getPartyName();
						}

						if (item.getBillTo().getAddress1() != null) {
							adress1 = item.getBillTo().getAddress1();
						}

						if (item.getBillTo().getCountry() != null) {
							country = item.getBillTo().getCountry();
						}

						if (item.getBillTo().getCity() != null) {
							city = item.getBillTo().getCity();
						}

						if (item.getBillTo().getPostalCode() != null) {
							postalCode = item.getBillTo().getPostalCode().toString();
						}

					} else {
						return "";
					}					
					
					String campo56 = "";
					String campo187 = "";
					
					if (item.getMonedaDocu().equals("COP") && item.getCompania().getAbreviatura().equals("PTQ") && item.getBillTo().getPartyName().equals("NUVANT S.A.S.")) {
						
						campo56 = item.getCompania().getTipo().equals("N") ? df.format(Math.round(Math.abs(factura - reteiva - restaIva))) : df.format(Math.abs(factura - reteiva - restaIva));
						campo187 =  df.format(ivaAcumulado + subTotal - retenciones);
					} else {
						
						if (item.getMonedaDocu().equals("COP")) {
							
							campo187 =  df.format(ivaAcumulado + subTotal - retenciones);
							campo56 =  df.format(ivaAcumulado + subTotal);

						} else {
							if (Math.abs(diferenciaIva) > 0) {
								campo187 =  df.format(ivaAcumulado + subTotal - retenciones);
								campo56 =  df.format(ivaAcumulado + subTotal);
							} else {
								campo56 = item.getCompania().getTipo().equals("N") ? df.format(Math.abs(factura + reteiva - restaIva)) : df.format(Math.abs(factura + reteiva - restaIva));
								campo187 = item.getCompania().getTipo().equals("N") ? df.format(Math.abs(factura - restaIva)) : df.format(Math.abs(factura - restaIva));
								
							}
						}
						
					}
					
					
					StringBuilder sb = new StringBuilder();
					
					/* 1 */sb.append("~").append(item.getTrxNumber()).append("|")
							/* 2 */.append(item.getNombreEmisor() == null ? "" : item.getNombreEmisor()).append("|")
							/* 3 */.append(item.getNitEmisor() == null ? "" : item.getNitEmisor()).append("|")
							/* 4 */.append(item.getEmisorDireccion() == null ? "" : item.getEmisorDireccion())
							.append("|")/* 5 */.append(domEmisorNoExterior).append("|")
							/* 6 */.append(domEmisorNoInterior).append("|")
							/* 7 */.append(item.getEmisorDpto() == null ? "" : item.getEmisorDpto()).append("|")
							/* 8 */.append(item.getEmisorCiudad() == null ? "" : item.getEmisorCiudad()).append("|")
							/* 9 */.append(item.getCodDptoEmisor() == null ? "" : item.getCodDptoEmisor()).append("|")
							/* 10 */.append(item.getCodCiudadEmisor().substring(item.getCodDptoEmisor().length(), item.getCodCiudadEmisor().length()))
							.append("|")// fila 10?
							/* 11 */.append(item.getEmisorDpto() == null ? "" : item.getEmisorDpto()).append("|")
							/* 12 */.append(item.getEmisorPais() == null ? "" : item.getEmisorPais()).append("|")
							/* 13 */.append(item.getCompania().getCodigoPostal() == null ? ""
									: item.getCompania().getCodigoPostal())
							.append("|")/* 14 */.append("|")/* 15 */.append("|")/* 16 */.append("|")/* 17 */.append("|")
							/* 18 */.append("|")/* 19 */.append("|")/* 20 */.append("|")/* 21 */.append("|")
							/* 22 */.append("|")/* 23 */.append("|")/* 24 */.append("|")/* 25 */.append("|")
							/* 26 */.append("DIAN 2.1|")
							/* 27 */.append(resolucionDTO.getSerie()).append("|")
							/* 28 */.append(resolucionDTO.getNroAprobacion()).append("|")
							/* 29 */.append(
									item.getDiasPago() == null ? "1" : (item.getDiasPago().equals("0") ? "1" : "2"))
							.append("|")/* 30 */.append(item.getFechaDocu() == null ? "" : item.getFechaDocu())
							.append("|")/* 31 */.append(item.getHora()).append("|")
							/* 32 */.append(item.getEmisorDireccion() == null ? "" : item.getEmisorDireccion())
							.append("|")/* 33 */.append(domEmisorNoExterior).append("|")
							/* 34 */.append(domEmisorNoInterior).append("|")
							/* 35 */.append(item.getEmisorDpto() == null ? "" : item.getEmisorDpto()).append("|")
							/* 36 */.append(item.getEmisorCiudad() == null ? "" : item.getEmisorCiudad()).append("|")
							/* 37 */.append(item.getCodDptoEmisor() == null ? "" : item.getCodDptoEmisor()).append("|")
							/* 38 */.append(item.getCodCiudadEmisor().substring(item.getCodDptoEmisor().length(), item.getCodCiudadEmisor().length()))
							.append("|")// fila 10?
							/* 39 */.append(item.getEmisorDpto() == null ? "" : item.getEmisorDpto()).append("|")
							/* 40 */.append(item.getEmisorPais() == null ? "" : item.getEmisorPais()).append("|")
							/* 41 */.append("|")
							/* 42 */.append(partyName == null ? "" : partyName).append("|")
							/* 43 */.append(nitCliente).append("|")
							/* 44 */.append(adress1 == null ? "" : adress1).append("|")
							/* 45 */.append("|")
							/* 46 */.append("|")
							/* 47 */.append(StringUtils.isBlank(item.getBillTo().getState()) ? "ND" : item.getBillTo().getState()).append("|")
							/* 48 */.append(city == null ? "" : city).append("|")
							/* 49 */.append(StringUtils.isBlank(item.getBillTo().getCodDptoReceptor()) ? "ND" : item.getBillTo().getCodDptoReceptor()).append("|")
							/* 50 */.append(StringUtils.isBlank(item.getBillTo().getCodCiudadReceptor()) ? "ND" : item.getBillTo().getCodCiudadReceptor()).append("|")
							/* 51 */.append(StringUtils.isBlank(item.getBillTo().getState()) ? "ND" : item.getBillTo().getState()).append("|")
							/* 52 */.append(country == null ? "" : country).append("|")
							/* 53 */.append(postalCode == null ? "" : postalCode).append("|")
							/* 54 */.append(df.format(subTotal)).append("|")
							/* 55 */.append(df.format(subTotal)).append("|")
							/* 56 */.append(campo56).append("|")
							/* 57 */.append("1").append("|")
							/* 58 */.append(TipoCfd).append("|")
							/* 59 */.append(item.getMonedaDocu().equals("COP") ? item.getObservaciones() != null ? item.getObservaciones() : "" : "TRM Documento " + item.getTrm()).append("|")
							/* 60 */.append("|")
							/* 61 */.append("|")
							/* 62 */.append("|")
							/* 63 */.append("|")
							/* 64 */.append("|")
							/* 65 */.append("|")
							/* 66 */.append(item.getOrdenCompra() == null ? "" : item.getOrdenCompra()).append("|")
							/* 67 */.append("|")
							/* 68 */.append("|")
							/* 69 */.append("|")
							/* 70 */.append("|")
							/* 71 */.append("|")
							/* 72 */.append("|")
							/* 73 */.append("|")
							/* 74 */.append("|")
							/* 75 */.append("|")
							/* 76 */.append("|")
							/* 77 */.append("|")
							/* 78 */.append("|")
							/* 79 */.append("|")
							/* 80 */.append("|")
							/* 81 */.append("|")
							/* 82 */.append("|")
							/* 83 */.append(monedaDocumento).append("|")
							/* 84 */.append(diasPago).append("|")
							/* 85 */.append("|")
							/* 86 */.append("|")
							/* 87 */.append("|")
							/* 88 */.append("|")
							/* 89 */.append(montoDescuento).append("|")
							/* 90 */.append(item.getDetalle().size()).append("|")
							/* 91 */.append(item.getFechaVencimiento() == null ? "" : item.getFechaVencimiento()).append("|")
							/* 92 */.append("|")
							/* 93 */.append("|")
							/* 94 */.append(item.getCodVendedor() == null ? "" : item.getCodVendedor()).append("|")
							/* 95 */.append(item.getNombreVendedor() == null ? "" : item.getNombreVendedor()).append("|")
							/* 96 */.append("|")
							/* 97 */.append("|")
							/* 98 */.append("|")
							/* 99 */.append("|")
							/* 100 */.append(campo56.contains(",") ? NumerosALetras.convertirNumeroALetras(Double.valueOf(campo56.replace(",",".")), monedaDocumento): NumerosALetras.convertirNumeroALetras(Double.valueOf(campo56), monedaDocumento)).append("|")
							/* 101 */.append("|")
							/* 102 */.append("|")
							/* 103 */.append("|")
							/* 104 */.append("|")
							/* 105 */.append("|")
							/* 106 */.append(item.getNombreEmisor() == null ? "" : item.getNombreEmisor()).append("|")
							/* 107 */.append("|")
							/* 108 */.append(montoOtrosImp).append("|")
							/* 109 */.append(campo56).append("|")
							/* 110 */.append("|")/* 111 */.append("|")
							/* 112 */.append("|")
							/* 113 */.append("|")
							/* 114 */.append(item.getFechaVencimiento() == null ? "" : item.getFechaVencimiento()).append("|")/* 115 */.append("|")/* 116 */.append("|")/* 117 */.append("|")
							/* 118 */.append(misc06).append("|")
							/* 119 */.append("|")
							/* 120 */.append("|")
							/* 121 */.append(misc09).append("|")
							/* 122 */.append("|")
							/* 123 */.append("|")
							/* 124 */.append("|")
							/* 125 */.append("|")
							/* 126 */.append("|")
							/* 127 */.append(item.getMailEnvio() == null ? "" : item.getMailEnvio()).append("|")
							/* 128 */.append("|")/* 129 */.append(item.getRemision() == null ? "" : item.getRemision())
							.append("|")/* 130 */.append(item.getPedido() == null ? "" : item.getPedido()).append("|")
							/* 131 */.append(textoResolucion).append("|")/* 132 */.append("|")/* 133 */.append("|")
							/* 134 */.append("|")/* 135 */.append("|")/* 136 */.append("|")/* 137 */.append("|")
							/* 138 */.append(!item.getCompania().getTipo().equals("N") ? "R-99-PN" : item.getCompania().getRegimen()).append("|")
							/* 139 */.append(misc27).append("|")
							/* 140 */.append(misc28).append("|")
							/* 141 */.append("|")
							/* 142 */.append(item.getCompania().getAbreviatura()).append("-")
							.append(item.getTipoCfd()).append("-").append(item.getCompania().getTipo()).append("-")
							.append(resolucionDTO.getSerie()).append("-").append(item.getTrxNumber()).append("|")
							/* 143 */.append("|")
							/* 144 */.append(docuEmisorSubString.substring(10, 11) == null ? ""
									: docuEmisorSubString.substring(10, 11))
							.append("|")
							/* 145 */.append("|")
							/* 146 */.append(item.getCompania().getTipo().equals("N") ? item.getDigitoVerificacion() : "").append("|")
							/* 147 */.append(monedaDocumento).append("|")
							/* 148 */.append("|")/* 149 */.append("|")/* 150 */.append(misc38).append("|")
							/* 151 */.append(misc39).append("|")/* 152 */.append("|")
							/* 153 */.append(!item.getBillTo().getMonedaImp().equals("COP") ? df.format(item.getTrm()) : "1.00")
							.append("|")/* 154 */.append("|")/* 155 */.append("|")
							/* 156 */.append(item.getCompania().getEmail() == null ? "" : item.getCompania().getEmail()).append("|")
							/* 157 */.append("0.00").append("|")
							/* 158 */.append(montoTotalSerExen).append("|")
							/* 159 */.append(montoTotalMerGrav).append("|")
							/* 160 */.append(montoTotalMerExen).append("|")/* 161 */.append(resolucionDTO.getFechaResolucion()).append("|")
							/* 162 */.append(item.getRazonReferencia() != null ? item.getRazonReferencia() : "").append("|")/* 163 */.append("|")
							/* 164 */.append("|")/* 165 */.append(resolucionDTO.getRangoResolucion()).append("|")// query
							/* 166 */.append("|")
							/* 167 */.append(String.valueOf(item.getCompania().getTipo()).equals("N") ? "01" : "02").append("|")
							/* 168 */.append(item.getCompania().getResponsabilidad() == null ? ""
									: item.getCompania().getResponsabilidad())
							.append("|")/* 169 */.append(item.getNroEnvio() == null ? "" : item.getNroEnvio())
							.append("|")/* 170 */.append("|")/* 171 */.append("|")/* 172 */.append("|")
							/* 173 */.append("|")/* 174 */.append("|")/* 175 */.append("|")
							/* 176 */.append(item.getTrxNumber() == null ? "" : item.getTrxNumber()).append("|")
							/* 177 */.append(!item.getBillTo().getMonedaImp().equals("COP") ? df.format(item.getTrm()) : "1.00")
							.append("|")
							/* 178 */.append(tipoDocIdEmisor).append("|")
							/* 179 */.append(attribute2 == null ? "" : attribute2).append("|")
							/* 180 */.append(tipoPersonaEmisor).append("|")
							/* 181 */.append(attribute8.equals("N") ? "2" : "1").append("|")/* 182 */.append("|")
							/* 183 */.append("|")/* 184 */.append("|")/* 185 */.append("|")
							/* 186 */.append(montoTotalRecargos).append("|")
							/* 187 */.append(campo187).append("|")
							/* 188 */.append(resolucionDTO.getClaveTecnica()).append("|")
							/* 189 */.append("|")/* 190 */.append(metodoPago).append("|")
							/* 191 */.append(item.getCodReferencia() != null ? item.getCodReferencia() : "").append("|")
							/* 192 */.append(montoTotalGravado).append("|")
							/* 193 */.append(montoTotalExento);

										

					sb.append(sbDetalle).append(sbImpuestos).append(sbAuxiliar.toString());

					return sb.toString();

				} catch (EmptyResultDataAccessException e) {
					throw new NoDataFoundException(MessageFormat.format("No se encuentran datos de resolucion parametrizados para la compania {0} de tipo {1} para la resolucion {2} y la fecha {3}", item.getCompania().getAbreviatura(), item.getCompania().getTipo(), item.getTrxNumber(), item.getFechaDocu()), e);
				}
			}
		});

		//super.setLineSeparator("\n");

	}
}