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
 * (c)reated 02.03.21 by oliver (ob@oasd.de)
 */
package gdv.xport.core.internal;

import gdv.xport.core.GdvFeld;
import gdv.xport.core.GdvSatz;
import gdv.xport.core.GdvSatzTyp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class SatzParser.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public class SatzParser {

    private static final Logger LOG = LogManager.getLogger();
    private final List<GdvSatz> saetze = new ArrayList<>();
    private final Map<GdvSatzTyp, GdvSatz> satzarten = new HashMap<>();
    private final Map<String, GdvFeld> felder = new HashMap<>();

    public SatzParser(XMLEventReader parser) throws XMLStreamException {
        this(new XmlParser(parser));
    }

    private SatzParser(XmlParser parser) throws XMLStreamException {
        this(parser, parser.getNextStartElement());
    }

    private SatzParser(XmlParser parser, StartElement startElement) throws XMLStreamException {
        parse(startElement, parser);
    }

    private void parse(final StartElement element, final XmlParser parser) throws XMLStreamException {
        XMLEventReader reader = parser.getReader();
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                parseElement(event.asStartElement(), reader);
            } else if (XmlParser.isEndElement(event, element.getName())) {
                LOG.info("{} Satzarten mit {}...{} wurden eingelesen.", this.saetze.size(), element, event);
                return;
            }
            LOG.trace("Event {} is ignored.", event);
        }
        throw new XMLStreamException("end of " + element + " not found");
    }

    private void parseElement(final StartElement element, final XMLEventReader reader) throws XMLStreamException {
        LOG.trace("Parsing element {}.", element);
        QName name = element.getName();
        if ("satzarten".equals(name.getLocalPart())) {
            parseSatzarten(element, reader);
        } else if ("felder".equals(name.getLocalPart())) {
            this.felder.putAll(parseFelder(element, reader));
            this.setFelder(felder);
        } else {
            XmlParser.ignore(name, reader);
        }
    }

    private void parseSatzarten(final StartElement element, final XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (XmlParser.isStartElement(event, "satzart")) {
//                GdvSatz satz = new GdvSatz(reader, event.asStartElement());
//                this.saetze.add(satz);
//                LOG.debug("Satz {} added .", satz);
            } else if (XmlParser.isEndElement(event, element.getName())) {
                LOG.debug("{} satzarten successful parsed.", this.satzarten.size());
                return;
            }
        }
        throw new XMLStreamException("end of " + element + " not found");
    }

    /**
     * Liest die &lt;felder&gt;-Elemente ein und liefert sie als Map zurueck.
     *
     * @param reader the reader
     * @return Map mit den Feldern
     * @throws XMLStreamException the XML stream exception
     */
    public static Map<String, GdvFeld> parseFelder(final XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (XmlParser.isStartElement(event, "felder")) {
                return parseFelder(event.asStartElement(), reader);
            }
            LOG.trace("Event {} is ignored.", event);
        }
        throw new XMLStreamException("<felder>...</felder> not found");
    }

    private static Map<String, GdvFeld> parseFelder(final StartElement element, final XMLEventReader reader) throws XMLStreamException {
        LOG.trace("Element {} will be parsed.", element);
        Map<String, GdvFeld> felder = new HashMap<>();
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
//                GdvFeld feld = new GdvFeld(reader, event.asStartElement());
//                felder.put(feld.getId(), feld);
            } else if (XmlParser.isEndElement(event, element.getName())) {
                LOG.debug("{} felder between {}...{} successful parsed.", felder.size(), element, event);
                return felder;
            }
        }
        throw new XMLStreamException("end of " + element + " not found");
    }

    /**
     * Liefert die Felder mit den Referenzen.
     *
     * @return the felder
     * @since 1.1
     */
    public Map<String, GdvFeld> getFelder() {
        return this.felder;
    }

    private void setFelder(Map<String, GdvFeld> felder) {
        LOG.debug("Missing felder for {} saetze will be set.", this.satzarten.size());
        for (GdvSatz satz : this.saetze) {
//            satz.setFelder(felder);
        }
    }

}
