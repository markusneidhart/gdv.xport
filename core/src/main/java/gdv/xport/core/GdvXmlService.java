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
 * (c)reated 01.03.21 by oliver (ob@oasd.de)
 */
package gdv.xport.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class GdvXmlService.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public class GdvXmlService {

    private static final Logger LOG = LogManager.getLogger();
    private static final Map<String, GdvXmlService> INSTANCES = new HashMap<>();

//    protected GdvXmlService() {
//    }
//
//    protected GdvXmlService(SatzParser parser) throws XMLStreamException {
//        this(parser, parser.getNextStartElement());
//    }
//
//    public GdvXmlService(SatzParser parser, StartElement nextStartElement) {
//    }

    /**
     * Liefert den Satz zur gewuenschten Satzart.
     *
     * @param satzNr z.B. SatzTyp.of("0100") fuer Satz100 (Adressteil)
     * @return die entsprechende Satzart
     */
    public GdvSatz getSatzart(final GdvSatzTyp satzNr) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Liefert Service-Instanz anhand der uebergebenen Resource. Da der Aufruf
     * des {@link GdvXmlService}-Konstruktors teuer ist und einige Sekunden braucht
     * (2-3 Sekunden auf einem MacBook-Air von 2011), wird ein interner Cache
     * verwendet, um nicht jedesmal die Resource parsen zu muessen.
     *
     * @param resource Resource-Name (z.B. "/gdv/xport/satz/xml/VUVM2018.xml")
     * @return der frisch instantiierte GdvXmlService
     * @throws XMLStreamException falls die angegebene Resource nicht existiert
     *             oder nicht interpretiert werden kann
     */
    public static GdvXmlService getInstance(final String resource) throws XMLStreamException {
        GdvXmlService service = INSTANCES.get(resource);
        if (service == null) {
            XMLEventReader parser = createXMLEventReader(resource);
            try {
                //service = new GdvXmlService(new SatzParser(parser));
                service = new GdvXmlService();
                INSTANCES.put(resource, service);
            } finally {
                parser.close();
            }
        }
        LOG.info("Instance {} with resource {} was created.", service, resource);
        return service;
    }

    protected static XMLEventReader createXMLEventReader(final String resourceName) throws XMLStreamException {
        InputStream istream = GdvXmlService.class.getResourceAsStream(resourceName);
        if ((istream == null) && !resourceName.startsWith("/gdv")) {
            return createXMLEventReader("/gdv/xport/satz/xml/" + resourceName);
        }
        if (istream == null) {
            throw new XMLStreamException("resource '" + resourceName + "' not found");
        }
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        return factory.createXMLEventReader(istream);
    }

}
