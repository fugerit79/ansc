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
public class StatoModel implements Serializable {

	private static final long serialVersionUID = -2758520602786047144L;
	
	@Getter private String id;
	
	@Getter private String denominazione;
	
	@Getter private String codiceStato;
	
	@Getter private String dataInizioValidita;
	
	@Getter private String dataFineValidita;
	
	private static final String PATH_CSV = "italia/anpr/docs/Decodifiche/2_amm_stato.csv";
	
	public static DecTable loadCsv() {
		return DecTable.parse( PATH_CSV );
	}
	
	public static List<StatoModel> loadList() {
		return DecTableMapper.mapStati( loadCsv() );
	}
	
}
