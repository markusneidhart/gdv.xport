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

package gdv.xport.util;

import gdv.xport.core.GdvSatzTyp;
import org.apache.commons.lang3.StringUtils;

/**
 * Der SatzTyp fuehrt Satzart, Sparte, Wagnisart und laufende Nummer eines
 * Teildatensatz zusammen. Sie wird von der {@link SatzFactory} fuer die
 * Registrierung verwendet.
 * <p>
 * Vorher hiess diese Klasse "SatzNummer", wurde aber mit 1.1 in SatzType
 * umbenannt, da "Satznummer" als Klassenname etwas irritierend ist, da es ein
 * Feld "Satznummer" innerhalb eines Satzes bereits gibt.
 * </p>
 *
 * @author oliver
 * @since 0.9 (26.01.2013)
 */
public class SatzTyp extends GdvSatzTyp {

    /**
     * Damit laesst sich ein SatzTyp anhand der entsprechenden String-
     * Repraesentation erzeugen.
     *
     * @param nr z.B. "0210.050"
     * @return der entsprechende SatzTyp
     * @since 5.0
     */
    public static SatzTyp of(String nr)  {
    	return new SatzTyp(nr);
	}

	/**
	 * Anhand der übergebenen Zahlen wird der entsprechende SatzTyp aufgebaut.
	 *
	 * @param args the args
	 * @return the satz typ
	 * @since 5.0
	 */
	public static SatzTyp of(int... args) {
    	switch(args.length) {
			case 1:
				return of(String.format("%04d", args[0]));
			case 2:
				return of(String.format("%04d.%03d", args[0], args[1]));
			case 3:
				return of(String.format("%04d.%03d.%d", args[0], args[1], args[2]));
			case 4:
				return of(String.format("%04d.%03d.%d.%d", args[0], args[1], args[2], args[3]));
			default:
				throw new IllegalArgumentException("1 - 4 arguments expected, not " + args.length);
		}
	}

	/**
	 * Damit laesst sich ein SatzTyp anhand der entsprechenden String-
	 * Repraesentation erzeugen.
	 *
	 * @param nr z.B. "0210.050"
	 */
	public SatzTyp(String nr) {
		super(nr);
	}

	/**
	 * Damit laesst sich ein SatzTyp anhand der Einzelteile zusammensetzen.
	 *
	 * @param args z.B. 0210, 050
	 */
	public SatzTyp(int... args) {
		super(args);
	}

	/**
     * Legt eine neue SatzNummer an.
	 * TODO: Wird ab v6 nicht mehr zur Verfuegung stehen.
     *
     * @param satzart die Satzart (vierstellig)
     * @param sparte die Sparte (dreistellig)
     * @param wagnisart die Wagnisart (ein- bis zweisstellig)
     * @param krankenFolgeNr Folge-Nr. aus Sparte 20, Satzart 220 (Wert 1-3)
     * @param lfdNummer die laufende Nummer (Teildatensatz-Nummer)
     * @deprecated wurde ersetzt durch {@link #of(String)}
     */
    @Deprecated
    public SatzTyp(final int satzart, final int sparte, final int wagnisart, final int krankenFolgeNr, final int lfdNummer) {
      this(satzart, sparte, wagnisart, krankenFolgeNr, lfdNummer, -1);
    }

	/**
	 * Legt eine neue SatzNummer an.
	 *
	 * @param satzart die Satzart (vierstellig)
	 * @param sparte die Sparte (dreistellig)
	 * @param wagnisart die Wagnisart (ein- bis zweisstellig)
	 * @param krankenFolgeNr Folge-Nr. aus Sparte 20, Satzart 220 (Wert 1-3)
	 * @param lfdNummer die laufende Nummer (Teildatensatz-Nummer)
	 * @param bausparenArt die Art bei Sparte 580, Satzart 220 (Wert 1 - 2)
	 * @since 4.X
	 */
	private SatzTyp(final int satzart, final int sparte, final int wagnisart, final int krankenFolgeNr, final int lfdNummer, final int bausparenArt) {
		assert (satzart >= 0) && (satzart <= 9999) : "Satzart " + satzart
		        + " muss zwischen 0 und 9999 liegen";
		assert (sparte == -1) || ((0 <= sparte) && (sparte <= 999)) : "Sparte " + sparte
		        + " muss zwischen 0 und 999 liegen";
		assert (wagnisart == -1) || ((0 <= wagnisart) && (wagnisart <= 9)) || (wagnisart == 13) || (wagnisart == 48) :
				"Wagnisart " + wagnisart + " muss zwischen 0 und 9 liegen";
		assert (krankenFolgeNr == -1) || ((1 <= krankenFolgeNr) && (krankenFolgeNr <= 3)) : "Kranken Folge-Nr. "
		        + krankenFolgeNr + " muss zwischen 1 und 3 liegen";
		assert (lfdNummer == -1) || ((0 <= lfdNummer) && (lfdNummer <= 9)) : "teildatensatzNummer "
		        + lfdNummer + " muss zwischen 0 und 9 liegen";
		assert (bausparenArt == -1) || ((0 <= bausparenArt) && (bausparenArt <= 9)) : "bausparenArt "
		        + bausparenArt + " muss zwischen 0 und 9 liegen";
	}

	/**
	 * Liefert die Sparte als String.
	 *
	 * @return z.B. "030"
	 * @since 5.0
	 */
	public String getSparteAsString() {
		return Integer.toString(this.getSparte());
	}

	/**
	 * Liefert die Wagnisart als String.
	 *
	 * @return z.B. "9"
	 * @since 5.0
	 */
	public String getWagnisartAsString() {
		return Integer.toString(this.getWagnisart());
	}

	public boolean hasParent() {
		return StringUtils.countMatches(this.toString(), '.') > 0;
	}

	public SatzTyp getParent() {
		String parent = StringUtils.substringBeforeLast(this.toString(), ".");
		return SatzTyp.of(parent);
	}

}
