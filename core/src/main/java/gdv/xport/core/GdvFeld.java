/*
 * Copyright (c) 2021 by Oli B.
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
 * (c)reated 09.02.2021 by Oli B. (ob@aosd.de)
 */

package gdv.xport.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Die GdvFeld-Klasse ist repraesentiert ein Feld im Datensatz.
 * Sie wurde mit v5 aus der Feld-Klasse im lib-Modul herausgezogen.
 *
 * @author oliver
 * @since 09.02.2021
 */
public class GdvFeld implements Comparable<GdvFeld> {

    private static final Logger LOG = LogManager.getLogger();
    private final GdvBezeichner bezeichner;
    private final char[] inhalt;
    private final ByteAdresse byteAdresse;

    /**
     * Instantiates a new feld.
     *
     * @param name        Name
     * @param byteAdresse Byte-Adresse von 1 bis 256
     * @param length      Laenge
     */
    public GdvFeld(GdvBezeichner name, ByteAdresse byteAdresse, int length) {
        this.bezeichner = name;
        this.byteAdresse = byteAdresse;
        this.inhalt = new char[length];
        Arrays.fill(this.inhalt, ' ');
    }

    /**
     * Instantiates a new feld.
     *
     * @param name        Name
     * @param byteAdresse Byte-Adresse von 1 bis 256
     * @param s           Inhalt
     */
    public GdvFeld(GdvBezeichner name, ByteAdresse byteAdresse, String s) {
        this(name, byteAdresse, s.length());
        s.getChars(0, s.length(), this.inhalt, 0);
    }

    /**
     * Dies ist der Copy-Constructor, mit dem man ein bestehendes Feld
     * kopieren kann.
     *
     * @param other das originale Feld
     */
    public GdvFeld(final GdvFeld other) {
        this(other.bezeichner, other.byteAdresse, other.getInhalt());
    }

    /**
     * Gets the bezeichnung.
     *
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return this.bezeichner.getName();
    }

    /**
     * Liefert den Bezeichner eines Feldes zurueck.
     *
     * @return den Bezeichner des Feldes
     */
    public GdvBezeichner getBezeichner() {
        return this.bezeichner;
    }

    /**
     * Setzt den Inhalt eines Feldes.
     *
     * @param s der neue Inhalt
     */
    public void setInhalt(final String s) {
        int anzahlBytes = this.getAnzahlBytes();
        if (s.length() > anzahlBytes) {
            throw new IllegalArgumentException("Feld " + this.getBezeichner() + ": Parameter \"" + s
                    + "\" ist laenger als " + anzahlBytes + " Zeichen!");
        }
        this.resetInhalt();
        s.getChars(0, s.length(), inhalt, 0);
    }

    /**
     * Setzt den Inhalt
     *
     * @param n neuer Inhalt
     */
    public void setInhalt(final int n) {
        this.setInhalt(Integer.toString(n));
    }

    /**
     * Setzt den Inhalt
     *
     * @param c neuer Inhalt
     */
    public void setInhalt(char c) {
        this.resetInhalt();
        this.setInhalt(c, 0);
    }

    /**
     * Setzt ein Zeichen.
     *
     * @param c  zu setzendes Zeichen
     * @param i index, beginnend bei 0
     */
    public void setInhalt(char c, int i) {
        this.inhalt[i] = c;
    }

    /**
     * Liefert den Inhalt zurueck.
     *
     * @return the inhalt
     */
    public String getInhalt() {
        return new String(inhalt);
    }

    /**
     * Setzt das Feld und liefert es als Ergebnis zurueck.
     *
     * @param inhalt neuer Inhalt
     * @return das gesetzte Feld
     */
    public GdvFeld withInhalt(String inhalt) {
        this.setInhalt(inhalt);
        return this;
    }

    /**
     * Reset inhalt.
     */
    public void resetInhalt() {
        Arrays.fill(this.inhalt, ' ');
    }

    /**
     * Gets the anzahl bytes.
     *
     * @return the anzahl bytes
     */
    public int getAnzahlBytes() {
        return this.inhalt.length;
    }

    /**
     * Gets the byte adresse.
     *
     * @return Byte-Adresse, beginnend bei 1
     */
    public int getByteAdresse() {
        return this.byteAdresse.intValue();
    }

    /**
     * Gets the end adresse.
     *
     * @return absolute End-Adresse
     */
    public int getEndAdresse() {
        return this.getByteAdresse() + this.getAnzahlBytes() - 1;
    }

    /**
     * Ueberprueft, ob sich zwei Felder mit unterschiedlichen Start-Adressen
     * ueberlagern.
     *
     * @param other das andere Feld
     * @return true, falls sich die Felder ueberlappen
     */
    public final boolean overlapsWith(final GdvFeld other) {
        if (this.byteAdresse.equals(other.byteAdresse)) {
            return false;
        }
        if (this.getByteAdresse() < other.getByteAdresse()) {
            return this.getEndAdresse() >= other.getByteAdresse();
        }
        return other.getEndAdresse() >= this.getByteAdresse();
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return StringUtils.isBlank(this.getInhalt());
    }

    /**
     * Dient zum Ermittel, ob ein Werte schon gesetzt wurde. Dabei werden
     * typische Initialisierungswerte wie "0" als "nicht gesetzt"
     * interpretiert.
     *
     * @return true, falls Feld mit einem Wert belegt ist
     * @since 3.1
     */
    public boolean hasValue() {
        String value = StringUtils.trimToEmpty(this.getInhalt());
        return !value.isEmpty() && !value.equals("0");
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getBezeichner() + "(" + this.byteAdresse + "-"
                + this.getEndAdresse() + "): \"" + this.getInhalt().trim() + "\"";
    }

    /**
     * Zwei Felder sind gleich, wenn sie die gleiche Adresse und den gleichen
     * Inhalt haben.
     *
     * @param obj das andere Feld
     * @return true, wenn beide Felder gleich sind
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof GdvFeld)) {
            return false;
        }
        GdvFeld other = (GdvFeld) obj;
        return this.bezeichner.equals(other.bezeichner) && this.getInhalt().equals(other.getInhalt())
                && (this.byteAdresse.equals(other.byteAdresse));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.getInhalt().hashCode();
    }

    /**
     * Es gilt fuer Feld a und b: a &lt; b, wenn die Start-Adresse von a vor b
     * liegt.
     *
     * @param other das andere Feld
     * @return 0 wenn beide Felder die gleiche Startadresse haben
     */
    @Override
    public int compareTo(final GdvFeld other) {
        return this.getByteAdresse() - other.getByteAdresse();
    }

}
