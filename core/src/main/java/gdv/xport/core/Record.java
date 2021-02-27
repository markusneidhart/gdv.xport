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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 21.02.21 by oliver (ob@oasd.de)
 */
package gdv.xport.core;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * Ein Record ist eine Sammlung von Feldern.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public interface Record {

    /**
     * Fuegt das uebergebene Feld zum Record hinzu.
     *
     * @param feld das Feld
     */
    void add(GdvFeld feld);

    /**
     * Liefert das Feld mit der angegebenen Byte-Adresse. Im Gegensatz zum
     * Index aendert sich die Adresse nicht, wenn neue Elemente in einen
     * Record hinzukommen.
     *
     * @param adresse zwischen 1 und 256
     * @return das entsprechende Feld
     */
    GdvFeld getFeld(ByteAdresse adresse);

    /**
     * Liefert das Feld mit der gewuenschten Nummer zurueck.
     *
     * @param nr z.B. 1
     * @return das Feld (z.B. mit der Satzart)
     */
    GdvFeld getFeld(final int nr);

    /**
     * Liefert das Feld mit der angegebenen Bezeichner.
     *
     * @param bezeichner der Feld-Bezeichner
     * @return das entsprechende Feld
     */
    GdvFeld getFeld(GdvBezeichner bezeichner);

    /**
     * Falls ein Feld zuviel gesetzt wurde, kann es mit 'remove" wieder entfernt
     * werden.
     *
     * @param bezeichner der Feld-Bezeichner
     */
    void remove(GdvBezeichner bezeichner);

    /**
     * Fraegt ab, ob das entsprechende Feld vorhanden ist.
     *
     * @param bezeichner gewuenschter Bezeichner des Feldes
     * @return true / false
     */
    boolean hasFeld(final GdvBezeichner bezeichner);

    /**
     * Liefert alle Felder eines Teildatensatzes zurueck.
     *
     * @return Liste der Felder
     */
    Collection<? extends GdvFeld> getFelder();

    /**
     * Falls ein Feld zuviel gesetzt wurde, kann es mit 'remove" wieder
     * entfernt werden.
     *
     * @param feld das Feld, das entfernt werden soll
     */
    void remove(GdvFeld feld);

    /**
     * Exportiert den Record.
     *
     * @param writer the writer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void export(Writer writer) throws IOException;

}
