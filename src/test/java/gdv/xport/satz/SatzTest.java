/*
 * Copyright (c) 2009 - 2012 by Oli B.
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
 * (c)reated 19.10.2009 by Oli B. (ob@aosd.de)
 */

package gdv.xport.satz;

import static gdv.xport.feld.Bezeichner.NAME1;
import static gdv.xport.feld.Bezeichner.ORT;
import static org.junit.Assert.*;
import gdv.xport.annotation.FelderInfo;
import gdv.xport.feld.*;
import gdv.xport.satz.feld.Feld200;
import gdv.xport.satz.feld.MetaFeldInfo;
import gdv.xport.satz.feld.common.Feld1bis7;
import gdv.xport.satz.feld.sparte53.Feld220;

import java.io.*;
import java.util.List;

import net.sf.oval.ConstraintViolation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * Test-Klasse fuer Satz.
 *
 * @author oliver
 * @since 19.10.2009
 */
public final class SatzTest extends AbstractSatzTest {

    private static final Log log = LogFactory.getLog(SatzTest.class);
    private static final String INPUT_SATZ_123
            = "0123Hello 007                                                   "
            + "                                                                "
            + "                                                                "
            + "                                                               1"
            + "\n";
    private final Satz satz = new Datensatz(123);

    /**
     * Hier erzeugen wir einen Satz zum Testen.
     *
     * @return Satz zum Testen
     * @see gdv.xport.satz.AbstractSatzTest#getSatz()
     */
    @Override
    protected Satz getSatz() {
        return new Datensatz(123);
    }

    /**
     * Ein einfacher Test, der lediglich die Satzart ueberprueft.
     */
    @Test
    public void testSatz() {
        Satz satz100 = new Datensatz(100, 1);
        assertEquals(satz100.getSatzart(), 100);
    }

    /**
     * Test method for {@link gdv.xport.satz.Satz#add(gdv.xport.feld.Feld)}.
     * Falls ein Feld hinzugefuegt wird, das ein anderes Feld (teilweise)
     * ueberschreiben wuerde, sollte eine Exception geworfen werden.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdd() {
        satz.add(new AlphaNumFeld(NAME1, 30, 44));
        satz.add(new AlphaNumFeld("Bumm", 4, 50));
    }

    /**
     * Test method for
     * {@link gdv.xport.satz.Satz#set(java.lang.String, java.lang.String)}. Es
     * kann nur ein Feld gesetzt werden, das vorher ueber "add(..)" hinzugefuegt
     * wurde.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSet() {
        satz.set("gibtsnet", "plopp");
    }

    /**
     * Test method for {@link gdv.xport.satz.Satz#get(java.lang.String)}.
     */
    @Test
    public void testGet() {
        satz.add(new AlphaNumFeld(ORT, 30, 50));
        satz.set(ORT, "Stuttgart");
        assertEquals("Stuttgart", satz.get(ORT).trim());
    }

    /**
     * Test method for {@link gdv.xport.satz.Satz#getFeld(java.lang.String)}.
     * Fuer ein Feld, das nicht existiert, wird nicht mehr NULL_FELD als
     * Ergebnis erwartet sondern eine IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetFeld() {
        Feld f = satz.getFeld("hemmernet");
        assertSame(Feld.NULL_FELD, f);
    }

    /**
     * Ein Export mit einem Teildatensatz sollte aus genau 256 Bytes bestehen,
     * da in der SetUp-Methode das EOD-Zeichen auf "" gesetzt wurde.
     *
     * @throws IOException
     *             sollte nicht auftreten, da wir mit StringWriter arbeiten
     */
    @Test
    public void testExport() throws IOException {
        StringWriter swriter = new StringWriter(256);
        satz.export(swriter, "");
        swriter.close();
        String content = swriter.toString();
        assertEquals(256, content.length());
        assertEquals(satz.getSatzartFeld().getInhalt(), content.substring(0, 4));
    }

    /**
     * Test-Methode fuer {@link Satz#export(File)}.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testExportFile() throws IOException {
        File tmpFile = File.createTempFile("gdv", ".xport");
        log.info("File \"" + tmpFile + "\" created.");
        try {
            satz.export(tmpFile);
            String exported = FileUtils.readFileToString(tmpFile);
            assertEquals(satz.toLongString(), exported);
        } finally {
            tmpFile.delete();
            log.info("File \"" + tmpFile + "\" deleted.");
        }
    }

    /**
     * Ein einfach Import-Test.
     *
     * @throws IOException sollte eigenlich nicht passieren, da wir von einem
     *             String lesen
     */
    @Test
    public void testImport() throws IOException {
        Satz x = new Datensatz(123);
        x.add(new AlphaNumFeld("F1", 5, 5));
        x.importFrom(INPUT_SATZ_123);
        assertEquals(123, x.getSatzart());
        assertEquals("Hello", x.getFeld("F1").getInhalt());
        assertEquals(INPUT_SATZ_123.trim(), x.toLongString().trim());
    }

    /**
     * Hier probieren wir jetzt den Import ueber einen Reader.
     *
     * @throws IOException sollte eigenlich nicht passieren, da wir von einem
     *             String lesen
     */
    @Test
    public void testImportFromReader() throws IOException {
        Satz x = new Datensatz(123, 7);
        Reader reader = new StringReader(INPUT_SATZ_123);
        try {
            checkImport(x, reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Hier probieren wir jetzt 2 Saetze ueber einen Reader einzulesen.
     *
     * @throws IOException sollte eigenlich nicht passieren, da wir von einem
     *             String lesen
     */
    @Test
    //@Ignore // Fehler ist noch nicht behoben
    public void testImportFromReaderTwice() throws IOException {
        Satz x = new Datensatz(123, 7);
        Reader reader = new StringReader(INPUT_SATZ_123 + INPUT_SATZ_123);
        try {
            checkImport(x, reader);
            checkImport(x, reader);
        } finally {
            reader.close();
        }
    }

    private void checkImport(final Satz x, final Reader reader) throws IOException {
        x.importFrom(reader);
        assertEquals(INPUT_SATZ_123.trim(), x.toLongString().trim());
    }

    /**
     * Test-Methode fuer {@link Satz#importFrom(File)}.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testImportFile() throws IOException {
        File tmpFile = File.createTempFile("gdv", ".xport");
        log.info("File \"" + tmpFile + "\" created.");
        try {
            String fileContent = satz.toLongString();
            FileUtils.writeStringToFile(tmpFile, fileContent);
            satz.importFrom(tmpFile);
            assertEquals(fileContent, satz.toLongString());
        } finally {
            tmpFile.delete();
            log.info("File \"" + tmpFile + "\" deleted.");
        }
    }

    /**
     * Ein unbekannte Datensatz ist nicht valide.
     */
    @Test
    public void testIsValid() {
        Satz a = new Datensatz("xxxx", 1);
        assertFalse("Diese Satzart gibt es nicht: " + a, a.isValid());
    }

    /**
     * Ein (Daten-)Satz mit einem nicht validen Feld ist auch nicht valide.
     */
    @Test
    public void testIsValidWithInvalidFeld() {
        NumFeld schrott = new NumFeld("schrott", "xxxx");
        satz.add(schrott);
        assertFalse(satz + " has invalid fields!", satz.isValid());
    }

    /**
     * Bei einem unbekannten Datensatz sollte die Validierung fehlschlagen.
     */
    @Test
    public void testValidate() {
        Satz a = new Datensatz("xxxx", 1);
        assertFalse("Diese Satzart gibt es nicht: " + a, a.isValid());
        List<ConstraintViolation> violations = a.validate();
        for (ConstraintViolation violation : violations) {
            log.info("ConstraintViolation: " + violation);
        }
        assertEquals(2, violations.size());
    }

    /**
     * Zwei gleiche Datensaetze muessen natuerlich auch den gleichen Hashcode
     * besitzen.
     */
    @Test
    public void testIsEquals() {
        Satz a = new Datensatz(123);
        Satz b = new Datensatz(123);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        b.add(new Feld("c", 5, 'c'));
        assertFalse(a + " differs from " + b, a.equals(b));
    }

    /**
     * Hier testen wir das Enfernen von Teildatensaetze.
     *
     * @since 0.4
     */
    @Test
    public void testRemoveTeildatensatz() {
        Satz s = new Vorsatz();
        int n = s.getTeildatensaetze().size();
        s.removeTeildatensatz(n);
        assertEquals(n - 1, s.getTeildatensaetze().size());
        s.removeTeildatensatz(1);
        assertEquals(n - 2, s.getTeildatensaetze().size());
    }

    /**
     * Hier testen wir das Entfernen aller Teildatensaetze.
     *
     * @since 0.4
     */
    @Test
    public void testRemoveAllTeildatensaetze() {
        Satz s = new Vorsatz();
        s.removeAllTeildatensaetze(2);
        assertEquals(1, s.getTeildatensaetze().size());
    }

    /**
     * Test method for {@link Satz#getAsList(Enum[])}.
     */
    @Test
    public void testGetAsListSimple() {
        List<MetaFeldInfo> feldInfos = Satz.getMetaFeldInfos(Feld200.values());
        assertFalse("empty list", feldInfos.isEmpty());
        log.info("Feld200 has " + feldInfos.size() + " FeldInfos.");
        assertTrue("Feld200 should have more than " + Feld200.values().length + " entries",
                feldInfos.size() >= Feld200.values().length);
    }

    /**
     * Test method for {@link Satz#getAsList(Enum[])}.
     */
    @Test
    public void testGetAsListComposite() {
        List<MetaFeldInfo> feldInfos = Satz.getMetaFeldInfos(Feld220.values());
        assertFalse("empty list", feldInfos.isEmpty());
        log.info(Feld220.class.getName() + " has " + feldInfos.size() + " FeldInfos.");
        assertTrue("elements are missing", feldInfos.size() > Feld220.values().length);
    }

    /**
     * {@link Feld1bis7} ist ein Beispiel, wo kein Teildatensatz gesetzt ist.
     * Dieser wird z.B. beim {@link Feld200} ueber die {@link FelderInfo}-
     * Annotation gesetzt. Ob dieses Wert tatsaechlich gesetzt wird, wird
     * ueber diesen Test geprueft.
     */
    @Test
    public void testGetAsListTeildatensatz() {
        List<MetaFeldInfo> metaFeldInfos = Satz.getMetaFeldInfos(Feld200.values());
        int found = 0;
        for (MetaFeldInfo metaFeldInfo : metaFeldInfos) {
            if (metaFeldInfo.getName().equals("SATZART")) {
                found++;
                checkSatzart(metaFeldInfo, found);
            }
        }
    }

    private static void checkSatzart(final MetaFeldInfo satzart, final int found) {
        log.info(found + ". MetaFeldInfo: " + satzart );
        assertEquals(1, satzart.getNr());
        assertEquals(found, satzart.getTeildatensatzNr());
    }

    /**
     * Die Satzart ist im ersten Feld (Byte 1 - 4) enthalten und ist in jedem
     * Satz vorhanden (auch Vorsatz und Nachsatz).
     */
    @Test
    public void testSatzartInhalt() {
        Feld satzart = satz.getFeld(Feld1bis7.SATZART);
        assertEquals("0123", satzart.getInhalt());
    }

}
