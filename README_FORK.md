# Versione distribuibile come progetto maven del repository github [italia/ansc](https://github.com/italia/ansc)

[![Changelog v1.0.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md) 
[![license](https://img.shields.io/badge/License-CC%20BY%204.0-teal.svg)](https://creativecommons.org/licenses/by/4.0/)
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java.fork/ansc.svg)](https://mvnrepository.com/artifact/org.fugerit.java.fork/ansc)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_ansc&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit79_ansc)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit79_ansc&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit79_ansc)

## Introduzione

Non verrà effettuata nessuna modifica alle risorse già esistenti, questo README_FORK.md non sostituisce il [README](README.md) originale del progetto.

## Struttura dell'artefatto jar generato ansc-X.X.X.jar

```
+--ansc-X.X.X.jar
   +--italia/
      +--ansc
         +--docs/                       # contiene la documentazione esportata
            +--Decodifiche/             # tutte le decodifiche di ansc in solo formato csv
            +--Mapping_casi_uso/        # tutte i mapping casi d'uso in solo formato csv
      +--anpr            
         +--docs/      
            +--Decodifiche/             # tutte le decodifiche di anpr in solo formato csv
```

Attraverso la [DecodificheFacade](src/main/java/org/fugerit/fork/italia/ansc/decodifiche/DecodificheFacade.java) è possibile caricare il contenuto delle decodifiche, ecco un esempio : 

```
DecodificheFacade facade = DecodificheFacade.newInstance();
ListMapStringKey<DecHelperEntry> decodifica = facade.getDecodifica( "1" ); );
decodifica.stream().forEach( ( c ) -> log.info( "entry : {}", c ) );
```

Esiste anche una unità di test funzionante [TestDecodificheFacade](src/test/java/test/org/fugerit/fork/italia/ansc/decodifiche/TestDecodificheFacade.java)

## Dipendenza maven

Per usare il progetto basta aggiungere la dipendenza : 

```
		<dependency>
			<groupId>org.fugerit.java.fork</groupId>
			<artifactId>ansc</artifactId>
			<version>${ansc-version}</version>			
		</dependency>
```

## AnscTool

Esiste un semplice tool di verifica, per lanciarlo basta :

1. Clonare il progetto : `https://github.com/fugerit79/ansc.git`
2. Eseguire la build : `mvn clean install -P singlepackage`
3. Lanciarlo : `java -jar dist-ansc-X.X.X.jar --print-dec-id 1` (senza parametri per l'help)

Requisti : 
* JDK 11+ (testato con amazon-corretto-11.0.19.7.1-linux-x64)
* Maven 3.8+ (testato con Apache Maven 3.9.4)

## Versionamento e branching

Il branch main è dove viene manutenuta l'ultima versione stabile del progetto.

Il branch italia/ansc/main viene allineato con il progetto da cui è avvenuto il fork [italia/ansc](https://github.com/italia/ansc).

Le versioni seguono quelle del progetto italia/ansc (eventualmente con dei metadati come da versionamento semantico, es 1.11.4-rc.1).