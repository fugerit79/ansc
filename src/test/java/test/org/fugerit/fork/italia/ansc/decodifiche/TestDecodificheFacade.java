package test.org.fugerit.fork.italia.ansc.decodifiche;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.fugerit.fork.italia.ansc.decodifiche.DecHelperEntry;
import org.fugerit.fork.italia.ansc.decodifiche.DecodificheFacade;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDecodificheFacade extends BasicTest {

	private static final String ID_DECODIFICA_TEST = "1";
	
	@Test
	public void testDecodifcaFacade() throws ConfigException, IOException {
		DecodificheFacade facade = DecodificheFacade.newInstance();
		Assert.assertNotNull( facade );
		log.info( "facade:{}, elenco id:{}", facade, facade.getIdSet() );
		if ( facade.hasDecodifica( ID_DECODIFICA_TEST ) ) {
			ListMapStringKey<DecHelperEntry> decodifica = facade.getDecodifica( ID_DECODIFICA_TEST );
			Assert.assertNotNull( decodifica );
			decodifica.stream().forEach( ( c ) -> log.info( "entry : {}", c ) );
			DecodificheFacade copyFacade = (DecodificheFacade)this.fullSerializationTest( facade );
			Assert.assertTrue( copyFacade.hasDecodifica( ID_DECODIFICA_TEST ) );
		} else {
			fail( "Decodifica non trovata : "+ID_DECODIFICA_TEST );
		}
	}
	
	@Test
	public void testDecodifcaFacadeFailIndexNonEsiste() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( "test/index/non_esiste.xml" ) );
	}
	
	@Test
	public void testDecodifcaFacadeFailDecodificaNonEsiste() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( "test/index/decodifiche_non_esiste.xml" ) );
	}
	
	@Test
	public void testDecodifcaFacadeFailDecodificaVuota() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> DecodificheFacade.newInstance( "test/index/decodifiche_vuota.xml" ) );
	}
	
}
