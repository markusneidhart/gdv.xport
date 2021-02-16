/*
 * Copyright (c) 2013 by Oli B.
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
 * (c)reated 26.01.2013 by Oli B. (ob@aosd.de)
 */

package gdv.xport.core;

import org.junit.Test;
import patterntesting.runtime.junit.ObjectTester;

import static org.junit.Assert.*;
import static patterntesting.runtime.junit.ObjectTester.assertNotEquals;

/**
 * JUnit-Tests fuer {@link GdvSatzTyp}.
 *
 * @author oliver
 * @since 0.9 (26.01.2013)
 */
public class GdvSatzTypTest {

    /**
     * Test method for {@link GdvSatzTyp#equals(Object)}.
     */
    @Test
    public void testEqualsObject() {
        GdvSatzTyp one = new GdvSatzTyp(1);
        GdvSatzTyp anotherOne = new GdvSatzTyp(1);
        ObjectTester.assertEquals(one, anotherOne);
    }

    /**
     * Test method for {@link GdvSatzTyp#equals(Object)}.
     */
    @Test
    public void testNotEquals() {
        GdvSatzTyp one = new GdvSatzTyp("1.1");
        GdvSatzTyp other = new GdvSatzTyp("1.1.1");
        assertNotEquals(other, one.equals(other));
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        assertEquals("0001", new GdvSatzTyp(1).toString());
        assertEquals("0210.050", new GdvSatzTyp(210, 50).toString());
        assertEquals("0220.010.0", new GdvSatzTyp(220, 10, 0).toString());
        assertEquals("0220.010.6.1", new GdvSatzTyp(220, 10, 6, 1).toString());
    }

    @Test
    public void testCtors() {
        assertEquals(new GdvSatzTyp("0001"), new GdvSatzTyp(1));
        assertEquals(new GdvSatzTyp("0210.050"), new GdvSatzTyp(210, 50));
        assertEquals(new GdvSatzTyp("0220.010.0"), new GdvSatzTyp(220, 10, 0));
        assertEquals(new GdvSatzTyp("0220.010.6.1"), new GdvSatzTyp(220, 10, 6, 1));
    }

    @Test
    public void testGetSatzart() {
        assertEquals(210, new GdvSatzTyp("0210.050").getSatzart());
    }

    @Test
    public void testGetSparte() {
        assertEquals(50, new GdvSatzTyp("0210.050").getSparte());
    }

    @Test
    public void testOfWagnisart1u3() {
        assertEquals(new GdvSatzTyp("0220.010.13.7"), new GdvSatzTyp(220, 10, 1, 7));
        assertEquals(new GdvSatzTyp("0220.010.13.7"), new GdvSatzTyp(220, 10, 3, 7));
    }

    /**
     * GdvSatzTyp "0220.010.13" gibt es eigentlich nicht. Oft ist damit aber
     * eigentlich GdvSatzTyp "0220.010.13.1" gemeint.
     */
    @Test
    public void tesetOfWagnisartLeben() {
        assertEquals(new GdvSatzTyp("0220.010.13.1"), new GdvSatzTyp("0220.010.13"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOfInvalid() {
        new GdvSatzTyp("0001.a");
    }

    @Test
    public void testGetArt() {
        assertEquals("1", new GdvSatzTyp("0220.020.1").getArtAsString());
        assertEquals("2", new GdvSatzTyp("0220.020.2").getArtAsString());
        assertEquals("3", new GdvSatzTyp("0220.020.3").getArtAsString());
        assertEquals("01", new GdvSatzTyp("0220.580.01").getArtAsString());
        assertEquals("2", new GdvSatzTyp("0220.580.2").getArtAsString());
    }

    @Test
    public void testGetArt4Wagnisart() {
        assertEquals("0", new GdvSatzTyp("0220.010.0").getArtAsString());
        assertEquals("13", new GdvSatzTyp("0220.010.13").getArtAsString());
        assertEquals("13", new GdvSatzTyp("0221.010.13").getArtAsString());
        assertEquals("48", new GdvSatzTyp("0220.010.48").getArtAsString());
        assertEquals("48", new GdvSatzTyp("0221.010.48").getArtAsString());
        assertEquals("6", new GdvSatzTyp("0220.010.6").getArtAsString());
        assertEquals("6", new GdvSatzTyp("0221.010.6").getArtAsString());
        assertEquals("5", new GdvSatzTyp("0220.010.5").getArtAsString());
        assertEquals("5", new GdvSatzTyp("0221.010.5").getArtAsString());
        assertEquals("2", new GdvSatzTyp("0220.010.2").getArtAsString());
        assertEquals("2", new GdvSatzTyp("0221.010.2").getArtAsString());
        assertEquals("7", new GdvSatzTyp("0220.010.7").getArtAsString());
        assertEquals("7", new GdvSatzTyp("0221.010.7").getArtAsString());
        assertEquals("9", new GdvSatzTyp("0220.010.9").getArtAsString());
    }

    @Test
    public void testGetWagnisartAsString() {
        assertEquals("0", new GdvSatzTyp("0220.010.0").getWagnisartAsString());
        assertEquals("13", new GdvSatzTyp("0220.010.13").getWagnisartAsString());
        assertEquals("13", new GdvSatzTyp("0221.010.13").getWagnisartAsString());
        assertEquals("48", new GdvSatzTyp("0220.010.48").getWagnisartAsString());
        assertEquals("48", new GdvSatzTyp("0221.010.48").getWagnisartAsString());
        assertEquals("6", new GdvSatzTyp("0220.010.6").getWagnisartAsString());
        assertEquals("6", new GdvSatzTyp("0221.010.6").getWagnisartAsString());
        assertEquals("5", new GdvSatzTyp("0220.010.5").getWagnisartAsString());
        assertEquals("5", new GdvSatzTyp("0221.010.5").getWagnisartAsString());
        assertEquals("2", new GdvSatzTyp("0220.010.2").getWagnisartAsString());
        assertEquals("2", new GdvSatzTyp("0221.010.2").getWagnisartAsString());
        assertEquals("7", new GdvSatzTyp("0220.010.7").getWagnisartAsString());
        assertEquals("7", new GdvSatzTyp("0221.010.7").getWagnisartAsString());
        assertEquals("9", new GdvSatzTyp("0220.010.9").getWagnisartAsString());
    }

    @Test
    public void testOfWagnisart() {
        assertEquals(new GdvSatzTyp("0220.010.0"), new GdvSatzTyp(220, 10, 0));
        assertEquals(new GdvSatzTyp("0220.010.13"), new GdvSatzTyp(220, 10, 13));
        assertEquals(new GdvSatzTyp("0221.010.13"), new GdvSatzTyp(221, 10, 13));
        assertEquals(new GdvSatzTyp("0220.010.48"), new GdvSatzTyp(220, 10, 48));
        assertEquals(new GdvSatzTyp("0221.010.48"), new GdvSatzTyp(221, 10, 48));
        assertEquals(new GdvSatzTyp("0220.010.6"), new GdvSatzTyp(220, 10, 6));
        assertEquals(new GdvSatzTyp("0221.010.6"), new GdvSatzTyp(221, 10, 6));
        assertEquals(new GdvSatzTyp("0220.010.9"), new GdvSatzTyp(220, 10, 9));
    }

    @Test
    public void testGetArtWagnisart13() {
        assertEquals("13", new GdvSatzTyp("220.10.1").getArtAsString());
        assertEquals("13", new GdvSatzTyp("220.10.3").getArtAsString());
        assertEquals("13", new GdvSatzTyp("0220.010.13").getWagnisartAsString());
    }

    @Test
    public void testGetArtWagnisart48() {
        assertEquals("48", new GdvSatzTyp("220.10.4").getArtAsString());
        assertEquals("48", new GdvSatzTyp("220.10.8").getArtAsString());
        assertEquals("48", new GdvSatzTyp("0220.010.48").getWagnisartAsString());
    }

    @Test
    public void testHasArt() {
        assertTrue(new GdvSatzTyp("0220.020.1").hasArt());
        assertFalse(new GdvSatzTyp("0220.000").hasArt());
    }

    @Test
    public void testToStringBausparen() {
        assertEquals("0220.580.01", new GdvSatzTyp("0220.580.01").toString());
        assertEquals("0220.580.2", new GdvSatzTyp("0220.580.2").toString());
    }

    @Test
    public void testGetBausparenArt() {
        assertEquals("01", new GdvSatzTyp("0220.580.01").getBausparenArtAsString());
        assertEquals("2", new GdvSatzTyp("0220.580.2").getBausparenArtAsString());
        assertEquals("", new GdvSatzTyp("0220.570").getBausparenArtAsString());
    }

    @Test
    public void testOfBausparenArt() {
        assertEquals(new GdvSatzTyp("0220.580.01"), new GdvSatzTyp(220, 580, 1));
        assertEquals(new GdvSatzTyp("0220.580.2"), new GdvSatzTyp(220, 580, 2));
        assertEquals(new GdvSatzTyp("0220.570"), new GdvSatzTyp(220, 570));
    }

    @Test
    public void testGetKrankenFolgeNr() {
        assertEquals(1, new GdvSatzTyp("0220.020.1").getKrankenFolgeNr());
        assertEquals(2, new GdvSatzTyp("0220.020.2").getKrankenFolgeNr());
        assertEquals(3, new GdvSatzTyp("0220.020.3").getKrankenFolgeNr());
    }

    @Test
    public void testHasKrankenFolgeNr() {
        assertTrue(new GdvSatzTyp("0220.020.1").hasKrankenFolgeNr());
        assertTrue(new GdvSatzTyp("0220.020.2").hasKrankenFolgeNr());
        assertTrue(new GdvSatzTyp("0220.020.3").hasKrankenFolgeNr());
        assertFalse(new GdvSatzTyp("0220.000").hasKrankenFolgeNr());
        assertFalse(new GdvSatzTyp("0220.580.01").hasKrankenFolgeNr());
        assertFalse(new GdvSatzTyp("0220.580.2").hasKrankenFolgeNr());
    }

    @Test
    public void testToStringKrankenFolgeNr() {
        assertEquals("0220.020.1", new GdvSatzTyp("0220.020.1").toString());
        assertEquals("0220.020.2", new GdvSatzTyp("0220.020.2").toString());
        assertEquals("0220.020.3", new GdvSatzTyp("0220.020.3").toString());
    }

    @Test
    public void testOfKrankenFolgeNr() {
        assertEquals(new GdvSatzTyp(220, 20, 1), new GdvSatzTyp("0220.020.1"));
        assertEquals(new GdvSatzTyp(220, 20, 2), new GdvSatzTyp("0220.020.2"));
        assertEquals(new GdvSatzTyp(220, 20, 3), new GdvSatzTyp("0220.020.3"));
    }

    /**
     * Fuer Satzart 210, 211 und 220 gibt es einen "Allgemeinen Satz", die
     * "000" als Sparte hat. Daher sollte "0210" auf "0210.000" abgebildet
     * werden (entsprechendes gilt fuer Satzart 211 und 220).
     */
    @Test
    public void testAllgemeinerSatz() {
        assertEquals(new GdvSatzTyp("0210.000"), new GdvSatzTyp("0210"));
        assertEquals(new GdvSatzTyp("0211.000"), new GdvSatzTyp("0211"));
        assertEquals(new GdvSatzTyp("0220.000"), new GdvSatzTyp("0220"));
    }

    /**
     * GdvSatzTyp "0220.010" gibt es eigentlich nicht. In diesem Fall sollte die
     * Wagnisart 0 genommen werden.
     */
    @Test
    public void testLebenWagnisart0() {
        assertEquals(new GdvSatzTyp("0220.010.0"), new GdvSatzTyp("0220.010"));
        assertEquals("0220.010.0", new GdvSatzTyp("0220.010.0").toString());
    }

    @Test
    public void testGdvSatzTyp100() {
        GdvSatzTyp a = new GdvSatzTyp("0100");
        GdvSatzTyp b = new GdvSatzTyp("0100.000");
        assertEquals(a.toString(), b.toString());
        ObjectTester.assertEquals(a, b);
    }

}
