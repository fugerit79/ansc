package test.org.fugerit.fork.italia.ansc.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestGenerateIndex extends BasicTest {

	private static final String INPUT_DECODIFICHE = "docs/Decodifiche/";
	
	private static final String OUTPUT_BASE = "src/main/resources";
			
	private static final String OUTPUT_DECODIFICHE = OUTPUT_BASE+"/italia/ansc/index/decodifiche.xml"; 
	
	private boolean generateWorker( String input, String output, final Comparator<String> comp, Consumer<Properties> additionaProcessing ) {
		boolean ok = false;
		File fileInput = new File( INPUT_DECODIFICHE );
		File fileOutput = new File( output );
		try ( OutputStream os = new FileOutputStream( fileOutput ) ) {
			log.info( "input -> {}, output -> {}", fileInput.getCanonicalPath(), fileOutput.getCanonicalPath() );
			Properties outputProps = new Properties() {
				private static final long serialVersionUID = 7181636921206803632L;
				@Override
				public Set<Entry<Object, Object>> entrySet() {
					List<Entry<Object, Object>> ordered = super.entrySet().stream().sorted(
						new Comparator<Entry<Object, Object>>() {
							@Override
							public int compare(java.util.Map.Entry<Object, Object> o1,
									java.util.Map.Entry<Object, Object> o2) {
								return comp.compare( o1.getKey().toString() , o2.getKey().toString() );
							}
						}
					).collect( Collectors.toList() );
					log.info( "ordered : {}", ordered );
					return new LinkedHashSet<Entry<Object, Object>>( ordered )  ;
				}
			};
			for ( File current : fileInput.listFiles( ( f ) -> f.getName().endsWith( ".csv" ) ) ) {
				log.info( "current file {}", current.getCanonicalPath() );
				String key = current.getName().replace( ".csv" , "" );
				String value = current.getCanonicalPath().replace( "\\", "/" );	// per windows
				int index = value.indexOf( input );
				value = value.substring( index );	// il path nel jar
				log.info( "set {} -> {}", key, value );
				outputProps.setProperty(key, value);
			}
			additionaProcessing.accept(outputProps);
			log.info( "test {}", outputProps );
			outputProps.storeToXML( os , "Elenco risorse in "+input, StandardCharsets.UTF_8.name() );
			ok = !outputProps.isEmpty();
		} catch (Exception e) {
			this.failEx(e);
		}
		return ok;
	}
	
	@Test
	public void generateIndexDecodifiche() {
		boolean ok = this.generateWorker(INPUT_DECODIFICHE, OUTPUT_DECODIFICHE, 
				new Comparator<String>() {
					private int helper( String key ) {
						int result = 0;
						int index = key.indexOf( "_" );
						if ( index == -1 ) {
							result+= Integer.parseInt( key.substring( 0 ) );
						} else {
							result+= 1000+Integer.parseInt( key.substring( 0, index ) );
						}
						return result;
					}
					@Override
					public int compare(String o1, String o2) {
						int result = 0;
						try {
							result = (helper(o1) - helper(o2));
						} catch (NumberFormatException e) {
							result = o1.compareTo( o2 );
						}
						return result;
					}
				},
				new Consumer<Properties>() {
					@Override
					public void accept(Properties t) {
						Properties p = (Properties)t.clone();
						p.entrySet().stream().forEach( ( e ) -> t.put( e.getKey().toString().substring( 0, e.getKey().toString().indexOf( '_' ) ) , e.getValue() ) );
						p.entrySet().stream().forEach( ( e ) -> t.put( e.getKey().toString().substring( e.getKey().toString().indexOf( '_' )+1 ) , e.getValue() ) );
					}
				}
		);
		Assert.assertTrue(ok);
	}
	
}
