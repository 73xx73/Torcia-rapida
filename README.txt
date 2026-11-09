TORCIA RAPIDA - progetto Android con compilazione cloud

L'app è progettata per funzionare così:
- Tocca l'icona "Torcia Rapida": la torcia si accende.
- Tocca di nuovo la stessa icona: la torcia si spegne.
- Richiede Android 6.0 o successivo e un telefono con flash LED.

COMPILAZIONE SENZA PC (GitHub Actions)
1. Da Chrome sul telefono apri https://github.com/ e crea un account o accedi.
2. Crea un nuovo repository, ad esempio "TorciaRapida", impostato su Public (così GitHub Actions è disponibile senza configurazioni aggiuntive).
3. Carica tutti i file e le cartelle contenuti in questo ZIP nella radice del repository. È importante includere anche la cartella nascosta .github/workflows e il file android.yml.
4. Apri la scheda Actions del repository e abilita i workflow se GitHub lo richiede.
5. Se il workflow non parte automaticamente, apri "Build Torcia Rapida APK", premi "Run workflow" e conferma.
6. Attendi che il workflow finisca con il segno verde.
7. Apri l'esecuzione completata, scorri fino ad Artifacts e scarica "TorciaRapida-debug-apk".
8. Estrai lo ZIP scaricato: dentro trovi app-debug.apk. Toccalo per installarlo. Android potrebbe chiedere di consentire l'installazione da Chrome o dal gestore file.

NOTE
- Questo ambiente non può produrre direttamente un APK compilato.
- Il workflow compila una versione debug non firmata per distribuzione pubblica, adatta all'installazione manuale di prova.
- Il permesso CAMERA serve per comandare il flash LED.
- Non caricare credenziali o dati personali nel repository.
