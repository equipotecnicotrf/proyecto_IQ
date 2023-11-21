package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DocuSatDAO {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertDocuSat(String compania, String documento, String fechaEvento, String cufe, String email, String tipoCFD, String trxId) throws EmptyResultDataAccessException {
		String sql = "insert into docu_sat (cia, numero, fechahora, fauto, auto, mail, tipo, customer_trx_id) values(:COMPANIA, :DOCUMENTO, now(), :FECHA_EVENTO, :CUFE, :MAIL, :CFD, :TRX_ID)";
		
		sql = sql.replace(":COMPANIA", "'" + compania + "'").replace(":DOCUMENTO", "'" + documento +"'").replace(":DOCU", "'" +documento + "'").replace(":FECHA_EVENTO", "'" + fechaEvento + "'").replace(":CUFE", "'" + cufe + "'").replace(":MAIL", "'" + email + "'").replace(":CFD", "'" + tipoCFD + "'").replace(":TRX_ID", "'" + trxId + "'");
		
		jdbcTemplate.execute(sql);
	}

}
