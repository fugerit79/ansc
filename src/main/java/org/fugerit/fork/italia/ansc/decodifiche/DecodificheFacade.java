package org.fugerit.fork.italia.ansc.decodifiche;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodificheFacade implements Serializable {
	
	private static final long serialVersionUID = -1871192809782528391L;

	private static final String BASE_PATH = "italia/ansc/";
	
	public static final String INDEX_DECODIFICHE_PATH = BASE_PATH+"index/decodifiche.xml";
	
	private Map<String, Map<String, String>> catalog;
	
	public Set<String> getIdSet() {
		return this.catalog.keySet();
	}
	
	public boolean hasDecodifica( String id ) {
		return this.catalog.containsKey(id);
	}
	
	public ListMapStringKey<DecHelperEntry> getDecodifica( String id ) {
		return this.catalog.get( id ).entrySet().stream().map(
			e -> new DecHelperEntry( e.getKey(), e.getValue() )
		).collect( Collectors.toCollection( () -> new ListMapStringKey<>() ) );
	}
	
	private DecodificheFacade( Map<String, Map<String, String>> catalog ) {
		this.catalog = catalog;
	}
	
	private static Map<String, String> loadCurrent( String path ) {
		Map<String, String> current = new LinkedHashMap<>();
		try ( CSVReader reader = new CSVReader( new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( BASE_PATH+path ) ) ) ) {
			String[] line = reader.readNext();
			if( line != null ) {
				// verifico header
				int indexId = -1;
				int indexDescrizione = -1;
				for ( int k=0; k<line.length; k++ ) {
					if ( line[k].equalsIgnoreCase( "ID" ) ) {
						indexId = k;
					} else if ( line[k].equalsIgnoreCase( "DESCRIZIONE" ) ) {
						indexDescrizione = k;
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
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "loadCurrent", e );
		}
		return current;
	}

	public static DecodificheFacade newInstance( String indexFilePath ) throws ConfigException {
		Map<String, Map<String, String>> catalog = new HashMap<>();
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( indexFilePath ) ) {
			Properties props = new Properties();
			props.loadFromXML( is );
			props.entrySet().stream().forEach( e -> {
				String key = e.getKey().toString();
				String value = e.getValue().toString();
				log.debug( "load {} -> {}", key, value );
				catalog.put( key, loadCurrent( value ) );
			} );
		} catch (Exception e) {
			throw ConfigException.convertExMethod( "newInstance" , e );
		}
		return new DecodificheFacade(catalog);
	}
	
	public static DecodificheFacade newInstance() throws ConfigException {
		return newInstance( INDEX_DECODIFICHE_PATH );
	}
	
}
