package org.fugerit.fork.italia.ansc.decodifiche.model;

import java.io.Serializable;
import java.util.List;

import org.fugerit.fork.italia.ansc.decodifiche.DecTable;
import org.fugerit.fork.italia.ansc.decodifiche.DecTableMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProvinciaModel implements Serializable {

	private static final long serialVersionUID = -7239866321286814130L;

	@Getter private String id;
	
	@Getter private String denominazione;
	
	@Getter private String sigla;
	
	@Getter private String dataInizioValidita;
	
	@Getter private String dataFineValidita;

	private static final String PATH_CSV = "italia/anpr/docs/extra/amm_provincia.csv";
	
	public static DecTable loadCsv() {
		return DecTable.parse( PATH_CSV );
	}
	
	public static List<ProvinciaModel> loadList() {
		return DecTableMapper.mapProvince( loadCsv() );
	}
	
}
