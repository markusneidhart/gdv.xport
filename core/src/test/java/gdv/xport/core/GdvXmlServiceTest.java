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

import org.junit.Test;

import javax.xml.stream.XMLStreamException;

import static org.junit.Assert.*;

/**
 * Unit-Tests fuer gdv.xport.core.GdvXmlService.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public final class GdvXmlServiceTest {

    @Test
    public void testGetInstance() throws XMLStreamException {
        GdvXmlService service = GdvXmlService.getInstance("/gdv/xport/satz/xml/VUVM2018.xml");
        assertNotNull(service);
    }

}