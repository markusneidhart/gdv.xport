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
 * (c)reated 08.02.2021 by oboehm
 */
package gdv.xport.core;

import de.jfachwert.Text;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Diese Klasse repraesentiert einen Bezeichner in der GDV-Beschreibung. Sie
 * enthaelt neben dem Namen auch den technischen Namen.
 *
 * @author oliver
 * @since 5.0 (08.01.21)
 */
public class GdvBezeichner {

    private final String name;
    private final String technischerName;

    public GdvBezeichner(String name) {
        this(name, toTechnischerName(name));
    }

    public GdvBezeichner(String name, String technischerName) {
        this.name = name;
        this.technischerName = technischerName;
    }

    private static String toTechnischerName(String input) {
        StringBuilder buf = new StringBuilder();
        String[] words = input.split(" ");
        for (String word : words) {
            buf.append(GdvBezeichner.toShortcut(word));
        }
        return buf.toString();
    }

    private static String toShortcut(final String input) {
        StringBuilder converted = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char ch : chars) {
            appendLetterOrDigitOrProzent(converted, ch);
        }
        String word = converted.toString();
        switch (word) {
            case "fuer":
                return "";
            case "Nummer":
                return "Nr";
            case "Gesamtbeitrag":
                return "Gesbeitrag";
            case "VN":
                return "Vn";
            case "VP":
                return "Vp";
            case "VS":
                return "Vs";
            case "Waehrungseinheiten":
                return "WE";
            default:
                if ((word.length() == 3) && (word.toLowerCase().charAt(0) == 'd') && (word.charAt(2) != 'n')) {
                    return "";
                } else if (word.endsWith("datum")) {
                    return word.substring(0, word.length() - 2);
                } else if (word.toLowerCase().endsWith("versicherung")) {
                    String versicherung = WordUtils.capitalize(word);
                    return versicherung.substring(0, versicherung.length() - 12) + "Vers";
                } else if (word.startsWith("eVB")) {
                    return "eVB" + WordUtils.capitalize(word.substring(3));
                } else if (word.startsWith("KFT")) {
                    return "Kft" + WordUtils.capitalize(word.substring(3));
                } else if (word.startsWith("KFV")) {
                    return "Kfv" + WordUtils.capitalize(word.substring(3));
                } else if (word.startsWith("KH")) {
                    return "Kh" + WordUtils.capitalize(word.substring(2));
                }
                return WordUtils.capitalize(word);
        }
    }

    private static void appendLetterOrDigitOrProzent(StringBuilder converted, char aChar) {
        if (Character.isLetterOrDigit(aChar)) {
            String s = Text.replaceUmlaute(Character.toString(aChar));
            converted.append(s);
        } else if (aChar == '%') {
            converted.append("Proz");
        }
    }

    /**
     * Liefert den Namen des Bezeichners.
     *
     * @return der Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Der technische Name leitet sich aus dem normalen Namen ab. Im
     * Gegensatz zum normalen Namen enthaelt er aber keine Leerzeichen,
     * Sonderzeichen oder Umlaute.
     * <p>
     * Der technische Name wird auch dazu verwendet, um zwei {@link GdvBezeichner}
     * auf Gleichheit zu testen.
     * </p>
     *
     * @return der technische Name
     */
    public String getTechnischerName() {
        return this.technischerName;
    }

    /**
     * Zum Vergleich zweier {@link GdvBezeichner} wird der technische Name
     * herangezogen.
     *
     * @param obj der andere Bezeichner
     * @return true, wenn er als gleich angesehen wird
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof GdvBezeichner)) {
            return false;
        }
        GdvBezeichner other = (GdvBezeichner) obj;
        return this.getTechnischerName().equalsIgnoreCase(other.getTechnischerName());
    }

    @Override
    public int hashCode() {
        return this.getTechnischerName().toUpperCase().hashCode();
    }

    /**
     * Da der Bezeichner als Ersatz fuer die String-Klasse eingesetzt werden soll,
     * liefern wir den (technischen) Namen hier zurueck.
     *
     * @return den Namen
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return this.technischerName;
    }

}
