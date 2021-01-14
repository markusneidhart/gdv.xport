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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 13.01.2021 by oboehm
 */

package gdv.xport.util;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;

import static org.junit.Assert.*;

/**
 * Unit-Tests fuer {@link XmlSatzFactory}.
 *
 * @author oboehm
 * @since 5.0 (13.01.2021)
 */
public final class XmlSatzFactoryTest {

    @Test
    public void testGetInstance2015() {
        XmlSatzFactory factory = XmlSatzFactory.getInstance("VUVM2015.xml");
        assertNotNull(factory);
    }

    @Test
    public void testGetSameInstance() {
        XmlSatzFactory f1 = XmlSatzFactory.getInstance();
        XmlSatzFactory f2 = XmlSatzFactory.getInstance();
        assertSame(f1, f2);
    }

    @Test
    public void testGetDifferentInstances() {
        XmlSatzFactory f2015 = XmlSatzFactory.getInstance("VUVM2015.xml");
        XmlSatzFactory f2018 = XmlSatzFactory.getInstance("VUVM2018.xml");
        assertNotEquals(f2015, f2018);
    }

}
