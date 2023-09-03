package org.fugerit.fork.italia.ansc.decodifiche;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.ClassHelper;

import com.opencsv.CSVReader;

import lombok.Getter;

public class DecTable implements Serializable {

	private static final long serialVersionUID = 1685521358483564058L;

	@Getter private DecRow header;
	
	@Getter private List<DecRow> rows;
	
	public DecTable() {
		this.header = new DecRow();
		this.rows = new ArrayList<>();
	}
	
	public DecTable setHeader( String[] header ) {
		this.header = new DecRow( Arrays.asList( header ) );
		return this;
	}
	
	public DecTable addRow( String[] row ) {
		this.rows.add( new DecRow (Arrays.asList( row ) ) );
		return this;
	}
	
	public static DecTable parse( String path ) {
		DecTable table = null;
		try (CSVReader reader = new CSVReader( new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( path ) ) )) {
			String[] line = reader.readNext();
			if ( line != null ) {
				table = new DecTable();
				table.setHeader( line );
				line = reader.readNext();
				while ( line != null ) {
					table.addRow( line );
					line = reader.readNext();
				}
			}
		} catch (Exception e) {
			throw new ConfigRuntimeException( e ); 
		}
		return table;
	}
	
}
