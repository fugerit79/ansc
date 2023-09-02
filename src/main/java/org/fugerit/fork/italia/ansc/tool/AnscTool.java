package org.fugerit.fork.italia.ansc.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.fugerit.fork.italia.ansc.decodifiche.DecodificheFacade;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnscTool {

	public static final String ARG_PRINT_DEC_ID = "print-dec-id";
	
	public static final String ARG_HELP = "help";
		
	public boolean handler( Properties params ) throws ConfigException {
		boolean ok = false;
		if ( params.isEmpty() || params.containsKey( ARG_HELP ) ) {
			printHelp();
		} else {
			String decId = params.getProperty( ARG_PRINT_DEC_ID );
			if ( StringUtils.isNotEmpty( decId ) ) {
				DecodificheFacade facade = DecodificheFacade.newInstance();
				if ( facade.hasDecodifica( decId ) ) {
					facade.getDecodifica( decId ).stream().forEach( e -> log.warn( "{}:{}", e.getKey(), e.getValue() ) );
					ok = true;
				} else {
					throw new ConfigException( "Decodifica non trovata "+decId );
				}
			} else {
				throw new ConfigException( "Parametro obbligatorio : "+ARG_PRINT_DEC_ID );
			}
		}
		return ok;
	}
	
	private static void printHelp() {
		log.warn( "AnscTool (semplice strumento di test) : " );
		log.warn( "  --{} - [opzionale] stampa questo help", ARG_HELP );
		log.warn( "  --{} - [obbligatorio] '$idDec' - stampa la decodifica richiesta\n", ARG_PRINT_DEC_ID );
	}
	
	public static int mainWorker( String[] args, AnscTool tool ) {
		int result = Result.RESULT_CODE_KO;
		try ( BufferedReader reader = new BufferedReader( new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( "banner.txt" ) ) ) ) {
			// stampa il banner
			reader.lines().forEach( l -> log.warn( l ) );
			// esegui il tool
			Properties params = ArgUtils.getArgs( args );
			if ( tool.handler(params) ) {
				result = Result.RESULT_CODE_OK;
			}
		} catch (ConfigException e) {
			log.error( "Errore configuratione : {}", e.getMessage() );
			printHelp();
		} catch (Exception e) {
			log.error( "Errore : "+e, e );
			printHelp();
		}
		log.warn( "exit {}", result );
		return result;
	}
	
	public static void main( String[] args ) {
		System.exit( mainWorker(args, new AnscTool()) );
	}
	
}
