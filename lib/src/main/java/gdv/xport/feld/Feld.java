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
 * (c)reated 04.10.2009 by Oli B. (ob@aosd.de)
 */

package gdv.xport.feld;

import gdv.xport.annotation.FeldInfo;
import gdv.xport.config.Config;
import gdv.xport.core.ByteAdresse;
import gdv.xport.core.GdvBezeichner;
import gdv.xport.core.GdvFeld;
import gdv.xport.satz.feld.FeldX;
import gdv.xport.util.SimpleConstraintViolation;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.NotEqual;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Die Feld-Klasse bezieht ihre Information hauptsaechlich aus Enum-Klassen wie
 * Feld100 oder Feld1bis7, die mit Annotationen versehen sind.
 *
 * @author oliver
 * @since 04.10.2009
 */
public class Feld extends GdvFeld implements Cloneable {

    private static final Logger LOG = LogManager.getLogger();
    /** statt "null". */
    public static final Feld NULL_FELD = new Feld();
    /** Ausrichtung: rechts- oder linksbuendig. */
    @NotEqual("UNKNOWN")
    private final Align ausrichtung;

    /**
     * Legt ein neues Feld an. Dieser Default-Konstruktor ist fuer Unterklassen
     * vorgesehen. Da er aber auch fuer die {@link Cloneable}-Implementierung
     * benoetigt wird, ist er 'public'.
     *
     * @since 1.0
     */
    public Feld() {
        this(FeldX.UNBEKANNT);
    }

    /**
     * Legt ein neues Feld an. Die Informationen dazu werden aus der
     * uebergebenen Enum bezogen.
     *
     * @param feldX Enum mit den Feldinformationen
     * @since 0.9
     */
    public Feld(final Enum feldX) {
        this(feldX, getFeldInfo(feldX));
    }

    /**
     * Kreiert ein neues Feld.
     *
     * @param feldX der entsprechende Aufzaehlungstyp
     * @param info Annotation mit den Feldinformationen
     * @since 0.6
     */
    public Feld(final Enum feldX, final FeldInfo info) {
        super(Feld.getAsBezeichner(feldX), ByteAdresse.of(info.byteAdresse()), info.anzahlBytes());
        this.ausrichtung = getAlignmentFrom(info);
        this.setInhalt(info.value());
    }

    /**
     * Instantiates a new feld.
     *
     * @param name
     *            the name
     * @param s
     *            the s
     * @param alignment
     *            the alignment
     */
    public Feld(final String name, final String s, final Align alignment) {
        this(new Bezeichner(name), 1, s, alignment);
    }

    /**
     * Instantiates a new feld.
     *
     * @param name the name
     * @param start Start-Adresse
     * @param s der Inhalt
     * @param alignment the alignment
     */
    public Feld(final Bezeichner name, final int start, final String s, final Align alignment) {
        super(name, ByteAdresse.of(start), s);
        this.ausrichtung = alignment;
    }

    /**
     * Erzeugt ein neues Feld.
     *
     * @param bezeichner der Name des Felds
     * @param length die Anzahl der Bytes
     * @param start die Start-Adresse
     * @param alignment die Ausrichtung
     * @since 1.0
     */
    public Feld(final Bezeichner bezeichner, final int length, final int start, final Align alignment) {
        super(bezeichner, ByteAdresse.of(start), length);
        this.ausrichtung = alignment;
    }

    /**
     * Instantiates a new feld.
     *
     * @param name
     *            the name
     * @param length
     *            the length
     * @param start
     *            the start
     * @param c
     *            the c
     * @param alignment
     *            the alignment
     */
    public Feld(final String name, final int length, final int start, final char c, final Align alignment) {
        this(new Bezeichner(name), length, start, alignment);
        this.setInhalt(c);
    }

    /**
     * Instantiates a new feld.
     *
     * @param name
     *            the name
     * @param length
     *            the length
     * @param start
     *            the start
     * @param s
     *            the s
     * @param alignment
     *            the alignment
     */
    public Feld(final String name, final int length, final int start, final String s, final Align alignment) {
        this(new Bezeichner(name), length, start, alignment);
        this.setInhalt(s);
    }

    /**
     * Instantiates a new feld.
     *
     * @param name
     *            the name
     * @param start
     *            the start
     * @param c
     *            the c
     */
    public Feld(final String name, final int start, final char c) {
        this(name, 1, start, c, Align.LEFT);
    }

    /**
     * Instantiates a new feld.
     *
     * @param start
     *            the start
     * @param s
     *            the s
     * @param alignment
     *            the alignment
     */
    public Feld(final int start, final String s, final Align alignment) {
        super(createBezeichner(), ByteAdresse.of(start), s);
        this.ausrichtung = alignment;
    }

    /**
     * Instantiates a new feld.
     *
     * @param length
     *            the length
     * @param alignment
     *            the alignment
     */
    public Feld(final int length, final Align alignment) {
        this(length, 1, alignment);
    }

    /**
     * Instantiates a new feld.
     *
     * @param length
     *            the length
     * @param start
     *            the start
     * @param alignment
     *            the alignment
     */
    public Feld(final int length, final int start, final Align alignment) {
        super(createBezeichner(), ByteAdresse.of(start), length);
        this.ausrichtung = alignment;
    }

    /**
     * Dies ist der Copy-Constructor, mit dem man ein bestehendes Feld
     * kopieren kann.
     *
     * @param other das originale Feld
     */
    public Feld(final Feld other) {
        this(other.getBezeichner(), other.getAnzahlBytes(), other.getByteAdresse(), other.ausrichtung);
        this.setInhalt(other.getInhalt());
    }

    /**
     * Die Default-Ausrichtung ist links-buendig. Diese Vorgabe kann aber von den Unterklassen ueberschrieben werde.
     *
     * @return links-buendig
     */
    protected Align getDefaultAlignment() {
        return Align.LEFT;
    }

    private Align getAlignmentFrom(final FeldInfo info) {
        if (info.align() == Align.UNKNOWN) {
            return this.getDefaultAlignment();
        }
        return info.align();
    }

    private static Bezeichner createBezeichner() {
        return new Bezeichner("Feld@" + Long.toHexString(System.currentTimeMillis()));
    }

    /**
     * Legt das gewuenschte Feld an, das sich aus der uebergebenen Annotation
     * ergibt (Factory-Methode). Der Name wird dabei aus dem uebergebenen
     * Enum-Feld abgeleitet.
     *
     * @param feldX Enum fuer das erzeugte Feld
     * @param info die FeldInfo-Annotation mit dem gewuenschten Datentyp
     * @return das erzeugte Feld
     */
    public static Feld createFeld(final Enum feldX, final FeldInfo info) {
        try {
            Constructor<? extends Feld> ctor = info.type().getConstructor(Enum.class, FeldInfo.class);
            return ctor.newInstance(feldX, info);
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException("no constructor " + info.type().getSimpleName()
                    + "(String, FeldInfo) found", ex);
        } catch (InstantiationException ex) {
            throw new IllegalArgumentException("can't instantiate " + info.type(), ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("can't access ctor for " + info.type(), ex);
        } catch (InvocationTargetException ex) {
            throw new IllegalArgumentException("error invoking ctor for " + info.type() + " ("
                    + ex.getTargetException() + ")", ex);
        }
    }

    /**
     * Liefert den Bezeichner eines Feldes zurueck.
     *
     * @return den Bezeichner des Feldes
     * @since 1.0
     */
    @Override
    public Bezeichner getBezeichner() {
        return toBezeichner(super.getBezeichner());
    }

    private static Bezeichner toBezeichner(GdvBezeichner x) {
        return new Bezeichner(x.getName(), x.getTechnischerName());
    }

    /**
     * Sets the inhalt.
     *
     * @param s
     *            the new inhalt
     */
    public final void setInhalt(final String s) {
        int anzahlBytes = this.getAnzahlBytes();
        if (s.length() > anzahlBytes) {
            throw new IllegalArgumentException("Feld " + this.getBezeichner() + ": Parameter \"" + s
                    + "\" ist laenger als " + anzahlBytes + " Zeichen!");
        }
        this.resetInhalt();
        switch (this.ausrichtung) {
            case LEFT:
                super.setInhalt(StringUtils.overlay(getInhalt(), s, 0, s.length()));
                break;
            case RIGHT:
                int l = s.length();
                int start = anzahlBytes - l;
                super.setInhalt(StringUtils.overlay(getInhalt(), s, start, anzahlBytes));
                break;
            default:
                throw new IllegalStateException("object was not properly initialized");
        }
    }

    /**
     * Setzt das Feld und liefert es als Ergebnis zurueck.
     *
     * @param inhalt neuer Inhalt
     * @return das gesetzte Feld
     * @since 5.0
     */
    public Feld withInhalt(String inhalt) {
        this.setInhalt(inhalt);
        return this;
    }

    /**
     * Write.
     *
     * @param writer the writer
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final void write(final Writer writer) throws IOException {
        writer.write(this.getInhalt());
    }

    /**
     * Valid bedeutet: Byte-Adresse &gt;= 1, Feld geht nicht ueber
     * (Teil-)Datensatz-Grenze hinaus, Ausrichtung ist bekannt.
     * <p>
     * Aus Performance-Gruenden stuetzt sich diese Methode nicht direkt auf 
     * validate(), sondern implementiert die entsprechenden Abfragen selbst
     * und bricht ab, wenn er etwas ungueltiges findet.
     * </p>
     *
     * @return false, falls Verletzung erkannt wird
     * @since 0.1.0
     */
    public boolean isValid() {
        if (this.ausrichtung == Align.UNKNOWN) {
            return false;
        }
        return this.validate().isEmpty();
    }

    /**
     * Checks if is invalid.
     *
     * @return true, if is invalid
     */
    public boolean isInvalid() {
        return !isValid();
    }

    /**
     * Validate.
     *
     * @return eine Liste mit Constraint-Verletzungen
     */
    public List<ConstraintViolation> validate() {
        Validator validator = new Validator();
        List<ConstraintViolation> violations = validator.validate(this);
        if (this.getEndAdresse() > 256) {
            ConstraintViolation cv = new SimpleConstraintViolation(this + ": boundary exceeded", this,
                    this.getEndAdresse());
            violations.add(cv);
        }
        return violations;
    }

    /**
     * Diese Methode ist dafuer vorgesehen, das Feld als normalen String ausgeben zu koennen. Zahlen koennen so z.B. in
     * der Form "123,45" ausgegeben werden, unter Beruecksichtigung der eingestellten "Locale".
     *
     * @return Inhalt des Feldes
     * @since 0.5.1
     */
    public String format() {
        return this.getInhalt();
    }

    /**
     * Liefert die uebergebene Enum-Konstante als Bezeichner zurueck. Dazu
     * verwendet es die {@link Bezeichner}-Klasse, um festzustellen, ob es den
     * Namen schon als Konstante dort gibt.
     *
     * @param feldX die Enum-Konstante
     * @return den Bezeichner
     * @since 1.0
     */
    public static Bezeichner getAsBezeichner(final Enum feldX) {
        Object object = getAsObject(feldX);
        if (object instanceof Bezeichner) {
            return (Bezeichner) object;
        }
        return new Bezeichner((String) object);
    }

    /**
     * Liefert den Namen als Bezeichner zurueck. Dazu verwendet es die
     * {@link Bezeichner}-Klasse, um festzustellen, ob es den Namen schon als
     * Bezeichner gibt. Falls nicht, wird der Name zurueckgeliefert.
     *
     * @param feldX das Feld-Element mit dem gesuchten Bezeichner
     * @return z.B. "Inkassoart"
     */
    public static String getAsBezeichnung(final Enum feldX) {
        return getAsObject(feldX).toString();
    }

    private static Object getAsObject(final Enum feldX) {
        try {
            Field field = Bezeichner.class.getField(feldX.name());
            return field.get(null);
        } catch (NoSuchFieldException ex) {
            LOG.info("Bezeichner.{} not found:", feldX.name());
            LOG.debug("Details:", ex);
        } catch (IllegalArgumentException ex) {
            LOG.warn("Can't get {} as object.", feldX, ex);
        } catch (IllegalAccessException ex) {
            LOG.warn("Can't access Bezeichner.{}:", feldX.name(), ex);
        }
        return toBezeichnung(feldX);
    }

    /**
     * Konvertiert einen Bezeichner (in GROSSBUCHSTABEN) in die entsprechende Bezeichnung.
     *
     * @param name
     *            z.B. HELLO_WORLD (als Aufzaehlungstyp)
     * @return z.B. "Hello World"
     */
    public static String toBezeichnung(final Enum name) {
        FeldInfo feldInfo = getFeldInfo(name);
        if ((feldInfo == null) || StringUtils.isEmpty(feldInfo.bezeichnung())) {
            return toBezeichnung(name.name());
        } else {
            return feldInfo.bezeichnung();
        }
    }

    private static String toBezeichnung(final String name) {
        String converted = name.replaceAll("_", " ");
        ByteBuffer outputBuffer = Config.DEFAULT_ENCODING.encode(converted);
        String convertedISO = new String(outputBuffer.array(), Config.DEFAULT_ENCODING);
        return WordUtils.capitalize(convertedISO.toLowerCase());
    }

    /**
     * Ermittelt die FeldInfo aus dem uebergebenen Enum.
     *
     * @param feldX the feld x
     * @return the feld info
     */
    protected static FeldInfo getFeldInfo(final Enum feldX) {
        try {
            Field field = feldX.getClass().getField(feldX.name());
            return field.getAnnotation(FeldInfo.class);
        } catch (NoSuchFieldException nsfe) {
            throw new InternalError("no field " + feldX + " (" + nsfe + ")");
        }
    }

    /**
     * Die clone-Methode hat gegenueber dem CopyConstructor
     * {@link Feld#Feld(Feld)} den Vorteil, dass es den richtigen Typ fuer die
     * abgeleiteten Klassen zurueckliefert.
     *
     * @return eine Kopie
     */
    @SuppressWarnings("squid:S2975")
    @Override
    public Object clone() {
        return new Feld(this);
    }

}
