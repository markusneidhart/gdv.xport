# Changelog

Hier sind alle wichtigen Änderungen dieses Projekts aufgeführt.
Es ersetzt die ehemaligen **Release Notes** und
[Changes-Report](http://www.aosd.de/gdv.xport/changes-report.html)
von gdv.xport und orientiert sich an 
[Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
genauso wie an [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
Aus Gründen der Übersichtlichkeit sind bei älteren Versionen die einzelnen Patch-Versionen nicht extra aufgeführt, sondern in der Minor-Version.


## [Unreleased]


### Security

- _fixed_: jackson-databind-Abhängigkeit aktualisiert
  ([Issue #81](https://github.com/oboehm/gdv.xport/issues/81))

### Added

- Doku um Docker-Einstieg ergänzt

### Changed

- _fixed_: gemischte Teildatensätze für Sparte Leben werden beim Import richtig erkannt
  ([Issue #82](https://github.com/oboehm/gdv.xport/issues/82))
- Performance um ca. 20% verbessert
- numerische Felder mit Format 'MMJJJJ' werden als Datum erkannt


## [6.1.2] - 2020-03-18

### Changed

- _fixed_: Datenpaket.pack()-Methode funktioniert jetzt korrekt bei Lücken
  ([Issue #79](https://github.com/oboehm/gdv.xport/issues/79))
- _fixed_: Vorsatz und Nachsatz im Datenpaket haben jetzt die gleiche Config wie das Datenpaket
- Datum mit "00" als Tag oder Monat (z.B. "00032022") werden nicht mehr als gültig angesehen


## [6.1.1] - 2020-03-08

### Changed

- Datum.setInhalt(..) überprüft das Datumsformat im STRICT-Modus


## [6.1.0] - 2022-03-04

### Added

- XmlService erlaubt jetzt die Registrierung eigener Sätze per XML-Resource
- SatzXml.of(..) akzeptiert jetzt auch URI (z.B. "classpath:/com/panik/unfall.xml") als Parameter

### Changed

- **ACHTUNG**: bei NumFeld und abgeleitete Klassen werden Nachkommastellen beim Setzen von Zahlen berücksichtigt
- Speicherbrauch um 55% reduziert.
  Damit können ca. 2 Mio. Datensätze (ca. 3 Mio. Records) bei 8 GB Hauptspeicher eingelesen werden.
- `Datenpaket.add(Datensatz)` setzt jetzt korrekte Anzahl (Teildaten-)-Saetze im Nachsatz
- verteilte Import-Funktionalität in Importer-Klasse zusammengefasst

### Removed

- VU-Nummer wird nicht mehr standardmäßig über Default-Properties vorbelegt


## [6.0.1] - 2022-01-06

### Security

- Log4J auf 2.17.1 angehoben
  ([CVE-2021-44832](https://github.com/advisories/GHSA-8489-44mv-ggj8))


## [6.0.0] - 2022-01-03

### Changed

- doppelte Performance
- Umstieg auf Java 11


### Removed

- Enum-Unterstützung wurde nach gdv-xport-deprecated verschoben
- eine Reihe Methoden, die für v6 als "deprecated" gekennzeichnet waren, wurden entfernt
- Property "gdv.numfeld.fill-blanks" wurde wieder entfernt


## [5.4] - 2021-12-12

### Security

- Log4J auf 2.17.0 angehoben
  (s.a. [Log4Shell / Apache Log4j Injection Vulnerability CVE-2021-44228](https://www.truesec.com/hub/blog/apache-log4j-injection-vulnerability-cve-2021-44228-impact-and-response))

### Removed

- einge 'deprecated' Klassen und Methoden wurden im Vorgriff auf v6 bereits entfernt


## [5.3] - 2021-11-11

### Added

- über Properties kann das Standard-Verhalten beim Setzen von Feldern oder Valdierung konfiguriert werden
  (s. [default.properties](lib/src/main/resources/gdv/xport/config/default.properties))
- Satz#setVermittler(..) und Satz#getVermittler() hinzugefügt

### Changed

- Validierung verbessert und ausgebaut
- rechtsbündige Ausrichtung bei AlphaNumFeld wird jetzt aus Bemerkungs-Tag abgeleitet
- statische Methoden in Config durch Properties ersetzt
- Datenpaket kann jetzt als Listener beim DatenpaketStreamer registriert werden
- Stand Test-Dateien teilweise aktualisiert
- _fixed_: Registrierte Datensätze werden in SatzRegistry richtig geklont
  ([Issue #66](https://github.com/oboehm/gdv.xport/issues/66))


## [5.2] - 2021-10-10

### Added

- zusätzliche Unterstützung von Datensätzen Stand 2009
  (Stand 2007 wird nicht unterstützt, weil die vom GDV bereit gestellte `VUVM20007_150507.xml` [Fehler](http://www.gdv-online.de/vuvm/bestand/best_2007.htm) enthält und damit z.B. Satz 0220.030 beim Import nicht richtig gefüllt wird)
- SatzRegistry#getSatz(..) akzeptiert jetzt zusätzlich die gewünschte Version
- Liste der gesetzten Versionen läßt sich jetzt über `Vorsatz.getSatzartVersionen()` abfragen
- Datenpaket besitzt jetzt eine pack()-Methode
  ([Issue #62](https://github.com/oboehm/gdv.xport/issues/62))
- GdvXmlFormatter generiert jetzt eine Info-Angabe nach dem XML-Header
- Satz#setVermittler(..) und #getVermittler() hinzugefügt

### Changed

- Datenpaket#setVuNummer(..) und Datenpaket#setVermittler(..) setzt jetzt nicht nur die Werte im Vor-/Nachsatz, sondern in allen Sätzen
- Datenpaket#importFrom(..)-Methoden berücksichtigen jetzt die angegebene Version aus dem Vorsatz und liefern Sätze der entsprechende Version (aktuell für Version VUVM2009, VUVM2013, VUVM2015 und VUVM2018, [Issue #64](https://github.com/oboehm/gdv.xport/issues/64))
- Datenpaket#importFrom(..)-Methoden liefern jetzt ein Datenpaket zur Weiterverarbeitung zurück, z.B. für `x = importFrom(istream).pack();`
- _fixed_: Parsen von Teildatensätzen ohne Satznummer korrigiert
  ([Issue #63](https://github.com/oboehm/gdv.xport/pull/63))
- _fixed_: Satznummer wird jetzt auch für Satzart 0220.030 und anderen Satzarten mit mehrdeutigen Satznummernpositionen korrekt erkannt
- _fixed_: SatzRegistry.getAllSupportedSaetze() liefert jetzt den passenden Vorsatz zur SatzRegistry
- Satz#set(..) durch Satz#setFeld(..) ersetzt und Satz#get(..) als @Deprecated markiert


## [5.1] - 2021-08-29

### Changed

- _fixed_: Datensätze mit Sparte Leben werden jetzt korrekt importiert
  ([Issue #61](https://github.com/oboehm/gdv.xport/issues/61))


## [5.0] - 2021-07-22

### Added

- statt "VUVM2018.xml" kann jetzt über '-Dgdv.XML-Resource=VUVM2015.xml' die 2015er-Version vorgegeben werden (oder andere Versionen)
- XmlSatzFactory erlaubt die Unterstützung mehrerer GDV-Versionen gleichzeitig
- GdvXmlFormatter formattiert (exportiert) jetzt Datensätze in das XML-Format von GDV-Online
- Versionen im Vorsatz können jetzt mit der aktuellen Version aus der XML-Beschreibung vorbelegt werden
- Nachsatz besitzt eine add-Methode
- numerische Felder akzeptieren BigDecimal und können auch nach BigDecimal konvertiert werden
- ByteAdresse ist als neuer Datentyp hinzugekommen und wird bei Teildatensatz.getFeld(..) als Parameter akzeptiert


### Changed

- es werden viel mehr Satzarten direkt auf der XML-Beschreibung in VUVM2018.xml abgeleitet
- Widersprüche in VUVM2018.xml wurden manuell bereinigt
- Enums zur Beschreibung von Datensätzen sind jetzt @deprecated  
- SatzTyp wurde ausgebaut und um of(..)-Methode erweitert
- gdv-xport-service auf OpenAPI / Swagger 3.0 aktualisiert
- _fixed_: Formatierung in Javaoc wird jetzt korrekt mit einem 't' geschrieben
  ([Issue #57](https://github.com/oboehm/gdv.xport/issues/57))



## [4.2] - 2020-09-17

### Added

- Vorsatz.setVersion(..) eingeführt

### Changed

- Versionen im Vorsatz werden nicht mehr vorbelegt


## [4.1] - 2020-06-08

### Added
- rudimentäre Docker-Unterstützung
  ([Issue #46](https://github.com/oboehm/gdv.xport/issues/46))
- Dokumentation um Bau-Anleitung ergänzt
  ([Issue #42](https://github.com/oboehm/gdv.xport/issues/42))

### Changed
- _fixed_: format-Parameter in `api/v1/Datenpaket*` wird jetzt beachtet
  ([Issue #48](https://github.com/oboehm/gdv.xport/issues/48))
- _fixed_: Fehler in der Swagger-API-Dokumentation behoben
  ([Issue #45](https://github.com/oboehm/gdv.xport/issues/45))


## [4.0] - 2019-01-20

### Added
- zusätzlicher Konstruktor für Wagnisart in Satzart 0220.010 und 0221.010
  (behebt [Issue #39](https://github.com/oboehm/gdv.xport/issues/39))
- es gibt ein neues Modul 'gdv-xport-deprecated', in der Klassen und Enumerationen verschoben wurden, die 'deprecated' sind.
  Dieses Abhängigkeit wird nur dann benötigt, wenn man noch einer dieser Klassen verwendet

### Changed
- _fixed_: Security-Problem mit jackson-databind behoben
  ([Issue #38](https://github.com/oboehm/gdv.xport/issues/38))
- _fixed_: Umlaut-Problem und fehlerhafter XML-Header
  ([Issue #37](https://github.com/oboehm/gdv.xport/issues/37))
- _fixed_: Start-Probleme mit Uberjar
  ([Issue #36](https://github.com/oboehm/gdv.xport/issues/36))
- _fixed_: Problem beim CSV-Export mit Sonderzeichen (Strichpunkt) behoben
  ([Issue #35](https://github.com/oboehm/gdv.xport/issues/35))
- Aktualisierung auf die aktuelle XML-Version von 2018.
- Bezeichner-Klasse wurde aufgeräumt und falsche bzw. nicht benötigte Konstanten entfernt.
  Ebenso wurden manche Konstanten in den Feld-Beschreibungen korrigiert.
- Komplexität reduziert
- Umstellung auf Java 8
- Umstellung REST-Services auf Spring-Boot 2


### [0.0.1 bis 3.2.2] - ab 2009

Die Versionen 3.x und früher sind auf der Maven-Projekt-Seite unter [changes](http://www.aosd.de/gdv.xport/changes-report.html) aufgelistet.
