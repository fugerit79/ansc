# Changelog

Tutte le modifiche significative a questo progetto verranno documentate in questo file.

Il formato è basato su [Tenere un changelog](https://keepachangelog.com/en/1.0.0/),
e il progetto si conforma al [Versionamento semantico](https://semver.org/spec/v2.0.0.html).

## [Non rilasciato]

### Aggiunto

- Aggiunte decodifiche 88 e 98 all'indice.

### Modificato

- version parent fj-bom impostata a 1.3.6
- version fj-core impostata a 2.0.2
- integrazione con tag v1.12.0 italia/ansc

## [1.11.4-rc.2] - 2023-09-03

### Aggiunto

- Accesso alle decodifiche di ANPR
- Nuovo metodo di decodifiche facade per ottenere la modellazione completa del csv sottostante la decodifica.
- Metodi e modelli specifici per comuni, province e stati di ANPR.

### Fixed

- Path nel readme corretti

## [1.11.4-rc.1] - 2023-09-02

### Aggiunto

- Setup iniziale del progetto a partire dalla versione [1.11.4](https://github.com/italia/ansc/tree/v1.11.4) del repository italia/ansc.
- Nell'artefatto maven (jar) generato vengono salvati come risorse tutti i csv contenuti nell cartella docs/ (mappati su italia/ansc/docs/).
- Aggiunto tool di test per stamapre le decodifiche
- Aggiunti badge di maven repo central, licenza, quality tate e changelog.
- Aggiunto readme alternativo [README_FORK.md](README_FORK.md)