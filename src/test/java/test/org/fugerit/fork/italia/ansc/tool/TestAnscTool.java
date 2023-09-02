package test.org.fugerit.fork.italia.ansc.tool;

import java.util.ArrayList;
import java.util.List;

import org.fugerit.fork.italia.ansc.tool.AnscTool;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.util.result.Result;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestAnscTool extends BasicTest {

	private static final AnscTool TOOL = new AnscTool();
	
	private static final String DEC_ID_TEST = "1";
	
	private static final String HELP_VALUE = "true";
	
	private static String[] paramHelper( String decId, String help, String wrongParam ) {
		List<String> list = new ArrayList<>();
		if ( decId != null ) {
			list.add( ArgUtils.getArgString( AnscTool.ARG_PRINT_DEC_ID ) );
			list.add( decId );
		}
		if ( help != null ) {
			list.add( ArgUtils.getArgString( AnscTool.ARG_HELP ) );
			list.add( help );
		}
		if ( wrongParam != null ) {
			list.add( ArgUtils.getArgString( AnscTool.ARG_PRINT_DEC_ID+"_WRONG" ) );
			list.add( wrongParam );
		}
		return list.toArray(String[]::new);
	}
	
	private static String[] paramHelper( String decId, String help ) {
		List<String> list = new ArrayList<>();
		if ( decId != null ) {
			list.add( ArgUtils.getArgString( AnscTool.ARG_PRINT_DEC_ID ) );
			list.add( decId );
		}
		if ( help != null ) {
			list.add( ArgUtils.getArgString( AnscTool.ARG_HELP ) );
			list.add( help );
		}
		return list.toArray(String[]::new);
	}
	
	@Test
	public void testDecodificaDecTestOk() throws ConfigException {
		 Assert.assertTrue( TOOL.handler( ArgUtils.getArgs( paramHelper( DEC_ID_TEST, null ) ) ) );
	}

	@Test
	public void testDecodificaDecTestNotEsiste() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> TOOL.handler( ArgUtils.getArgs( paramHelper( "non_esiste", null ) ) ) );
	}
	
	@Test
	public void testDecodificaDecTestParametroErrato() throws ConfigException {
		Assert.assertThrows( ConfigException.class , () -> TOOL.handler( ArgUtils.getArgs( paramHelper( null, null, DEC_ID_TEST ) ) ) );
	}
	
	@Test
	public void testDecodificaHelp() throws ConfigException {
		Assert.assertFalse( TOOL.handler( ArgUtils.getArgs( paramHelper( null, HELP_VALUE ) ) ) );
	}

	@Test
	public void testToolOk() throws ConfigException {
		Assert.assertEquals( Result.RESULT_CODE_OK , AnscTool.mainWorker( paramHelper( DEC_ID_TEST, null ), TOOL ) );
	}
	
	@Test
	public void testToolTestNotEsiste() throws ConfigException {
		Assert.assertEquals( Result.RESULT_CODE_KO , AnscTool.mainWorker( paramHelper( "non_esiste", null ), TOOL ) );
	}
	
	@Test
	public void testToolTestHelp() throws ConfigException {
		Assert.assertEquals( Result.RESULT_CODE_KO , AnscTool.mainWorker( paramHelper( null, HELP_VALUE ), TOOL ) );
	}
	
	@Test
	public void testToolTestNoParam() throws ConfigException {
		Assert.assertEquals( Result.RESULT_CODE_KO , AnscTool.mainWorker( new String[0], TOOL ) );
	}
	
	@Test
	public void testToolTestNullParam() throws ConfigException {
		Assert.assertEquals( Result.RESULT_CODE_KO , AnscTool.mainWorker( null, null ) );
	}
	
}
