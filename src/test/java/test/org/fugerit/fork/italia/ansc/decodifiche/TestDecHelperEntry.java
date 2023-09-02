package test.org.fugerit.fork.italia.ansc.decodifiche;

import org.fugerit.fork.italia.ansc.decodifiche.DecHelperEntry;
import org.fugerit.java.core.cfg.ConfigException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDecHelperEntry extends BasicTest {

	private static final String TEST_ID = "1";
	
	private static final String TEST_VALUE = "test";
	
	@Test
	public void testDecEntryRequiredParameter() throws ConfigException {
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry( TEST_ID, null, TEST_VALUE ) );
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry( null, TEST_ID, TEST_VALUE ) );
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry( TEST_ID, TEST_ID, null ) );
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry().setId( null ) );
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry().setKey( null ) );
		Assert.assertThrows( Exception.class , () -> new DecHelperEntry().setValue( null ) );
	}
	
	@Test
	public void testDecEntryRequiredArgsConstructor() throws ConfigException {
		DecHelperEntry entry = new DecHelperEntry( TEST_ID, TEST_ID, TEST_VALUE );
		Assert.assertNotNull( entry );
	}
	
	@Test
	public void testDecEntry() throws ConfigException {
		DecHelperEntry entry = new DecHelperEntry();
		Assert.assertNotNull( entry );
		entry.setId( TEST_ID );
		entry.setKey( TEST_ID );
		entry.setValue( TEST_VALUE );
		Assert.assertEquals( TEST_VALUE, entry.getSimpleValue() );
		entry.setSimpleValue( entry.getId()+"_"+entry.getValue() );
		log.info( "entry {}", entry );
		Assert.assertNotEquals( TEST_VALUE, entry.getSimpleValue() );
	}
	
}
