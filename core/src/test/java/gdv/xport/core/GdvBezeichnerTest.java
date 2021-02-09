/*
 * Copyright (c) 2014 by Oli B.
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
 * (c)reated 25.09.2014 by Oli B. (ob@aosd.de)
 */

package gdv.xport.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import patterntesting.runtime.junit.ObjectTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit-Tests fuer die {@link GdvBezeichner}-Klasse.
 *
 * @author oliver (oliver.boehm@gmail.com)
 * @since 1.0 (25.09.2014)
 */
public class GdvBezeichnerTest {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Test-Methode fuer {@link GdvBezeichner#toString()}.
     */
    @Test
    public void testToString() {
        String name = "Anrede";
        String s = new GdvBezeichner(name).toString();
        assertTrue(s, s.contains(name));
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Die Daten
     * dazu stammen aus der XML-Datei ("VUVM2013").
     */
    @Test
    public void testGetTechnischerName() {
        GdvBezeichner vermittler = new GdvBezeichner("Gesch\u00e4ftsstelle / Vermittler");
        assertEquals("GeschaeftsstelleVermittler", vermittler.getTechnischerName());
    }

    @Test
    public void testGetTechnischerNameEmpty() {
        GdvBezeichner sparte = new GdvBezeichner("Sparte", "");
        assertEquals(sparte.getName(), sparte.getTechnischerName());
    }

    @Test
    public void testGetTechnischerNameVuNr() {
        GdvBezeichner vunr = new GdvBezeichner("VU-Nummer");
        assertEquals("VuNr", vunr.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Namen, die
     * auf "Datum" aufhoeren, haben meist "Dat" als Endung fuer den technischen
     * Namen.
     */
    @Test
    public void testGetTechnischerNameForDatum() {
        GdvBezeichner zuzahlungsdatum = new GdvBezeichner("Zuzahlungsdatum");
        assertEquals("Zuzahlungsdat", zuzahlungsdatum.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Namen, die
     * "Datum" als Bestandteil haben, haben "Dat" als Teil des technischen
     * Namens.
     */
    @Test
    public void testGetTechnischerNameForDatumInside() {
        GdvBezeichner dat = new GdvBezeichner("Aufgabedatum dieses Geschaeftsvorfalls");
        assertEquals("AufgabedatDiesesGeschaeftsvorfalls", dat.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Namen, die auf
     * "Waehrungseinheit" aufhoeren, haben meist "WE" als Endung fuer den
     * technischen Namen.
     */
    @Test
    public void testGetTechnischerNameForWaehrungseinheit() {
        GdvBezeichner zuzahlungsdatum = new GdvBezeichner("Zuzahlungsbetrag in Waehrungseinheiten");
        assertEquals("ZuzahlungsbetragInWE", zuzahlungsdatum.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Namen, die auf
     * "VS" aufhoeren, haben meist "Vs" (mit kleinem 's') als Endung fuer den
     * technischen Namen.
     */
    @Test
    public void testGetTechnischerNameForVS() {
        GdvBezeichner zuzahlungsdatum = new GdvBezeichner("Erlebensfall VS");
        assertEquals("ErlebensfallVs", zuzahlungsdatum.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Aus "%-Satz"
     * wird "...ProzSatz" als technischer Name.
     */
    @Test
    public void testGetTechnischerNameForProzSatz() {
        GdvBezeichner prozSatz = new GdvBezeichner("Einschluss %-Satz");
        assertEquals("EinschlussProzSatz", prozSatz.getTechnischerName());
    }

    /**
     * Test-Methode fuer {@link GdvBezeichner#getTechnischerName()}. Aus
     * "...VP..." wird "...Vp..." als technischer Name.
     */
    @Test
    public void testGetTechnischerNameForVp() {
        GdvBezeichner vp = new GdvBezeichner("Einschlussdatum VP / Personengruppe");
        assertEquals("EinschlussdatVpPersonengruppe", vp.getTechnischerName());
    }

    /**
     * Artikel wie "der" sind nicht Bestandteil eines technischen Namens.
     */
    @Test
    public void testGetTechnischerNameWithArtikel() {
        assertEquals("ErsteZulassungAufDenVn", new GdvBezeichner("Erste Zulassung auf den VN").getTechnischerName());
    }

    /**
     * Artikel wie "den" sind dagegen Bestandteil des technischen Namens.
     */
    @Test
    public void testGetTechnischerNameWithDen() {
        assertEquals(
                "AbstandJahresrentenaenderungstermine",
                new GdvBezeichner("Abstand der Jahresrentenaenderungstermine").getTechnischerName()
        );
    }

    /**
     * "Versicherung" wird als "Vers" abgekuerzt.
     */
    @Test
    public void testGetTechnischerNameWithVersicherung() {
        assertEquals("ErweiterteNeuwertVers", new GdvBezeichner("erweiterte Neuwertversicherung").getTechnischerName());
    }

    /**
     * "eVB" wird als "eVB" abgekuerzt.
     */
    @Test
    public void testGetTechnischerNameEVB() {
        assertEquals("eVBNummer", new GdvBezeichner("eVB-Nummer").getTechnischerName());
    }

    /**
     * "...nummer" wird nur manchmal als "...Nr" abgekuerzt.
     */
    @Test
    public void testGetTechnischerNameWithNummer() {
        assertEquals("Referenznummer", new GdvBezeichner("Referenznummer").getTechnischerName());
    }

    /**
     * Zwei GdvBezeichner mit dem identischen Namen sollten natuerlich gleich
     * sein.
     */
    @Test
    public void testEqualsExact() {
        String name = "Hello";
        ObjectTester.assertEquals(new GdvBezeichner(name), new GdvBezeichner(name));
    }

    /**
     * Gross-/Kleinschreibung sollte egal fuer den Vergleich sein.
     */
    @Test
    public void testEqualsUpperCase() {
        ObjectTester.assertEquals(new GdvBezeichner("Gross"), new GdvBezeichner("GROSS"));
    }

    /**
     * Hier gab es Probleme, da sowohl Leerzeichen, Sonderzeichen ("/") und
     * Umlaut eine besondere Herausforderung fuer den Vergleich darstellten.
     */
    @Test
    public void testEqualsVermittler() {
        ObjectTester.assertEquals(new GdvBezeichner("Geschaeftsstelle/Vermittler"),
                new GdvBezeichner("Gesch\u00e4ftsstelle / Vermittler"));
    }

    /**
     * Auch die VU-Nummer hat so ihre Besonderheiten. So ist der technische
     * Name dafuer "VuNr", was sich nicht direkt aus dem Name ("VU-Nummer")
     * ableiten laesst.
     */
    @Test
    public void testEqualsVuNummer() {
        ObjectTester.assertEquals(new GdvBezeichner("VU-Nummer", "VuNr"), new GdvBezeichner("VuNr"));
    }

    /**
     * Das gleiche wie fuer den vorigen Test gilt auch fuer die
     * "Versicherungsschein-Nummer": diese wird als "VsNr" abgekuerzt.
     */
    @Test
    public void testEqualsVsNr() {
        ObjectTester.assertEquals(new GdvBezeichner("Versicherungsschein-Nummer", "VsNr"), new GdvBezeichner("VsNr"));
    }

}
