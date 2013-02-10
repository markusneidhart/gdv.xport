/*
 * Copyright (c) 2011, 2012 by Oli B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 23.03.2011 by Oli B. (ob@aosd.de)
 */

package gdv.xport.satz.feld.sparte10.wagnisart13;

import gdv.xport.annotation.FeldInfo;
import gdv.xport.annotation.FelderInfo;
import gdv.xport.feld.AlphaNumFeld;
import gdv.xport.feld.Betrag;
import gdv.xport.feld.Datum;
import gdv.xport.feld.NumFeld;
import gdv.xport.feld.Zeichen;
import gdv.xport.satz.feld.common.Feld1bis7;

/**
 * Diese Enum-Klasse repraesentiert die Felder fuer Satzart 220, Sparte 10 <br/>
 * (Leben - Kapitallebens-/Risikovers. = Wagnisart 1 u. 3).
 * 
 * @author ralfklemmer
 * @since 17.01.2013
 */
public enum Feld220Wagnis13 {

    // /// Teildatensatz 1 /////////////////////////////////////////////////

    /** Feld 1 - 7 sind fuer jeden (Teil-)Datensatz identisch. */
    @FelderInfo(teildatensatz = 1, type = Feld1bis7.class)
    INTRO1,

    /**
     * Lfd. Nummer der versicherten Person (VP).<br/>
     * lfd. Nr., die im VU geführt wird
     */
    @FeldInfo(teildatensatz = 1, nr = 8, type = AlphaNumFeld.class, anzahlBytes = 17, byteAdresse = 43)
    LFD_NUMMER_VP_PERSONENGRUPPE,

    /**
     * Wagnisart.<br/>
     * 1 = Kapitallebensversicherung 3 = Risikoversicherung
     */
    @FeldInfo(teildatensatz = 1, nr = 9, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 60)
    WAGNISART,

    /**
     * Lfd Nummer zur Wagnisart.<br/>
     */
    @FeldInfo(teildatensatz = 1, nr = 10, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 61)
    LFD_NUMMER_ZUR_WAGNISART,

    /**
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden <br>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 11, type = Datum.class, anzahlBytes = 8, byteAdresse = 62)
    BEGINN,

    /**
     * Vertragsablaufdatum (inkl. Abrufphase) Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt
     * werden.<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 12, type = Datum.class, anzahlBytes = 8, byteAdresse = 70)
    ABLAUF,

    /**
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 13, type = Datum.class, anzahlBytes = 8, byteAdresse = 78)
    AENDERUNG,

    /**
     * JJJ Jahre (lebenslänglich ist mit Wert 999 zu schlüsseln)
     */
    @FeldInfo(teildatensatz = 1, nr = 14, type = NumFeld.class, anzahlBytes = 3, byteAdresse = 86)
    VERTRAGSLAUFZEIT,

    /**
     * 1 = Grundvertrag<br/>
     * 2 = Dynamik<br/>
     * 3 = Grundvertrag incl. vorletzter Dynamik<br/>
     * 4 = Grundvertrag incl. letzter Dynamik.
     */
    @FeldInfo(teildatensatz = 1, nr = 15, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 89)
    VERTRAGSART,

    /**
     * Beitrag gem. Zahlungsweise (10,2 Stelle)
     */
    @FeldInfo(teildatensatz = 1, nr = 16, type = Betrag.class, anzahlBytes = 12, byteAdresse = 90)
    BEITRAG,

    /**
     * Erlebensfall VS in Währungseinheiten zum Ablauf<br/>
     * Vertraglich vereinbarte Versicherungssumme im Erlebensfall (aktuelle VS)<br/>
     * zum Ablauf (inkl. Abrufphase)<br/>
     * (9,0 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 17, type = NumFeld.class, anzahlBytes = 9, byteAdresse = 102)
    ERLEBENSFALL_VS_IN_WAEHRUNGSEINHEITEN_ZUM_ABLAUF,

    /**
     * Todesfall VS in Währungseinheiten zum Ablauf<br/>
     * Vertraglich vereinbarte Versicherungssumme im Todesfall (aktuelle VS)<br/>
     * zum Ablauf (inkl. Abrufphase)<br/>
     * (9,0 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 18, type = Betrag.class, anzahlBytes = 9, byteAdresse = 111)
    TODESFALL_VS_IN_WAEHRUNGSEINHEIT_ZUM_ABLAUF,

    /**
     * fallende VS<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 19, type = AlphaNumFeld.class, anzahlBytes = 1, byteAdresse = 120)
    FALLENDE_VS,

    /**
     * Nettobeitrag in Währungseinheiten<br/>
     * Beitrag gem. Zahlungsweise nach Überschussanrehnung<br/>
     * (10,2 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 20, type = Betrag.class, anzahlBytes = 12, byteAdresse = 121)
    NETTOBEITRAG_IN_WAEHRUNGSEINHEIT,

    /**
     * Erlebensfall VS II in Währungseinheiten zum Ablauf<br/>
     * nach Überschussanrechnung erreichte Summe zum Ablauf (inkl. Abrufphase)<br/>
     * (9,0 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 21, type = NumFeld.class, anzahlBytes = 9, byteAdresse = 133)
    ERLEBENSFALL_VS_II_IN_WAEHRUNGSEINHEIT_ZUM_ABLAUF,

    /**
     * Tarifbezeichnung.<br/>
     * Kurzbezeichnung des Tarifes
     */
    @FeldInfo(teildatensatz = 1, nr = 22, type = AlphaNumFeld.class, anzahlBytes = 20, byteAdresse = 142)
    TARIFBEZEICHNUNG,

    /**
     * Status.<br/>
     * siehe Anlage 24
     */
    @FeldInfo(teildatensatz = 1, nr = 23, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 162)
    STATUS,

    /**
     * Beitragsfreie Erlebensfall VS in Währungseinheiten.<br/>
     */
    @FeldInfo(teildatensatz = 1, nr = 24, type = NumFeld.class, anzahlBytes = 9, byteAdresse = 163)
    BEITRAGSFREIE_ERLEBENSFALL_VS_IN_WAEHRUNGSEINHEITEN,

    /**
     * Beitragsfreie Todesfall VS in Währungseinheiten.<br/>
     */
    @FeldInfo(teildatensatz = 1, nr = 25, type = NumFeld.class, anzahlBytes = 9, byteAdresse = 172)
    BEITRAGSFREIE_TODESFALL_VS_IN_WAEHRUNGSEINHEITEN,

    /**
     * Laufzeitverkürzung.<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 26, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 181)
    LAUFZEITVERKUERZUNG,

    /**
     * Gewinnverwendungsart.<br/>
     * siehe Anlage 71
     */
    @FeldInfo(teildatensatz = 1, nr = 27, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 182)
    GEWINNVERWENDUNGSART,

    /**
     * Überschuss gültig ab.<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden<br/>
     * Bezugsjahr zum Bonus/Ansammlungsguthaben<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 28, type = Datum.class, anzahlBytes = 8, byteAdresse = 183)
    UEBERSCHUSS_GUELTIG_AB,

    /**
     * Risikoeinschränkung<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 29, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 191)
    RISIKOEINSCHRAENKUNG,

    /**
     * Risikozuschläge<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 30, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 192)
    RISIKOZUSCHLAEGE,

    /**
     * Dynamik %-Satz<br/>
     * (3,2 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 31, type = NumFeld.class, anzahlBytes = 5, byteAdresse = 193)
    DYNAMIK_PROZENT_SATZ,

    /**
     * Erhöhungsbasis Dynamik<br/>
     * 1 = Beitrag, 2 = Versicherungssumme
     */
    @FeldInfo(teildatensatz = 1, nr = 32, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 198)
    ERHOEHUNGSBASIS_DYNAMIK,

    /**
     * Erhöhungsart Dynamik<br/>
     * siehe Anlage 72
     */
    @FeldInfo(teildatensatz = 1, nr = 33, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 199)
    ERHOEHUNGSART_DYNAMIK,

    /**
     * Dynamikstop<br/>
     * Datum, ab dem keine Dynamikerhöhung mehr möglich ist.<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 34, type = Datum.class, anzahlBytes = 8, byteAdresse = 200)
    DYNAMIKSTOP,

    /**
     * Datum der letzten positiven Dynamik<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 1, nr = 35, type = Datum.class, anzahlBytes = 8, byteAdresse = 208)
    DATUM_DER_LETZTEN_POSITIVEN_DYNAMIK,

    /**
     * Endalter<br/>
     * JJJ Alter Lebenslänglich ist mit 999 zu schlüsseln
     */
    @FeldInfo(teildatensatz = 1, nr = 36, type = Zeichen.class, anzahlBytes = 3, byteAdresse = 216)
    ENDALTER,

    /**
     * Eintrittsalter<br/>
     * JJ Alter
     */
    @FeldInfo(teildatensatz = 1, nr = 37, type = NumFeld.class, anzahlBytes = 2, byteAdresse = 219)
    EINTRITTSALTER,

    /**
     * Vertrag mit Zuwachsgarantie<br/>
     * Recht auf Dynamikerhöhungen<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 38, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 221)
    VERTRAG_MIT_ZUWACHSGARANTIE,

    /**
     * Rückkaufswert in Währungseinheiten<br/>
     * kumuliert, incl. aller Dynamiken<br/>
     * (9,0 Stellen)
     */
    @FeldInfo(teildatensatz = 1, nr = 39, type = Betrag.class, anzahlBytes = 9, byteAdresse = 222)
    RUECKKAUFSWERT_IN_WAEHRUNGSEINHEITEN,

    /**
     * Rückkaufswert gültig ab<br/>
     * Monat / Jahr (MMJJJJ) Sollte Monat nicht vorhanden sein, muss "00" geschlüsselt werden
     */
    @FeldInfo(teildatensatz = 1, nr = 40, type = Datum.class, anzahlBytes = 6, byteAdresse = 231)
    RUECKKAUFSWERT_GUELTIG_AB,

    /**
     * Guthaben Divid. Ansammlungen in Währungseinheiten<br/>
     * Aktuelle Ansammlung
     */
    @FeldInfo(teildatensatz = 1, nr = 41, type = Betrag.class, anzahlBytes = 9, byteAdresse = 237)
    GUTHABEN_DIVID_ANSAMMLUNGEN_IN_WAEHRUNGSEINHEITEN,

    /**
     * Umtauschrecht<br/>
     * Recht auf Wagniswechsel<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 1, nr = 42, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 246)
    UMTAUSCHRECHT,

    /**
     * Sparvorgang<br/>
     * 1 = gestundet, 2 = ausgesetzt
     */
    @FeldInfo(teildatensatz = 1, nr = 43, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 247)
    SPARVORGANG,

    /**
     * gestundet / ausgesetzt bis<br/>
     * Sparvorgang bis. Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden
     */
    @FeldInfo(teildatensatz = 1, nr = 44, type = Datum.class, anzahlBytes = 8, byteAdresse = 248)
    GESTUNDET_AUSGESETZT_BIS,

    // /// Teildatensatz 2 /////////////////////////////////////////////////

    /** Feld 1 - 7 sind fuer jeden (Teil-)Datensatz identisch. */
    @FelderInfo(teildatensatz = 2, type = Feld1bis7.class)
    INTRO2,

    /**
     * Lfd. Nummer der versicherten Person (VP).<br/>
     * lfd. Nr., die im VU geführt wird
     */
    @FeldInfo(teildatensatz = 2, nr = 8, type = AlphaNumFeld.class, anzahlBytes = 17, byteAdresse = 43)
    LFD_NUMMER_VP_PERSONENGRUPPE2,

    /**
     * Wagnisart.<br/>
     * 1 = Kapitallebensversicherung 3 = Risikoversicherung
     */
    @FeldInfo(teildatensatz = 2, nr = 9, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 60)
    WAGNISART2,

    /**
     * Lfd Nummer zur Wagnisart.<br/>
     */
    @FeldInfo(teildatensatz = 2, nr = 10, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 61)
    LFD_NUMMER_ZUR_WAGNISART2,

    /**
     * Abweichende Vertragslaufzeit<br/>
     * Abgekürzte oder verlängerte Vertragslaufzeit Anzahl Jahre (JJJ) Lebenslänglich ist mit "999" zu schlüsseln<br/>
     */
    @FeldInfo(teildatensatz = 2, nr = 11, type = NumFeld.class, anzahlBytes = 3, byteAdresse = 62)
    ABWEICHENDE_VERTRAGSLAUFZEIT,

    /**
     * Abweichender Ablauf<br/>
     * Ablauf abgekürzt oder verlängert Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt <br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 2, nr = 12, type = Datum.class, anzahlBytes = 8, byteAdresse = 65)
    ABWEICHENDER_ABLAUF,

    /**
     * Risikozuschlag in Währungseinheiten<br/>
     * Risikozuschlag gem. Zahlungsweise<br/>
     * (10,2 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 13, type = Betrag.class, anzahlBytes = 12, byteAdresse = 73)
    RISIKOZUSCHLAG_IN_WAEHRUNGSEINHEITEN,

    /**
     * Tarifbezeichnung des Folgetarifs<br/>
     * Klartextbezeichnung des Folgetarifs (wenn der Tarif noch nicht umgestellt wurde)
     */
    @FeldInfo(teildatensatz = 2, nr = 14, type = AlphaNumFeld.class, anzahlBytes = 30, byteAdresse = 85)
    TARIFBEZEICHNUNG_DES_FOLGETARIFS,

    /**
     * Umstellungsdatum des Folgetarifs<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 2, nr = 15, type = Datum.class, anzahlBytes = 8, byteAdresse = 115)
    UMSTELLUNGSDATUM_DES_FOLGETARIFS,

    /**
     * Zukünftiger Beitrag in Währungseinheiten<br/>
     * gem. Zahlungsweise wenn der Beitrag noch nicht umgestellt wurde<br/>
     * (10,2 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 16, type = Betrag.class, anzahlBytes = 12, byteAdresse = 123)
    ZUKUENFTIGER_BEITRAG_IN_WAEHRUNGSEINHEITEN,

    /**
     * Vertragsbedingung<br/>
     * Individuelle Vertragsbedingung (frei definierbar)
     */
    @FeldInfo(teildatensatz = 2, nr = 17, type = AlphaNumFeld.class, anzahlBytes = 30, byteAdresse = 135)
    VERTRAGSBEDINGUNG,

    /**
     * Dynamikbeginn<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden.<br/>
     * Datum der ersten Dynamikerhöhung<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 2, nr = 18, type = Datum.class, anzahlBytes = 8, byteAdresse = 165)
    DYNAMIKBEGINN,

    /**
     * Abweichendes Dynamikendalter<br/>
     * Wenn abweichend von normalem Endalter<br/>
     * (JJJ)
     */
    @FeldInfo(teildatensatz = 2, nr = 19, type = NumFeld.class, anzahlBytes = 3, byteAdresse = 173)
    ABWEICHENDES_DYNAMIKENDALTER,

    /**
     * Absoluter Dynamikerhöhungsbetrag<br/>
     * (9,0 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 20, type = Betrag.class, anzahlBytes = 9, byteAdresse = 176)
    ABSOLUTER_DYNAMIKERHOEHUNGSBETRAG,

    /**
     * Anteiliger Dynamikprozentsatz<br/>
     * Prozentualer Anteil am Dynamikerhöhungsbetrag Sozialversicherung prozentual oder absolut<br/>
     * bzw. Gehaltsanpassung (3,2 Stellen)<br/>
     * z. B.: 100,00 = volle absolute BfA-Dynamik<br/>
     * 50,00 = halbe absolute BfA-Dynamik (3,2 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 21, type = NumFeld.class, anzahlBytes = 5, byteAdresse = 185)
    ANTEILIGER_DYNAMIKPROZENTSATZ,

    /**
     * Vereinbarter Dynamikmindestanpassungsprozentsatz<br/>
     * (3,2 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 22, type = NumFeld.class, anzahlBytes = 5, byteAdresse = 190)
    VEREINBARTER_DYNAMIKMINDESTANPASSUNGSPROZENTSATZ,

    /**
     * Vereinbarter Dynamikmaximalanpassungsprozentsatz<br/>
     * (3,2 Stellen)
     */
    @FeldInfo(teildatensatz = 2, nr = 23, type = NumFeld.class, anzahlBytes = 5, byteAdresse = 195)
    VEREINBARTER_DYNAMIKMAXIMALANPASSUNGSPROZENTSATZ,

    /**
     * Anzahl verbleibende Dynamikwidersprüche<br/>
     * Anzahl der verbleibenden Dynamikwidersprüche, ohne das Recht auf Dynamikerhöhung zu verlieren
     */
    @FeldInfo(teildatensatz = 2, nr = 24, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 200)
    ANZAHL_VERBLEIBENDE_DYNAMIKWIDERSPRUECHE,

    /**
     * Leistung bei schwerer Erkrankung<br/>
     * "Dread Disease"<br/>
     * 0 = nein, 1 = ja
     */
    @FeldInfo(teildatensatz = 2, nr = 25, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 201)
    LEISTUNG_BEI_SCHWERER_ERKRANKUNG,

    /**
     * Versicherte Erkrankungen<br/>
     * Versicherte Erkrankungen bei "Dread Disease" (15 x 2 Stellen)<br/>
     * 01 = Herzinfarkt<br/>
     * 02 = Schlaganfall<br/>
     * 03 = Multiple Sklerose<br/>
     * 04 = Krebs<br/>
     * 05 = Bypass-Operation<br/>
     * 06 = Dauerdialyse<br/>
     * 07 = Nierentransplantation
     */
    @FeldInfo(teildatensatz = 2, nr = 26, type = AlphaNumFeld.class, anzahlBytes = 30, byteAdresse = 202)
    VERSICHERTE_ERKRANKUNGEN,

    /**
     * Leistungsbeginn ab<br/>
     * Leistungsbeginn / Karenzzeit bei Dread Disease"<br/>
     * (xxxT) oder (xxxW).<br/>
     * Die letzte Stelle muss mit "T" oder "W" belegt werden wenn Eintrag vorhanden,<br/>
     * z. B. 004T = 4 Tage 007W = 7 Wochen
     */
    @FeldInfo(teildatensatz = 2, nr = 27, type = AlphaNumFeld.class, anzahlBytes = 4, byteAdresse = 232)
    LEISTUNGSBEGINN_AB,

    /**
     * Beginn Abrufphase<br/>
     * Termin an dem die Abrufphase beginnt.<br/>
     * Sollten Tag und/oder Monat nicht vorhanden sein, muss "00" geschlüsselt werden.<br/>
     * Tag/Monat/Jahr (TTMMJJJJ)
     */
    @FeldInfo(teildatensatz = 2, nr = 28, type = Datum.class, anzahlBytes = 8, byteAdresse = 236)
    BEGINN_ABRUFPHASE,

    /**
     * Leerstellen.<br/>
     */
    @FeldInfo(teildatensatz = 2, nr = 29, type = AlphaNumFeld.class, anzahlBytes = 12, byteAdresse = 244)
    LEERSTELLEN,

    // /// Teildatensatz 3 /////////////////////////////////////////////////

    /** Feld 1 - 7 sind fuer jeden (Teil-)Datensatz identisch. */
    @FelderInfo(teildatensatz = 3, type = Feld1bis7.class)
    INTRO3,

    /**
     * Lfd. Nummer der versicherten Person (VP).<br/>
     * lfd. Nr., die im VU geführt wird
     */
    @FeldInfo(teildatensatz = 3, nr = 8, type = AlphaNumFeld.class, anzahlBytes = 17, byteAdresse = 43)
    LFD_NUMMER_VP_PERSONENGRUPPE3,

    /**
     * Wagnisart.<br/>
     * 1 = Kapitallebensversicherung 3 = Risikoversicherung
     */
    @FeldInfo(teildatensatz = 3, nr = 9, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 60)
    WAGNISART3,

    /**
     * Lfd Nummer zur Wagnisart.<br/>
     */
    @FeldInfo(teildatensatz = 3, nr = 10, type = Zeichen.class, anzahlBytes = 1, byteAdresse = 61)
    LFD_NUMMER_ZUR_WAGNISART3,

    /**
     * Erlebensfall VS in Währungseinheiten zum Beginn der Abrufphase<br/>
     * Vertraglich vereinbarte Versicherungssumme im Erlebensfall (aktuelle VS) zum Beginn der Abrufphase<br/>
     * (12,2 Stellen)
     */
    @FeldInfo(teildatensatz = 3, nr = 11, type = Betrag.class, anzahlBytes = 14, byteAdresse = 76)
    ERLEBENSFALL_VS_IN_WAEHRUNGSEINHEITEN_ZUM_BEGINN_DER_ABRUFPHASE,

    /**
     * Todesfall VS in Währungseinheiten zum Beginn der Abrufphase<br/>
     * Vertraglich vereinbarte Versicherungssumme im Todesfall (aktuelle VS) zum Beginn der Abrufphase<br/>
     * (12,2 Stellen)
     */
    @FeldInfo(teildatensatz = 3, nr = 12, type = Betrag.class, anzahlBytes = 14, byteAdresse = 90)
    TODESFALL_VS_IN_WAEHRUNGSEINHEITEN_ZUM_BEGINN_DER_ABRUFPHASE,
    /**
     * Todesfall VS II in Währungseinheiten zum Beginn der Abrufphase<br/>
     * nach Überschussanrechnung erreichte Summe zum Beginn der Abrufphase<br/>
     * (12,2 Stellen)
     */
    @FeldInfo(teildatensatz = 3, nr = 13, type = Betrag.class, anzahlBytes = 14, byteAdresse = 90)
    ERLEBENSFALL_VS_II_IN_WAEHRUNGSEINHEITEN_ZUM_BEGINN_DER_ABRUFPHASE,

    /**
     * Leerstellen.<br/>
     */
    @FeldInfo(teildatensatz = 3, nr = 14, type = AlphaNumFeld.class, anzahlBytes = 152, byteAdresse = 104)
    LEERSTELLEN2,
}
