package test.org.fugerit.fork.italia.ansc.decodifiche;

import java.util.Collection;

import org.fugerit.fork.italia.ansc.decodifiche.model.ComuneModel;
import org.fugerit.fork.italia.ansc.decodifiche.model.ProvinciaModel;
import org.fugerit.fork.italia.ansc.decodifiche.model.StatoModel;
import org.fugerit.java.core.cfg.ConfigException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDecTableMapper extends BasicTest {

	@Test
	public void testListaComuni() throws ConfigException {
		Collection<ComuneModel> list = ComuneModel.loadList();
		Assert.assertNotNull( list );
		list.stream().forEach( ( c ) -> log.info( "comune -> {}", c ) );
	}
	
	@Test
	public void testListaStati() throws ConfigException {
		Collection<StatoModel> list = StatoModel.loadList();
		Assert.assertNotNull( list );
		list.stream().forEach( ( c ) -> log.info( "comune -> {}", c ) );
	}
	
	
	@Test
	public void testListaProvincia() throws ConfigException {
		Collection<ProvinciaModel> list = ProvinciaModel.loadList();
		Assert.assertNotNull( list );
		list.stream().forEach( ( c ) -> log.info( "comune -> {}", c ) );
	}
	
	@Test
	public void testComuneModel() {
		ComuneModel model = new ComuneModel();
		Assert.assertNotNull( model );
	}
	
	@Test
	public void testStatoModel() {
		StatoModel model = new StatoModel();
		Assert.assertNotNull( model );
	}
	
	@Test
	public void testProvinciaModel() {
		ProvinciaModel model = new ProvinciaModel();
		Assert.assertNotNull( model );
	}
	
}
