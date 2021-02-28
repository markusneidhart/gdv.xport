/*
 * Copyright (c) 2009-2020 by Oli B.
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
 * (c)reated 09.10.2009 by Oli B. (ob@aosd.de)
 */
package gdv.xport.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Collection;

/**
 * Die Satz-Klasse ist die oberste Klasse, von der alle weiteren Saetze
 * abgeleitet sind.
 *
 * @author oliver
 */
public class GdvSatz implements Record {

	private static final Logger LOG = LogManager.getLogger();
	private final GdvSatzTyp satzTyp;

	public GdvSatz(GdvSatzTyp art) {
		this.satzTyp = art;
	}

	/**
	 * Falls ein Feld zuviel gesetzt wurde, kann es mit 'remove" wieder
	 * entfernt werden.
	 *
	 * @param feld das Feld, das entfernt werden soll
	 */
	@Override
	public void remove(GdvFeld feld) {
		remove(feld.getBezeichner());
	}

	/**
	 * Exportiert den Record.
	 *
	 * @param writer the writer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void export(Writer writer) throws IOException {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public GdvFeld getFeld(GdvBezeichner bezeichner) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	/**
	 * Falls ein Feld zuviel gesetzt wurde, kann es mit 'remove" wieder entfernt
	 * werden.
	 *
	 * @param bezeichner der Feld-Bezeichner
	 */
	@Override
	public void remove(GdvBezeichner bezeichner) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	/**
	 * Fraegt ab, ob das entsprechende Feld vorhanden ist.
	 *
	 * @param bezeichner gewuenschter Bezeichner des Feldes
	 * @return true / false
	 */
	@Override
	public boolean hasFeld(GdvBezeichner bezeichner) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	/**
	 * Liefert alle Felder eines Teildatensatzes zurueck.
	 *
	 * @return Liste der Felder
	 */
	@Override
	public Collection<? extends GdvFeld> getFelder() {
		throw new UnsupportedOperationException("not yet implemented");
	}

	/**
	 * Fuegt das uebergebene Feld zum Record hinzu.
	 *
	 * @param feld das Feld
	 */
	@Override
	public void add(GdvFeld feld) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public GdvFeld getFeld(ByteAdresse adresse) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public GdvFeld getFeld(int nr) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	/**
	 * Liefert die Satzart zurueck.
	 *
	 * @return die Satzart als int
	 */
	public int getSatzart() {
		return this.satzTyp.getSatzart();
	}

	/**
	 * Liefert den Satz-Typ zurueck. Der Satz-Typ ist eine Zusammenfassung aus
	 * Satzart und Sparte.
	 *
	 * @return den Satz-Typ
	 * @since 1.0
	 */
	@JsonIgnore
	public GdvSatzTyp getSatzTyp() {
		return this.satzTyp;
	}

	/**
	 * Exportiert den Satz.
	 *
	 * @param file Datei
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void export(final File file) throws IOException {
		try (Writer writer = new FileWriter(file)) {
			this.export(writer);
		}
	}

	/**
	 * @param ostream z.B. System.out
	 * @throws IOException falls mal was schief geht
	 * @since 0.3
	 */
	public void export(final OutputStream ostream) throws IOException {
		Writer writer = new OutputStreamWriter(ostream);
		export(writer);
		writer.flush();
		ostream.flush();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
            return this.toShortString() + " (" + StringUtils.abbreviate(this.toLongString(), 47) + ")";
		} catch (RuntimeException shouldNeverHappen) {
			LOG.error("shit happens in toString()", shouldNeverHappen);
			return super.toString();
		}
	}

	/**
	 * To short string.
	 *
	 * @return the string
	 */
	public String toShortString() {
        return "Satzart " + this.satzTyp;
	}

	/**
	 * To long string.
	 *
	 * @return the string
	 */
	public String toLongString() {
		StringWriter swriter = new StringWriter();
		try {
			this.export(swriter);
		} catch (IOException canthappen) {
			LOG.warn(canthappen + " ignored", canthappen);
			swriter.write(canthappen.getLocalizedMessage());
		}
		return swriter.toString();
	}

	/**
	 * Zwei Saetze sind gleich, wenn sie die gleichen Daten besitzen. Die
	 * Idee dabei ist, dass wir die beiden Saetze exportieren und dann das
	 * Resultat vergleichen.
	 *
	 * @param obj der andere Satz
	 * @return true, wenn beide Saetze gleich sind
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(final Object obj) {
	    if (!(obj instanceof GdvSatz)) {
	        return false;
	    }
		GdvSatz other = (GdvSatz) obj;
        return this.toLongString().equals(other.toLongString());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getSatzTyp().hashCode();
	}

}
