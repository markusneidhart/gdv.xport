/*
 * Copyright (c) 2013-2021 by Oli B.
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

import org.apache.commons.lang3.StringUtils;

/**
 * Der GdvSatzTyp fuehrt Satzart, Sparte, Wagnisart und laufende Nummer eines
 * Teildatensatz zusammen. Sie wird u.a. von der SatzFactory fuer die
 * Registrierung verwendet und wurde mit v5 aus SatzTyp herausausgezogen.
 * <p>
 * Vorher hiess diese Klasse "SatzNummer", wurde aber mit 1.1 in SatzType
 * umbenannt, da "Satznummer" als Klassenname etwas irritierend ist, da es ein
 * Feld "Satznummer" innerhalb eines Satzes bereits gibt.
 * </p>
 *
 * @author oliver
 */
public class GdvSatzTyp {

	private short[] n;

	/** The wagnisart. */
	private final int wagnisart;

	/** Kranken, Folgenummer */
	private final int krankenFolgeNr;

	/** The lfd nummer. */
	private final int teildatensatzNummer;

	/** Bausparen, bausparenArt */
	private final int bausparenArt;

	/**
	 * Damit laesst sich ein SatzTyp anhand der entsprechenden String-
	 * Repraesentation erzeugen.
	 *
	 * @param nr z.B. "0210.050"
	 * @return der entsprechende SatzTyp
	 */
	public GdvSatzTyp(String nr) {
		this(toIntArray(nr));
	}

	private static int[] toIntArray(String nr) {
		String[] parts = StringUtils.split(nr, '.');
		int[] array = new int[parts.length];
		try {
			for (int i = 0; i < parts.length; i++) {
				array[i] = Integer.parseInt(parts[i]);
			}
			return array;
		} catch (NumberFormatException ex)  {
			throw new IllegalArgumentException("kein Satz-Typ: '" + nr + "'", ex);
		}
	}

	public GdvSatzTyp(int... args) {
		this(toNumbers(args));
		this.n = createArray(args);
	}

	private static short[] createArray(int[] args) {
		short[] array = new short[args.length];
		for (int i = 0; i < args.length; i++) {
			array[i] = (short) args[i];
		}
		int satzart = array[0];
		if (((satzart == 210 ) || (satzart == 211 ) || (satzart == 220 )) && (args.length < 2)) {
			array = new short[args.length + 1];
			array[0] = (short) args[0];
			array[1] = 0;
		}
		return array;
	}

	private GdvSatzTyp(short[] n) {
		this(n[0], n[1], n[2], n[3], n[4], n[5]);
	}

	private static short[] toNumbers (int[] parts)  {
		short[] numbers = { -1, -1, -1, -1, -1, -1 };
		for (int i = 0; i < parts.length; i++) {
			numbers[i] = (short) parts[i];
		}
		if (numbers[1] == 20) {
			// bei Kranken muss krankenFolgeNr belegt werden
			numbers[3] = numbers[2];
			numbers[2] = -1;
			numbers[4] = -1;
			numbers[5] = -1;
		} else if (numbers[1] == 580) {
			// bei Bausparen muss bausparenArt belegt werden
			numbers[5] = numbers[2];
			numbers[2] = -1;
			numbers[3] = -1;
			numbers[4] = -1;
		} else {
			numbers[4] = numbers[3];
			numbers[3] = -1;
			numbers[5] = -1;
		}
		return numbers;
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
	private GdvSatzTyp(short satzart, short sparte, short wagnisart, short krankenFolgeNr, short lfdNummer, short bausparenArt) {
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
		this.wagnisart = ((satzart == 220) && (sparte == 10) && (wagnisart < 0)) ? 0 : wagnisart;
		this.krankenFolgeNr = krankenFolgeNr;
		this.teildatensatzNummer = ((wagnisart > 0) && (lfdNummer < 0) && (sparte == 10)) ? 1 :  lfdNummer;
		this.bausparenArt = bausparenArt;
	}

	/**
	 * Gets the satzart.
	 *
	 * @return the satzart
	 */
	public int getSatzart() {
		return n[0];
	}

	/**
	 * Gets the sparte.
	 *
	 * @return the sparte
	 */
	public int getSparte() {
		return n[1];
	}

  /**
   * Liefert die Sparte als String.
   *
   * @return z.B. "030"
   * @since 5.0
   */
  public String getSparteAsString()
  {
    return Integer.toString(this.getSparte());
  }

  /**
   * Gets the wagnisart.
   *
   * @return the wagnisart
   */
  public int getWagnisart()   {
    return this.wagnisart;
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

	/**
	 * Liefert die BausparenArt zurueck. Dies ist bei SatzTyp "0220.580.01" der letzte
	 * Teil ("01"). Diese Methode macht nur bei den Satz-Typen
	 * "0220.580.01" und "0220.580.2" Sinn.
	 *
	 * @return z.B. 1 bei SatzTyp "0220.580.01"
	 * @since 5.0
	 */
	public int getBausparenArt() {
		return this.bausparenArt;
	}

	/**
	 * Liefert die BausparenArt zurueck. Dies ist bei SatzTyp "0220.580.01" der letzte
	 * Teil ("01"). Diese Methode macht nur bei den Satz-Typen
	 * "0220.580.01" und "0220.580.2" Sinn.
	 *
	 * @return z.B. "01" bei SatzTyp "0220.580.01"
	 * @since 5.0
	 */
	public String getBausparenArtAsString() {
		if (this.getBausparenArt() < 0) {
			return "";
		}
		if (this.getBausparenArt() == 1) {
			return "01";
		} else {
			return Integer.toString(this.getBausparenArt());
		}
	}

	/**
	 * Liefert die Wagnisart, BausparenArt oder KrankenFolgeNr zurueck.
	 * Dies ist der dritte Teil nach der Sparte, als z.B. die 0 bei
	 * SatzTyp.of("0220.010.0").
	 *
	 * @return z.B. 1 bei SatzTyp "0220.580.01"
	 * @since 5.0
	 */
	public int getArt() {
		if (this.getSparte() == 10) {
			switch (this.getWagnisart()) {
				case 1:
				case 3:
					return 13;
				case 4:
				case 8:
					return 48;
				default:
					return this.getWagnisart();
			}
		} else if (this.getSparte() == 20) {
			return this.getKrankenFolgeNr();
		} else if (this.getSparte() == 580) {
			return this.getBausparenArt();
		}
		return -1;
	}

	/**
	 * Liefert die Wagnisart, BausparenArt oder KrankenFolgeNr als String
	 * zurueck. Dies ist der dritte Teil nach der Sparte, als z.B. die
	 * "0" bei SatzTyp.of("0220.010.0").
	 *
	 * @return z.B. "01" bei SatzTyp "0220.580.01"
	 * @since 5.0
	 */
	public String getArtAsString() {
		if (this.getBausparenArt() == 1) {
			return "01";
		} else {
			return Integer.toString(this.getArt());
		}
	}

	/**
	 * Liefert true oder false zurueck, je nachdem, ob der SatzTyp eine Art
	 * hat. Dies ist z.B. bei den Satz-Typen 0220.580.01" und "0220.580.2"
	 * der Fall.
	 *
	 * @return true oder false
	 * @since 5.0
	 */
	public boolean hasArt() {
		return (this.getWagnisart() >= 0) || (this.getBausparenArt() >= 0) || (this.getKrankenFolgeNr() >= 0);
	}
	
	/**
	 * Gets the krankenFolgeNr.
	 * 
	 * @return the krankenFolgeNr
	 */
	public int getKrankenFolgeNr() {
	    return this.krankenFolgeNr;
	}

	/**
	 * Dies ist die laufende Nummer bei der Wagnisart.
	 *
	 * @return the lfd nummer
	 */
	public int getTeildatensatzNummer() {
		return this.teildatensatzNummer;
	}

	/**
	 * Liefert true zurueck, wenn die Sparte gesetzt ist.
	 *
	 * @return true, if successful
	 */
	public boolean hasSparte() {
		return n.length > 1 && n[1] > 0;
	}

	/**
	 * Liefert true zurueck, wenn die Wagnisart gesetzt ist.
	 *
	 * @return true, if successful
	 */
	public boolean hasWagnisart() {
		return this.getWagnisart() >= 0;
	}
	
	/**
	 * Liefert true zurueck, wenn die Folge-Nr in Sparte 20, Satzart 220 gesetzt ist.
	 * 
	 * @return true, if successful
	 */
	public boolean hasKrankenFolgeNr() {
	    return this.getKrankenFolgeNr() >= 0;
	}

    /**
     * Liefert true zurueck, wenn die Bausparen-Artin Sparte 580, Satzart 220
     * gesetzt ist.
     * 
     * @return true, if successful
     */
    public boolean hasBausparenArt()
    {
      return this.getBausparenArt() >= 0;
    }

    /**
     * Liefert true zurueck, wenn die laufende Nummer (fuer Wagnisart) gesetzt
     * ist.
     *
     * @return true, if successful
     */
    public boolean hasTeildatensatzNummer()  {
      return this.getTeildatensatzNummer() >= 0;
    }

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GdvSatzTyp)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("%04d", this.getSatzart()));
		if (this.hasSparte()) {
			buf.append(String.format(".%03d", this.getSparte()));
			if (this.hasArt()) {
				buf.append(".");
				buf.append(this.getArtAsString());
				if (this.getTeildatensatzNummer() >= 0) {
					buf.append(".");
					buf.append(this.getTeildatensatzNummer());
				}
			}
		}
		return buf.toString();
	}

}
