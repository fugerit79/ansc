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
public class ComuneModel implements Serializable {

	private static final long serialVersionUID = 6524759619493333086L;

	@Getter private String id;
	
	@Getter private String codistat;
	
	@Getter private String sigliaProvincia;
	
	@Getter private String denominazioneIt;
	
	@Getter private String dataIstituzione;
	
	@Getter private String dataCessazione;
	
	@Getter private String idProvincia;

	private static final String PATH_CSV = "italia/anpr/docs/Decodifiche/3_amm_comune.csv";
	
	public static DecTable loadCsv() {
		return DecTable.parse( PATH_CSV );
	}
	
	public static List<ComuneModel> loadList() {
		return DecTableMapper.mapComuni( loadCsv() );
	}
	
}
