/*
 * Copyright (c) 2011 by agentes
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
 * (c)reated 27.03.2011 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.util;

import static org.junit.Assert.*;
import gdv.xport.Datenpaket;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;
import org.junit.Test;

/**
 * JUnit-Test fuer JavaFormatter.
 * 
 * @author oliver (oliver.boehm@agentes.de)
 * @since 27.03.2011
 */
public class JavaFormatterTest extends AbstractFormatterTest {
    
    private static final Log log = LogFactory.getLog(JavaFormatterTest.class);
    private Datenpaket datenpaket = new Datenpaket();

    /**
     * Test method for {@link JavaFormatter#write(gdv.xport.Datenpaket)}.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testWriteDatenpaket() throws IOException {
        StringWriter swriter = new StringWriter();
        JavaFormatter formatter = new JavaFormatter(swriter);
        formatter.write(datenpaket);
        swriter.close();
        log.debug(swriter);
        assertTrue("empty result!", StringUtils.isNotEmpty(swriter.toString()));
    }
    
    /**
     * Test to string.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    //@Test
    public void testToString() throws IOException {
        String vorsatz = getResource("Feld0001.txt");
        String nachsatz = getResource("Feld9999.txt");
        String expected = vorsatz + nachsatz;
        assertEquals(expected, JavaFormatter.toString(datenpaket));
    }

    private static String getResource(final String name) throws IOException {
        InputStream istream = JavaFormatterTest.class.getResourceAsStream(name);
        try {
            return IOUtils.toString(istream);
        } finally {
            istream.close();
        }
    }

}

