package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DocuDIANDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertDocuDian(String compania, String tipoDocumento, String numeroDocumento, String cufeDocumento,
			String cufeDian, String codigoEvento, String fechaEvento) {
		String sql = "insert into docu_dian (dn_cmpy, dn_tipo_docu, dn_nume_docu, dn_cufedocu, dn_cufedian, dn_codevento, dn_certi, dn_fechahora_evento) values(:COMPANIA, :TIPO_DOCUMENTO, :DOCUMENTO, :CUFE_DOCU, :CUFE_DIAN, :COD_EVENTO, :CERTI, :FECHA_EVENTO)";

		sql = sql.replace(":COMPANIA", "'" + compania + "'").replace(":TIPO_DOCUMENTO", "'" + tipoDocumento + "'")
				.replace(":DOCUMENTO", "'" + numeroDocumento + "'").replace(":CUFE_DOCU", "'" + cufeDocumento + "'")
				.replace(":CUFE_DIAN", "'" + cufeDian + "'").replace(":COD_EVENTO", "'" + codigoEvento + "'")
				.replace(":CERTI", "'" + "certi" + "'").replace(":FECHA_EVENTO", "'" + fechaEvento + "'");

		jdbcTemplate.execute(sql);
	}

}
