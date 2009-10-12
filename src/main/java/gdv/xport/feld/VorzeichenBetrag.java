/*
 * Copyright (c) 2009 by agentes
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
 * (c)reated 11.10.2009 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.feld;

/**
 * Im Gegensatz zum Betrag hat diese Klasse ein Vorzeichen ('+' oder '-').
 * 
 * @author oliver
 * @since 11.10.2009
 * @version $Revision$
 */
public class VorzeichenBetrag extends Betrag {

	/**
	 * @param length das Vorzeichen muss dabei mitgezaehlt werden
	 * @param start
	 */
	public VorzeichenBetrag(int length, int start) {
		super(length, start);
		this.setInhalt('+', length-1);
	}
	
}

