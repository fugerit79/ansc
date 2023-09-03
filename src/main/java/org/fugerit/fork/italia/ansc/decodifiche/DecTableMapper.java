package org.fugerit.fork.italia.ansc.decodifiche;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.fugerit.fork.italia.ansc.decodifiche.model.ComuneModel;
import org.fugerit.fork.italia.ansc.decodifiche.model.ProvinciaModel;
import org.fugerit.fork.italia.ansc.decodifiche.model.StatoModel;

public class DecTableMapper {

	private DecTableMapper() {}
	
	public static final Function<DecRow, ComuneModel> COMUNE_MAPPER =  t -> {
		String id = t.get(0);
		String codistat = t.get(3);
		String sigliaProvincia = t.get(13);
		String denominazioneIt = t.get(5);
		String dataIstituzione = t.get(1);
		String dataCessazione = t.get(2);
		String idProvincia = t.get(9);
		return new ComuneModel(id, codistat, sigliaProvincia, denominazioneIt, dataIstituzione, dataCessazione, idProvincia);
	};

	public static final Function<DecRow, ProvinciaModel> PROVINCIA_MAPPER = t -> {
		String id = t.get(0);
		String denominazione = t.get(1);
		String sigla = t.get(2);
		String dataInizioValidita = t.get(3);
		String dataFineValidita = t.get(4);
		return new ProvinciaModel(id, denominazione, sigla, dataInizioValidita, dataFineValidita);
	};
	
	public static final Function<DecRow, StatoModel> STATO_MAPPER = t -> {
		String id = t.get(0);
		String denominazione = t.get(1);
		String codiceStato = t.get(8);
		String dataInizioValidita = t.get(4);
		String dataFineValidita = t.get(5);
		return new StatoModel(id, denominazione, codiceStato, dataInizioValidita, dataFineValidita);
	};
	
	private static <T> List<T> mapHelper(DecTable table, Function<DecRow, T> mapper) {
		return table.getRows().stream().map(mapper).collect(Collectors.toList());
	}

	public static List<ComuneModel> mapComuni(DecTable table) {
		return mapHelper(table, COMUNE_MAPPER);
	}
	
	public static List<ProvinciaModel> mapProvince(DecTable table) {
		return mapHelper(table, PROVINCIA_MAPPER);
	}
	
	public static List<StatoModel> mapStati(DecTable table) {
		return mapHelper(table, STATO_MAPPER);
	}
	
}
