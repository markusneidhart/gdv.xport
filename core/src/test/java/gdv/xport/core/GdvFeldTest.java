/*
 * Copyright (c) 2009 - 2019 by Oli B.
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
 * (c)reated 05.10.2009 by Oli B. (ob@aosd.de)
 */

package gdv.xport.core;

import org.junit.Test;
import patterntesting.runtime.junit.ObjectTester;

import static org.junit.Assert.*;

/**
 * JUnit-Test fuer die GdvFeld-Klasse.
 *
 * @author oliver
 * @since 05.10.2009
 */
public final class GdvFeldTest {

    private final GdvFeld feld = new GdvFeld(new GdvBezeichner("hello"), ByteAdresse.of(42), "world");

    @Test
    public void testCopyCtor() {
        GdvFeld copy = new GdvFeld(feld);
        ObjectTester.assertEquals(feld, copy);
    }

    /**
     * Test method for {@link GdvFeld#resetInhalt()}.
     */
    @Test
    public void testResetInhalt() {
        feld.resetInhalt();
        assertEquals("     ", feld.getInhalt());
    }

    /**
     * Test set inhalt.
     */
    @Test
    public void testSetInhalt() {
        feld.setInhalt("abc");
        assertEquals("abc  ", feld.getInhalt());
    }

    /**
     * Test overlaps with.
     */
    @Test
    public void testOverlapsWith() {
        GdvFeld a = new GdvFeld(new GdvBezeichner("a"), ByteAdresse.of(1), 2);    // Byte 1-2
        GdvFeld b = new GdvFeld(new GdvBezeichner("b"), ByteAdresse.of(3), 2);    // Byte 3-4
        GdvFeld c = new GdvFeld(new GdvBezeichner("c"), ByteAdresse.of(2), 2);    // Byte 2-3
        assertFalse(a + " overlaps with " + b, a.overlapsWith(b));
        assertFalse(b + " overlaps with " + a, b.overlapsWith(a));
        assertTrue(b + " doesn't overlap with " + c, b.overlapsWith(c));
        assertTrue(c + " doesn't overlap with " + a, c.overlapsWith(a));
        assertTrue(c + " doesn't overlap with " + b, c.overlapsWith(b));
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        GdvFeld a = new GdvFeld(new GdvBezeichner("x"), ByteAdresse.of(2), 1);
        GdvFeld b = new GdvFeld(new GdvBezeichner("x"), ByteAdresse.of(2), 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        b.setInhalt('b');
        assertNotEquals(a + " differs from " + b, a, b);
    }

    /**
     * Bezeichnung kann aus mehreren Woertern in Gross- und Kleinschreibung
     * bestehen, der Bezeichner enthaelt neben der Bezeichnung auch den
     * "technischen Namen", der aus einem Wort besteht.
     */
    @Test
    public void testGetBezeichner() {
        GdvBezeichner bezeichner = new GdvBezeichner("VU-Nummer");
        GdvFeld x = new GdvFeld(bezeichner, ByteAdresse.of(1), "Test");
        assertEquals(bezeichner.getName(), x.getBezeichnung());
        assertEquals(bezeichner, x.getBezeichner());
    }

    /**
     * Test-Methode fuer {@link GdvFeld#getInhalt()} in Zusammenhang mit dem
     * Encoding.
     */
    @Test
    public void testEncoding() {
        GdvFeld feld = new GdvFeld(new GdvBezeichner("Gruesse"), ByteAdresse.of(1), "Gr\u00fc\u00dfe");
        assertEquals("Gr\u00fc\u00dfe", feld.getInhalt());
    }

}
