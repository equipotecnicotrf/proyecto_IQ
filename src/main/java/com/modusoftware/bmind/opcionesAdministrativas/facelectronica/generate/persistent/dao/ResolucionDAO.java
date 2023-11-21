package com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.persistent.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.modusoftware.bmind.opcionesAdministrativas.facelectronica.generate.dto.ResolucionDTO;

@Repository
public class ResolucionDAO {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ResolucionDTO getDataResolucion(String compania, String tipo, String documento, String fechaDocumento, Long notaCredito) throws EmptyResultDataAccessException {
		String sql = "select serie, nro_aprobacion, fecha_ini || ';' || fecha_fin, rango_ini || ';' || rango_fin, rango_ini, rango_fin, fecha_ini, meses_vigencia, clave_tecnica "
				+ "from resolucion "
				+ "where cmpy = :COMPANIA "
				+ "and tipo = :TIPO "
				+ "and activo = 'S' "
				+ "and :DOCU between rango_ini  and rango_fin "
				+ "and :FECHA_DOCU between  fecha_ini and fecha_fin "
				+ "and nota_credito = :NOTA_CREDITO";
		
		
		sql = sql.replace(":COMPANIA", "'" + compania + "'").replace(":TIPO", "'" + tipo +"'").replace(":DOCU", "'" +documento + "'").replace(":FECHA_DOCU", "'" + fechaDocumento + "'").replace(":NOTA_CREDITO", "'" + notaCredito + "'");
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ResolucionDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
		
	}	

}
