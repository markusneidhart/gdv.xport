/*
 * Copyright (c) 2011, 2012 by Oli B.
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
 * (c)reated 10.05.2011 by Oli B. (ob@aosd.de)
 */

package gdv.xport.feld;

import gdv.xport.config.Config;
import net.sf.oval.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * JUnit-Test fuer AlphaNum-Klasse.
 *
 * @author oliver (ob@aosd.de)
 * @since 0.6 (10.05.2011)
 */
public final class AlphaNumFeldTest extends AbstractFeldTest {

    private static final Logger LOG = LogManager.getLogger(AlphaNumFeldTest.class);

    /* (non-Javadoc)
     * @see gdv.xport.feld.AbstractFeldTest#getTestFeld()
     */
    @Override
    protected Feld getTestFeld() {
        return new AlphaNumFeld(Bezeichner.NAME1, 30, 44);
    }

    /**
     * Test-Methode fuer
     * {@link AlphaNumFeld#AlphaNumFeld(Bezeichner, int, int, Align)}.
     */
    @Test
    public void testAlphaNumFeldBezeichner() {
        AlphaNumFeld a = new AlphaNumFeld(Bezeichner.VS_NR, 2, 4, Align.LEFT);
        assertEquals(Bezeichner.VS_NR, a.getBezeichner());
    }

    /**
     * Fehlerhafte IBANs sollten bei der Validierung erkannt werden.
     */
    @Test
    public void testValidateIBAN() {
        AlphaNumFeld iban = new AlphaNumFeld(Bezeichner.IBAN1, 34, 209);
        assertTrue("empty IBAN should be valid: " + iban, iban.isValid());
        iban.setInhalt("DE99300606010006605605");
        assertNotValid(iban);
    }

    /**
     * "GENODEF1J" ist keine gueltige BIC (zu kurz).
     */
    @Test
    public void testValidateBIC() {
        AlphaNumFeld bic = new AlphaNumFeld(Bezeichner.BIC2, 11, 198);
        bic.setInhalt("GENODEF1J");
        assertNotValid(bic);
    }

    private void assertNotValid(AlphaNumFeld feld) {
        List<ConstraintViolation> violations = feld.validate();
        assertEquals(1, violations.size());
        assertFalse("not a valid Feld: " + feld, feld.isValid());
        LOG.info("Violoations = {}", violations);
    }

    @Test
    public void testValidateAlignStrict() {
        AlphaNumFeld name = new AlphaNumFeld(Bezeichner.NAME1, 8, 1, Align.LEFT)
                .mitConfig(Config.EMPTY.withProperty("gdv.feld.validate", "strict"));
        name.setInhalt("Hugo");
        assertEquals("Hugo    ", name.getInhalt());
        name.setInhalt("  Boss  ");
        assertEquals("Boss    ", name.getInhalt());
    }

    @Test
    public void testAlignment() {
        AlphaNumFeld feld = new AlphaNumFeld(Bezeichner.VP_PERSONENNUMMER_VERSICHERER, 16, 43, Align.RIGHT);
        checkRightAlignment(feld, "      12345678  ", "12345678  ");
        checkRightAlignment(feld.mitConfig(Config.STRICT), "        12345678", "12345678  ");
    }

    private void checkRightAlignment(AlphaNumFeld feld, String expected, String content) {
        feld.setInhalt(content);
        assertEquals(expected, feld.getInhalt());
    }

}
