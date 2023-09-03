package test.org.fugerit.fork.italia.ansc.decodifiche;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.fugerit.fork.italia.ansc.decodifiche.DecHelperEntry;
import org.fugerit.fork.italia.ansc.decodifiche.DecTable;
import org.fugerit.fork.italia.ansc.decodifiche.DecodificheFacade;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDecodificheFacade extends BasicTest {

	private static final String ID_DECODIFICA_TEST = "1";
	
	public boolean testDecodifcaFacadeWorker( DecodificheFacade facade, String idDec ) throws ConfigException, IOException {
		boolean ok = false;
		Assert.assertNotNull( facade );
		log.info( "facade:{}, elenco id:{}", facade, facade.getIdSet() );
		if ( facade.hasDecodifica( idDec ) ) {
			// testo elenco deodifiche
			ListMapStringKey<DecHelperEntry> decodifica = facade.getDecodifica( idDec );
			Assert.assertNotNull( decodifica );
			decodifica.stream().forEach( ( c ) -> log.info( "entry : {}", c ) );
			DecodificheFacade copyFacade = (DecodificheFacade)this.fullSerializationTest( facade );
			Assert.assertTrue( copyFacade.hasDecodifica( idDec ) );
			// test csv diretto
			DecTable table = facade.getDecTable(idDec);
			log.info( "header {}", table.getHeader() );
			table.getRows().stream().forEach( ( c ) -> log.info( "entry : {}", c ) );
			Assert.assertNotNull( table );
			ok = true;
		} else {
			fail( "Decodifica non trovata : "+ID_DECODIFICA_TEST );
		}
		return ok;
	}
	
	@Test
	public void testDecodifcaFacadeAnsc() throws ConfigException, IOException {
		boolean ok = this.testDecodifcaFacadeWorker(DecodificheFacade.newInstanceAnsc(), ID_DECODIFICA_TEST);
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testDecodifcaFacadeAnpr() throws ConfigException, IOException {
		boolean ok = this.testDecodifcaFacadeWorker(DecodificheFacade.newInstanceAnpr(), ID_DECODIFICA_TEST);
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testDecodifcaFacadeFailIndexNonEsiste() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( DecodificheFacade.BASE_PATH_ANSC, "index/non_esiste.xml" ) );
	}
	
	@Test
	public void testDecodifcaFacadeFailDecodificaNonEsiste() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( DecodificheFacade.BASE_PATH_ANSC, "index/decodifiche_non_esiste.xml" ) );
	}
	
	@Test
	public void testDecodifcaFacadeFailDecodificaVuota() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( DecodificheFacade.BASE_PATH_ANSC, "index/decodifiche_vuota.xml" ) );
	}
	
	@Test
	public void testDecodifcaFacadeFailDecodificaVuotaCsv() throws ConfigException {
		Assert.assertThrows( ConfigRuntimeException.class , () -> DecTable.parse( "path/non/esiste.csv" ) );
	}
	
}
