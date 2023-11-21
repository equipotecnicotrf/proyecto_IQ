package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NumerosALetras {
    private static final String[] UNIDADES = { "cero ", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ",
            "nueve ", "diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciséis ", "diecisiete ", "dieciocho ",
            "diecinueve " };
    private static final String[] DECENAS = { "", "", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ",
            "setenta ", "ochenta ", "noventa " };
    private static final String[] CENTENAS = { "", "ciento ", "doscientos ", "trescientos ", "cuatrocientos ", "quinientos ",
            "seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
    private static final Map<Long, String> EXCEPCIONES;

    static {
        Map<Long, String> excepciones = new HashMap<>();
        excepciones.put(21L, "veintiun ");
        excepciones.put(22L, "veintidos ");
        excepciones.put(23L, "veintitres ");
        excepciones.put(24L, "veinticuatro ");
        excepciones.put(25L, "veinticinco ");
        excepciones.put(26L, "veintiseis ");
        excepciones.put(27L, "veintisiete ");
        excepciones.put(28L, "veintiocho ");
        excepciones.put(29L, "veintinueve ");
        excepciones.put(100L, "cien ");
        excepciones.put(1000L, "mil ");

        EXCEPCIONES = Collections.unmodifiableMap(excepciones);
    }

    public static String convertirNumeroALetras(double numero, String abrv) {

        String nombreMoneda;
        String moneda;
        
        if (abrv.equals("COP")) {
            if (numero % 1000000 == 0) {
                moneda = "de pesos colombianos";
            } else {
                moneda = "pesos colombianos";
            }
            nombreMoneda = "M/CTE";
        } else  if (abrv.equals("USD")) {
            if (numero % 1000000 == 0) {
                moneda = "de dolares americanos";
            } else {
                moneda = "dolares americanos";
            }
            nombreMoneda = "USD";
        } else {
            if (numero % 1000000 == 0) {
                moneda = "de euros";
            } else {
                moneda = "euros";
            }
            nombreMoneda = "EUR";
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String[] partes = df.format(numero).split("\\.");

        Long parteEntera = Long.parseLong(partes[0].equals("") ? "0" : partes[0]);
        Long parteDecimal = Long.parseLong(partes[1]);

        String numeroEnLetras = convertirNumeroALetras(parteEntera, false) + moneda + " con " + parteDecimal + "/100 " + nombreMoneda;

        return numeroEnLetras.toUpperCase();
    }

    private static String convertirNumeroALetras(Long numero, Boolean compuesto) {

        if (EXCEPCIONES.containsKey(numero)) {
            return EXCEPCIONES.get(numero);
        }

        if (numero < 0) {
            return "menos " + convertirNumeroALetras(Math.abs(numero), false);
        }

        if (numero < 20) {
            if (compuesto) {
                return "";
            } else {
                return UNIDADES[numero.intValue()];
            }
        }

        if (numero < 100) {
            Long unidades = numero % 10;
            Long decenas = numero / 10;
            if (unidades == 0) {
                return DECENAS[decenas.intValue()];
            } else {
                return DECENAS[decenas.intValue()] + "y " + UNIDADES[unidades.intValue()];
            }
        }

        if (numero < 1_000) {
            Long centenas = numero / 100;
            Long resto = numero % 100;
            if (resto == 0) {
                return CENTENAS[centenas.intValue()];
            } else {
                return CENTENAS[centenas.intValue()] + convertirNumeroALetras(resto, false);
            }
        }

        if (numero < 1_000_000) {
            Long miles = numero / 1_000;
            Long resto = numero % 1_000;
            if (resto == 0) {
                return convertirNumeroALetras(miles, false) + "mil ";
            } else {
                
                if (miles.equals(1L)) {
                    return convertirNumeroALetras(miles, true) + "mil " + convertirNumeroALetras(resto, false);
                } else {
                    return convertirNumeroALetras(miles, false) + "mil " + convertirNumeroALetras(resto, false);
                }

            }
        }

        if (numero < 1_000_000_000) {
            Long millones = numero / 1_000_000;
            Long resto = numero % 1_000_000;
            if (resto == 0) {
                if (millones.equals(1L)) {
                    return convertirNumeroALetras(millones, false) + "millon ";
                } else {
                    return convertirNumeroALetras(millones, false) + "millones ";
                }
                
            } else {
                if (millones.equals(1L)) {
                    return convertirNumeroALetras(millones, false) + "millon " + convertirNumeroALetras(resto, false);
                } else {
                    return convertirNumeroALetras(millones, false) + "millones " + convertirNumeroALetras(resto, false);
                }
                
            }
        }

        if (numero < 1_000_000_000_000L) {
            Long milesMillones = numero / 1_000_000_000;
            Long resto = numero % 1_000_000_000;
            if (resto == 0) {
                return convertirNumeroALetras(milesMillones, false) + "mil millones";
            } else {
                return convertirNumeroALetras(milesMillones, false) + "mil " + convertirNumeroALetras(resto, false);
            }
        }

        return "";

    }

}