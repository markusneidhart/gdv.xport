package gdv.xport.core;

import org.junit.Test;
import patterntesting.runtime.junit.ObjectTester;

import static org.junit.Assert.assertEquals;

/**
 * Unit-Tests fuer gdv.xport.core.ByteAdresse.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public final class ByteAdresseTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseNull() {
        ByteAdresse.of(0);
    }

    @Test
    public void testAdresseEins() {
        assertEquals(1, ByteAdresse.of(1).intValue());
    }

    @Test
    public void testAdresse256() {
        assertEquals(256, ByteAdresse.of(256).intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseZuGross() {
       ByteAdresse.of(257);
    }

    @Test
    public void testToString() {
        assertEquals("222", ByteAdresse.of(222).toString());
    }

    @Test
    public void testEquals() {
        ObjectTester.assertEquals(ByteAdresse.of(42), ByteAdresse.of(42));
    }

}
