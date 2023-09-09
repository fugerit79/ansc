package test.org.fugerit.fork.italia.ansc.versione;

import org.fugerit.fork.italia.ansc.decodifiche.DecHelperEntry;
import org.fugerit.fork.italia.ansc.decodifiche.DecodificheFacade;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMajor1Minor12Patch0 {

	@Test
	public void testDecodifica88() throws ConfigException {
		DecodificheFacade facade = DecodificheFacade.newInstanceAnsc();
		ListMapStringKey<DecHelperEntry> dec = facade.getDecodifica( "88" );
		dec.stream().forEach( d -> log.info( "corrente : {}", d ) );
		Assert.assertEquals( 3 , dec.size() );
	}
	
	@Test
	public void testDecodifica98() throws ConfigException {
		DecodificheFacade facade = DecodificheFacade.newInstanceAnsc();
		ListMapStringKey<DecHelperEntry> dec = facade.getDecodifica( "98" );
		dec.stream().forEach( d -> log.info( "corrente : {}", d ) );
		Assert.assertEquals( 4 , dec.size() );
	}
	
}
