package org.fugerit.fork.italia.ansc.decodifiche;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.collection.ListMapStringKey;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodificheFacade implements Serializable {
	
	private static final long serialVersionUID = -1871192809782528391L;

	private static final Properties EXCEPTIONS = new Properties();
	static {
		// inserite solo per compatibilit, meglio recuperare i valori di queste classi in modo completo
		EXCEPTIONS.setProperty( "docs/Decodifiche/45_comuni_subentrati.csv", "0,1" );
		EXCEPTIONS.setProperty( "docs/Decodifiche/2_amm_stato.csv", "0,1" );
		EXCEPTIONS.setProperty( "docs/Decodifiche/3_amm_comune.csv", "0,5" );
		EXCEPTIONS.setProperty( "docs/Decodifiche/33_conversione_codici_aire.csv", "1,2" );
	}
	
	public static final String BASE_PATH_ANSC = "italia/ansc/";
	
	public static final String BASE_PATH_ANPR = "italia/anpr/";
	
	public static final String INDEX_DECODIFICHE_PATH_ANSC = "index/decodifiche.xml";
	
	public static final String INDEX_DECODIFICHE_PATH_ANPR = "index/decodifiche.xml";
	
	private String basePath;
	
	private Map<String, DecHolder> catalog;
	
	public Set<String> getIdSet() {
		return this.catalog.keySet();
	}
	
	public boolean hasDecodifica( String id ) {
		return this.catalog.containsKey(id);
	}
	
	public ListMapStringKey<DecHelperEntry> getDecodifica( String id ) {
		return this.catalog.get( id ).getMap().entrySet().stream().map(
			e -> new DecHelperEntry( e.getKey(), e.getValue() )
		).collect( Collectors.toCollection( () -> new ListMapStringKey<>() ) );
	}
	
	public DecTable getDecTable( String id ) {
		DecHolder holder = this.catalog.get(id);
		DecTable table = null;
		if ( holder != null ) {
			table = DecTable.parse( this.basePath+"/"+holder.getPath()  );
		}
		return table;
	}
	
	private DecodificheFacade( Map<String, DecHolder> catalog, String basePath ) {
		this.catalog = catalog;
		this.basePath = basePath;
	}
	
	private static void parse( CSVReader reader, Map<String, String> current, String path ) throws CsvValidationException, IOException, ConfigException {
		String[] line = reader.readNext();
		if( line != null ) {
			// verifico header
			int indexId = -1;
			int indexDescrizione = -1;
			String exception = EXCEPTIONS.getProperty( path );
			if ( exception != null ) {
				String[] split = exception.split( "," );
				indexId = Integer.parseInt( split[0] );
				indexDescrizione = Integer.parseInt( split[1] );
			} else {
				// autoscan colonne da usare
				for ( int k=0; k<line.length; k++ ) {
					if ( line[k].equalsIgnoreCase( "ID" ) ) {
						indexId = k;
					} else if ( line[k].equalsIgnoreCase( "DESCRIZIONE" ) ) {
						indexDescrizione = k;
					}
				}
			}
			line = reader.readNext();
			while ( line != null ) {
				current.put( line[indexId] , line[indexDescrizione] );
				line = reader.readNext();	
			}
		} else {
			throw new ConfigException( "Il contenuto della decodifica è vuoto : "+path );
		}
	}
	
	private static DecHolder loadCurrent( String basePath, String path ) {
		Map<String, String> current = new LinkedHashMap<>();
		log.debug( "basePath {}, path {}", basePath, path );
		try ( CSVReader reader = new CSVReader( new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( basePath+path ) ) ) ) {
			parse(reader, current, path);
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "loadCurrent", e );
		}
		return new DecHolder(path, current);
	}

	public static DecodificheFacade newInstance( String basePath, String indexFilePath ) throws ConfigException {
		Map<String, DecHolder> catalog = new HashMap<>();
		String fullPath = basePath+indexFilePath;
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( fullPath ) ) {
			log.info( "path: {}", fullPath );
			Properties props = new Properties();
			props.loadFromXML( is );
			props.entrySet().stream().forEach( e -> {
				String key = e.getKey().toString();
				String value = e.getValue().toString();
				log.debug( "load {} -> {}", key, value );
				catalog.put( key, loadCurrent( basePath, value ) );
			} );
		} catch (Exception e) {
			throw ConfigException.convertExMethod( "newInstance" , e );
		}
		return new DecodificheFacade(catalog, basePath);
	}
	
	public static DecodificheFacade newInstance() throws ConfigException {
		return newInstanceAnsc();
	}
	
	public static DecodificheFacade newInstanceAnsc() throws ConfigException {
		return newInstance( BASE_PATH_ANSC, INDEX_DECODIFICHE_PATH_ANSC );
	}
	
	public static DecodificheFacade newInstanceAnpr() throws ConfigException {
		return newInstance( BASE_PATH_ANPR, INDEX_DECODIFICHE_PATH_ANPR );
	}
	
}

@AllArgsConstructor
class DecHolder implements Serializable {
	
	private static final long serialVersionUID = -6331978479654038342L;

	@Getter private String path;
	
	@Getter private Map<String, String> map;
	
}
