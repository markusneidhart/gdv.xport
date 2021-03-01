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
 * (c)reated 01.03.21 by oliver (ob@oasd.de)
 */
package gdv.xport.core;

/**
 * Class GdvXmlService.
 *
 * @author <a href="ob@aosd.de">oliver</a>
 */
public class GdvXmlService {

    /**
     * Liefert den Satz zur gewuenschten Satzart.
     *
     * @param satzNr z.B. SatzTyp.of("0100") fuer Satz100 (Adressteil)
     * @return die entsprechende Satzart
     */
    public GdvSatz getSatzart(final GdvSatzTyp satzNr) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
