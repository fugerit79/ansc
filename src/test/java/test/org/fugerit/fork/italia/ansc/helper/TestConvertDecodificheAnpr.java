package test.org.fugerit.fork.italia.ansc.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;

import org.fugerit.java.core.cfg.xml.GenericListCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Assert;
import org.junit.Test;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestConvertDecodificheAnpr extends BasicTest {

	private static final String INPUT_FOLDER = "dec_anpr/csv";
	
	private static final String OUTPUT_FOLDER = "src/main/resources/italia/anpr/docs/Decodifiche";
	
	private static final String CATALOG_CONFIG_PATH = "dec_anpr/decodifiche-data-catalog-config.xml";
	
	private boolean convertCurrent( ConvertAnprDecEntry anprEntry ) throws IOException, CsvValidationException {
		int lineCount = 0;
		String nomeFile = anprEntry.getTabella()+".csv";
		CSVParser csvParser = new CSVParserBuilder().withSeparator( ';' ).build();
		try ( InputStreamReader reader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( INPUT_FOLDER+"/"+nomeFile ) );
				FileOutputStream os = new FileOutputStream( new File( OUTPUT_FOLDER, anprEntry.getId()+"_"+nomeFile.toLowerCase() ) );
				CSVReader csvReader = new CSVReaderBuilder( reader ).withCSVParser( csvParser ).build();
				CSVWriter csvWriter = new CSVWriter( new OutputStreamWriter( os ) ) ) {
			String[] line = csvReader.readNext();
			
			while ( line != null ) {
				csvWriter.writeNext( line );
				line = csvReader.readNext();
				lineCount++;
			}
			log.info( "tatal write {} for {}", lineCount, anprEntry );
		}
		return lineCount > 0;
	}
	
	@Test
	public void convert() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( CATALOG_CONFIG_PATH ) ) {
			GenericListCatalogConfig<ConvertAnprDecEntry> catalogDecodifiche = new GenericListCatalogConfig<ConvertAnprDecEntry>();
			GenericListCatalogConfig.load( is , catalogDecodifiche );
			 Collection<ConvertAnprDecEntry> decodifiche = catalogDecodifiche.getDataList( "tabelle-dec" );
			 for ( ConvertAnprDecEntry anprEntry : decodifiche ) {
				 log.info( "current : {}", anprEntry );
				 boolean ok = this.convertCurrent(anprEntry);
				 Assert.assertTrue( "Fallimento su entry : "+anprEntry.getId() , ok );
			 }
		} catch (Exception e) {
			this.failEx(e);
		}
		
	}
	
}

