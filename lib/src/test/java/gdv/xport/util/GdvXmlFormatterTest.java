/*
 * Copyright (c) 2021 by Oliver Boehm
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
 * (c)reated 27.03.21 by oliver (ob@oasd.de)
 */
package gdv.xport.util;

import gdv.xport.Datenpaket;
import gdv.xport.feld.ByteAdresse;
import gdv.xport.feld.Feld;
import gdv.xport.feld.Zeichen;
import gdv.xport.satz.Satz;
import gdv.xport.satz.Teildatensatz;
import gdv.xport.satz.xml.SatzXml;
import gdv.xport.satz.xml.XmlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit-Tests fuer gdv.xport.util.GdvXmlFormatter.
 *
 * @author oliver
 */
public final class GdvXmlFormatterTest extends AbstractFormatterTest {

    private static final Logger LOG = LogManager.getLogger();
    private static final File XML_DIR = new File("target", "xml");

    @Override
    protected AbstractFormatter createFormatter() {
        return new GdvXmlFormatter();
    }

    @BeforeClass
    public static void setUpXmlDir() {
        if (XML_DIR.mkdirs()) {
            LOG.info("Verzeichnis '{}' wurde angelegt.", XML_DIR);
        }
    }

    @Test
    public void testWriteSatz() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            GdvXmlFormatter formatter = new GdvXmlFormatter(writer);
            Satz satz100 = XmlService.getInstance().getSatzart(SatzTyp.of(100));
            formatter.write(satz100);
            assertThat(writer.toString().trim(), containsString("<satzart"));
        }
    }

    @Test
    public void testCreateSatzFromOutput() throws IOException, XMLStreamException {
        Satz adressteil = XmlService.getInstance().getSatzart(SatzTyp.of(102));
        File output = new File(XML_DIR, "satz102.xml");
        SatzXml generated = formatSatz(adressteil, output);
        assertEquals(adressteil.getNumberOfTeildatensaetze(), generated.getNumberOfTeildatensaetze());
        assertEquals(adressteil.getFelder().size(), generated.getFelder().size());
        assertEquals(102, generated.getSatzart());
        assertEquals(adressteil.getSatzversion(), generated.getSatzversion());
        assertEquals(adressteil, generated);
    }

    @Test
    public void testBausparen() throws IOException, XMLStreamException {
        SatzTyp bausparen = SatzTyp.of("0220.580.01");
        Satz satz = XmlService.getInstance().getSatzart(bausparen);
        File target = new File(XML_DIR, "satz0220.580.01.xml");
        SatzXml generated = formatSatz(satz, target);
        assertEquals(220, generated.getSatzart());
        assertEquals(580, generated.getSparte());
        assertEquals(bausparen, generated.getSatzTyp());
    }

    /**
     * Hier testen wir mit Satz fuer die Kfz-Haftpflicht (0221.051) gegen die
     * Enum-Beschreibung aus Feld221. Im Gegensatz zur GDV-XML-Beschreibung ist
     * hier die Deckungssumme in 3 Teile aufgeteilt, was die Handhabung damit
     * vereinfacht.
     *
     * @throws IOException        the io exception
     * @throws XMLStreamException the xml stream exception
     */
    @Test
    public void testSatzart0221051() throws IOException, XMLStreamException {
        checkSatzart(SatzTyp.of(221, 51), gdv.xport.satz.feld.sparte51.Feld221.class);
    }

    @Test
    public void testSatzartLebenWagnis2() throws IOException, XMLStreamException {
        checkSatzart(SatzTyp.of("0220.010.2.1"), gdv.xport.satz.feld.sparte10.wagnisart2.Feld220Wagnis2.class);
    }

    private void checkSatzart(SatzTyp satzTyp, Class<? extends Enum> enumClass) throws IOException, XMLStreamException {
        File target = new File(XML_DIR, String.format("satz%s.xml", satzTyp.toString()));
        try {
            SatzRegistry.getInstance().registerEnum(enumClass, satzTyp);
            Satz satz = SatzFactory.getDatensatz(satzTyp);
            formatTo(target, satz);
            Satz xmlSatz = SatzXml.of(target);
            checkSatz(satz, xmlSatz);
        } finally {
            SatzRegistry.getInstance().unregister(satzTyp);
        }
    }

    @NotNull
    private static SatzXml formatSatz(Satz adressteil, File output) throws IOException, XMLStreamException {
        formatTo(output, adressteil);
        SatzXml generated = SatzXml.of(output);
        assertNotNull(generated);
        return generated;
    }

    private static void formatTo(File output, Satz satz) throws IOException {
        try (FileOutputStream ostream = new FileOutputStream(output);
             GdvXmlFormatter formatter = new GdvXmlFormatter(ostream)) {
            formatter.write(satz);
        }
        LOG.info("{} wurde nach {} geschrieben.", satz, output);
    }

    @Test
    public void testWriteDatensatz() throws IOException, XMLStreamException {
        Datenpaket datenpaket = new Datenpaket();
        formatDatenpaket(datenpaket);
    }

    @Test
    public void testFormatAllSupportedSaetze() throws IOException, XMLStreamException {
        Datenpaket datenpaket = SatzFactory.getAllSupportedSaetze();
        formatDatenpaket(datenpaket);
    }

    @Test
    public void testFormatAllSupportedSaetze2015() throws IOException, XMLStreamException {
        Datenpaket datenpaket = SatzRegistry.getInstance("VUVM2015.xml").getAllSupportedSaetze();
        formatDatenpaket(datenpaket);
    }

    private void formatDatenpaket(Datenpaket datenpaket) throws IOException, XMLStreamException {
        File target = new File(XML_DIR,
                String.format("datensatz%s.xml", datenpaket.getVorsatz().getVersion(SatzTyp.of("0052")).trim()));
        try (FileOutputStream ostream = new FileOutputStream(target);
             GdvXmlFormatter formatter = new GdvXmlFormatter(ostream)) {
            formatter.write(datenpaket);
        }
        XmlService xmlService = XmlService.getInstance(target.toURI());
        checkSatz(datenpaket.getVorsatz(), xmlService.getSatzart(SatzTyp.of(1)));
        checkSatz(datenpaket.getNachsatz(), xmlService.getSatzart(SatzTyp.of(9999)));
        for (Satz satz : datenpaket.getAllSaetze()) {
            LOG.debug("{} wird geprueft.", satz);
            checkSatz(satz, xmlService.getSatzart(satz.getSatzTyp()));
        }
    }

    private static void checkSatz(Satz reference, Satz satz) {
        for (int i = 1; i <= reference.getNumberOfTeildatensaetze(); i++) {
            checkTeildatensatz(reference.getTeildatensatz(i), satz.getTeildatensatz(i));
        }
        assertEquals(reference, satz);
    }

    private static void checkTeildatensatz(Teildatensatz reference, Teildatensatz teildatensatz) {
        for (Feld refFeld : reference.getFelder()) {
            Feld feld = teildatensatz.getFeld(ByteAdresse.of(refFeld.getByteAdresse()));
            assertEquals(refFeld, feld);
            if (feld.getClass().equals(Zeichen.class) && !refFeld.getClass().equals(Zeichen.class)) {
                LOG.info("Datentyp von {} weicht von {} ab ({}).", refFeld, feld, reference);
            } else {
                assertEquals(refFeld.getClass(), feld.getClass());
            }
        }
        assertEquals(reference, teildatensatz);
    }

    @Test
    public void testMusterdatei() throws IOException, XMLStreamException {
        File xmlFile = exportMusterdatei(new GdvXmlFormatter(), "musterdatei_GdvXmlFormatter.xml");
        XmlService xmlService = XmlService.getInstance(xmlFile.toURI());
        assertNotNull(xmlService);
    }

}